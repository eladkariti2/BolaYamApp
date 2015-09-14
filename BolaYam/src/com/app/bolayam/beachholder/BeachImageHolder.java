package com.app.bolayam.beachholder;

import com.application.imageholders.ImageHolder;
import com.application.models.Whether;

public class BeachImageHolder extends ImageHolder {

	private int mMansNumber;
	private int mWomenNumber;
	private int mStars;
	private int mComments;
	private	int mLikes;
	private int mCheckin;
	private Whether whether;
	private String attractions;
	
	public BeachImageHolder(String id,String title,Whether whether,String attractinos, String description, String url,int mensNumber,int womenNumber,int stars,int likes,int comments,int checkin) {
		super(id,title, description, url);
		// TODO Auto-generated constructor stub
		this.setWhether(whether);
		this.attractions = attractinos;
		mMansNumber = mensNumber;
		mCheckin = checkin;
		mComments = comments;
		mLikes = likes;
		mWomenNumber = womenNumber;
		mStars = stars;
	}
	
	public int getMansNumber() {
		return mMansNumber;
	}
	
	public int getStars() {
		return mStars;
	}
	
	public int getWomenNumber() {
		return mWomenNumber;
	}

	public int getLikes() {
		// TODO Auto-generated method stub
		return mLikes;
	}

	
	public int getComments() {
		// TODO Auto-generated method stub
		return mComments;
	}
	public int getCheckin() {
		// TODO Auto-generated method stub
		return mCheckin;
	}
	
	
	//Setters
	
	public void setMansNumber(int numbers) {
		mMansNumber = numbers;
	}
	
	public void setStars(int stars) {
		mStars = stars;
	}
	
	public void setWomenNumber(int number) {
		mWomenNumber= number;
	}

	public void setLikes(int likes) {
		// TODO Auto-generated method stub
		mLikes = likes;
	}

	
	public void setComments(int comments) {
		// TODO Auto-generated method stub
		 mComments = comments;
	}
	public void setCheckin(int checkin) {
		// TODO Auto-generated method stub
		 mCheckin = checkin;
	}

	public String getAttractions() {
		return attractions;
	}

	public void setAttractions(String attractions) {
		this.attractions = attractions;
	}

	public Whether getWhether() {
		return whether;
	}

	public void setWhether(Whether whether) {
		this.whether = whether;
	}
}
