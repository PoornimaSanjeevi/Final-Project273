package my.sjsu.twlambda.tw;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@RestController
public class TweetController {
	// private static AtomicInteger modIdGen = new AtomicInteger();

	// @Autowired
	// Storage storage = new MongoDBStorage();
	//
	// // Create moderator
	// @RequestMapping(value = "/api/v1/moderators{id}", consumes =
	// "application/json", method = RequestMethod.POST)
	// @ResponseBody
	// @ResponseStatus(HttpStatus.CREATED)
	// public Moderator addModerator(@RequestBody @Valid Moderator input) {
	//
	// int id = modIdGen.incrementAndGet();
	// input.setId(id);
	// storage.addModerator(id, input);
	// return (input);
	// }
	//
	// // View Moderator
	//
	// @RequestMapping(value = "/api/v1/moderators/{id}", method =
	// RequestMethod.GET)
	// @ResponseStatus(HttpStatus.OK)
	// public Moderator getModerator(@PathVariable("id") int id) throws
	// Exception {
	// return storage.getModerator(id);
	// }

	// Update Moderator

	private static final SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-MM-dd-HH");

	static BufferedWriter fw;

	static Map<String, List<RawTweets>> rawTweets = new HashMap<String, List<RawTweets>>();

	@RequestMapping(value = "/api/v1/searchtweets/{ht1}/{ht2}/{from}/{to}", method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.CREATED)
	public void searchTweets(@PathVariable("ht1") String ht1,
			@PathVariable("ht2") String ht2, @PathVariable("from") String from,
			@PathVariable("to") String to) throws Exception {
		String fn = ht1 + ht2 + from + to + ".json";
		String fullFilenName = "/tmp/" + fn;
		fw = new BufferedWriter(new FileWriter(fullFilenName));
		Twitter twitter = new TwitterFactory().getInstance();
		rawTweets.put("ht1", new ArrayList<RawTweets>());
		rawTweets.put("ht2", new ArrayList<RawTweets>());

		try {
			Query query = new Query("#" + ht1 + " OR  #" + ht2);
			query.setSince(from);
			query.setUntil(to);
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
						RawTweets rt = new RawTweets(ht1, df.format(tweet
								.getCreatedAt()), u.getFollowersCount(), lat,
								lon, u.getLocation(), hashlist);
						rawTweets.get("ht1").add(rt);
					}
					if (hasHt2) {
						RawTweets rt = new RawTweets(ht2, df.format(tweet
								.getCreatedAt()), u.getFollowersCount(), lat,
								lon, u.getLocation(), hashlist);
						rawTweets.get("ht2").add(rt);
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
		putToS3(fn, fullFilenName);
	}

	public void putToS3(String fileName, String fullFileName) {
		// credentials object identifying user for authentication
		// user must have AWSConnector and AmazonS3FullAccess for
		// this example to work
		AWSCredentials credentials = new BasicAWSCredentials(
				"key",
				"secret");

		// create a client connection based on credentials
		AmazonS3 s3client = new AmazonS3Client(credentials);
		// create bucket - name must be unique for all S3 users
		String bucketName = "twitter-lambda";

		// upload file to folder and set it to public

		s3client.putObject(new PutObjectRequest(bucketName, fileName, new File(
				fullFileName))
				.withCannedAcl(CannedAccessControlList.PublicRead));

	}

}