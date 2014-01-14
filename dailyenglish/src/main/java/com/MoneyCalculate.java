package com;

public class MoneyCalculate {
    public static void main(String[] args){
    	double rate = 0.035;
    	double sum = 0;
    	calculate(rate, 5, sum, 60000, 0);
    	//calculate(0.055, 1, sum, 720000, 0);
    }
    
    public static void calculate(double rate, int year, double sum, double monthMoney, int time){
    	double rateMonthMoney = monthMoney * rate;
    	double allMonthMoney = rateMonthMoney + monthMoney;
    	sum += rateMonthMoney * 12;
    	System.out.println("Year : " + year + "-----all profit------" + sum);
    	if(time < year-1){
    		time ++;
    		calculate(rate, year, sum, allMonthMoney, time);
    	}
    }
}
