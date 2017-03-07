package com.example.dr.hyphope;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
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

import android.util.Log;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.internal.http.Request;
//import com.squareup.okhttp.internal.http.Response;
public class DataActivity extends AppCompatActivity {
    private Button btnGetData;//this button get data about specific date
    private Button btnWriteToFile;

    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private DalDynamic dalDynamic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
        dalDynamic=new DalDynamic(this);
        dalDynamic.addRowToTable1("sleepLength");
        dalDynamic.addRowToTable1("wakeUpTime");
        btnWriteToFile=(Button)findViewById(R.id.btnWriteToFile);
        btnWriteToFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                writeToFile();
            }
        });


        btnGetData=(Button)findViewById(R.id.btnGetData);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.v("data activity","on click");
                alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

                //Create pending intent & register it to your alarm notifier class
                Intent intent = new Intent(getApplicationContext(), AlarmReceiverData.class);
//                intent.putExtra("uur", "1e"); // if you want
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                //set timer you want alarm to work
                //TODO: insert 00:10 instead of this hour
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, 11);
                calendar.set(Calendar.MINUTE,53);
                calendar.set(Calendar.SECOND, 0);

                //set that timer as a RTC to alarm manager object
                alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);

            }
        });


    }

    private void writeToFile(){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

        File root = android.os.Environment.getExternalStorageDirectory();
        Log.v("check external storage","\nExternal file system root: "+root);



        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "records.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);

            ArrayList<Record> list = dalDynamic.getRecordsList();
            for (Record i : list)
                pw.println(i.toString());
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i("write to file", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("write to file","\n\nFile written to "+file);
    }

}
