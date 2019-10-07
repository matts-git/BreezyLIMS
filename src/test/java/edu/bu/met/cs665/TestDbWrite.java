package edu.bu.met.cs665;

import edu.bu.met.cs665.datadomain.DBwriter;
import edu.bu.met.cs665.documentdomain.Experiment;
import edu.bu.met.cs665.documentdomain.enums.Property;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.PropertyConfigurator;

import org.junit.Test;

/**
 * Test class for DBwriter.
 */
public class TestDbWrite {
  
  @Test
  public void shouldExecuteAppWithoutException() {
    
    PropertyConfigurator.configure("log4j.properties");
       
    Map<String, Object> soyHemExperiment = new HashMap<>();
    soyHemExperiment.put(Property.ID.toString(), "Soy Bean Mass Spec");
    soyHemExperiment.put(Property.PROJECT.toString(), "Soy Bean");
    

    Map<String, Object> sampleSH001 = new HashMap<>();
    sampleSH001.put(Property.ID.toString(), "SH-001");
    sampleSH001.put(Property.RESULT.toString(), "150.5 kD");
    

    Map<String, Object> sampleSH002 = new HashMap<>();
    sampleSH002.put(Property.ID.toString(), "SH-002");
    sampleSH002.put(Property.RESULT.toString(), "151.0 kD");
    
    Map<String, Object> sampleHS001 = new HashMap<>();
    sampleHS001.put(Property.ID.toString(), "HS-001");
    sampleHS001.put(Property.RESULT.toString(), "75.0 kD");

    soyHemExperiment.put(Property.SAMPLES.toString(), Arrays.asList(sampleSH001, sampleSH002,
        sampleHS001));                        
    
    System.out.println("\nSummary:");
    
    Experiment experiment1 = new Experiment(soyHemExperiment);
    
    System.out.println("\nSummary:");
    
    System.out.println("-> Experiment: " + experiment1.getID().get());
    
    System.out.println("-> Sample Details: ");
     
    System.out.printf("%-20s%-20s%n", "|ID|", "|Result|");
    System.out.printf("%-20s%-20s%n", "----", "--------");
    
    experiment1.getSamples().forEach(samp -> System.out.printf("%-20s%-20s%n",
        samp.getID().get(), samp.getResults().get()));
    
    System.out.println("\n\n-> Database Entry Test: ");
    
    DBwriter testDBwriter = new DBwriter();
    
    testDBwriter.writeExperiment(experiment1);
     
  }

}
