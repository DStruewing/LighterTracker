package model.Lighter;

public class StringData {

    public String lighterId = "";
    public String lighterName = "";
    public String purchaseDate = "";
    public String lighterColor = "";
	public String trackingCode = "";
    public String recordStatus = "default"; // will be used later when doing ajax

    @Override
    public String toString() {
        return "lighterId[" + valueOrNull(lighterId) + "] lighterName[" + valueOrNull(lighterName)
                + "] purchaseDate[" + valueOrNull(purchaseDate) + "] lighterColor[" + valueOrNull(lighterColor)
                + "] trackingCode[" + valueOrNull(trackingCode) + "] recordStatus[" + valueOrNull(recordStatus) + "]";
    } // toString()

    private String valueOrNull(String in) {
        if (in == null) {
            return "null";
        }
        return in;
    }
    
    public String toJSON() {
        return "({ lighterId: '" + valueOrNull(lighterId) + "', lighterName: '" + valueOrNull(lighterName)
                + "', purchaseDate: '" + valueOrNull(purchaseDate) + "', lighterColor: '" + valueOrNull(lighterColor)
				+ "', trackingCode: '" + valueOrNull(trackingCode) + "', recordStatus: '" + valueOrNull(recordStatus) + "' })";
    }

    public String getLighterId() {
        return lighterId;
    }

    public void setLighterId(String lighterId) {
        this.lighterId = lighterId;
    }

    public String getLighterName() {
        return lighterName;
    }

    public void setLighterName(String lighterName) {
        this.lighterName = lighterName;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
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

    public String getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }
    
    

}
