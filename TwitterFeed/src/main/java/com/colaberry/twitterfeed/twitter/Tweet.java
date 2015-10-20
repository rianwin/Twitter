package com.colaberry.twitterfeed.twitter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Properties;

import com.colaberry.twitterfeed.mongodb.dao.TwitterFeedDao;

import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Tweet {
	private TwitterStream theTwitterStream;
	private String[] theKeywords;
	Properties theProp = new Properties();

	public Tweet() {
		super();
		try {
			theProp.load(new FileInputStream("src/main/resources/twitter.properties"));
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setOAuthConsumerKey(theProp.getProperty("oauth.consumerKey"));
			cb.setOAuthConsumerSecret(theProp.getProperty("oauth.consumerSecret"));
			cb.setOAuthAccessToken(theProp.getProperty("oauth.accessToken"));
			cb.setOAuthAccessTokenSecret(theProp.getProperty("oauth.accessTokenSecret"));
			//cb.setJSONStoreEnabled(true);
			//cb.setIncludeEntitiesEnabled(true);
			theTwitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void startTwitter() {

		String keywordString = theProp.getProperty("TWITTER_KEYWORDS");
		//String keywordString = "java";
		theKeywords = keywordString.split(",");
		for (int i = 0; i < theKeywords.length; i++) {
			theKeywords[i] = theKeywords[i].trim();
		}




		// Set up the stream's listener (defined above),
		theTwitterStream.addListener(listener);

		System.out.println("Starting down Twitter sample stream...");

		// Set up a filter to pull out industry-relevant tweets
		FilterQuery query = new FilterQuery().track(theKeywords);
		theTwitterStream.filter(query);
	}


	public void stopTwitter() {
		System.out.println("Shutting down Twitter sample stream...");
		theTwitterStream.shutdown();
	}
	StatusListener listener = new StatusListener(){
		public void onStatus(Status status) {
			System.out.println(status.getUser().getScreenName() + ": " + status);

			System.out.println("timestamp : "+ String.valueOf(status.getCreatedAt().getTime()));
			//write to mongodb here

			try {
				TwitterFeedDao.insertData("twitter", "twitterFeed", status);
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
			System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
		}

		public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
		}

		public void onScrubGeo(long userId, long upToStatusId) {
			System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
		}

		public void onStallWarning(StallWarning warning) {
			System.out.println("Got stall warning:" + warning);
		}

		public void onException(Exception ex) {
			ex.printStackTrace();
		}

	};
}
