package servletManager.functions;

import com.google.gson.Gson;
import servletManager.model.RawTweets;
import twitter4j.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SearchTweets {
	static int STATUS_LIMIT = 1000;
	private static final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd");
	static Date d = new Date();
	static String fn = "C:\\SJSU\\SecondSem\\283\\Project2\\273Final\\"
			+ df.format(d) + ".txt";
	static BufferedWriter fw;
	static int statusCount = 0;
	static Map<String, List<RawTweets>> rawTweets = new HashMap<String, List<RawTweets>>();

	public static void sendTweets(String ht1,String ht2,String fromDate,String toDate) throws IOException {
		fw = new BufferedWriter(new FileWriter(fn));
		Twitter twitter = new TwitterFactory().getInstance();

		rawTweets.put(ht1, new ArrayList<RawTweets>());
		rawTweets.put(ht2, new ArrayList<RawTweets>());

		try {
			Query query = new Query("#" + ht1 + " OR  #" + ht2);
			query.setSince(fromDate);
			query.setCount(100);
			QueryResult result;
			do {
				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
                    //System.out.println(tweet.getId());
					boolean hasHt1 = false;
					boolean hasHt2 = false;
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
					RawTweets rt = new RawTweets(
							df.format(tweet.getCreatedAt()),
							 lat, lon, hashlist);
					if (hasHt1) {
						rawTweets.get(ht1).add(rt);
					}
					if (hasHt2) {
						rawTweets.get(ht2).add(rt);
					}
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
			System.exit(-1);
		}
        Gson gson = new Gson();
        //System.out.println(gson.toJson(rawTweets));
        fw.write(gson.toJson(rawTweets));
        //gson.toJson(rawTweets,fw);

		fw.close();
	}

}
