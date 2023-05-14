package exodusplayer.classes;

import java.time.LocalTime;

public class OnlineDay {
	
	private String date;
	private int seconds;
	
	public OnlineDay(String date, int seconds){
		this.date = date;
		this.seconds = seconds;
	}
	
	public String getTime(){
		return LocalTime.MIN.plusSeconds(seconds).toString();
	}
	
	public int getSeconds(){
		return this.seconds;
	}
	
	public String getDate(){
		return this.date;
	}
	
	public void addSecond(){
		this.seconds += 1;
	}

}
