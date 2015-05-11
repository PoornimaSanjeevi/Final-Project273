package my.sjsu.twlambda.tw;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class SearchTweets {
	static int STATUS_LIMIT = 1000;
	private static final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd-HH");
	static Date d = new Date();
	static String fn = "/Users/Poornima/univ/273/twitter-lambda/out/"
			+ df.format(d) + ".txt";
	static BufferedWriter fw;
	static int statusCount = 0;
	static Map<String, List<RawTweets>> rawTweets = new HashMap<String, List<RawTweets>>();

	public static void main(String[] args) throws IOException {
		fw = new BufferedWriter(new FileWriter(fn));
		Twitter twitter = new TwitterFactory().getInstance();
		String ht1 = "sjsu";
		String ht2 = "sjpd";
		rawTweets.put("ht1", new ArrayList<RawTweets>());
		rawTweets.put("ht2", new ArrayList<RawTweets>());

		try {
			Query query = new Query("#" + ht1 + " OR  #" + ht2);
			query.setSince("2015-05-08");
			query.setCount(100);
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					boolean hasHt1 = false;
					boolean hasHt2 = false;

					User u = tweet.getUser();

					HashtagEntity[] hashTags = tweet.getHashtagEntities();
					String[] hashlist = new String[hashTags.length];
					int i = 0;
					for (HashtagEntity h : hashTags) {
						if (ht1.equalsIgnoreCase(h.getText())) {
							hasHt1 = true;
						}
						if (ht2.equalsIgnoreCase(h.getText())) {
							hasHt2 = true;
						}
						hashlist[i] = h.getText();
						i++;
					}
					double lat = -1000;
					double lon = -1000;
					if (tweet.getGeoLocation() != null) {
						lat = tweet.getGeoLocation().getLatitude();
						lon = tweet.getGeoLocation().getLongitude();
					}

					if (hasHt1) {
						RawTweets rt = new RawTweets("ht1", df.format(tweet
								.getCreatedAt()), u.getFollowersCount(), lat,
								lon, u.getLocation(), hashlist);
						rawTweets.get(ht1).add(rt);
					}
					if (hasHt2) {
						RawTweets rt = new RawTweets("ht2", df.format(tweet
								.getCreatedAt()), u.getFollowersCount(), lat,
								lon, u.getLocation(), hashlist);
						rawTweets.get(ht2).add(rt);
					}
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(fw, rawTweets);
		fw.close();
	}
}
