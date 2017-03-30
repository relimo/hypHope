package com.example.dr.hyphope;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import android.util.Log;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.internal.http.Request;
//import com.squareup.okhttp.internal.http.Response;
public class DataActivity extends AppCompatActivity {
    private Button btnGetData;//this button get data about specific date
    private Button btnWriteToFile;
    private Button btnSleepData;
    private Button btnWakeUp;


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private DalDynamic dalDynamic;

    public SharedPreferences sp;
    public SharedPreferences .Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        sp = getSharedPreferences("pref_avg", MODE_PRIVATE);
        editor = sp.edit();
        editor.putInt("days", 5);
        editor.putFloat("avg",18);
        editor.apply();


        dalDynamic=new DalDynamic(this);
        dalDynamic.addRowToTable1("sleepLength");
        dalDynamic.addRowToTable1("wakeUpTime");
        dalDynamic.addRowToTable1("minutesWalk");
        dalDynamic.addRowToTable1("minutesWalkTillEvening");//

        btnWriteToFile=(Button)findViewById(R.id.btnWriteToFile);
        btnWriteToFile.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkExternalMedia();
                writeToFile_first_table();
                writeToFile();
            }
        });

        btnSleepData=(Button)findViewById(R.id.btnSleep);
        btnSleepData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//func to insert data sleep
                Log.v("data activity","on sleep click");
                alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                //Create pending intent & register it to your alarm notifier class
                // Intent intent = new Intent(getApplicationContext(), AlarmReceiveWakeUp.class);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiverDataSleep.class);
//                intent.putExtra("uur", "1e"); // if you want
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                //set timer you want alarm to work
                //in 01:00 put the data sleep in DB of yesterday
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 01);
                calendar.set(Calendar.MINUTE,00);
                calendar.set(Calendar.SECOND, 0);

                //set that timer as a RTC to alarm manager object
                alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);


            }
        });

        btnWakeUp=(Button)findViewById(R.id.btnWake);
        btnWakeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//func of wakimg up
                Log.v("data activity","on wake up click");
                alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                //Create pending intent & register it to your alarm notifier class
                // Intent intent = new Intent(getApplicationContext(), AlarmReceiveWakeUp.class);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiveWakeUp.class);
//                intent.putExtra("uur", "1e"); // if you want
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                //set timer you want alarm to work
                //get the time from sp to know when to check if the user wake up
                sp = getSharedPreferences("pref_avg_sleep", Context.MODE_PRIVATE);
                float time=sp.getFloat("wakeUpTime",7);
                int []arr=gethour_Minute(time);
                int hour,minute;
                hour=arr[0];
                minute=arr[1];
                //put this time in the alarm
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE,minute);
                calendar.set(Calendar.SECOND, 0);

                //set that timer as a RTC to alarm manager object
                alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);



            }
        });

        btnGetData=(Button)findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("data activity","on click");
                alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                //Create pending intent & register it to your alarm notifier class
               // Intent intent = new Intent(getApplicationContext(), AlarmReceiveWakeUp.class);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiverDataWalking.class);
//                intent.putExtra("uur", "1e"); // if you want
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                //set timer you want alarm to work
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 14);
                calendar.set(Calendar.MINUTE,40);
                calendar.set(Calendar.SECOND, 0);

                //set that timer as a RTC to alarm manager object
                alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);


            }
        });


    }
    private void checkExternalMedia(){
        boolean mExternalStorageAvailable = false;
        boolean mExternalStorageWriteable = false;
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            // Can read and write the media
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            // Can only read the media
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            // Can't read or write
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
        Log.v("check external storage","\n\nExternal Media: readable="
                +mExternalStorageAvailable+" writable="+mExternalStorageWriteable);
    }
    private void writeToFile(){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

        File root = android.os.Environment.getExternalStorageDirectory();

        Log.v("check external storage","\nExternal file system root:    !!! "+root);



        File dir = new File (root.getAbsolutePath() + "/download");
//        File dir = new File (root.getAbsolutePath());

        dir.mkdirs();
        File file = new File(dir, "records.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            Log.v("try1","after f");
            PrintWriter pw = new PrintWriter(f);

            ArrayList<Record> list = dalDynamic.getRecordsList();
            Log.v("try2","after dal");
            for (Record i : list)
                pw.println(i.toString());
            pw.flush();
            pw.close();
            f.close();
            Log.i("write to file","\n\nFile written to "+file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("write to file", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void writeToFile_first_table(){

        File root = android.os.Environment.getExternalStorageDirectory();

        Log.v("check external storage","\nExternal file system root:    !!! "+root);



        File dir = new File (root.getAbsolutePath() + "/download");


        dir.mkdirs();
        File file = new File(dir, "operations.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            Log.v("try1","after f");
            PrintWriter pw = new PrintWriter(f);

            Map<Long,String> map=new TreeMap<>();
            map=dalDynamic.getTable1();
            Log.v("try2","after dal");


            for(Map.Entry<Long,String> entry : map.entrySet()) {
                Long key = entry.getKey();
                String value = entry.getValue();
                pw.print(key+" ");
                pw.println(value);
            }

            pw.flush();
            pw.close();
            f.close();
            Log.i("write to file table1","\n\nFile written to "+file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("write to file", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public int[] gethour_Minute(float time){
        int timeArr[]=new int[2];
        int hour=(int)time;
        timeArr[0]=hour;
        int minutes=(int)((time-hour)*60);
        timeArr[1]=minutes;
        Log.v("time ","time float "+time+" hour "+hour+" minutes "+minutes);
        return timeArr;

    }

}
