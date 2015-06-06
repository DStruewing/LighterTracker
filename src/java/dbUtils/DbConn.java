package dbUtils;

import java.sql.DriverManager;
import java.sql.Connection;

/** Wrapper class for database connection.  
 *  Constructor opens connection.  Close method closes connection.  */
public class DbConn {

    private String errMsg = ""; // will remain "" unless error getting connection
    private String connectionMsg = "Connection Error-Uninitialized."; // log of getting connection
    private Connection conn = null;

    /** Constructor - opens database connection to database, 
     * This version determines if the app is running locally or not (by checking if "temple.edu"
     * is at the end of the hostname of the machine on which you are running your app).
     */
    public DbConn() {
        this.connect(this.isTemple());
    } // method

    /** Constructor - opens database connection to database, 
    This version uses boolean input parameter to determine if the app is running locally or not */
    public DbConn(boolean isTemple) {
        this.connect(isTemple);
    }

    /** Open a connection to your database either using the Temple connection string or the local 
     * connection string.
     * @param isTemple: if this is true, it will use the Temple connection string (else it will 
     * use the local connection string).
     */
    private void connect(boolean isTemple) {
        this.connectionMsg = "";
        try {
            this.connectionMsg += "ready to get driver... <br/>";
            String DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER).newInstance();
            this.connectionMsg += "got the driver... <br/>";
            try {
                
                // Assume you are running from home using tunneling...
                String url = "jdbc:mysql://localhost:3307/SP15_2308_tud04734?user=tud04734&password=aweevaim";
                
                // unless you are working from temple (wachman hall)
                if (isTemple) {
                    url = "jdbc:mysql://cis-linux2.temple.edu:3306/SP15_2308_tud04734?user=tud04734&password=aweevaim";  
                    this.connectionMsg += "Working from CIS network - no tunneling. <br/>";
                }                 
                this.conn = DriverManager.getConnection(url);
                this.connectionMsg += "Connected successfully. <br/>";

            } catch (Exception e) { // cant get the connection
                this.connectionMsg += "problem getting connection:" + e.getMessage() + "<br/>";
                this.errMsg = "problem getting connection:" + e.getMessage();
            }
        } catch (Exception e) { // cant get the driver...
            this.connectionMsg += "problem getting driver:" + e.getMessage() + "<br/>";
            this.errMsg = "problem getting driver:" + e.getMessage();
        }
    } // method

    /** Returns database connection for use in SQL classes.  */
    public Connection getConn() {
        return this.conn;
    }

    /** Returns database connection error message or "" if there is none.  */
    public String getErr() {
        return this.errMsg;
    }

    /** Returns debugging message or database connection error message if there is one.  */
    public String getConnectionMsg() {
        return this.connectionMsg;  // will have messages even if OK.
    }

    /** Close database connection.  */
    public void close() {
        // be careful - you can get an error trying to
        // close a connection if it is null.
        if (conn != null) {
            try {
                conn.close();
            } // try
            catch (Exception e) {
                errMsg = "Error closing connection in DbConn: "
                        + e.getMessage();
                System.out.println(errMsg);
                //e.printStackTrace();
            } // catch
        } // if
    } // method

    /** Checks the hostname to see if app is running at Temple or not.  */
    private boolean isTemple() {
        boolean temple = false;
        try {
            String hostName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            hostName = hostName.toLowerCase();
            if (hostName.endsWith("temple.edu")) {
                temple = true;
                System.out.println("************* Running from Temple, so using cis-linux2 for db connection");
            } else {
                System.out.println("************* Not running from Temple, so using local for db connection");
            }
        } catch (Exception e) {
            System.out.println("************* Unable to get hostname. " + e.getMessage());
        }
        return temple;
    }
} // class