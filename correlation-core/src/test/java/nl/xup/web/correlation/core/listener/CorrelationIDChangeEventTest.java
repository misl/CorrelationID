package nl.xup.web.correlation.core.listener;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

/**
 * Test cases for CorrelationIDChangeEvent
 * 
 * @author misl
 */
public class CorrelationIDChangeEventTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructorNew() {
    // Prepare

    // Execute
    final CorrelationIDChangeEvent event =
        new CorrelationIDChangeEvent( Thread.currentThread(), "new" );

    // Verify
    assertThat( event.getThread(), is( Thread.currentThread() ) );
    assertThat( event.isCreated(), is( true ) );
    assertThat( event.getOldValue(), is( nullValue() ) );
    assertThat( event.getNewValue(), is( equalTo( "new" ) ) );
  }

  @Test
  public void testConstructorModify() {
    // Prepare

    // Execute
    final CorrelationIDChangeEvent event =
        new CorrelationIDChangeEvent( Thread.currentThread(), "old", "new" );

    // Verify
    assertThat( event.getThread(), is( Thread.currentThread() ) );
    assertThat( event.isCreated(), is( false ) );
    assertThat( event.getOldValue(), is( equalTo( "old" ) ) );
    assertThat( event.getNewValue(), is( equalTo( "new" ) ) );
  }

  @Test
  public void testConstructorModifyWithNullOld() {
    // Prepare

    // Execute
    final CorrelationIDChangeEvent event = new CorrelationIDChangeEvent( null, null, "new" );

    // Verify
    assertThat( event.getThread(), is( nullValue() ) );
    assertThat( event.isCreated(), is( true ) );
    assertThat( event.getOldValue(), is( nullValue() ) );
    assertThat( event.getNewValue(), is( equalTo( "new" ) ) );
  }

  // Setters where tested implicitly
}
