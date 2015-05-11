package my.sjsu.twlambda.tw;

import java.util.ArrayList;
import java.util.List;

public class RawTweets {
	public String hashTag;
	public String time;
	public int followerCount;
	double latitude;
	double longitude;
	String location;
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	List<String> hashes = new ArrayList<String>();

	public RawTweets(String hashTag, String time, int followerCount, double latitude,
			double longitude, String loc, String[] hashlist) {
		super();
		this.hashTag = hashTag;
		this.time = time;
		this.followerCount = followerCount;
		this.latitude = latitude;
		this.longitude = longitude;
		this.location = loc;
		for (String h : hashlist) {
			hashes.add(h);
		}
	}

	public RawTweets() {
		super();
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getFollowerCount() {
		return followerCount;
	}

	public void setFollowerCount(int followerCount) {
		this.followerCount = followerCount;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public List<String> getHashes() {
		return hashes;
	}

	public void setHashes(List<String> hashes) {
		this.hashes = hashes;
	}

}