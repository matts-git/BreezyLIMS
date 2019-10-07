package edu.bu.met.cs665.documentdomain;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Document interface for abstract document design pattern.
 */
public interface Document {

  /**
   * Put value corresponding to trait key.
   *
   * @param key element key
   * @param value element value
   * @return Void
   */
  Void put(String key, Object value);

  /**
   * Get the trait key value.
   *
   * @param key element key
   * @return value or null
   */
  Object get(String key);

  /**
   * Get stream of child documents.
   *
   * @param key         element key
   * @param constructor constructor of child class
   * @return child documents
   */
  <T> Stream<T> children(String key, Function<Map<String, Object>, T> constructor);
}
