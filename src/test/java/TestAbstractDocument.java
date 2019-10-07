package edu.bu.met.cs665;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import edu.bu.met.cs665.documentdomain.AbstractDocument;

import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.Test;

/**
 * AbstractDocument test class.
 */
public class TestAbstractDocument {

  private static final String KEY = "trait";
  private static final String VALUE = "value";

  private class ConcreteDoc extends AbstractDocument {

    ConcreteDoc(Map<String, Object> properties) {
      super(properties);
    }
  }

  private ConcreteDoc document = new ConcreteDoc(new HashMap<>());

  @Test
  public void valueTest() {
    document.put(KEY, VALUE);
    assertEquals(VALUE, document.get(KEY));
  }

  @Test
  public void childrenTest() {
    Map<String, Object> child1 = new HashMap<>();
    Map<String, Object> child2 = new HashMap<>();
    List<Map<String, Object>> children = Arrays.asList(child1, child2);

    document.put(KEY, children);

    Stream<ConcreteDoc> childrenStream = document
        .children(KEY, ConcreteDoc::new);
    assertNotNull(children);
    assertEquals(2, childrenStream.count());
  }

  @Test
  public void emptyChildStream() {
    Stream<ConcreteDoc> children = document.children(KEY, ConcreteDoc::new);
    assertNotNull(children);
    assertEquals(0, children.count());
  }

}
