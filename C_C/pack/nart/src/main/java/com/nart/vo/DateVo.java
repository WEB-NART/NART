package com.nart.vo;

import lombok.Data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class DateVo {
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;

    public DateVo getDateToString(long time) {
        //System.out.println(time);
        String s = String.valueOf(time);
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        //yyyy-MM-dd HH:mm:ss
        res = simpleDateFormat.format(date);
        //System.out.println(res);
        DateVo dateVo = new DateVo();
        return createDateVo(res,dateVo);
    }

    public long toLong() {
        String dateString = String.valueOf(year) + "-" +
                String.valueOf(month) + "-" +
                String.valueOf(day) + " " +
                String.valueOf(hour) + ":" +
                String.valueOf(min);
        DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = null;
        try {
            date = format1.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date != null;
        return date.getTime();
    }

    public DateVo createDateVo(String dateToString, DateVo dateVo){
        String nian = dateToString.substring(0, 4);
        int Nian = Integer.parseInt(nian);
//        System.out.println(Nian);
        dateVo.setYear(Nian);


        String yue = dateToString.substring(5, 7);
        int YUE = Integer.parseInt(yue);
//        System.out.println(YUE);
        dateVo.setMonth(YUE);

        String ri = dateToString.substring(8,10);
        int RI = Integer.parseInt(ri);
//        System.out.println(RI);
        dateVo.setDay(RI);

        String xiaoshi = dateToString.substring(11,13);
        int XIAOSHI = Integer.parseInt(xiaoshi);
//        System.out.println(XIAOSHI);
        dateVo.setHour(XIAOSHI);

        String fenzhong = dateToString.substring(14,16);
        int Fen = Integer.parseInt(fenzhong);
//        System.out.println(Fen);
        dateVo.setMin(Fen);

        return dateVo;
    }
}
