package com.colaberry.twitterfeed.util;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public enum MongoUtil { 
	INSTANCE; 
	private MongoClient mongoClient; 
	//private Properties properties;
	private MongoUtil() 
	{
		try 
		{ 
			/*if (properties == null) 
				{ 
					try { 
						properties = loadProperties(); 
						} 
					catch (IOException e) 
					{ 
						e.printStackTrace();
						} 
					}*/ if (mongoClient == null)
						mongoClient = getClient(); 
		} catch (Exception e) 
		{ e.printStackTrace(); 
		}
	}
	/*private static Properties loadProperties() throws IOException 
		{ 
			Properties properties = new Properties(); 
			InputStream inputStream = MongoResource.class.getResourceAsStream("/mongodb.properties");
			if (inputStream == null) 
			{ 
				throw new FileNotFoundException("not loaded!"); 
				} properties.load(inputStream); 
				return properties; 
				}*/
	private MongoClient getClient()
	{ 
		try
		{ 
			return new MongoClient("localhost", 27017);
			//return new MongoClient( properties.getProperty("host"), Integer.valueOf(properties.getProperty("port"))); 
		} catch (UnknownHostException uh) 
		{
			uh.printStackTrace(); 
		} return null; 
	} 
	public DB getDBName(String dbName){
		return mongoClient.getDB(dbName);
	}
	/*@Nullable 
		public Datastore getDatastore(@NotNull String dbName) 
		{ Datastore ds; 
		// with authentication? 
		if (!properties.getProperty("username").isEmpty() && !properties.getProperty("password").isEmpty()) 
		{ 
			ds = new Morphia(). createDatastore(mongoClient,dbName, properties.getProperty("username"), properties.getProperty("password").toCharArray());
			} 
		else { 
			ds = new Morphia(). createDatastore(mongoClient,dbName); } return ds; 
			} 
		} */


}

