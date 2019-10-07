package edu.bu.met.cs665.documentdomain;

import edu.bu.met.cs665.documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasResults trait for static access to 'result' property.
 */
public interface HasResults extends Document {

  default Optional<String> getResults() {
    return Optional.ofNullable((String) get(Property.RESULT.toString()));
  }

}