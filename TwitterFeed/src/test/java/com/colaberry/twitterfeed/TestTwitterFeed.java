package com.colaberry.twitterfeed;
import java.net.UnknownHostException;

import org.junit.Test;

import com.colaberry.twitterfeed.mongodb.dao.TwitterFeedDao;
import com.colaberry.twitterfeed.twitter.Tweet;
import com.mongodb.MongoException;

import twitter4j.TwitterException;
public class TestTwitterFeed {

	@Test
	public void testReadingTwitteFeed() throws TwitterException{
		try {
			TwitterFeedDao.removeData("twitter", "twitterFeed");
			Tweet twitter = new Tweet();
			twitter.startTwitter();
			Thread.sleep(60000);
			twitter.stopTwitter();


		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testReadTwitteFeedMongo() throws TwitterException{
		try {
			System.out.println("Reading Data from MongoDB");
			TwitterFeedDao.findData("twitter", "twitterFeed");

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}


}
