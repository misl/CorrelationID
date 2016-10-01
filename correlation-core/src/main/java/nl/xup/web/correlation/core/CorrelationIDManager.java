package nl.xup.web.correlation.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ServiceLoader;

import nl.xup.web.correlation.core.listener.CorrelationIDChangeEvent;
import nl.xup.web.correlation.core.listener.CorrelationIDChangeListener;

/**
 * Singleton manager object for managing the correlation ID. This is achieved by allowing to store
 * and retrieve the current correlation ID as well as the name under which this correlation ID is
 * known.
 * 
 * Note: Only 1 correlation ID can be stored per thread.
 * 
 * Patterns: Singleton, Observer
 * 
 * @author misl
 */
public final class CorrelationIDManager {

  // --------------------------------------------------------------------------
  // Class attributes
  // --------------------------------------------------------------------------

  private static CorrelationIDManager instance = null;
  private static String correlationIDName = "correlation-id";

  // --------------------------------------------------------------------------
  // Object attributes
  // --------------------------------------------------------------------------

  private CorrelationIDFactory<? extends Object> correlationIDFactory = null;
  private ThreadLocal<Object> threadlocalCorrelationID = new ThreadLocal<>();
  private Collection<CorrelationIDChangeListener> listeners = new ArrayList<>();

  // --------------------------------------------------------------------------
  // Constructors
  // --------------------------------------------------------------------------

  private CorrelationIDManager() {
    instance = this;
    initialize();
  }

  // --------------------------------------------------------------------------
  // Getters and Setters
  // --------------------------------------------------------------------------

  /**
   * Sets the name to be used for the correlation ID.
   * 
   * @param name used for the correlation ID
   */
  public static void setName( final String name ) {
    if (name == null || name.isEmpty()) {
      throw new IllegalArgumentException( "Correlation ID name can not be NULL or empty" );
    }
    correlationIDName = name;
  }

  /**
   * Retrieves the name used for the correlation ID.
   * 
   * @return String with the name of the correlation ID.
   */
  public static String getName() {
    return correlationIDName;
  }

  /**
   * Store the correlation ID for later retrieval.
   * 
   * @param correlationID to be used
   */
  public static void setCorrelationID( final Object correlationID ) {
    getInstance().setCorrelationIDInternal( correlationID );
  }

  /**
   * Retrieves a previously stored correlation ID.
   * 
   * @return The correlation ID object stored previously or null if not yet stored.
   */
  public static Object getCorrelationID() {
    return getInstance().threadlocalCorrelationID.get();
  }

  // --------------------------------------------------------------------------
  // Public interface
  // --------------------------------------------------------------------------

  public static Object createCorrelationID() {
    final Object correlationID = getInstance().correlationIDFactory.createCorrelationID();
    getInstance().setCorrelationIDInternal( correlationID );
    return correlationID;
  }

  public static void clear() {
    getInstance().threadlocalCorrelationID.remove();
  }
  
  public static void addChangeListener( CorrelationIDChangeListener newListener ) {
    if (newListener == null) {
      throw new IllegalArgumentException( "Change listener can not be NULL" );
    }
    getInstance().listeners.add( newListener );
  }

  public static void removeChangeListener( CorrelationIDChangeListener newListener ) {
    getInstance().listeners.remove( newListener );
  }

  // --------------------------------------------------------------------------
  // Private methods
  // --------------------------------------------------------------------------

  /**
   * Get the singleton instance of the correlation ID manager.
   * 
   * @return CorrelationIDManager object
   */
  private static final CorrelationIDManager getInstance() {
    if (instance == null) {
      new CorrelationIDManager();
    }
    return instance;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private void initialize() {
    // Get rid of any previous factory.
    correlationIDFactory = null;

    final ServiceLoader<CorrelationIDFactory> factoryLoader =
        ServiceLoader.load( CorrelationIDFactory.class );
    for (final CorrelationIDFactory factory : factoryLoader) {
      if (correlationIDFactory == null) {
        correlationIDFactory = factory;
        return;
      }
    }

    // Use default facotry if none was found.
    correlationIDFactory = new DefaultCorrelationIDFactory();
  }

  private void setCorrelationIDInternal( final Object correlationID ) {
    if (correlationID == null || correlationID.toString().isEmpty()) {
      throw new IllegalArgumentException( "Correlation ID can not be NULL or empty" );
    }

    final Object oldValue = threadlocalCorrelationID.get();
    threadlocalCorrelationID.set( correlationID );

    if (oldValue == null) {
      createNotify( correlationID );
    } else {
      modifyNotify( correlationID, oldValue );
    }

  }

  private void createNotify( final Object correlationID ) {
    for (CorrelationIDChangeListener listener : listeners) {
      try {
        listener.correlationIDChanged( new CorrelationIDChangeEvent( correlationID ) );
      } finally {
        // no-op
      }
    }
  }

  private void modifyNotify( final Object correlationID, final Object oldValue ) {
    for (CorrelationIDChangeListener listener : listeners) {
      try {
        listener.correlationIDChanged( new CorrelationIDChangeEvent( oldValue, correlationID ) );
      } finally {
        // no-op
      }
    }
  }
}
