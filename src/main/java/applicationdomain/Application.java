package applicationdomain;

import datadomain.DBreader;
import datadomain.DBwriter;
import documentdomain.Experiment;
import documentdomain.enums.Property;
import java.util.Arrays;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.apache.log4j.Logger;

/**
 * Main Class for LIMS toy application Abstract Document use case.
 * User input methods are included, but CS665 submission on 2019-8-20 excludes use of these.
 * The 2019-8-20 submission main method is a simple demonstration method.
 * Application User Input Classes = userMenu, dataEntryApp, dataRetreivalApp. 
 * The Abstract Document pattern enables flexible handling of additional, non-static
 * properties. This pattern uses the concept of traits (or properties) to enable type 
 * safety and separate properties of different classes into set of interfaces. Traits 
 * are defined in a manner allowing access to properties in a static static way.
 * 
 * @author Matthew Schuckmann
 * 
 */
public class Application {
  
  ExperimentBuilder userExperiment;
  DBwriter dbwrite;
  String anotherSample;
  DBreader dbtestRead;
  
  Scanner userInput = new Scanner(System.in, "utf-8");
  
  private static Logger logger = Logger.getLogger(Application.class);

  /**
   * Default constructor.
   */
  public Application() {
  }
  
  /**
   * Program entry point.
   * Demonstration method - user input functionality is currently commented out - see
   * first three lines of method
   *
   * @param args command line arguments
   */
  public static void main(String[] args)  {
    
    // To utilize user interface requiring user input, run the following lines
    //Application run = new Application();
    //run.userMenu();
    
    // Test application
    logger.info("->Experiment document creation");
    Map<String, Object> soyHemExperiment = new HashMap<>();
    soyHemExperiment.put(Property.ID.toString(), "Soy Bean Mass Spec");
    soyHemExperiment.put(Property.PROJECT.toString(), "Soy Bean");
    
    Map<String, Object> sampleSH001 = new HashMap<>();
    sampleSH001.put(Property.ID.toString(), "SH-001");
    sampleSH001.put(Property.RESULT.toString(), "150.5 kD");
    logger.info("Sample data for SH-001 entered");
    
    Map<String, Object> sampleSH002 = new HashMap<>();
    sampleSH002.put(Property.ID.toString(), "SH-002");
    sampleSH002.put(Property.RESULT.toString(), "151.0 kD");
    logger.info("Sample data for SH-002 entered");
    
    Map<String, Object> sampleZX001 = new HashMap<>();
    sampleZX001.put(Property.ID.toString(), "ZX-001");
    sampleZX001.put(Property.RESULT.toString(), "75.0 kD");
    logger.info("Sample data for ZX-001 entered");
    
    logger.info("->Put Sample sub-document children into Experiment Document");
    soyHemExperiment.put(Property.SAMPLES.toString(), Arrays.asList(sampleSH001, sampleSH002,
        sampleZX001));
    
    logger.info("->Enter Document data into database");
    Experiment experiment1 = new Experiment(soyHemExperiment);   
    DBwriter testDBwriter = new DBwriter();   
    testDBwriter.writeExperiment(experiment1); 
    
    logger.info("->Read Document data");
    DBreader testDBreader = new DBreader();    
    testDBreader.readExperiment(experiment1);   
  }
  
  /**
   * User input method providing user menu.
   * @throws ClosingException custom exception to close application
   * 
   */
  public void userMenu() {
    logger.info("---------");
    logger.info("User Menu");
    logger.info("---------\n");
    logger.info("Please select a function by entering the selection number:\n");
    logger.info("1: Enter experiment data\n");
    logger.info("2: View existing experiment data\n");
    logger.info("3: Exit\n");
    try {
      int menuSelection = userInput.nextInt();
      switch (menuSelection) {
        case 1:
          dataEntryApp();
          break;
        case 2:
          dataRetrievalApp();
          break;
        case 3:          
          throw new ClosingException("Exiting application.");
        default:
          logger.info("Invalid input, please try again.");
          userInput.close();
          userMenu();
      }
    } catch (InputMismatchException e) {
      logger.info("Invalid input.");
      userInput.close();
    } catch (IllegalArgumentException ex) {
      logger.info("Invalid input.");
      userInput.close();
    } catch (ClosingException clex) {
      userInput.close();
      logger.info("Thanks for using Breazy LIMS!");
    }
  }
  
  /**
   * User Input method to accept user input for experiment data database entry.
   * 
   */
  public void dataEntryApp() {
    
    logger.info("--------------------------------");
    logger.info("Input Experiment and Sample Data");
    logger.info("--------------------------------\n");
   
    userExperiment = new ExperimentBuilder();    
    dbwrite = new DBwriter();
    
    //experiment = userExperiment.inputExperiment();    
    //experimentPlus = userExperiment.inputSample(experiment);
    dbwrite.writeExperiment(userExperiment.inputSample(userExperiment.inputExperiment()));
    
    logger.info("\n-----------------------------------------------------------");
    logger.info("Would you like to enter additional data? Y or N");
    logger.info("-----------------------------------------------------------");
    
    userInput = new Scanner(System.in, "utf-8");
    anotherSample = userInput.nextLine();
    
    while (anotherSample.contains("Y") || anotherSample.contains("y")) {
      
      dbwrite.writeExperiment(userExperiment.inputSample(userExperiment.inputExperiment()));
      
      logger.info("\n-----------------------------------------------------------");
      logger.info("Would you like to enter additional data? Y or N");
      logger.info("-----------------------------------------------------------");
      
      anotherSample = userInput.nextLine();
    } 
    logger.info("System closing.");
    userInput.close();
   
    try {
      throw new ClosingException("Exiting application");
    } catch (ClosingException e) {
      logger.info("Thanks for using Breazy LIMS!");
    }    
  }
  
  /**
   * User Input method for retrieving experiment data from database.
   * 
   */
  public void dataRetrievalApp() {
    logger.info("--------------------------------");
    logger.info("Retrieve Experiment Data");
    logger.info("--------------------------------\n");
         
    userExperiment = new ExperimentBuilder();       
    dbtestRead = new DBreader();    
    dbtestRead.readExperiment(userExperiment.inputExperiment());
       
    logger.info("\n-----------------------------------------------------------");
    logger.info("Would you like to retrieve data for another experiment? Y or N");
    logger.info("-----------------------------------------------------------");
    
    userInput = new Scanner(System.in, "utf-8");
    anotherSample = userInput.nextLine();
    
    if (anotherSample.contains("Y") || anotherSample.contains("y")) {
      dataRetrievalApp();      
    } else {  
      logger.info("System closing.");
      userInput.close();
      
      try {
        throw new ClosingException("Exiting application");
      } catch (ClosingException e) {
        logger.info("Exiting application. Thanks for using Breazy LIMS!");
      }
    }
  }
  
}