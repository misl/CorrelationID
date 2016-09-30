package nl.xup.web.correlation.core.listener;

/**
 * Listener interface to receive notifications when the correlation ID changes
 * 
 * @author misl
 */
public interface CorrelationIDChangeListener {

  /**
   * Notification handler method to be implemented to receive correlation id change events.
   * 
   * @param event the object with change details.
   */
  void correlationIDChanged( final CorrelationIDChangeEvent event );
}
