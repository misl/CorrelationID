package nl.xup.web.correlation.core;

/**
 * Factory interface to create a correlation ID
 * 
 * @author misl
 */
public interface CorrelationIDFactory<T> {
  
  /**
   * Creates a new correlation ID
   *  
   * @return New correlation ID
   */
  T createCorrelationID();
}
