package nl.xup.web.correlation.jaxrs.client;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Jax RS client request filter to be able to add a correlation ID to the request. If no correlation
 * ID present yet, an new one will be created. be created.
 * 
 * @author misl
 */
public class CorrelationIDClientRequestFilter implements ClientRequestFilter {

  // --------------------------------------------------------------------------
  // Implementing ClientRequestFilter
  // --------------------------------------------------------------------------

  @Override
  public void filter( ClientRequestContext requestContext ) throws IOException {
    // Check if a correlation ID already exists.
    Object correlationID = CorrelationIDManager.getCorrelationID();
    if (correlationID == null) {
      // Does not yet exist, so create one.
      CorrelationIDManager.createCorrelationID();
    }

    // Add correlation ID to the request
    requestContext.getHeaders().add( CorrelationIDManager.getName(),
        CorrelationIDManager.getCorrelationID() );
  }
}
