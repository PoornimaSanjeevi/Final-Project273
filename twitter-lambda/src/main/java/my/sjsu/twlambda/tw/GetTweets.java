package my.sjsu.twlambda.tw;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

public class GetTweets {
	static int STATUS_LIMIT = 1000;
	private static final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd-HH");
	static Date d = new Date();
	static String fn = "/Users/Poornima/univ/273/twitter-lambda/out/"
			+ df.format(d) + ".txt";
	static FileWriter fw;
	static int statusCount = 0;
	private static final Object lock = new Object();
	static Map<String, Integer> hashTags = new HashMap<String, Integer>();

	public static void main(String[] args) throws IOException {
		if (args.length != 2) {
			System.err
					.println("Usage: my.sjsu.twlambda.tw.GetTweets <outputFolderPath> <numberOfHashTagsToGet>");
			return;
		}
		ObjectMapper mapper = new ObjectMapper();

		fn = args[0] + "/" + df.format(d) + ".txt";
		STATUS_LIMIT = Integer.parseInt(args[1]);
		StatusListener listener = new StatusListener() {
			public void onStatus(Status status) {
				HashtagEntity[] hashEntities = status.getHashtagEntities();
				for (HashtagEntity hashtagEntity : hashEntities) {
					statusCount++;
					String ht = hashtagEntity.getText();
					if (hashTags.containsKey(ht)) {
						hashTags.put(ht, hashTags.get(ht) + 1);
					} else {
						hashTags.put(ht, 1);
					}
				}
				if (statusCount >= STATUS_LIMIT) {
					synchronized (lock) {
						lock.notify();
					}
				}

			}

			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
			}

			public void onException(Exception ex) {
				ex.printStackTrace();
			}

			@Override
			public void onScrubGeo(long userId, long upToStatusId) {

			}

			@Override
			public void onStallWarning(StallWarning warning) {

			}
		};
		fw = new FileWriter(fn);
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.addListener(listener);
		// sample() method internally creates a thread which manipulates
		// TwitterStream and calls these adequate listener methods continuously.
		twitterStream.sample("en");
		try {
			synchronized (lock) {
				lock.wait();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mapper.writeValue(fw, hashTags);
		System.out.println("Done.");
		twitterStream.shutdown();
		fw.close();
	}
}
