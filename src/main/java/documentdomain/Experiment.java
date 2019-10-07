package edu.bu.met.cs665.documentdomain;

import java.util.Map;

/**
 * Concrete Document class.
 */
public class Experiment extends AbstractDocument implements HasID,
    HasSamples, HasProject {

  public Experiment(Map<String, Object> properties) {
    super(properties);
  }

}
