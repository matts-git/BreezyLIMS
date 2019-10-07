package datadomain;

import applicationdomain.ClosingException;
import documentdomain.Experiment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Class providing methods for querying database.
 * 
 * @author Matthew Schuckmann
 *
 */
public class DBreader {
  
  Connection conn = null;
  PreparedStatement reportStmt = null;
  ResultSet reportResult = null; 
  ConnectionSingleton jdbc;
  
  private static Logger logger = Logger.getLogger(DBreader.class);
    
  /**
   * Method for querying database for experiment data.
   * 
   * @param ex represents an Experiment object to be queried.
   */
  public void readExperiment(Experiment ex) {
     
    try {

      jdbc = ConnectionSingleton.getInstance();
      conn = jdbc.getConnection();
       
      reportStmt = conn.prepareStatement("SELECT * FROM resultdata WHERE "
          + "experiment = ? AND project = ?");
      
      reportStmt.setString(1, ex.getID().get());
      reportStmt.setString(2, ex.getProject().get());
    
      reportResult = reportStmt.executeQuery();
     
      if (reportResult.next()) {
        logger.info("\nRecords Located for sample: " + ex.getID().get());
      } else {
        logger.info("Sample not found. System exiting.");
        try {
          throw new ClosingException("Exiting application");
        } catch (ClosingException e) {
          reportStmt.close();
          conn.close();
          logger.info("Exiting application. Thanks for using Breazy LIMS!");
        }
      }
                
      // Metadata object instantiation to provide parameters for outputting 
      // database tables to console
      ResultSetMetaData metaReport = reportResult.getMetaData();
      
      // Loop intended to output formatted table 'users' column names to console
      for (int i = 1; i <= metaReport.getColumnCount(); i++) {
        System.out.printf("%-20s", metaReport.getColumnName(i));
      } 
      
      // Must reset cursor since reportResult.next() in above if statement caused it to move
      // https://stackoverflow.com/questions/15819153/moving-resultset-cursor-forward-and-backward-in-a-jdbc-application
      reportResult = reportStmt.executeQuery();
      metaReport = reportResult.getMetaData();
      logger.info("\n");
      // Loop intended to output formatted table records to console 
      while (reportResult.next()) {
        for (int i = 1; i <= metaReport.getColumnCount(); i++) {
          System.out.printf("%-20s", reportResult.getObject(i));
        }
        System.out.println(); 
      }

      System.out.println();
      reportStmt.close();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      if (reportResult != null) {
        try {
          reportResult.close();
        } catch (SQLException e) { /* ignored */ }
      }
      if (reportStmt != null) {
        try {
          reportStmt.close();
        } catch (SQLException e) { /* ignored */ }
      }
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) { /* ignored */ }
      }
    }
  }
    
}

