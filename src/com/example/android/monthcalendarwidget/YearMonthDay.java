package com.example.android.monthcalendarwidget;

public class YearMonthDay {
	
	int year;
	int month;
	int day;
	
	public YearMonthDay(int y, int m, int d){
		this.year = y;
		this.month = m;
		this.day = d;
	}
	
	public YearMonthDay(){
		this(0,0,0);
	}
	
}
