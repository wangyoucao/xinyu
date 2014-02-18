/*
 * Created on 2012-2-13
 *
 * TODO To describe a star
 * Window - Preferences - Java - Code Style - Code Templates
 */
package together.models;

public class UserMsg {
	private String uid;
	private String longitude;
	private String latitude;
	private String description;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
