package nl.xup.web.correlation.core.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Java EE Servlet Filter to retrieve the correlation ID from the request when present. When not
 * present a new correlation ID will be created.
 * 
 * @author misl
 */
public class CorrelationIDFilter extends ClearCorrelationIDFilter {

  // --------------------------------------------------------------------------
  // Constants
  // --------------------------------------------------------------------------

  public static final String INIT_PARAM_NAME = "header-parameter-name";

  // --------------------------------------------------------------------------
  // Overriding ClearCorrelationIDFilter
  // --------------------------------------------------------------------------

  @Override
  public void init( FilterConfig filterConfig ) throws ServletException {
    super.init( filterConfig );

    final String name = filterConfig.getInitParameter( INIT_PARAM_NAME );
    if (!(name == null || name.isEmpty())) {
      CorrelationIDManager.setName( name );
    }
  }

  @Override
  public void doFilter( final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain ) throws IOException, ServletException {

    if (servletRequest instanceof HttpServletRequest) {
      final HttpServletRequest request = HttpServletRequest.class.cast( servletRequest );

      final String currentCorrelationID = request.getHeader( CorrelationIDManager.getName() );
      if (currentCorrelationID == null || currentCorrelationID.isEmpty()) {
        CorrelationIDManager.createCorrelationID();        
      } else {
        CorrelationIDManager.setCorrelationID( currentCorrelationID );
      }
    } else {
      // Don't know how to retrieve correlation ID (if it is there). Let's create a new one.
      CorrelationIDManager.createCorrelationID();        
    }

    super.doFilter( servletRequest, servletResponse, filterChain );
  }
}
