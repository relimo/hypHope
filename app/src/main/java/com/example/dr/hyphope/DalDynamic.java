package com.example.dr.hyphope;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created on 17/07/2016.
 */
public class DalDynamic {
    private static final int NOT_EXIST=-1;
    private DBHelperDynamic helper;
    private final String TAG = getClass().getSimpleName();
    public DalDynamic(Context context){
        helper = new DBHelperDynamic(context);
        Log.v(TAG,"DalDynamic Constructor");
    }


    public long addRowToTable1(String name){
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();
        //values to save
        ContentValues values = new ContentValues();
        // values.put(ContractDynamic.COLUMN_ID,m.getId());
        values.put(ContractDynamic.COLUMN_NAME_1,name);
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_ID_1).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_NAME_1).toString());


        //save the values
        long id=db.insert(ContractDynamic.TABLE_NAME_1, null, values);//id=the row ID of the newly inserted row, or -1 if an error occurred
        Log.v(TAG,id+" the id after insert to table 1");
        db.close();
        return id;

    }//addRowToTable1

    public long addRowToTable2(Record record){
        Log.v(TAG,"add row to table 2");
        //get DB
        SQLiteDatabase db = helper.getWritableDatabase();
        //values to save
        ContentValues values = new ContentValues();


        values.put(ContractDynamic.COLUMN_HOUR_2,record.getHour());
        values.put(ContractDynamic.COLUMN_MINUTE_2,record.getMinute());
        values.put(ContractDynamic.COLUMN_DATE_2,record.getDate());
        values.put(ContractDynamic.COLUMN_DAY_IN_WEEK_2,record.getDayInWeek());
        values.put(ContractDynamic.COLUMN_EVENT_TYPE_ID_2,record.getIdOperation());
        values.put(ContractDynamic.COLUMN_VALUE,record.getValue());


        Log.v(TAG,values.get(ContractDynamic.COLUMN_HOUR_2).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_MINUTE_2).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_DATE_2).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_DAY_IN_WEEK_2).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_EVENT_TYPE_ID_2).toString());
        Log.v(TAG,values.get(ContractDynamic.COLUMN_VALUE).toString());


        //save the values
        long id=db.insert(ContractDynamic.TABLE_NAME_2, null, values);//id=the row ID of the newly inserted row, or -1 if an error occurred
        Log.v(TAG,id+"id");

        db.close();
        return id;
    }//addRowToTable2


