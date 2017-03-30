package com.example.dr.hyphope;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by d&r on 21/03/2017.
 */

public class Statistics {
    public SharedPreferences sp;
    public SharedPreferences .Editor editor;
    private DalDynamic dalDynamic;
    private Context context;
    private Calendar today;
    private static  final int DAYS_DEVIATION=3;//how many days ago to check if there was a deviation


    public Statistics(Context context){
        this.context=context;
        dalDynamic=new DalDynamic(context);


    }

    /**
     * this method checks some days ago (according to the final :DAYS_DEVIATION)
     * if there were consecutive deviations in all the last days
     */

    public void toPunish() {

        //// TODO: 24/03/2017  delete the following lines
//        dalDynamic.addRowToTable2("23/03/2017","Thursday",4,walkingLength,deviation)



        int dev,sum=0;
        long id;
        id=dalDynamic.getRecordIdAccordingToRecordName("minutesWalkTillEvening");
        Calendar now = Calendar.getInstance();//today
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
        String date;
        for (int i = 0; i <DAYS_DEVIATION ; i++) {
            date=dateFormat.format(calendar.getTime());
            Log.v("Statistic","date "+date);
            dev = dalDynamic.getDeviationAccordingToeventTypeIdAnddate(id,date);
            sum+=dev;
            calendar.add(Calendar.DATE,-1);//// TODO: -1-i????
        }
        if(sum==DAYS_DEVIATION){
            Intent intent = new Intent(context,BlankActivity.class);
            context.startActivity(intent);
            //todo:punishment
        }

    }



//

    /**
     *
     * @param timeString in format hh:mm
     * @return that time as doubled value (for examle: if gets 08:30 returns 8.5)
     */
    private float getTime(String timeString){
        String []splitArr=timeString.split(":");
        double hour=Double.parseDouble(splitArr[0]);
        double minutes=Double.parseDouble(splitArr[1]);
        double time=hour+minutes/60;
        Log.v("time","time in string "+timeString+" time in double "+time);
        return (float) time;

    }
    /**
     * this method gets the wake up time, calc the statistics and the time to check wake up
     * @param wakeUpTime is the hour of wake up
     *
     */
    private void calculatingWakeUp(String wakeUpTime){
        final float MINUTES_PERCENTS=60/100;
        final float TOLLERANCE_WAKING=MINUTES_PERCENTS*50;

        sp = context.getSharedPreferences("pref_avg_sleep", Context.MODE_PRIVATE);
        float timeWakeUp=getTime(wakeUpTime);
        double avgTillToday=sp.getFloat("avg",timeWakeUp);
        int learnedDaysTillToday=sp.getInt("days", 0);
        Log.v("statistics: days",learnedDaysTillToday+"");
        Log.v("statistics: avg",avgTillToday+"");
        Log.v("statistics: waking",timeWakeUp+"");
        int isDeviation=0;

        //calc th e statistics and update yhe sp
        //update the days (add +1)11
        editor.putInt("days",learnedDaysTillToday+1);
        //update the avg
        float newAvg=(float) (avgTillToday*(learnedDaysTillToday)+timeWakeUp)/(learnedDaysTillToday+1);
        editor.putFloat("avg",newAvg);
        Log.v("statistics","end");

//        // TODO: variance

        //calc the time to wake him up tomorrow if he did not wake up
        float wakeUpTomorrow=newAvg*TOLLERANCE_WAKING;
        editor.putFloat("wakeUpTime",wakeUpTomorrow);
        editor.apply();

    }


}
