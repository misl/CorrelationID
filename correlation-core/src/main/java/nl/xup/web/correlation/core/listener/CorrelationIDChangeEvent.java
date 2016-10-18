package nl.xup.web.correlation.core.listener;

/**
 * Correlation ID change event. This event is used to notify listeners of correlation ID changes.
 * 
 * @author misl
 */
public class CorrelationIDChangeEvent {

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private Thread thread;
  private Object oldValue;
  private Object newValue;
  private boolean created;

  // --------------------------------------------------------------------------
  // Constructors
  // --------------------------------------------------------------------------

  public CorrelationIDChangeEvent(final Thread thread, final Object newValue) {
    this.thread = thread;
    this.oldValue = null;
    this.newValue = newValue;
    this.created = true;
  }

  public CorrelationIDChangeEvent(final Thread thread, final Object oldValue,
      final Object newValue) {
    this.thread = thread;
    this.oldValue = oldValue;
    this.newValue = newValue;
    this.created = (oldValue == null);
  }

  // --------------------------------------------------------------------------
  // Interface
  // --------------------------------------------------------------------------

  /**
   * Gives the thread that changed the correlation ID.
   * 
   * @return
   */
  public Thread getThread() {
    return thread;
  }

  /**
   * Gives the previous correlation ID.
   * 
   * @return
   */
  public Object getOldValue() {
    return oldValue;
  }

  /**
   * Gives the new correlation ID.
   * 
   * @return
   */
  public Object getNewValue() {
    return newValue;
  }

  /**
   * Indicates whether the correlation ID is new or modified.
   * 
   * @return true when a new correlation ID is created, false when an existing correlation ID is
   *         modified.
   */
  public boolean isCreated() {
    return created;
  }
}
