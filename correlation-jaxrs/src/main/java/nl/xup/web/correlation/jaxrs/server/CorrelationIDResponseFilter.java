package nl.xup.web.correlation.jaxrs.server;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CorrelationIDResponseFilter implements ContainerResponseFilter{

  @Override
  public void filter( ContainerRequestContext requestContext,
      ContainerResponseContext responseContext ) throws IOException {
    // TODO Auto-generated method stub
    
  }
}
