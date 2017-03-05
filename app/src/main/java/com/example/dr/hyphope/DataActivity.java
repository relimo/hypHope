package com.example.dr.hyphope;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import android.util.Log;
//import com.squareup.okhttp.OkHttpClient;
//import com.squareup.okhttp.internal.http.Request;
//import com.squareup.okhttp.internal.http.Response;
public class DataActivity extends AppCompatActivity {
    private Button btnGetData;//this button get data about specific date


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);
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
                calendar.set(Calendar.HOUR_OF_DAY, 13);
                calendar.set(Calendar.MINUTE,16);
                calendar.set(Calendar.SECOND, 0);

                //set that timer as a RTC to alarm manager object
                alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, alarmIntent);





////                code from postman
//               /* OkHttpClient client = new OkHttpClient();
//
//                Request request = new Request.Builder()
//                        .url("https://wapi.theneura.com/v1/users/profile/daily_summary?date=2017-1-26")
//                        .get()
//                        .addHeader("authorization", "Bearer 66e5d374e2bc3cf0cc6285095f82faf45a90354fa05ad08133a4853304cc1024")
//                        .addHeader("cache-control", "no-cache")
//                        .addHeader("postman-token", "2f792dd3-e3e4-29e7-7e6e-783ec666fbc5")
//                        .build();
//
//                Response response = client.newCall(request).execute();*/
//
//
//                Log.v("data activity","on click");
//                //from postman
//
//               // okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
//
//
//                    Thread thread=new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                OkHttpClient client = new OkHttpClient();
//                                Request request = new Request.Builder()
//                                        .url("https://wapi.theneura.com/v1/users/profile/daily_summary?date=2017-2-24")
//                                        .get()
//                                        .addHeader("authorization", "Bearer 66e5d374e2bc3cf0cc6285095f82faf45a90354fa05ad08133a4853304cc1024")
//                                        .addHeader("cache-control", "no-cache")
//                                        .addHeader("postman-token", "1e5fa65e-f5da-5a38-dd5f-65a182fbee2f")
//                                        .build();
//                                Response response = null;
//                                response = client.newCall(request).execute();
//                                Log.v("activity data","try after response");
//                                //TODO//right now the respone is printed in the logcat so- decide what to do with the data
//
//                                Log.d("activity data ",response.body().string());
//                                Log.v("activity data","try after printing response string");
//                            }catch(Exception e){
//                                Log.v("activity data","catch of run");
//                            }
//                        }//run
//                    });
//
//
//                thread.start();
//
//
            }
        });

    }
}
