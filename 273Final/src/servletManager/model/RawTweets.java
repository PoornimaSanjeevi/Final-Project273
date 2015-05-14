package servletManager.model;

import java.util.ArrayList;
import java.util.List;

public class RawTweets {
	
	public String time;
	double latitude;
	double longitude;

	List<String> hashes = new ArrayList<String>();

	public RawTweets(String time,  double latitude,
                     double longitude, String[] hashlist) {
		super();
		this.time = time;

		this.latitude = latitude;
		this.longitude = longitude;

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