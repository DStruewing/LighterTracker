/*
 * David Struewing, 2015.
 */
package Release.WebUser;

import org.json.JSONObject;

/**
 *
 * @author david
 */
public class StringData {
    
    public String webUserId = "";
    public String email = "";
    public String password1 = "";
    public String password2 = "";
    public String errorMsg = "";
    
    private String valueOrNull(String in) {
        if (in == null) {
            return "null";
        }
        return in;
    }
    
    public String toJSON() {
		JSONObject obj = new JSONObject();
		obj.put("webUserId", webUserId);
		obj.put("email", email);
		obj.put("errorMsg", errorMsg);
		return obj.toString();
    }
}
