package nl.xup.web.correlation.jaxrs.server;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;

import org.junit.Test;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Test cases for CorrelationIDContainerResponseFilter
 * 
 * @author misl
 */
public class CorrelationIDContainerResponseFilterTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final CorrelationIDContainerResponseFilter filter = new CorrelationIDContainerResponseFilter();

    // Verify
    assertThat( filter, is( notNullValue() ) );
  }

  @Test
  public void testDoFilter() throws Exception {
    // Prepare
    final ContainerRequestContext requestContext = mock( ContainerRequestContext.class );
    final ContainerResponseContext responseContext = mock( ContainerResponseContext.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDContainerResponseFilter filter = new CorrelationIDContainerResponseFilter();

    // Execute
    filter.filter( requestContext, responseContext );

    // Verify
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }
}
