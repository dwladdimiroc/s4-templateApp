/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package adapter;

import java.util.ArrayList;

import org.apache.s4.base.Event;
import org.apache.s4.core.adapter.AdapterApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eda.Configuration;
import eda.Tweet;
import utilities.MongoRead;

public class MongoAdapter extends AdapterApp {

	private static Logger logger = LoggerFactory
			.getLogger(MongoAdapter.class);
	public int retrieveTweet = 0;
	int numTweets;
	int levelConcurrency;

	@Override
	protected void onStart() {
		logger.info("MongoAdapter");
		 
		//Configuration configuration = new Configuration();
		numTweets = 100;
		levelConcurrency = 1;

		MongoRead mongoRead = new MongoRead();
		Configuration configuration = new Configuration();
		configuration.settingPE(1);
		configuration.getDateInit();
		configuration.getDateFinal();

		while (retrieveTweet < numTweets) {

			ArrayList<Tweet> tweets = mongoRead.getAllTweets();

			for (Tweet tweet : tweets) {
				retrieveTweet++;

				Event event = new Event();

				event.put("levelTweet", Integer.class, 1);
				event.put("id", Integer.class, retrieveTweet);
				event.put("tweet", Tweet.class, tweet);

				getRemoteStream().put(event);
				
				if(retrieveTweet == numTweets){
					logger.debug("Envio: " + retrieveTweet);
					break;
				}
			}

			logger.debug("Envio: " + retrieveTweet);
		}

	}

}
