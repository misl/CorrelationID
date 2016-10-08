package nl.xup.web.correlation.jaxrs.server;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;

import org.junit.After;
import org.junit.Test;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Test cases for CorrelationIDContainerRequestFilter
 * 
 * @author misl
 */
public class CorrelationIDContainerRequestFilterTest {

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
    final CorrelationIDContainerRequestFilter filter = new CorrelationIDContainerRequestFilter();

    // Verify
    assertThat( filter, is( notNullValue() ) );
  }

  @Test
  public void testDoFilterMissingRequestHeader() throws Exception {
    // Prepare
    final ContainerRequestContext context = mock( ContainerRequestContext.class );
    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDContainerRequestFilter filter = new CorrelationIDContainerRequestFilter();

    // Execute
    filter.filter( context );

    // Verify
    assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
    assertThat( CorrelationIDManager.getCorrelationID(), is( not( equalTo( "test" ) ) ) );
    // New correlation ID should use default factory to create UUID.
    assertThat( CorrelationIDManager.getCorrelationID(), is( instanceOf( UUID.class ) ) );
  }

  @Test
  public void testDoFilterEmptyRequestHeader() throws Exception {
    // Prepare
    final ContainerRequestContext context = mock( ContainerRequestContext.class );
    when( context.getHeaderString( CorrelationIDManager.getName() ) ).thenReturn( "" );
    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDContainerRequestFilter filter = new CorrelationIDContainerRequestFilter();

    // Execute
    filter.filter( context );

    // Verify
    assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
    assertThat( CorrelationIDManager.getCorrelationID(), is( not( equalTo( "test" ) ) ) );
    // New correlation ID should use default factory to create UUID.
    assertThat( CorrelationIDManager.getCorrelationID(), is( instanceOf( UUID.class ) ) );
  }

  @Test
  public void testDoFilterWithRequestHeader() throws Exception {
    // Prepare
    final ContainerRequestContext context = mock( ContainerRequestContext.class );
    when( context.getHeaderString( CorrelationIDManager.getName() ) ).thenReturn( "123" );
    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDContainerRequestFilter filter = new CorrelationIDContainerRequestFilter();

    // Execute
    filter.filter( context );

    // Verify
    assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
    assertThat( CorrelationIDManager.getCorrelationID(), is( equalTo( "123" ) ) );
  }
}
