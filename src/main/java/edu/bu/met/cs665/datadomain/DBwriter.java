package edu.bu.met.cs665.datadomain;

import edu.bu.met.cs665.documentdomain.Experiment;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import org.apache.log4j.Logger;

/**
 * Class providing methods for writing to database.
 * 
 * @author Matthew Schuckmann
 *
 */
public class DBwriter {
  
  Connection conn = null;
  PreparedStatement reportStmt = null;
  ResultSet reportResult = null;
  ConnectionSingleton jdbc;
   
  String reportString;
  String prepString;
   
  private static Logger logger = Logger.getLogger(DBwriter.class);
  
  /**
   * Method for writing Experiment Document traits to database.
   * 
   * @param ex an Experiment document
   * 
   */
  public void writeExperiment(Experiment ex) {
   
    try {
      
      //ConnectionSingleton jdbc = ConnectionSingleton.getInstance();
      jdbc = ConnectionSingleton.getInstance();
      conn = jdbc.getConnection();
           
      //https://stackoverflow.com/questions/47790227/column-is-either-not-in-any-table-in-the-from-list-java-database-searching
      ex.getSamples().forEach(samp -> {        
        String recordSearch = ("SELECT index FROM resultdata WHERE experiment = '"
            + ex.getID().get()
            + "' AND sample = '" + samp.getID().get() + "' AND project = '"
            + ex.getProject().get() + "'");
           
        try {
          reportStmt = conn.prepareStatement(recordSearch);
        
          reportResult = reportStmt.executeQuery();
         
          if (reportResult.next()) {
            logger.info("\nDuplicate Record Detected!");
            reportResult.close();          
          } 
        } catch (SQLException e) {
          e.printStackTrace();
        }
          
        try {
          // https://stackoverflow.com/questions/4224228/preparedstatement-with-statement-return-generated-keys
          reportStmt = conn.prepareStatement("INSERT INTO resultdata (project, "
              + "experiment, sample, result) VALUES (?, ?, ?, ?)", 
              PreparedStatement.RETURN_GENERATED_KEYS);
          
          reportStmt.setString(1, ex.getProject().get());
          reportStmt.setString(2, ex.getID().get());
          reportStmt.setString(3, samp.getID().get());
          reportStmt.setString(4, samp.getResults().get());
    
          reportStmt.executeUpdate();
                    
          // https://stackoverflow.com/questions/4224228/preparedstatement-with-statement-return-generated-keys
          reportResult = reportStmt.getGeneratedKeys(); 
          
          int key = reportResult.next() ? reportResult.getInt(1) : 0;
    
          if (key != 0) {    
            logger.info("\nGenerated key = " + key + "\n");
          }

          logger.info("New Row Inserted");
                    
          reportStmt = conn.prepareStatement("SELECT * FROM resultdata WHERE index = ?");
          reportStmt.setInt(1, key);
          
          reportResult = reportStmt.executeQuery();
          
          // Metadata object instantiation to provide parameters for outputting
          // database tables to console
          ResultSetMetaData metaReport1 = reportResult.getMetaData();
          
          // Loop intended to output formatted table 'users' column names to console
          for (int i = 1; i <= metaReport1.getColumnCount(); i++) {
            System.out.printf("%-20s", metaReport1.getColumnName(i));
          }
          
          System.out.println();
          
          // Loop intended to output formatted table records to console 
          while (reportResult.next()) {
            for (int i = 1; i <= metaReport1.getColumnCount(); i++) {
              System.out.printf("%-20s", reportResult.getObject(i));
            }
            System.out.println();            
          } 
        } catch (SQLException e1) {
          e1.printStackTrace();
        }
      });

      // End of stream loop
      
      conn.close();
    } catch (SQLException e1) {
      e1.printStackTrace();
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