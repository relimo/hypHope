package com.example.dr.hyphope;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class saves in DB data of walking
 */
public class AlarmReceiverDataWalking extends BroadcastReceiver {
    private DalDynamic dalDynamic;
    private Context context;
    private Statistics statistics;
    public SharedPreferences sp;
    public SharedPreferences .Editor editor;

    @Override


    public void onReceive(Context context, Intent intent) {
        this.context=context;
        statistics=new Statistics(context);
        sp = context.getSharedPreferences("pref_avg", Context.MODE_PRIVATE);
        dalDynamic=new DalDynamic(context);
        Thread thread=new Thread(new Runnable() {

                        @Override
                        public void run() {

                            Calendar now = Calendar.getInstance();
                            String day=(now.get(Calendar.DAY_OF_MONTH))+"";//we want to get data of the last full day
                            String month=(now.get(Calendar.MONTH)+1)+"";//January is 0 so there is a need to +1
                            String year=now.get(Calendar.YEAR)+"";
                            Log.v("Alarm Receiver","now: date="+year+"-"+month+"-"+day);


                            try {
                                OkHttpClient client = new OkHttpClient();
                                Request request = new Request.Builder()
//
//                                        .url("https://wapi.theneura.com/v1/users/profile/daily_summary?date=2017-2-26")// we get in this date: {"status":"success","timestamp":1488283626,"data":null}
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
                                    String jsonDataForNull=obj.getString("data");

                                    if(jsonDataForNull.equals("null")) {
                                        Log.d("Run ", "data is NULL");
                                        return;
                                    }else {
                                        Log.d("Run ", "data is NOT NULL");

                                        JSONObject jsonData=  new JSONObject(obj.getString("data")) ;//if the data is not null so take the data as json object

                                        String walkingLength=jsonData.getString("minutesWalk");
                                        Log.d("Run walkingLength: ",walkingLength );//minutes of walking
//

                                        long id=dalDynamic.getRecordIdAccordingToRecordName("minutesWalkTillEvening");
                                        Log.d("Run id operation: ",id+"" );
                                        SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");
                                        String date=dateFormat.format(now.getTime());
                                        Log.d("Run id date: ",date+"" );
                                        SimpleDateFormat dayFormat=new SimpleDateFormat("EEEE", Locale.ENGLISH);//format day in week
                                        String day_week=dayFormat.format(now.getTime());
                                        Log.d("Run day week: ",day_week+"" );
                                        //check if today the user walked less than other days
                                        int deviation=statistics.calculatingDeviation(walkingLength);
                                        Record record=new Record(date,day_week,id,walkingLength,deviation);
                                        //insert the walking data of today till evening to the DB
                                        long id2=dalDynamic.addRowToTable2(record);
                                        Log.d("Run id table 2: ",id2+"");
                                        //check if there is a need to punish
                                        statistics.toPunish();



                                    }
                                } catch (Throwable tx) {
                                    Log.e("My App", "Could not parse malformed JSON");
                                }


                                Log.v("activity data","try after printing response string");
                            }catch(Exception e){
                                Log.v("activity data","catch of run");
                            }
                        }//run
                    });


                thread.start();


    }//on receive





}

