package applicationdomain;

import documentdomain.Experiment;
import documentdomain.enums.Property;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import org.apache.log4j.Logger;

/**
 * This class is only required when utilizing user input methods in Application class.
 * Application User Input Classes = userMenu, dataEntryApp, dataRetreivalApp
 * 
 * @author Matthew.Schuckmann
 *
 */
public class ExperimentBuilder {
  
  Map<String, Object> experimentMap;
  Map<String, Object> sampleMap;
  String experimentID;
  String experimentProject;
  Experiment experiment;
  String sampleID;
  String sampleResult;
  
  Scanner userInput = new Scanner(System.in, "utf-8");
  
  private static Logger logger = Logger.getLogger(ExperimentBuilder.class);
  
  /**
   * Method to create Experiment document from user input properties.
   * 
   * @return experiment an Experiment object document
   */ 
  public Experiment inputExperiment() {
    
    experimentMap = new HashMap<>();

    logger.info("Please enter the experiment ID: ");
    experimentID = userInput.nextLine();   
    experimentMap.put(Property.ID.toString(), experimentID);
    
    logger.info("Please enter the associated project: ");
    experimentProject = userInput.nextLine();   
    experimentMap.put(Property.PROJECT.toString(), experimentProject);
    
    experiment = new Experiment(experimentMap);    
    return experiment;
  }
    
  /**
   * Method to create Sample document from user input properties.
   * 
   * @return experiment an Experiment object document containing Sample child documents
   */
  public Experiment inputSample(Experiment experiment) {
    
    sampleMap = new HashMap<>();
    
    logger.info("Please enter the sample ID: ");
    sampleID = userInput.nextLine();
    sampleMap.put(Property.ID.toString(), sampleID);
    
    logger.info("Please enter the experimental result: ");
    sampleResult = userInput.nextLine();
    sampleMap.put(Property.RESULT.toString(), sampleResult);
    
    experiment.put(Property.SAMPLES.toString(), Arrays.asList(sampleMap));   
    return experiment;    
  }
  
}
