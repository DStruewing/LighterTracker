package model.Lighter;

import java.sql.Date;

/* This class just bundles together all the pre-validated String values that a 
 * user might enter as part of a Web_User record. 
 */
public class TypedData {

    private Integer lighterId = null;
    private String lighterName = "";
    private java.sql.Date purchaseDate = null;
	private String trackingCode = "";
    private String lighterColor = "";
    
    public String displayHTML() {
        return buildDisplay("<br>");
    }

    public String displayLog() {
        return buildDisplay("\n");
    }

    // pass in "\n" for newline, "<br/>" if to be displayed on jsp page.
    public String buildDisplay(String newLineString) {
        return newLineString
                + "Lighter record" + newLineString
                + "==============" + newLineString
                + "lighterId: " + myToString(this.getLighterId()) + newLineString
                + "userEmail: " + myToString(this.getLighterName()) + newLineString
                + "purchaseDate: " + myToString(this.getPurchaseDate()) + newLineString
                + "lighterColor: " + myToString(this.getLighterColor()) + newLineString
				+ "trackingCode: " + myToString(this.getTrackingCode()) + newLineString;
    }

    private String myToString(Object obj) {
        if (obj == null) {
            return "null";
        } else {
            return obj.toString();
        }
    }

    public Integer getLighterId() {
        return lighterId;
    }

    public void setLighterId(Integer lighterId) {
        this.lighterId = lighterId;
    }

    public String getLighterName() {
        return lighterName;
    }

    public void setLighterName(String lighterName) {
        this.lighterName = lighterName;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(java.sql.Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getLighterColor() {
        return lighterColor;
    }

    public void setLighterColor(String lighterColor) {
        this.lighterColor = lighterColor;
    }
	
	public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }

}