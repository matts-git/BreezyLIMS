package documentdomain;

import documentdomain.enums.Property;

import java.util.Optional;

/**
 * HasProject trait class for static access to 'project' property.
 */
public interface HasProject extends Document {

  default Optional<String> getProject() {
    return Optional.ofNullable((String) get(Property.PROJECT.toString()));
  }

}
