package documentdomain;

import documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasResults trait for static access to 'result' property.
 */
public interface HasResults extends Document {

  default Optional<String> getResults() {
    return Optional.ofNullable((String) get(Property.RESULT.toString()));
  }

}