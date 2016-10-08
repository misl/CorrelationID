package nl.xup.web.correlation.core.filter;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.UUID;

import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Test cases for CorrelationIDFilter
 * 
 * @author misl
 */
public class CorrelationIDFilterTest {

  // --------------------------------------------------------------------------
  // Setup / Teardown
  // --------------------------------------------------------------------------

  @Before
  public void setup() {
    CorrelationIDManager.setName( "filterTest" );
  }

  // --------------------------------------------------------------------------
  // Test cases
  // --------------------------------------------------------------------------

  @Test
  public void testConstructor() {
    // Prepare

    // Execute
    final CorrelationIDFilter filter = new CorrelationIDFilter();

    // Verify
    assertThat( filter, is( notNullValue() ) );
  }

  @Test
  public void testInitNoHeader() throws Exception {
    // Prepare
    final FilterConfig filterConfig = mock( FilterConfig.class );

    // Execute
    new CorrelationIDFilter().init( filterConfig );

    // Verify
    assertThat( CorrelationIDManager.getName(), is( equalTo( "filterTest" ) ) );
  }

  @Test
  public void testInitEmptyHeader() throws Exception {
    // Prepare
    final FilterConfig filterConfig = mock( FilterConfig.class );
    when( filterConfig.getInitParameter( anyString() ) ).thenReturn( "" );

    // Execute
    new CorrelationIDFilter().init( filterConfig );

    // Verify
    assertThat( CorrelationIDManager.getName(), is( equalTo( "filterTest" ) ) );
  }

  @Test
  public void testInitWithHeader() throws Exception {
    // Prepare
    final FilterConfig filterConfig = mock( FilterConfig.class );
    when( filterConfig.getInitParameter( anyString() ) ).thenReturn( "test" );

    // Execute
    new CorrelationIDFilter().init( filterConfig );

    // Verify
    assertThat( CorrelationIDManager.getName(), is( equalTo( "test" ) ) );
  }

  @Test
  public void testDoFilterNotHttpRequest() throws Exception {
    // Prepare
    final ServletRequest request = mock( ServletRequest.class );
    final ServletResponse response = mock( ServletResponse.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDFilter filter = new CorrelationIDFilter();

    // Execute
    filter.doFilter( request, response, ( req, resp ) -> {
      // Verify (while processing)
      assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
      assertThat( CorrelationIDManager.getCorrelationID(), is( not( equals( "test" ) ) ) );
      // New correlation ID should use default factory to create UUID.
      assertThat( CorrelationIDManager.getCorrelationID(), is( instanceOf( UUID.class ) ) );
    } );

    // Verify (cleanup after processing)
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }

  @Test
  public void testDoFilterMissingRequestHeader() throws Exception {
    // Prepare
    final HttpServletRequest request = mock( HttpServletRequest.class );
    final ServletResponse response = mock( ServletResponse.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDFilter filter = new CorrelationIDFilter();

    // Execute
    filter.doFilter( request, response, ( req, resp ) -> {
      // Verify (while processing)
      assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
      assertThat( CorrelationIDManager.getCorrelationID(), is( not( equalTo( "test" ) ) ) );
      // New correlation ID should use default factory to create UUID.
      assertThat( CorrelationIDManager.getCorrelationID(), is( instanceOf( UUID.class ) ) );
    } );

    // Verify (cleanup after processing)
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }

  @Test
  public void testDoFilterEmptyRequestHeader() throws Exception {
    // Prepare
    final HttpServletRequest request = mock( HttpServletRequest.class );
    when( request.getHeader( CorrelationIDManager.getName() ) ).thenReturn( "" );
    final ServletResponse response = mock( ServletResponse.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDFilter filter = new CorrelationIDFilter();

    // Execute
    filter.doFilter( request, response, ( req, resp ) -> {
      // Verify (while processing)
      assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
      assertThat( CorrelationIDManager.getCorrelationID(), is( not( equalTo( "test" ) ) ) );
      // New correlation ID should use default factory to create UUID.
      assertThat( CorrelationIDManager.getCorrelationID(), is( instanceOf( UUID.class ) ) );
    } );

    // Verify (cleanup after processing)
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }

  @Test
  public void testDoFilterWithRequestHeader() throws Exception {
    // Prepare
    final HttpServletRequest request = mock( HttpServletRequest.class );
    when( request.getHeader( CorrelationIDManager.getName() ) ).thenReturn( "123" );
    final ServletResponse response = mock( ServletResponse.class );

    CorrelationIDManager.setCorrelationID( "test" );
    final CorrelationIDFilter filter = new CorrelationIDFilter();

    // Execute
    filter.doFilter( request, response, ( req, resp ) -> {
      // Verify (while processing)
      assertThat( CorrelationIDManager.getCorrelationID(), is( notNullValue() ) );
      assertThat( CorrelationIDManager.getCorrelationID(), is( equalTo( "123" ) ) );
    } );

    // Verify (cleanup after processing)
    assertThat( CorrelationIDManager.getCorrelationID(), is( nullValue() ) );
  }
}
