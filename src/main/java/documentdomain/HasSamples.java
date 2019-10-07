package documentdomain;

import documentdomain.enums.Property;

import java.util.stream.Stream;

/**
 * HasSamples trait for static access to 'Sample' property.
 */
public interface HasSamples extends Document {

  /**
   * Method to collect a stream of Sample child documents.
   * Syntax: Class name::method name is used to Call a method by 
   * referring to it with the help of its class directly
   * http://tcljava.sourceforge.net/docs/TclJava/JavaNewCmd.html
   * https://www.geeksforgeeks.org/double-colon-operator-in-java/
   * 
   * @return children stream of Sample documents
   */
  default Stream<Sample> getSamples() {
    return children(Property.SAMPLES.toString(), Sample::new);
  }

}
