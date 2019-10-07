package edu.bu.met.cs665.documentdomain;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Abstract implementation of Document interface.
 * 
 * @author Matthew Schuckmann
 */
public abstract class AbstractDocument implements Document {

  // Map containing document properties
  private final Map<String, Object> data;

  /**
   * A fail fast assignment to a map variable.
   * https://stackoverflow.com/questions/45632920/why-should-one-use-objects-requirenonnull/45632962
   * 
   * @param data a map containing document properties
   */
  protected AbstractDocument(Map<String, Object> data) {
    this.data = Objects.requireNonNull(data, "Data map is required");
  }

  /**
   * Method for editing document properties.
   * 
   */
  @Override
  public Void put(String key, Object value) {
    data.put(key, value);
    return null;
  }
  
  /**
   * Method for reading document properties.
   * 
   */
  @Override
  public Object get(String key) {
    return data.get(key);
  }
  
  /**
   * Method for returning stream that allows for traversing child documents.
   * https://dzone.com/articles/create-type-safe-views-of-data-using-abstract-docu
   * https://www.baeldung.com/java-null-safe-streams-from-collections
   * Abstract Document implementation inevitably risks casting errors.
   * Suppressed unchecked cast warning due to inside knowledge of children Object types.
   */
  @SuppressWarnings("unchecked")
  @Override
  public final <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor) {
    final List<Map<String, Object>> children = (List<Map<String, Object>>) get(key);
      
    // Set a single variable to one of two states based on a single condition while
    // checking to prevent null references.
    // http://www.cafeaulait.org/course/week2/43.html
    return children == null ? Stream.empty() : children.stream().map(constructor);
   
  }
  
}