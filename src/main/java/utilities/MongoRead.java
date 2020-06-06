package utilities;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import eda.Tweet;

public class MongoRead {

	private static Logger logger = LoggerFactory.getLogger(MongoRead.class);

	public static final String HOST = "localhost"; // database host
	public static final int PORT = 27017; // database port
	public static final String DB_NAME = "Kudaw"; // database name
	private String COLLECTION_NAME = "tweetRaw";// collection name
	private MongoClient mongo;
	private DB db;
	private DBCollection table;
	@SuppressWarnings("unused")
	private int status; // 0 for not initialized, 1 for initialized, -1 for
						// error.

	private int tweetRetrieve = 0;
	public int numTweets;

	public MongoRead() {
		this.status = 0;
		this.numTweets = 100;
	}

	public long tweetRawCount() {

		try {
			this.mongo = new MongoClient(HOST, PORT);
			this.db = this.mongo.getDB(DB_NAME);
			this.table = db.getCollection(COLLECTION_NAME);
			return this.table.count();

		} catch (UnknownHostException e) {
			e.printStackTrace();
			logger.error("Error");
			return 0;
		}

	}

	public ArrayList<Tweet> getAllTweets() {

		ArrayList<Tweet> tweets = new ArrayList<Tweet>();
		try {
			this.mongo = new MongoClient(HOST, PORT);
			this.db = this.mongo.getDB(DB_NAME);
			this.table = db.getCollection(COLLECTION_NAME);
			
			//DBCollection date = this.table.find({ created_at: { $gte: ISODate("2010-04-29T00:00:00.000Z"), $lt: ISODate("2010-05-01T00:00:00.000Z") } });

			for (DBObject var : this.table.find()) {
				Tweet auxTweet = new Tweet();
				
				auxTweet.setCountry((String) var.get("County"));
				auxTweet.setDate((Date) var.get("Date"));
				auxTweet.setFavoriteCount((int) var.get("FavoriteCount"));
				auxTweet.setFollowersCount((int) var.get("FollowersCount"));
				auxTweet.setFriendsCount((int) var.get("FriendsCount"));
				auxTweet.setId((long) var.get("Id"));
				auxTweet.setRetweetCount((int) var.get("RetweetCount"));
				auxTweet.setText((String) var.get("Text"));
				auxTweet.setUserLanguage((String) var.get("Language"));
				auxTweet.setUserName((String) var.get("UserName"));

				//TODOW
				//Leer todo lo que guardo en mongo, lo guardo en Tweet.java.
				
				tweets.add(auxTweet);

				tweetRetrieve++;
				auxTweet.setIdTweet(tweetRetrieve);
				if(tweetRetrieve == numTweets){
					//logger.debug("tweetRetrieve: " + tweetRetrieve);
					break;
				}
			}

			this.status = 1;
		} catch (UnknownHostException e) {
			this.status = -1;
		}
		return tweets;
	}

	public void mongoDisconnect() {
		this.mongo.close();
	}

}
