package model.Record;

import java.sql.Date;

public class TypedData {
    
    private Float latitude;
    private Float longitude;
    private Date date;
    private String condition;
    private Integer webUserId;
    private Integer lighterId;
    
    public String displayHTML() {
        return buildDisplay("<br>");
    }

    public String displayLog() {
        return buildDisplay("\n");
    }
    
    // pass in "\n" for newline, "<br/>" if to be displayed on jsp page.
    public String buildDisplay(String newLineString) {
        return newLineString
                + "TrackingRecord record" + newLineString
                + "==============" + newLineString
                + "latitude: " + myToString(this.getLatitude()) + newLineString
                + "longitude: " + myToString(this.getLongitude()) + newLineString
                + "date: " + myToString(this.getDate()) + newLineString
                + "condition: " + myToString(this.getCondition()) + newLineString
                + "webUserId: " + myToString(this.getWebUserId()) + newLineString
                + "lighterId: " + myToString(this.getLighterId()) + newLineString;
    }
    
    private String myToString(Object obj) {
        if (obj == null) {
            return "null";
        } else {
            return obj.toString();
        }
    }
    
    public boolean isReady() {
        return (this.latitude != null
                && this.longitude != null
                && this.date != null
                && this.condition != null
                && this.condition.length() != 0
                && webUserId != null
                && lighterId != null);
    }
    
    public void clear() {
        this.latitude = null;
        this.longitude = null;
        this.date = null;
        this.condition = null;
        this.webUserId = null;
        this.lighterId = null;
    }
    
    public TypedData() {
        this.clear();
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
    
    public Integer getWebUserId() {
        return webUserId;
    }

    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }

    public Integer getLighterId() {
        return lighterId;
    }

    public void setLighterId(Integer lighterId) {
        this.lighterId = lighterId;
    }
}
