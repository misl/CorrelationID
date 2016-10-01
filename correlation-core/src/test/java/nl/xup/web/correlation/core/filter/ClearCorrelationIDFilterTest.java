package nl.xup.web.correlation.core.filter;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.junit.Test;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Test cases for ClearCorrelationIDFilter
 * 
 * @author misl
 */
public class ClearCorrelationIDFilterTest {

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final ClearCorrelationIDFilter filter = new ClearCorrelationIDFilter();

    // Verify
    assertThat( filter, is( notNullValue() ) );
  }

  @Test
  public void testDoFilter() throws Exception {
    // Prepare
    final ServletRequest request = mock( ServletRequest.class );
    final ServletResponse response = mock( ServletResponse.class );
    final FilterChain chain = mock( FilterChain.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final ClearCorrelationIDFilter filter = new ClearCorrelationIDFilter();

    // Execute
    filter.doFilter( request, response, chain );

    // Verify
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }
}
