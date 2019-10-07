package applicationdomain;

/**
 * This custom exception is used to exit the program. Necessitated by Maven Surefire
 * lack of support for System.exit() method.
 * 
 * @author Matthew Schuckmann
 *
 */

public class ClosingException extends Exception { 
  
  /**
   * Default serialization ID.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Constructor taking message as argument.
   * 
   * @param message String representing message
   */
  public ClosingException(String message) {
    super(message);
  }
}