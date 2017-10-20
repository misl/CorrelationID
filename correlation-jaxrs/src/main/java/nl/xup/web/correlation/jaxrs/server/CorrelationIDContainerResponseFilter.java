package nl.xup.web.correlation.jaxrs.server;

import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Jax RS provider response filter to be able to reset the correlation ID
 * manager. This is to prevent interference between threads in case of thread
 * pooling.
 * 
 * @author misl
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class CorrelationIDContainerResponseFilter implements ContainerResponseFilter {

  // --------------------------------------------------------------------------
  // Implementing ContainerResponseFilter
  // --------------------------------------------------------------------------

  @Override
  public void filter( ContainerRequestContext requestContext,
      ContainerResponseContext responseContext ) throws IOException {
    CorrelationIDManager.clear();
  }
}
