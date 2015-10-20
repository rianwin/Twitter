package com.colaberry.twitterfeed.mongodb.dao;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import twitter4j.Status;

import com.colaberry.twitterfeed.mongodb.domain.TwitterData;
import com.colaberry.twitterfeed.util.MongoUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class TwitterFeedDao {
	/**Method to Get the Connection from the database*/
	public static DBCollection getConnection(String dbName, String collectionName)throws UnknownHostException {

		/** Connecting to MongoDB */
		//MongoClient mongo = new MongoClient("localhost", 27017);
		MongoUtil resource =MongoUtil.INSTANCE;
		/**Gets database, incase if the database is not existing
	             MongoDB Creates it for you*/
		DB db=resource.getDBName(dbName);
		//DB db = mongo.getDB(dbName);

		/**Gets collection / table from database specified if
	             collection doesn't exists, MongoDB will create it for
	             you*/
		DBCollection table = db.getCollection(collectionName);
		return table;
	}
	/**Method to insert data*/
	public static void insertData(String dbName, String collectionName,Status status) throws UnknownHostException{
		/**Connecting to MongoDB*/
		DBCollection table =TwitterFeedDao.getConnection(dbName, collectionName);
		BasicDBObject document = new BasicDBObject();
		document.put("userName",status.getUser().getScreenName());
		document.put("text",status.getText());
		table.insert(document);
	}
	/**Method to find data*/
	public static List<Map> findData(String dbName, String collectionName)throws UnknownHostException{
		DBCollection table =TwitterFeedDao.getConnection(dbName, collectionName);
		Map<String,Object> jsonDocument = new HashMap<String,Object>();
		List<Map> jsonObjects=new ArrayList<Map>();
		BasicDBObject searchQuery = new BasicDBObject();
		//searchQuery.put("userName", "java");
		//DBCursor cursor = table.find(searchQuery);
		DBCursor cursor = table.find();
		TwitterData twitterData=new TwitterData();
		while (cursor.hasNext()) {
			cursor.next();
			twitterData.setTheText(cursor.curr().get("text").toString());
			twitterData.setTheUserName(cursor.curr().get("userName").toString());
			jsonDocument.put(cursor.curr().get("_id").toString(),twitterData);
			jsonObjects.add(jsonDocument);

		}return jsonObjects;
	}
	/**Method to remove data*/
	public static void removeData(String dbName, String collectionName)throws UnknownHostException{
		DBCollection table =TwitterFeedDao.getConnection(dbName, collectionName);
		BasicDBObject document = new BasicDBObject();
		// Delete All documents from collection Using blank BasicDBObject
		table.remove(document);

	}

}
