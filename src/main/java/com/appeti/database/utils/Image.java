package com.appeti.database.utils;

public class Image {
	private String url;
	private String text;
	
	public Image(String url, String text){
		this.url = url;
		this.text = text;
	}
	
	public boolean equals(Image image){
		if(this.url.equals(image.getUrl()) && this.text.equals(image.getText()))
			return true;
		return false;		
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
}
