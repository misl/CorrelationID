
package nl.xup.web.correlation.factory.timestamp;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;

import org.junit.Test;

/**
 * Test cases for TimestampCorrelationIDFactory
 * 
 * @author misl
 */
public class TimestampCorrelationIDFactoryTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final TimestampCorrelationIDFactory idFactory = new TimestampCorrelationIDFactory();

    // Verify
    assertThat( idFactory, is( notNullValue() ) );
  }

  @Test
  public void testCreateCorrelationID() {
    // Prepare

    // Execute
    final TimestampCorrelationIDFactory idFactory = new TimestampCorrelationIDFactory();
    final LocalDateTime uuid = idFactory.createCorrelationID();

    // Verify
    assertThat( uuid, is( notNullValue() ) );
  }
}
