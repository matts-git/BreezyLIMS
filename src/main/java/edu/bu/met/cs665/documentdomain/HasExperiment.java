package edu.bu.met.cs665.documentdomain;

import edu.bu.met.cs665.documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasExperiment trait class for static access to 'experiment' property.
 */
public interface HasExperiment extends Document {

  default Optional<String> getExperiment() {
    return Optional.ofNullable((String) get(Property.EXPERIMENT.toString()));
  }

}
