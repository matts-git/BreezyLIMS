package edu.bu.met.cs665.documentdomain;

import java.util.Map;

/**
 * Sample objects represent child Documents of Experiment objects.
 */
public class Sample extends AbstractDocument implements  HasExperiment, HasProject,
    HasID, HasResults {

  public Sample(Map<String, Object> properties) {
    super(properties);
  }

}
