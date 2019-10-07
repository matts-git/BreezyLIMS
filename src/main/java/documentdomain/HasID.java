package edu.bu.met.cs665.documentdomain;

import edu.bu.met.cs665.documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasID trait class for static access to 'ID' property.
 */
public interface HasID extends Document {

  default Optional<String> getID() {
    return Optional.ofNullable((String) get(Property.ID.toString()));
  }

}

