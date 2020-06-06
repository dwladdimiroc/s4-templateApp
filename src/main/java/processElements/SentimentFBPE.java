package processElements;

import java.util.List;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.apache.s4.core.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utilities.EventFactory;

import eda.Tweet;

public class SentimentFBPE extends ProcessingElement {
	private static Logger logger = LoggerFactory
			.getLogger(SentimentFBPE.class);

	private boolean showEvent = false;
	
	EventFactory eventFactory;
	
	List<String> keywordsUnknown;
	Stream<Event> downStream;

	public void setDownStream(Stream<Event> stream) {
		downStream = stream;
	}

	public void onEvent(Event event) {
		
		Tweet tweet = event.get("tweet", Tweet.class);
		if(showEvent){logger.debug(tweet.toString());}
		
		Tweet newTweet = tweet.getClone();
		Event eventOutput = eventFactory.newEvent(newTweet);
		
		eventOutput.put("levelTweet", Integer.class, 1);
		downStream.put(eventOutput);
		
		//event.put("levelMallet", Integer.class, 1);
		//downStream.put(event);
	}

	@Override
	protected void onCreate() {
		logger.info("Create Sentiment FB PE");
		eventFactory = new EventFactory();
	}

	@Override
	protected void onRemove() {

	}

}
