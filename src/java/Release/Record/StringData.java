/*
 * David Struewing, 2015.
 */
package Release.Record;

/**
 *
 * @author david
 */
public class StringData {

	public String webUserId = "";
	public String lighterId = "";
	public String latitude = "";
	public String longitude = "";
	public String date = "";
	public String recordStatus = "default"; // will be used later when doing ajax
	public String errorMsg = "";

	@Override
	public String toString() {
		return "latitude[" + valueOrNull(latitude) + "] longitude[" + valueOrNull(longitude)
				+ "] date[" + valueOrNull(date)
				+ "] webUserId[" + valueOrNull(webUserId) + "] lighterId[ " + valueOrNull(lighterId)
				+ "] recordStatus[" + valueOrNull(recordStatus) + "]";
	} // toString()

	private String valueOrNull(String in) {
		if (in == null) {
			return "null";
		}
		return in;
	}

	public String toJSON() {
		return "{ latitude: '" + valueOrNull(latitude) + "', longitude: '" + valueOrNull(longitude)
				+ "', date: '" + valueOrNull(date)
				+ "', webUserId: '" + valueOrNull(webUserId) + "', lighterId: '" + valueOrNull(lighterId)
				+ "', recordStatus: '" + valueOrNull(recordStatus) + "' }";
	}
}