//    public long addRowToTable2(int hour, int minute, String date , String dayInWeek, long eventTypeId){
//        //get DB
//        SQLiteDatabase db = helper.getWritableDatabase();
//        //values to save
//        ContentValues values = new ContentValues();
//        // values.put(ContractDynamic.COLUMN_ID,m.getId());
//        values.put(ContractDynamic.COLUMN_HOUR_2,hour);
//        values.put(ContractDynamic.COLUMN_MINUTE_2,minute);
//        values.put(ContractDynamic.COLUMN_DATE_2,date.toString());
//        values.put(ContractDynamic.COLUMN_DAY_IN_WEEK_2,dayInWeek);
//        values.put(ContractDynamic.COLUMN_EVENT_TYPE_ID_2,eventTypeId);
//
//
////        Log.v(TAG,values.get(ContractDynamic.COLUMN_ID_2).toString());
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_HOUR_2).toString());
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_MINUTE_2).toString());
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_DATE_2).toString());
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_DAY_IN_WEEK_2).toString());
//        Log.v(TAG,values.get(ContractDynamic.COLUMN_EVENT_TYPE_ID_2).toString());
//
//        //save the values
//        long id=db.insert(ContractDynamic.TABLE_NAME_2, null, values);//id=the row ID of the newly inserted row, or -1 if an error occurred
//        Log.v(TAG,id+"id");
//
//        db.close();
//        return id;
//    }//addRowToTable2


    public ArrayList<Record> getRecordsList() {
        ArrayList<Record> list= new ArrayList<Record>();
        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();
        //get cursor
        Cursor c;
        c = db.rawQuery("SELECT * " +" FROM " + ContractDynamic.TABLE_NAME_2, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    //TODO: CHANGE FROM INDEX NUMBERS TO PARAMETERS- GET COLUMN INDEX
                    int id=c.getInt(0);
                    int h=c.getInt(1);
                    int m=c.getInt(2);
                    String date=c.getString(3);
                    String day=c.getString(4);
                    long idO=c.getLong(5);//the column of the id_operation
                    String value=c.getString(6);
//                    int id,int h,int m,String date,String dateInWeek,long idO,String value
                     Record r=new Record(id,h,m,date,day,idO,value);
                    list.add(r);

                }while(c.moveToNext());
                c.close();
                db.close();


            }
            return list;
        }
        return null;
    }

    public long getRecordIdAccordingToRecordName(String name) {
        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();
        //get cursor
        Cursor c;

        String sql="SELECT "+ContractDynamic.COLUMN_ID_1+ " FROM "+ ContractDynamic.TABLE_NAME_1
                + " WHERE " + ContractDynamic.COLUMN_NAME_1 + "=" +"'"+name+"'";

        Log.v(TAG,sql);
        c=db.rawQuery(sql,null);

        if (c != null) {

            if (c.moveToFirst()) {
                //get the index of the column
                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_ID_1);
                Log.v(TAG,columnIndex+"");
                //get the suitable questionnaire
                long recordId = c.getLong(columnIndex);
                Log.v(TAG,recordId+"");
                c.close();
                db.close();
                return recordId;
            }
        }
        return NOT_EXIST;
    }


    public Map<Long,String> getTable1(){
        Map<Long,String> map=new TreeMap<>();
        //get DB
        SQLiteDatabase db = helper.getReadableDatabase();
        //get cursor
        Cursor c;
        c = db.rawQuery("SELECT * " +" FROM " + ContractDynamic.TABLE_NAME_1, null);
        if (c != null) {
            if (c.moveToFirst()) {
                do {
             //  get the index of the column
                    int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_ID_1);
                    //get the suitable questionnaire
                    long opId = c.getLong(columnIndex);
                    //  get the index of the column
                    columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_NAME_1);
                    //get the suitable questionnaire
                    String opName = c.getString(columnIndex);
                    map.put(opId,opName);
                }while(c.moveToNext());
                c.close();
                db.close();

            }
            return map;
        }
        return null;
    }
