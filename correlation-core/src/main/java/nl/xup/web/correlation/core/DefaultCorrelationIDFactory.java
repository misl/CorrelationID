package nl.xup.web.correlation.core;

import java.util.UUID;

/**
 * Default factory to create a correlation ID. This factory generates a UUID.
 * 
 * @author misl
 */
class DefaultCorrelationIDFactory implements CorrelationIDFactory<UUID> {
  
  // --------------------------------------------------------------------------
  // Implementing CorrelationIDFactory
  // --------------------------------------------------------------------------

  /**
   * Creates a new correlation ID
   *  
   * @return New correlation ID
   */
  public UUID createCorrelationID() {
    return UUID.randomUUID();
  }
}
