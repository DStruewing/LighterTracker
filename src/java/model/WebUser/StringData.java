package model.WebUser;

public class StringData {

    public String webUserId = "";
    public String userEmail = "";
    public String userPw = "";
    public String userPw2 = "";
    public String membershipFee = "";
    public String userRoleId = "";
    public String birthday = "";
    public String recordStatus = "default"; // will be used later when doing ajax
    public String errorMsg = "";

    /**
     * @return the userEmail
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * @param userEmail the userEmail to set
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * @return the userPassword
     */
    public String getUserPw() {
        return userPw;
    }

    /**
     * @param userPassword the userPassword to set
     */
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    /**
     * @return the userRoleId
     */
    public String getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId the userRoleId to set
     */
    public void setUserRole(String userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return the dateAdded
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param dateAdded the dateAdded to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the userPw2
     */
    public String getUserPw2() {
        return userPw2;
    }

    /**
     * @param userPw2 the userPw2 to set
     */
    public void setUserPw2(String userPw2) {
        this.userPw2 = userPw2;
    }

    /**
     * @return the membershipFee
     */
    public String getMembershipFee() {
        return membershipFee;
    }

    /**
     * @param membershipFee the membershipFee to set
     */
    public void setMembershipFee(String membershipFee) {
        this.membershipFee = membershipFee;
    }

    /**
     * @return the webUserId
     */
    public String getWebUserId() {
        return webUserId;
    }

    /**
     * @param webUserId the webUserId to set
     */
    public void setWebUserId(String webUserId) {
        this.webUserId = webUserId;
    }

    /**
     * @return the recordStatus
     */
    public String getRecordStatus() {
        return recordStatus;
    }

    /**
     * @param recordStatus the recordStatus to set
     */
    public void setRecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return "webUserId[" + valueOrNull(webUserId) + "] userEmail[" + valueOrNull(userEmail)
                + "] userPw[" + valueOrNull(userPw) + "] userPw2[" + valueOrNull(userPw2)
                + "] membershipFee[" + valueOrNull(membershipFee) + "] userRoleId[ " + valueOrNull(userRoleId)
                + "] dateAdded[" + valueOrNull(birthday) + "] recordStatus[" + valueOrNull(recordStatus) + "]";
    } // toString()

    private String valueOrNull(String in) {
        if (in == null) {
            return "null";
        }
        return in;
    }

    public String toJSON() {
        return "({ webUserId: '" + valueOrNull(webUserId) + "', userEmail: '" + valueOrNull(userEmail)
                + "', userPw: '" + valueOrNull(userPw) + "', userPw2: '" + valueOrNull(userPw2)
                + "', membershipFee: '" + valueOrNull(membershipFee) + "', userRoleId: '" + valueOrNull(userRoleId)
                + "', birthday: '" + valueOrNull(birthday) + "', recordStatus: '" + valueOrNull(recordStatus) + "' })";
    }
} // class