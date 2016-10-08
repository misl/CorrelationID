package nl.xup.web.correlation.core;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import nl.xup.web.correlation.core.listener.CorrelationIDChangeEvent;
import nl.xup.web.correlation.core.listener.CorrelationIDChangeListener;

/**
 * Test cases for CorrelationIDManager
 * 
 * @author misl
 */
public class CorrelationIDManagerTest {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private CorrelationIDChangeListener listener = mock( CorrelationIDChangeListener.class );

  // --------------------------------------------------------------------------
  // Setup / teardown
  // --------------------------------------------------------------------------

  @Before
  public void setup() {
    CorrelationIDManager.addChangeListener( listener );
  }

  @After
  public void teardown() {
    CorrelationIDManager.clear();
    CorrelationIDManager.removeChangeListener( listener );
  }

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testDefaultCorrelationIDName() {
    // Prepare

    // Execute
    final String name = CorrelationIDManager.getName();

    // Verify
    assertThat( name, is( notNullValue() ) );
    assertThat( name.isEmpty(), is( false ) );
  }

  @Test
  public void testCorrelationIDName() {
    // Prepare
    final String name = "my-correlation-id";

    // Execute
    CorrelationIDManager.setName( name );

    // Verify
    final String correlationIDName = CorrelationIDManager.getName();
    assertThat( correlationIDName, is( notNullValue() ) );
    assertThat( correlationIDName.isEmpty(), is( false ) );
    assertThat( correlationIDName, is( equalTo( name ) ) );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameNull() {
    // Prepare

    // Execute
    CorrelationIDManager.setName( null );

    // Verify
    fail();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetNameEmpty() {
    // Prepare

    // Execute
    CorrelationIDManager.setName( "" );

    // Verify
    fail();
  }

  @Test
  public void testGetCorrelationIDUnset() {
    // Prepare

    // Execute
    Object correlationID = CorrelationIDManager.getCorrelationID();

    // Verify
    assertThat( correlationID, is( nullValue() ) );
  }

  @Test
  public void testGetCorrelationIDSet() {
    // Prepare
    CorrelationIDManager.setCorrelationID( "testID" );

    // Execute
    Object correlationID = CorrelationIDManager.getCorrelationID();

    // Verify
    assertThat( correlationID, is( notNullValue() ) );
    assertThat( correlationID, is( equalTo( "testID" ) ) );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCorrelationIDNull() {
    // Prepare

    // Execute
    CorrelationIDManager.setCorrelationID( null );

    // Verify
    fail();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSetCorrelationIDEmpty() {
    // Prepare

    // Execute
    CorrelationIDManager.setCorrelationID( "" );

    // Verify
    fail();
  }

  @Test
  public void testCreateCorrelationID() {
    // Prepare
    CorrelationIDManager.clear();

    // Execute
    Object correlationID1 = CorrelationIDManager.createCorrelationID();
    Object correlationID2 = CorrelationIDManager.getCorrelationID();

    // Verify
    assertThat( correlationID1, is( notNullValue() ) );
    assertThat( correlationID1, is( correlationID2 ) );
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddNullListener() {
    // Prepare

    // Execute
    CorrelationIDManager.addChangeListener( null );

    // Verify
    fail();
  }

  @Test
  public void testListenerEventFirst() {
    // Prepare
    ArgumentCaptor<CorrelationIDChangeEvent> argument =
        ArgumentCaptor.forClass( CorrelationIDChangeEvent.class );

    // Execute
    CorrelationIDManager.setCorrelationID( "123" );
    CorrelationIDManager.removeChangeListener( listener );

    // Verify
    verify( listener ).correlationIDChanged( argument.capture() );
    assertThat( (String) argument.getValue().getNewValue(), is( "123" ) );
    assertThat( argument.getValue().getOldValue(), is( nullValue() ) );
    assertThat( argument.getValue().isCreated(), is( true ) );
  }

  @Test
  public void testListenerEventCreated() {
    // Prepare
    ArgumentCaptor<CorrelationIDChangeEvent> argument =
        ArgumentCaptor.forClass( CorrelationIDChangeEvent.class );

    // Execute
    CorrelationIDManager.createCorrelationID();
    CorrelationIDManager.removeChangeListener( listener );

    // Verify
    verify( listener ).correlationIDChanged( argument.capture() );
    assertThat( argument.getValue().getNewValue(), is( notNullValue() ) );
    assertThat( argument.getValue().getNewValue(), is( instanceOf( UUID.class ) ) );
    assertThat( argument.getValue().getOldValue(), is( nullValue() ) );
    assertThat( argument.getValue().isCreated(), is( true ) );
  }

  @Test
  public void testListenerEventModified() {
    // Prepare
    ArgumentCaptor<CorrelationIDChangeEvent> argument =
        ArgumentCaptor.forClass( CorrelationIDChangeEvent.class );

    // Execute
    CorrelationIDManager.setCorrelationID( "123" );
    CorrelationIDManager.setCorrelationID( "456" );
    CorrelationIDManager.removeChangeListener( listener );

    // Verify
    verify( listener, times(2) ).correlationIDChanged( argument.capture() );
    assertThat( (String) argument.getValue().getNewValue(), is( "456" ) );
    assertThat( (String) argument.getValue().getOldValue(), is( "123" ) );
    assertThat( argument.getValue().isCreated(), is( false ) );
  }
}
