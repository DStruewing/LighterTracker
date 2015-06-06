package model.WebUser;

/* This class just bundles together all the pre-validated String values that a 
 * user might enter as part of a Web_User record. 
 */
public class TypedData {

    private Integer webUserId = null;
    private String userEmail = "";
    private String userPw = "";
    private String userPw2 = "";
    private java.math.BigDecimal membershipFee = null;
    private Integer userRoleId = null;
    private java.sql.Date birthday = null;
    
    public String displayHTML() {
        return buildDisplay("<br>");
    }

    public String displayLog() {
        return buildDisplay("\n");
    }

    // pass in "\n" for newline, "<br/>" if to be displayed on jsp page.
    public String buildDisplay(String newLineString) {
        return newLineString
                + "WebUser record" + newLineString
                + "==============" + newLineString
                + "webUserId: " + myToString(this.getWebUserId()) + newLineString
                + "userEmail: " + myToString(this.getUserEmail()) + newLineString
                + "userPw: " + myToString(this.getUserPw()) + newLineString
                + "userPw2: " + myToString(this.getUserPw2()) + newLineString
                + "membershipFee: " + myToString(this.getMembershipFee()) + newLineString
                + "userRoleId: " + myToString(this.getUserRoleId()) + newLineString
                + "birthday: " + myToString(this.getBirthday()) + newLineString;
    }

    private String myToString(Object obj) {
        if (obj == null) {
            return "null";
        } else {
            return obj.toString();
        }
    }

    
    /**
     * @return the webUserId
     */
    public Integer getWebUserId() {
        return webUserId;
    }

    /**
     * @param webUserId the webUserId to set
     */
    public void setWebUserId(Integer webUserId) {
        this.webUserId = webUserId;
    }
    
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
     * @return the userPw
     */
    public String getUserPw() {
        return userPw;
    }

    /**
     * @param userPw the userPw to set
     */
    public void setUserPw(String userPw) {
        this.userPw = userPw;
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
    public java.math.BigDecimal getMembershipFee() {
        return membershipFee;
    }

    /**
     * @param membershipFee the membershipFee to set
     */
    public void setMembershipFee(java.math.BigDecimal membershipFee) {
        this.membershipFee = membershipFee;
    }

    /**
     * @return the userRoleId
     */
    public Integer getUserRoleId() {
        return userRoleId;
    }

    /**
     * @param userRoleId the userRoleId to set
     */
    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    /**
     * @return the birthday
     */
    public java.sql.Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(java.sql.Date birthday) {
        this.birthday = birthday;
    }
}