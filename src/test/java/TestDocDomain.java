import static org.junit.Assert.assertEquals;

import documentdomain.Experiment;

import documentdomain.Sample;
import documentdomain.enums.Property;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

/**
 * Test for Experiment and Sample Construction.
 */
public class TestDocDomain {

  // Set project properties
  private static final String TEST_EXPERIMENT_ID = "test-experiment-type";
  private static final String TEST_EXPERIMENT_PROJECT = "test-experiment-project";
  private static final String TEST_EXPERIMENT_SAMPLE = "SP-001";
  
  // Set sample properties
  private static final String TEST_SAMPLE_EXPERIMENT = "test-sample-experiment";
  private static final String TEST_SAMPLE_RESULT = "0.0 test units";

  /**
   * Test method for Experiment Document.
   */
  @Test
  public void testConstructExperiment() {
    Map<String, Object> testExperimentProperties = new HashMap<>();
    testExperimentProperties.put(Property.ID.toString(), TEST_EXPERIMENT_ID);
    testExperimentProperties.put(Property.PROJECT.toString(), TEST_EXPERIMENT_PROJECT);
    testExperimentProperties.put(Property.SAMPLES.toString(), TEST_EXPERIMENT_SAMPLE);
    // Test ability of Experiment to take on an array of samples
    testExperimentProperties.put(Property.SAMPLES.toString(), Arrays.asList(new HashMap<>(),
        new HashMap<>()));
    
    Experiment testExperiment = new Experiment(testExperimentProperties);

    assertEquals(TEST_EXPERIMENT_ID, testExperiment.getID().get());
    assertEquals(TEST_EXPERIMENT_PROJECT, testExperiment.getProject().get());
    assertEquals(2, testExperiment.getSamples().count());
    
  }
  
  /**
   * Test method for Sample Document.
   */
  @Test
  public void testConstructSample() {
    Map<String, Object> sampleSP001Properties = new HashMap<>();
    sampleSP001Properties.put(Property.EXPERIMENT.toString(), TEST_SAMPLE_EXPERIMENT);
    sampleSP001Properties.put(Property.RESULT.toString(), TEST_SAMPLE_RESULT);
    Sample testSample = new Sample(sampleSP001Properties);

    assertEquals(TEST_SAMPLE_EXPERIMENT, testSample.getExperiment().get());
    assertEquals(TEST_SAMPLE_RESULT, testSample.getResults().get());
  }

}
