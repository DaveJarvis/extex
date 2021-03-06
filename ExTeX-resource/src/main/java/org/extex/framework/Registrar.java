/*
 * Copyright (C) 2005-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.framework;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provides a means to reconnect an object to a managing factory
 * after it has been disconnected. The disconnection might happen during
 * serialization and deserialization.
 *
 * <p>
 * Whenever an object is deserialized Java tries to invoke the method
 * {@code readResolve()}. This method can be used to get a hand on the object
 * which has just been reconstructed. Here the object can be replaced by another
 * one or some other action can be applied.
 * </p>
 * <p>
 * Any class which is serializable and wants to participate in the reconnection
 * mechanism should implement the method {@code readResolve}. In this
 * method the method {@code reconnect()} of the {@link Registrar Registrar}
 * should be invoked. This is shown in the following example:
 * </p>
 *
 * <pre class="JavaSample">
 *  <b>protected</b> Object readResolve() <b>throws</b> ObjectStreamException {
 *
 *      <b>return</b> Registrar.reconnect(this);
 *  }
 * </pre>
 *
 * <p>
 * Any factory which wants to participate in the reconnection mechanism should
 * implement the interface {@link org.extex.framework.RegistrarObserver
 * RegistrarObserver}.
 * </p>
 * <p>
 * Finally, before an object is deserialized, the interested parties should
 * register an observer at the {@link Registrar Registrar}.
 * </p>
 * <p>
 * Note that the registrar has to be implemented as a static singleton since
 * readResolve() does not provide any means to pass a reference to some other
 * object to it.
 * <p>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 */
public final class Registrar {

  /**
   * This class provides a container for a pair of a class and an observer.
   *
   * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
   */
  private static final class Obs {

    /**
     * The field {@code observer} contains the observer.
     */
    private final RegistrarObserver observer;

    /**
     * The field {@code type} contains the class.
     */
    private final Class<?> type;

    /**
     * Creates a new object.
     *
     * @param observer the observer
     * @param type     the interface or class to be observed
     */
    public Obs( RegistrarObserver observer, Class<?> type ) {

      this.type = type;
      this.observer = observer;
    }

    /**
     * Getter for observer.
     *
     * @return the observer
     */
    public RegistrarObserver getObserver() {

      return this.observer;
    }

    /**
     * Getter for type.
     *
     * @return the type
     */
    public Class<?> getType() {

      return this.type;
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

      return this.type.toString() + " " + this.observer.toString();
    }

  }

  /**
   * The field {@code active} contains the currently active registrar.
   */
  private static Registrar active = new Registrar();

  /**
   * The field {@code pipe} contains the list of registrars which wait to be
   * activated.
   */
  private static final List<Registrar> pipe = new ArrayList<Registrar>();

  /**
   * Create a new registrar and activate it.
   *
   * @return the new registrar
   * @throws RegistrarException in case of an error
   */
  public static Registrar activate() throws RegistrarException {

    Registrar registrar = new Registrar();
    activate( registrar );
    return registrar;
  }

  /**
   * Activate an existing registrar.
   *
   * @param registrar the registrar to activate
   * @throws RegistrarException in case of an error
   */
  public static void activate( Registrar registrar ) throws RegistrarException {

    if( active != null ) {
      pipe.add( registrar );
      try {
        for( ; ; ) {
          registrar.wait();
        }
      } catch( InterruptedException e ) {
        // ignored;
      }
    }
    active = registrar;
  }

  /**
   * Deactivate a currently active registrar.
   *
   * @param registrar the registrar to deactivate
   * @throws RegistrarException in case of an error
   */
  public static void deactivate( Registrar registrar )
      throws RegistrarException {

    if( active != registrar ) {
      throw new RegistrarException( "registrar is not active" );
    }
    if( pipe.size() > 0 ) {
      active = pipe.remove( 0 );
      active.notifyAll();
    }
    else {
      active = null;
    }
  }

  /**
   * Find anyone interested in an object and let the object be integrated into
   * their views of the world.
   *
   * @param object the object to reconnect
   * @return the object which should actually be used
   * @throws RegistrarException in case of a problem with registration
   */
  public static Object reconnect( Object object ) throws RegistrarException {

    Object ob = object;
    List<Obs> observers = active.observers;
    int n = observers.size();
    for( int i = 0; i < n; i++ ) {
      Obs obs = observers.get( i );
      if( obs.getType().isInstance( object ) ) {
        ob = obs.getObserver().reconnect( ob );
      }
    }
    return ob;
  }

  /**
   * This method registers an observer at the registrar. This observer is
   * invoked for each class which is deserialized and matches the class given.
   * The type argument can be an interface as well.
   *
   * @param observer the observer
   * @param type     the interface or class to be observed
   * @return a reference to an object which can be passed to unregister() for
   * removing the registered observer.
   */
  public static Object register( RegistrarObserver observer, Class<?> type ) {

    Obs obs = new Obs( observer, type );
    active.observers.add( obs );
    return obs;
  }

  /**
   * Unregister a registered observer.
   *
   * @param obs the reference obtained from register()
   * @return {@code true} iff the removal succeeded
   */
  public static boolean unregister( Object obs ) {

    if( !(obs instanceof Obs) ) {
      throw new IllegalArgumentException( "#unregister()" );
    }
    return active.observers.remove( obs );
  }

  /**
   * The field {@code observers} contains the observers which are currently
   * registered.
   */
  private final List<Obs> observers = new ArrayList<Obs>();

  /**
   * Private constructor to avoid instantiation.
   */
  private Registrar() {

  }

}
