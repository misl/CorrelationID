package nl.xup.web.correlation.core;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.UUID;

import org.junit.Test;

/**
 * Test cases for DefaultCorrelationIDFactory
 * 
 * @author misl
 */
public class DefaultCorrelationIDFactoryTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final DefaultCorrelationIDFactory idFactory = new DefaultCorrelationIDFactory();

    // Verify
    assertThat( idFactory, is( notNullValue() ) );
  }

  @Test
  public void testCreateCorrelationID() {
    // Prepare

    // Execute
    final DefaultCorrelationIDFactory idFactory = new DefaultCorrelationIDFactory();
    final UUID uuid = idFactory.createCorrelationID();

    // Verify
    assertThat( uuid, is( notNullValue() ) );
  }
}
