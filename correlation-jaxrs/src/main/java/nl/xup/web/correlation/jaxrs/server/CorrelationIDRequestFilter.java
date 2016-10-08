package nl.xup.web.correlation.jaxrs.server;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;

public class CorrelationIDRequestFilter implements ContainerRequestFilter {

  @Override
  public void filter( ContainerRequestContext requestContext ) throws IOException {
    // TODO Auto-generated method stub

  }
}
