package nl.xup.web.correlation.factory.timestamp;


import java.time.LocalDateTime;

import nl.xup.web.correlation.core.CorrelationIDFactory;

/**
 * Correlation IDfactory to create a correlation ID based on a timestamp.
 * 
 * @author misl
 */
class TimestampCorrelationIDFactory implements CorrelationIDFactory<LocalDateTime> {
  
  // --------------------------------------------------------------------------
  // Implementing CorrelationIDFactory
  // --------------------------------------------------------------------------

  /**
   * Creates a new correlation ID
   *  
   * @return New correlation ID
   */
  @Override
  public LocalDateTime createCorrelationID() {
    return LocalDateTime.now();
  }
}
