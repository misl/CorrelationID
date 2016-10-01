package nl.xup.web.correlation.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Java EE Servlet Filter to clear the correlation ID. This is required to present the correlation
 * ID from flowing over to another conversation when the thread is being reused.
 * 
 * @author misl
 */
public class ClearCorrelationIDFilter implements Filter {

  // --------------------------------------------------------------------------
  // Implementing Filter
  // --------------------------------------------------------------------------

  public void init( FilterConfig filterConfig ) throws ServletException {}


  @Override
  public void doFilter( final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain ) throws IOException, ServletException {
    filterChain.doFilter( servletRequest, servletResponse );
    CorrelationIDManager.clear();
  }

  @Override
  public void destroy() {}
}
