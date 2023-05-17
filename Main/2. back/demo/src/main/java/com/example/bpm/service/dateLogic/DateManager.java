package com.example.bpm.service.dateLogic;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateManager {

    public String logTime(){
        Date now = new Date();
        SimpleDateFormat dayf = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        return dayf.format(now).toString();
    }

    public Date LogTimeConvert(String dateString){

        Date date = null;

        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
            date = formatter.parse(dateString);
        }
        catch (Exception e){
            System.out.println("Date Format 오류 발생");
        }

        return date;
    }

    public String DocumentTime(){
        Date now = new Date();
        SimpleDateFormat dayf = new SimpleDateFormat("yyyy MM dd");
        return dayf.format(now).toString();
    }
}
