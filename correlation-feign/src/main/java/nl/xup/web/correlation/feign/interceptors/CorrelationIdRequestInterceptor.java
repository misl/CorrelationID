package nl.xup.web.correlation.feign.interceptors;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import nl.xup.web.correlation.core.CorrelationIDManager;

/**
 * Feign request interceptor to add the current correlation id to the fiegn 
 * based rest request.
 * 
 * @author misl
 */
public class CorrelationIdRequestInterceptor implements RequestInterceptor {

  // --------------------------------------------------------------------------
  // Implementing RequestInterceptor
  // --------------------------------------------------------------------------

  @Override
  public void apply( RequestTemplate template ) {
    Object correlationID = CorrelationIDManager.getCorrelationID();
    if (correlationID != null) {
      template.header( CorrelationIDManager.getName(), correlationID.toString() );
    }
  }
}
