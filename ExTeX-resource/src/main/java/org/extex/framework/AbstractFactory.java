/*
 * Copyright (C) 2004-2011 The ExTeX Group and individual authors listed below
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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.framework.configuration.exception.ConfigurationInvalidClassException;
import org.extex.framework.configuration.exception.ConfigurationMissingAttributeException;
import org.extex.framework.configuration.exception.ConfigurationMissingException;
import org.extex.framework.logger.LogEnabled;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;

/**
 * This is the abstract base class for factories. It contains some common
 * methods which should make it easy to create a custom factory.
 * <p>
 * The abstract factory supports utility events:
 * <ul>
 * <li>If the instantiated class implements the interface
 * {@link org.extex.framework.configuration.Configurable Configurable} then the
 * associated method is used to pass on the configuration to the new instance.</li>
 * <li>If the instantiated class implements the interface
 * {@link org.extex.framework.logger.LogEnabled LogEnabled} then the associated
 * method is used to pass on the logger to the new instance.</li>
 * </ul>
 * </p>
 * 
 * <h2>Usage</h2>
 * <p>
 * To make use of this base class create a derived class and provide a method
 * which creates a new instance. This method should make use of one of the
 * utility methods defined in this class. This is illustrated in the following
 * example:
 * </p>
 * 
 * <pre class="JavaSample">
 *  <b>class</b> MyFactory <b>extends</b> AbstractFactory<MyType> {
 *  
 *      <b>public</b> MyType newInstance() {
 *      
 *          <b>return</b> createInstance(MyType.class);
 *      }
 *  }
 * </pre>
 * <p>
 * The class defined in this way is automatically {@link Configurable},
 * {@link LogEnabled}, and {@link ResourceAware}. Thus appropriate objects
 * should be passed in before a new instance is requested:
 * </p>
 * 
 * <pre class="JavaSample">
 *     ...
 *     MyFactory factory = <b>new</b> MyFactory();
 *     factory.configure(theConfiguration);
 *     factory.enableLogging(theLogger);
 *     ...
 *     MyType t1 = factory.newInstance();
 * </pre>
 * 
 * @param <TYPE> the type of the object delived by the factory
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractFactory<TYPE> extends AbstractConfigurable
        implements
            LogEnabled,
            ResourceAware,
            RegistrarObserver {

    /**
     * The constant <tt>CLASS_ATTRIBUTE</tt> contains the name of the attribute
     * used to get the class name.
     */
    protected static final String CLASS_ATTRIBUTE = "class";

    /**
     * The constant <tt>SELECT_ATTRIBUTE</tt> contains the name of the attribute
     * used to get the default configuration.
     */
    protected static final String SELECT_ATTRIBUTE = "select";

    /**
     * Utility method to pass a logger to an object if it has a method to take
     * it. If the logger is <code>null</code> then this method simply does
     * nothing.
     * 
     * @param instance the instance to pass the logger to
     * @param logger the logger to pass. If the logger is <code>null</code> then
     *        nothing is done.
     */
    public static void enableLogging(Object instance, Logger logger) {

        if (logger != null && instance instanceof LogEnabled) {
            ((LogEnabled) instance).enableLogging(logger);
        }
    }

    /**
     * The field <tt>logger</tt> contains the logger to pass to the new
     * instances created.
     */
    private Logger logger = null;

    /**
     * The field <tt>resourceFinder</tt> contains the resource finder.
     */
    private ResourceFinder resourceFinder = null;

    /**
     * Creates a new factory object. No configuration is set yet. Thus the new
     * instance must be configured before it can be used to create a new object.
     */
    public AbstractFactory() {

    }

    /**
     * Creates a new factory object and configure it.
     * 
     * @param configuration the configuration object to consider
     */
    public AbstractFactory(Configuration configuration) {

        configure(configuration);
    }

    /**
     * Get an instance from the configuration of this factory.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If the no-argument constructor is found then it is invoked. If the
     * class is {@link LogEnabled} then the logger is passed in. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} then the logger of
     * this factory is passed in upon creation. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} then the
     * configuration parameter is passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} an a
     * {@link Logger} &ndash; in either order &ndash; then the configuration
     * parameter and the logger of this factory are passed in upon creation.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param target the expected class or interface
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstance(Class<?> target)
            throws ConfigurationException {

        return createInstanceForConfiguration(getConfiguration(), target);
    }

    /**
     * Get a new instance. This method selects one of the entries in the
     * configuration. The selection is done with the help of a type String. If
     * the type is <code>null</code> or the empty string then the default from
     * the configuration is used.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If a one argument constructor with a proper argument type is found
     * then it is invoked. If the class is {@link LogEnabled} then the logger is
     * passed in. If the class is {@link Configurable} then the configuration is
     * passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} and an additional
     * argument of proper type then the logger of this factory and the argument
     * are passed in upon creation. If the class is {@link Configurable} then
     * the configuration from the factory is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration}and an
     * additional argument of proper type then the configuration from the
     * factory and the argument are passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param target the expected class or interface
     * @param arg the argument for the constructor
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstance(Class<?> target, Object arg)
            throws ConfigurationException {

        return createInstanceForConfiguration(getConfiguration(), target, arg);
    }

    /**
     * Create a new instance with an additional argument for selecting a
     * sub-configuration. Thus the configuration of this class can contain
     * several alternatives. The proper one is chosen with the first argument.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If the no-argument constructor is found then it is invoked. If the
     * class is {@link LogEnabled} then the logger is passed in. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} then the logger of
     * this factory is passed in upon creation. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} then the
     * configuration parameter is passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} an a
     * {@link Logger} &ndash; in either order &ndash; then the configuration
     * parameter and the logger of this factory are passed in upon creation.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * 
     * @param type the name of the sub-configuration to use
     * @param target the expected class or interface
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstance(String type, Class<?> target)
            throws ConfigurationException {

        return createInstanceForConfiguration(selectConfiguration(type), target);
    }

    /**
     * Create a new instance with an additional argument for selecting a
     * sub-configuration and one to be passed to the constructor. Thus the
     * configuration of this class can contain several alternatives. The proper
     * one is chosen with the first argument.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If a one argument constructor with a proper argument type is found
     * then it is invoked. If the class is {@link LogEnabled} then the logger is
     * passed in. If the class is {@link Configurable} then the selected
     * configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} and an additional
     * argument of proper type then the logger of this factory and the argument
     * are passed in upon creation. If the class is {@link Configurable} then
     * the selected configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration}and an
     * additional argument of proper type then the selected configuration and
     * the argument are passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param type the name of the sub-configuration to use
     * @param target the expected class or interface
     * @param argClass the class of the argument
     * @param arg the argument
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstance(String type, Class<?> target,
            Class<?> argClass, Object arg) throws ConfigurationException {

        return createInstanceForConfiguration(selectConfiguration(type),
            target, argClass, arg);
    }

    /**
     * Create a new instance for a given configuration. The name of the class to
     * be instantiated is taken from the attribute <tt>class</tt> of the
     * configuration.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If the no-argument constructor is found then it is invoked. If the
     * class is {@link LogEnabled} then the logger is passed in. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} then the logger of
     * this factory is passed in upon creation. If the class is
     * {@link Configurable} then the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} then the
     * configuration parameter is passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration} an a
     * {@link Logger} &ndash; in either order &ndash; then the configuration
     * parameter and the logger of this factory are passed in upon creation.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param config the configuration to use
     * @param target the expected class or interface
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstanceForConfiguration(Configuration config,
            Class<?> target) throws ConfigurationException {

        if (config == null) {
            throw new ConfigurationMissingException("");
        }
        String className = config.getAttribute(CLASS_ATTRIBUTE);

        if (className == null) {
            throw new ConfigurationMissingAttributeException(CLASS_ATTRIBUTE,
                config);
        }

        Class<?> theClass;
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(className, config);
        }

        if (!target.isAssignableFrom(theClass)) {
            throw new ConfigurationInvalidClassException(theClass.getName(),
                target.getName(), config);
        }

        try {
            for (Constructor<?> constructor : theClass.getConstructors()) {
                Class<?>[] args = constructor.getParameterTypes();
                TYPE instance = null;
                switch (args.length) {
                    case 0:
                        return createInstanceForConfiguration0(config, theClass);
                    case 1:
                        instance =
                                createInstanceForConfiguration1(config,
                                    constructor, args[0]);
                        break;
                    case 2:
                        instance =
                                createInstanceForConfiguration2(config,
                                    constructor, args[0], args[1]);
                        break;
                    default:
                        continue; // Consider the next constructor
                }
                if (instance != null) {
                    return instance;
                }
            }
        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null && cause instanceof ConfigurationException) {
                throw (ConfigurationException) cause;
            }
            throw new ConfigurationInstantiationException(e);
        }

        throw new ConfigurationInvalidClassException(theClass.getName(), //
            target.getName(), config);
    }

    /**
     * Create a new instance for a given configuration with an additional
     * argument for the constructor. By specifying the class of the argument
     * explicitly it is possible for the argument to be <code>null</code>.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If a one argument constructor with a proper argument type is found
     * then it is invoked. If the class is {@link LogEnabled} then the logger is
     * passed in. If the class is {@link Configurable} then the configuration is
     * passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} and an additional
     * argument of proper type then the logger of this factory and the argument
     * are passed in upon creation. If the class is {@link Configurable} then
     * the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration}and an
     * additional argument of proper type then the configuration parameter and
     * the argument are passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param config the configuration to use
     * @param target the expected class or interface
     * @param argClass the class of the argument
     * @param arg the argument or <code>null</code>
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    @SuppressWarnings("unchecked")
    protected final TYPE createInstanceForConfiguration(Configuration config,
            Class<?> target, Class<?> argClass, Object arg)
            throws ConfigurationException {

        if (config == null) {
            throw new ConfigurationMissingException("");
        }

        String className = config.getAttribute(CLASS_ATTRIBUTE);

        if (className == null) {
            throw new ConfigurationMissingAttributeException(CLASS_ATTRIBUTE,
                config);
        }

        Class<?> theClass;
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e1) {
            throw new ConfigurationClassNotFoundException(className, config);
        }

        if (!target.isAssignableFrom(theClass)) {
            throw new ConfigurationInvalidClassException(theClass.getName(),
                target.getName(), config);
        }

        try {
            TYPE instance = null;

            for (Constructor<?> constructor : theClass.getConstructors()) {
                Class<?>[] args = constructor.getParameterTypes();
                switch (args.length) {
                    case 1:
                        if (args[0].isAssignableFrom(argClass)) {
                            instance = (TYPE) constructor.newInstance(arg);
                            enableLogging(instance, logger);
                            configure(instance, config);
                            return instance;
                        }
                        break;

                    case 2:
                        if (args[1].isAssignableFrom(argClass)) {
                            if (args[0].isAssignableFrom(Configuration.class)
                                    && args[1].isAssignableFrom(argClass)) {
                                instance = (TYPE) constructor.newInstance(//
                                    config, arg);
                                enableLogging(instance, logger);
                                return instance;
                            } else if (args[0].isAssignableFrom(Logger.class)
                                    && args[1].isAssignableFrom(argClass)) {
                                instance = (TYPE) constructor.newInstance(//
                                    config, arg);
                                configure(instance, config);
                                return instance;
                            }
                        }
                        break;
                    default: // Fall through to exception
                }
            }

        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalArgumentException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (InvocationTargetException e) {
            Throwable c = e.getCause();
            if (c != null && c instanceof ConfigurationException) {
                throw (ConfigurationException) c;
            }
            throw new ConfigurationInstantiationException(e);
        }

        throw new ConfigurationInvalidClassException(theClass.getName(), //
            target.getName(), config);
    }

    /**
     * Create a new instance for a given configuration with one additional
     * argument.
     * <p>
     * For the class given an appropriate constructor is sought. The following
     * constellations are considered:
     * </p>
     * <ul>
     * <li>If a one argument constructor with a proper argument type is found
     * then it is invoked. If the class is {@link LogEnabled} then the logger is
     * passed in. If the class is {@link Configurable} then the configuration is
     * passed in.</li>
     * <li>If there is a constructor taking a {@link Logger} and an additional
     * argument of proper type then the logger of this factory and the argument
     * are passed in upon creation. If the class is {@link Configurable} then
     * the configuration is passed in.</li>
     * <li>If there is a constructor taking a {@link Configuration}and an
     * additional argument of proper type then the configuration parameter and
     * the argument are passed in upon creation. If the class is
     * {@link LogEnabled} then the {@link Logger} of this factory is passed in.</li>
     * </ul>
     * <p>
     * Note that for the comparison the assignability is used. Thus constructors
     * taking one or two {@link Object}s may mistakenly be selected.
     * </p>
     * <p>
     * If more than one proper constructor is present then it is undefined which
     * one is used.
     * </p>
     * <p>
     * If no proper constructor can be found then an exception is raised.
     * </p>
     * 
     * @param config the configuration to use
     * @param target the expected class or interface
     * @param arg the constructor argument. It can not be <code>null</code>
     * 
     * @return a new instance
     * 
     * @throws ConfigurationException in case of an configuration error
     */
    protected final TYPE createInstanceForConfiguration(Configuration config,
            Class<?> target, Object arg) throws ConfigurationException {

        return createInstanceForConfiguration(config, target, arg.getClass(),
            arg);
    }

    /**
     * Create a new instance for a given configuration using a constructor
     * without arguments.
     * <p>
     * If the class is {@link LogEnabled} then the logger is passed in.
     * </p>
     * <p>
     * If the class is {@link Configurable} then the configuration is passed in.
     * </p>
     * 
     * @param config the configuration to us
     * @param theClass the class to instantiate
     * 
     * @return a new instance
     * 
     * @throws InstantiationException in case of an instantiation error
     * @throws IllegalAccessException in case of an access error
     * @throws ConfigurationException in case of a configuration error
     */
    private TYPE createInstanceForConfiguration0(Configuration config,
            Class<?> theClass)
            throws InstantiationException,
                IllegalAccessException,
                ConfigurationException {

        @SuppressWarnings("unchecked")
        TYPE instance = (TYPE) theClass.newInstance();
        enableLogging(instance, logger);
        configure(instance, config);
        return instance;
    }

    /**
     * Create a new instance for a given configuration using a constructor with
     * one argument.
     * 
     * @param config the configuration to us
     * @param constructor the constructor to use
     * @param arg the only argument for the constructor
     * 
     * @return a new instance or <code>null</code> if the constructor is not of
     *         a supported type
     * 
     * @throws InstantiationException in case of an instantiation error
     * @throws IllegalAccessException in case of an access error
     * @throws InvocationTargetException in case of an invocation error
     * @throws ConfigurationException in case of a configuration error
     */
    @SuppressWarnings("unchecked")
    private TYPE createInstanceForConfiguration1(Configuration config,
            Constructor<?> constructor, Class<?> arg)
            throws InstantiationException,
                IllegalAccessException,
                InvocationTargetException,
                ConfigurationException {

        TYPE instance;
        if (arg.isAssignableFrom(Configuration.class)) {
            instance = (TYPE) constructor.newInstance(config);
            enableLogging(instance, logger);
            return instance;
        } else if (arg.isAssignableFrom(Logger.class)) {
            instance = (TYPE) constructor.newInstance(logger);
            configure(instance, config);
            return instance;
        } else {
            return null;
        }
    }

    /**
     * Create a new instance for a given configuration using a constructor with
     * two arguments.
     * 
     * @param config the configuration to us
     * @param constructor the constructor to use
     * @param arg1 the first argument for the constructor
     * @param arg2 the second argument for the constructor
     * 
     * @return a new instance or <code>null</code> if the constructor is not of
     *         a supported type
     * 
     * @throws InstantiationException in case of an instantiation error
     * @throws IllegalAccessException in case of an access error
     * @throws InvocationTargetException in case of an invocation error
     */
    @SuppressWarnings("unchecked")
    private TYPE createInstanceForConfiguration2(Configuration config,
            Constructor<?> constructor, Class<?> arg1, Class<?> arg2)
            throws InstantiationException,
                IllegalAccessException,
                InvocationTargetException {

        TYPE instance;
        if (arg1.isAssignableFrom(Configuration.class)
                && arg2.isAssignableFrom(Logger.class)) {
            instance = (TYPE) constructor.newInstance(config, logger);
            return instance;
        } else if (arg1.isAssignableFrom(Logger.class)
                && arg2.isAssignableFrom(Configuration.class)) {
            instance = (TYPE) constructor.newInstance(logger, config);
            return instance;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(java.util.logging.Logger)
     */
    @Override
    public void enableLogging(Logger theLogger) {

        this.logger = theLogger;
    }

    /**
     * Getter for logger.
     * 
     * @return the logger.
     */
    public final Logger getLogger() {

        return this.logger;
    }

    /**
     * Getter for resourceFinder.
     * 
     * @return the resourceFinder
     */
    public final ResourceFinder getResourceFinder() {

        return resourceFinder;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.RegistrarObserver#reconnect(java.lang.Object)
     */
    @Override
    public Object reconnect(Object instance) throws RegistrarException {

        try {
            if (instance instanceof LogEnabled) {
                ((LogEnabled) instance).enableLogging(logger);
            }
            if (instance instanceof Configurable) {
                ((Configurable) instance).configure(getConfiguration());
            }
        } catch (ConfigurationException e) {
            throw new RegistrarException(e);
        }

        return instance;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.resource.ResourceAware#setResourceFinder(org.extex.resource.ResourceFinder)
     */
    @Override
    public void setResourceFinder(ResourceFinder finder) {

        this.resourceFinder = finder;
    }
}
