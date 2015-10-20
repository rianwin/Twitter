package com.colaberry.twitterfeed.mongodb.domain;

import com.mongodb.BasicDBObject;

public class TwitterData extends BasicDBObject{
/**
*/
	private static final long serialVersionUID = 1L;
private String theUserName;
private String theText;
public static final String COLLECTION_NAME = "twitter";

public String getTheUserName() {
	return theUserName;
}
public void setTheUserName(String theUserName) {
	this.theUserName = theUserName;
}
public String getTheText() {
	return theText;
}
public void setTheText(String theText) {
	this.theText = theText;
}


}