//
//    public List<Long> getMedicineIdFromTable2List() {
//        List<Long> idList= new ArrayList<Long>();
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c = db.rawQuery("SELECT "+ContractDynamic.COLUMN_ID_2 +" FROM " + ContractDynamic.TABLE_NAME_2, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                do {
//                    idList.add(c.getLong(0));//0 is the column
//                }while(c.moveToNext());
//                c.close();
//                db.close();
//
//
//            }
//            return idList;
//        }
//        return null;
//    }
//
//
//
//    //this function gets hour and minute and returns the matched medicine id
//    public long getMedicineIdAccordingToTime(int h,int m) {
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_ID_MEDICINE_2+ " FROM "+ ContractDynamic.TABLE_NAME_2
//                + " WHERE " + ContractDynamic.COLUMN_HOUR_2 + "=" + h + " and "+ ContractDynamic.COLUMN_MINUTE_2+ "=" + m, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_ID_MEDICINE_2);
//                //get the suitable questionnaire
//                long medId = c.getLong(columnIndex);
//                c.close();
//                db.close();
//                return medId;
//            }
//        }
//        return NOT_EXIST;
//    }
//
//
//    //this function gets medId and returns medicine name
//    public String getMedicineNameAccordingToMedId(long medId) {
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_NAME+ " FROM "+ ContractDynamic.TABLE_NAME
//                + " WHERE " + ContractDynamic.COLUMN_ID+ "=" +  medId, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_NAME);
//                //get the suitable questionnaire
//               String medName = c.getString(columnIndex);
//                c.close();
//                db.close();
//                return medName;
//            }
//        }
//        return "";//empty
//    }
//
//    //this function gets medId and returns quantity of medicine to take
//    public float getMedicineQuantityPerTimeAccordingToMedId(long medId) {
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_PER_TIME+ " FROM "+ ContractDynamic.TABLE_NAME
//                + " WHERE " + ContractDynamic.COLUMN_ID+ "=" +  medId, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_PER_TIME);
//                //get the suitable questionnaire
//                float medQuantity = c.getFloat(columnIndex);
//                c.close();
//                db.close();
//                return medQuantity;
//            }
//        }
//        return NOT_EXIST;
//    }
//    public int getQuantityPerDay(int medId){
//        //  get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_PER_DAY+ " FROM "+ ContractDynamic.TABLE_NAME
//                + " WHERE " + ContractDynamic.COLUMN_ID + "=" + medId, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_PER_DAY);
//                //get the suitable questionnaire
//                int q  = c.getInt(columnIndex);
//                c.close();
//                db.close();
//                return q;
//            }
//        }
//        return NOT_EXIST;
//
//    }
//
//    public int getQuantityPerPackage(int medId){
//        //  get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_QUANTITY_PER_PACKAGE+ " FROM "+ ContractDynamic.TABLE_NAME
//                + " WHERE " + ContractDynamic.COLUMN_ID + "=" + medId, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_QUANTITY_PER_PACKAGE);
//                //get the suitable questionnaire
//                int q  = c.getInt(columnIndex);
//                c.close();
//                db.close();
//                return q;
//            }
//        }
//        return NOT_EXIST;
//
//    }
//
//    public String getInstructions(int medId) {
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//        //get cursor
//        Cursor c;
//        c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_INSTRUCTIONS+ " FROM "+ ContractDynamic.TABLE_NAME
//                + " WHERE " + ContractDynamic.COLUMN_ID + "=" + medId, null);
//        if (c != null) {
//            if (c.moveToFirst()) {
//                //get column index
//                //int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_ID);
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_INSTRUCTIONS);
//                //get entry
//                //String answerINST = c.getInt(columnIndex)+"";
//                  String answerINST=c.getString(columnIndex);
//                // int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_INSTRUCTIONS);
//                c.close();
//                db.close();
//
//                return answerINST;
//            }
//        }
//        return "no instructions";
//    }

//    public int getCurrentQuantity(int medId){
//            //  get DB
//            SQLiteDatabase db = helper.getReadableDatabase();
//            //get cursor
//            Cursor c;
//            c= db.rawQuery("SELECT "+ContractDynamic.COLUMN_CURRENT_QUANTITY+ " FROM "+ ContractDynamic.TABLE_NAME
//                    + " WHERE " + ContractDynamic.COLUMN_ID + "=" + medId, null);
//            if (c != null) {
//            if (c.moveToFirst()) {
//                //get the index of the column
//                int columnIndex = c.getColumnIndex(ContractDynamic.COLUMN_CURRENT_QUANTITY);
//                //get the suitable questionnaire
//                int q  = c.getInt(columnIndex);
//                c.close();
//                db.close();
//                return q;
//            }
//        }
//        return NOT_EXIST;
//
//        }
//
//
//    public Cursor getAllMedicinesCursor(){
//        //get DB
//        SQLiteDatabase db = helper.getReadableDatabase();
//
//        Cursor c = db.rawQuery("SELECT * FROM " + ContractDynamic.TABLE_NAME, null);
//
//        return c;
//    }
//    public  void updateQuantity(int medId,float newQuantity,int oldQuantity){
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        String sqlStatement="UPDATE "+ContractDynamic.TABLE_NAME +" SET "+ContractDynamic.COLUMN_CURRENT_QUANTITY+" = replace("+ContractDynamic.COLUMN_CURRENT_QUANTITY+", "+oldQuantity+" , "+newQuantity+")"+" WHERE " + ContractDynamic.COLUMN_ID + " = " + medId;
//
//        db.execSQL(sqlStatement);
//
//    }
//    public int deleteMedicineFromTable1(String nameMedicine){
//        long idMedicine=getMedicineIdAccordingToMedicineName(nameMedicine);
//        int affected=deleteMedicineFromTable2(idMedicine);//delete from table 2 TODO:change to void
//        SQLiteDatabase db = helper.getWritableDatabase();
//
//        db.delete(ContractDynamic.TABLE_NAME,ContractDynamic.COLUMN_NAME + " = ?",new String[]{nameMedicine});//delete from table 1
//
//        Log.v(TAG,idMedicine+"");
//
//        db.close();
//        return affected;
//    }
//    public int deleteMedicineFromTable2(long id){
//        SQLiteDatabase db = helper.getWritableDatabase();
//        int affected=db.delete(ContractDynamic.TABLE_NAME_2,ContractDynamic.COLUMN_ID_MEDICINE_2 + " = ?",new String[]{id+""});
//
//        db.close();
//        return affected;
//    }

}//class DalDynamic
