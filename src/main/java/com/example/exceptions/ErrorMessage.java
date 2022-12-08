package com.example.exceptions;

import java.util.Date;

public class ErrorMessage extends Exception{
	
	int number;
	Date date;
	String message;
	String otherMessage;
	
	public ErrorMessage(int number, Date date, String message, String otherMessage) {
		super();
		this.number = number;
		this.date = date;
		this.message = message;
		this.otherMessage = otherMessage;
	}
	
  
}
