package processElements;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import org.apache.s4.base.Event;
import org.apache.s4.core.ProcessingElement;
import org.apache.s4.core.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utilities.EventFactory;

import cc.mallet.classify.Classifier;

import eda.Tweet;

public class SentimentMalletPE extends ProcessingElement {
	private static Logger logger = LoggerFactory
			.getLogger(SentimentMalletPE.class);

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
		
		
//		FileOutputStream classifier = new FileOutputStream("../myApp/config/mallet/Obj-Sub.classifier");
//		Classifier classifierObjSub = loadClassifier(classifier);
		eventOutput.put("levelTweet", Integer.class, 1);
		downStream.put(eventOutput);

	}

	public Classifier loadClassifier(File serializedFile)
			throws FileNotFoundException, IOException, ClassNotFoundException {

		Classifier classifier;

		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
				serializedFile));
		classifier = (Classifier) ois.readObject();
		ois.close();

		return classifier;
	}

	@Override
	protected void onCreate() {
		logger.info("Create Sentiment Mallet PE");
		eventFactory = new EventFactory();
	}

	@Override
	protected void onRemove() {

	}

}
