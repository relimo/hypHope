package com.example.dr.hyphope;

/**
 * Created by d&r on 01/11/2016.
 */
public class Record {

    private int id;
    private int hour;
    private int minute;
    private String date;
    private String dayInWeek;
    private long idOperation;
    private String value;
    private int deviation;


    public Record(int id,int h,int m,String date,String dateInWeek,long idO,String value,int variation){
        this(h,m,date,dateInWeek,idO,value);
        this.id=id;
        this.deviation=variation;

    }
    public Record(int h,int m,String date,String dateInWeek,long idO,String value){
        this( date, dateInWeek, idO, value);
        this.hour=h;
        this.minute=m;

}

    public Record(String date,String dateInWeek,long idO,String value){
        //in this constructor time doesn't matter
        this.hour=-1;
        this.minute=-1;
        this.date=date;
        this.dayInWeek=dateInWeek;
        this.idOperation=idO;
        this.setValue(value);
        this.deviation=-1;
    }
    public Record(String date,String dateInWeek,long idO,String value,int variation){
        //in this constructor time doesn't matter
        this.hour=-1;
        this.minute=-1;
        this.date=date;
        this.dayInWeek=dateInWeek;
        this.idOperation=idO;
        this.setValue(value);
        this.setDeviation(variation);


    }


    public long getIdOperation() {
        return idOperation;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }
    public String toString(){
        return id+" "+date+" "+dayInWeek+" "+getHour()+":"+getMinute()+"operation "+getIdOperation()+"value "+value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }
}
