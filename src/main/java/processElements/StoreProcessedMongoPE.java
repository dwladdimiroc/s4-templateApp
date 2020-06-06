/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package processElements;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eda.Tweet;

import utilities.EventFactory;
import utilities.MongoConnection;

public class StoreProcessedMongoPE extends ProcessingElement {

	private static Logger logger = LoggerFactory.getLogger(StoreProcessedMongoPE.class);
	private boolean showEvent = false;
	MongoConnection mongo;
	
	EventFactory eventFactory;


    public void onEvent(Event event) {
    	    			
		Tweet tweet = event.get("tweet", Tweet.class);
		if(showEvent){logger.debug(tweet.toString());}
		
		mongo.insert(tweet.storeEvent());
      
    }

    @Override
    protected void onCreate() {
    	
    	logger.info("Create Store Processed Mongo PE");

		//TODOW Que lea el nombre de la aplicación
		mongo = new MongoConnection();
		mongo.setCollectionName("tweetProcessed");
		mongo.setupMongo();
    	
    }

    @Override
    protected void onRemove() {
    	
    	mongo.disconnect();
    	
    }
    


}
