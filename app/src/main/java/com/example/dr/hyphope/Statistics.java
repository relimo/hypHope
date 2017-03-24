package com.example.dr.hyphope;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by d&r on 21/03/2017.
 */

public class Statistics {
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
}
