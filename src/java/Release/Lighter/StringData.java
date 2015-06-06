/*
 * David Struewing, 2015.
 */
package Release.Lighter;

import org.json.JSONObject;

/**
 *
 * @author david
 */
public class StringData {
    
    public String lighterId = "";
    public String name = "";
    public String color = "";
    public String errorMsg = "";
    
    private String valueOrNull(String in) {
        if (in == null) {
            return "null";
        }
        return in;
    }
    
    public String toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("lighterId", lighterId);
		obj.put("name", name);
		obj.put("color", color);
		obj.put("errorMsg", errorMsg);
		return obj.toString();
//        return "({ lighterId: '" + valueOrNull(lighterId) + "', lighterName: '" + valueOrNull(name)
//                + "', lighterColor: '" + valueOrNull(color) + "', errorMsg: '" + valueOrNull(errorMsg) + "' })";
    }
    
}
