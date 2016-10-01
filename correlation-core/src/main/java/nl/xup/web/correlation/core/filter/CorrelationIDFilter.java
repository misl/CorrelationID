package nl.xup.web.correlation.core.filter;

import java.io.IOException;

import javax.servlet.Filter;
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
  // Overriding ClearCorrelationIDFilter
  // --------------------------------------------------------------------------

  @Override
  public void doFilter( final ServletRequest servletRequest, final ServletResponse servletResponse,
      final FilterChain filterChain ) throws IOException, ServletException {

    if (servletRequest instanceof HttpServletRequest) {
      final HttpServletRequest request = HttpServletRequest.class.cast( servletRequest );

       final String currentCorrelationID = request.getHeader(CorrelationIDManager.getName());
       // String currentCorrId =
       // httpServletRequest.getHeader(RequestCorrelation.CORRELATION_ID_HEADER);
       CorrelationIDManager.setCorrelationID( currentCorrelationID );
    }
    // if (!currentRequestIsAsyncDispatcher(httpServletRequest)) {
    // if (currentCorrId == null) {
    // currentCorrId = UUID.randomUUID().toString();
    // LOGGER.info("No correlationId found in Header. Generated : " + currentCorrId);
    // } else {
    // LOGGER.info("Found correlationId in Header : " + currentCorrId);
    // }
    //
    // RequestCorrelation.setId(currentCorrId);
    // }

    filterChain.doFilter( servletRequest, servletResponse );
  }
}
