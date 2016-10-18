package nl.xup.web.correlation.jaxrs.server;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.ext.Provider;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Jax RS provider request filter to be able to retrieve the correlation ID
 * from the request (if present). If not present a new correlation ID will
 * be created.
 * 
 * @author misl
 */
@Provider
@PreMatching
public class CorrelationIDContainerRequestFilter implements ContainerRequestFilter {

  // --------------------------------------------------------------------------
  // Implementing ContainerRequestFilter
  // --------------------------------------------------------------------------

  @Override
  public void filter( ContainerRequestContext requestContext ) throws IOException {
      final String currentCorrelationID = requestContext.getHeaderString( CorrelationIDManager.getName() );
      if (currentCorrelationID == null || currentCorrelationID.isEmpty()) {
        CorrelationIDManager.createCorrelationID();        
      } else {
        CorrelationIDManager.setCorrelationID( currentCorrelationID );
      }
  }
}
