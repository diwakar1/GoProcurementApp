package com.example.model;

public class Message {
	private String result;

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Message(String result) {
		this.result= result;
	}
	@Override
	public String toString() {
		return result ;
	}
	
	
	

}
