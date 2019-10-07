package documentdomain;

import documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasID trait class for static access to 'ID' property.
 */
public interface HasID extends Document {

  default Optional<String> getID() {
    return Optional.ofNullable((String) get(Property.ID.toString()));
  }

}

