package com.hyron.timetask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

public class TimerManager {
    private static final long PERIOD_DAY = 24*60*60*1000;
    public TimerManager(){
    	Calendar calendar = Calendar.getInstance();
    	calendar.set(Calendar.HOUR, 1);
    	calendar.set(Calendar.MINUTE, 39);
    	calendar.set(Calendar.SECOND, 0);
    	Date date = calendar.getTime();
    	if(date.before(new Date())){
    		date = this.addDay(date,1);
    		System.out.println("date before now");
    	}
    	Timer timer = new Timer();
    	ParserNewsTimeTask pnTask = new ParserNewsTimeTask();
    	timer.schedule(pnTask, date , PERIOD_DAY);
    }
	public Date addDay(Date date, int i) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DAY_OF_MONTH, i);
		return startDT.getTime();
	}
}
