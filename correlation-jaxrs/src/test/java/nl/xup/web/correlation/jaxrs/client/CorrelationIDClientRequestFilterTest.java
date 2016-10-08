package nl.xup.web.correlation.jaxrs.client;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;

import org.junit.After;
import org.junit.Test;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Test cases for CorrelationIDClientRequestFilter
 * 
 * @author misl
 */
public class CorrelationIDClientRequestFilterTest {

  // --------------------------------------------------------------------------
  // Setup / Teardown
  // --------------------------------------------------------------------------

  @After
  public void tearDown() {
    CorrelationIDManager.clear();
  }

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final CorrelationIDClientRequestFilter filter = new CorrelationIDClientRequestFilter();

    // Verify
    assertThat( filter, is( notNullValue() ) );
  }

  @Test
  public void testAddCorrelationIDNotYetPresent() throws Exception {
    // Prepare
    CorrelationIDManager.clear();
    CorrelationIDManager.setName( "x-header-name" );

    final MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
    final ClientRequestContext context = mock( ClientRequestContext.class );
    when( context.getHeaders() ).thenReturn( headers );

    final CorrelationIDClientRequestFilter filter = new CorrelationIDClientRequestFilter();

    // Execute
    filter.filter( context );

    // Verify
    final Object correlationID = headers.getFirst( CorrelationIDManager.getName() );
    assertThat( correlationID, is( instanceOf( UUID.class ) ) );
  }

  @Test
  public void testAddCorrelationIDPresent() throws Exception {
    // Prepare
    CorrelationIDManager.clear();
    CorrelationIDManager.setName( "x-header-name" );
    CorrelationIDManager.setCorrelationID( "123" );

    final MultivaluedMap<String, Object> headers = new MultivaluedHashMap<>();
    final ClientRequestContext context = mock( ClientRequestContext.class );
    when( context.getHeaders() ).thenReturn( headers );

    final CorrelationIDClientRequestFilter filter = new CorrelationIDClientRequestFilter();

    // Execute
    filter.filter( context );

    // Verify
    final Object correlationID = headers.getFirst( CorrelationIDManager.getName() );
    assertThat( correlationID, is( instanceOf( String.class ) ) );
    assertThat( correlationID, is( equalTo( "123" ) ) );
  }
}
