package datadomain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Singleton class providing a Derby database connection.
 * 
 * @author Matthew Schuckmann
 *
 */
public final class ConnectionSingleton {
  
  // Eager Singleton initialization created at the time of class loading
  // https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples
  private static final ConnectionSingleton derbconn =  new ConnectionSingleton();
  
  /**
   * Default constructor.
   */
  private ConnectionSingleton() {
  }
  
  /**
   * Get instance of Singleton object.
   * 
   * @return derbconn a ConnectionSingleton object
   */
  public static ConnectionSingleton getInstance() {
    return derbconn;
  }
  
  /**
   * Method for other classes to call to get Singleton connection object.
   * 
   * @return conn a Derby database connection
   */
  public Connection getConnection() {
    final String dbName = "limsdb";
    final String dbPath = "jdbc:derby:" + dbName + ";create=false;user=matt;password=nopassw";
    Connection conn = null;
    
    try {
      conn = DriverManager.getConnection(dbPath);
    } catch (SQLException ex) {
      ex.printStackTrace();
    } 
    return conn;
  }
  
}

