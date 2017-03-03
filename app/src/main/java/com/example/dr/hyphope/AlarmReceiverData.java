package com.example.dr.hyphope;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * .
 */
public class AlarmReceiverData extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


//        Calendar now = Calendar.getInstance();
//
//        now.add(Calendar.DATE,-1);//we want to get data of the last full day
//        String day=now.get(Calendar.DAY_OF_MONTH)+"";
//        String month=now.get(Calendar.MONTH)+"";
//        Log.v("Alarm Receiver",month);
//        String year=now.get(Calendar.YEAR)+"";
//        Log.v("Alarm Receiver","date="+year+"-"+month+"-"+day);
//        Log.v("Alarm Receiver","message");//works at the requested time





        Thread thread=new Thread(new Runnable() {



                        @Override
                        public void run() {
                            Calendar now = Calendar.getInstance();
                            now.add(Calendar.DATE,-1);
                            String day=(now.get(Calendar.DAY_OF_MONTH))+"";//we want to get data of the last full day
                            String month=(now.get(Calendar.MONTH)+1)+"";
                            String year=now.get(Calendar.YEAR)+"";
                            Log.v("Alarm Receiver","now: date="+year+"-"+month+"-"+day);


                            try {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
//                                        .url("https://wapi.theneura.com/v1/users/profile/daily_summary?date=2017-2-24")// we get in this date: {"status":"success","timestamp":1488283626,"data":null}
                                        .url("https://wapi.theneura.com/v1/users/profile/daily_summary?date="+year+"-"+month+"-"+day)
                                        .get()
                                        .addHeader("authorization", "Bearer 66e5d374e2bc3cf0cc6285095f82faf45a90354fa05ad08133a4853304cc1024")
                                        .addHeader("cache-control", "no-cache")
                                        .addHeader("postman-token", "1e5fa65e-f5da-5a38-dd5f-65a182fbee2f")
                                        .build();
                                Response response = null;
                                response = client.newCall(request).execute();
                                Log.v("activity data","try after response");
                                //TODO//right now the response is printed in the logcat so- decide what to do with the data
                                String stringAsJson=response.body().string();

                                try {

                                    JSONObject obj = new JSONObject(stringAsJson);

                                    Log.d("Run", obj.toString());
                                    Log.d("Run ", obj.getString("status"));

                                    if(obj.getString("data").equals(null)) {
                                        Log.d("Run ", "data is NULL");
                                        return;
                                    }
                                } catch (Throwable tx) {
                                    Log.e("My App", "Could not parse malformed JSON");
                                }
//                                Log.d("activity data ",response.body().string());//{"status":"success","timestamp":1488283626,"data":null}

                                Log.v("activity data","try after printing response string");
                            }catch(Exception e){
                                Log.v("activity data","catch of run");
                            }
                        }//run
                    });


                thread.start();


    }
}

