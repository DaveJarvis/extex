/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */
package de.dante.extex.interpreter.context;

import de.dante.extex.interpreter.type.Font;
import de.dante.util.configuration.Configuration;
import de.dante.util.configuration.ConfigurationClassNotFoundException;
import de.dante.util.configuration.ConfigurationException;
import de.dante.util.configuration.ConfigurationInstantiationException;
import de.dante.util.configuration.ConfigurationMissingAttributeException;

/**
 * ...
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class TypesettingContextFactory {
    /**
     * The constant <tt>CLASS_ATTRIBUTE</tt> contains the name of the
     * attribute used to extract the class name fron the given configuration.
     */
    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * The field <tt>config</tt> contains the configuration for this factory.
     */
    private Configuration config = null;

    /**
     * The field <tt>theClass</tt> contains the class to instantiate. It is
     * kept here to speed up the method
     * <tt>{@link #newInstance() newInstance()}</tt>.
     */
    private Class theClass;

    /**
     * Creates a new object.
     *
     * @param configuration the configuration for this factory
     *
     * @throws ConfigurationMissingAttributeException in case that the
     *      attribute <tt>CLASS_ATTRIBUTE</tt> is not set for the given
     *      configuration.
     * @throws ConfigurationInstantiationException in case that the
     *      instantiation of the given class causes a SecurityException.
     * @throws ConfigurationClassNotFoundException in case that the named class
     *      could not be loaded.
     */
    public TypesettingContextFactory(final Configuration configuration)
            throws ConfigurationException {
        super();
        this.config = configuration;

        String classname = config.getAttribute(CLASS_ATTRIBUTE);
        if (classname == null) {
            throw new ConfigurationMissingAttributeException(CLASS_ATTRIBUTE,
                    config);
        }

        try {
            theClass = Class.forName(classname);
        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(classname, config);
        }
    }

    /**
     * Factory method to acquire an instance of the TypesettingContext.
     *
     * @return an appropriate instance of the TypesettingContext.
     *
     * @throws ConfigurationInstantiationException in case that the
     *             instantiation of the class failed.
     */
    public TypesettingContext newInstance()
            throws ConfigurationInstantiationException {

        TypesettingContext context;

        try {
            context = (TypesettingContext) (theClass.newInstance());
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        }

        return context;
    }

    /**
     * Factory method to acquire an instance of the TypesettingContext.
     *
     * @return an appropriate instance of the TypesettingContext.
     *
     * @throws ConfigurationInstantiationException in case that the
     *             instantiation of the class failed.
     */
    public TypesettingContext newInstance(final TypesettingContext context,
            final Font font)
            throws ConfigurationInstantiationException {

        TypesettingContext c;

        try {
            c = (TypesettingContext) (theClass.newInstance());
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        }
        
        c.set(context);
        c.setFont(font);

        return c;
    }

    /**
     * Factory method to acquire an instance of the TypesettingContext.
     *
     * @return an appropriate instance of the TypesettingContext.
     *
     * @throws ConfigurationInstantiationException in case that the
     *             instantiation of the class failed.
     */
    public TypesettingContext newInstance(final TypesettingContext context,
            final Color color)
            throws ConfigurationInstantiationException {

        TypesettingContext c;

        try {
            c = (TypesettingContext) (theClass.newInstance());
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        }
        
        c.set(context);
        c.setColor(color);

        return c;
    }

    /**
     * Factory method to acquire an instance of the TypesettingContext.
     *
     * @return an appropriate instance of the TypesettingContext.
     *
     * @throws ConfigurationInstantiationException in case that the
     *             instantiation of the class failed.
     */
    public TypesettingContext newInstance(final TypesettingContext context,
            final Direction direction)
            throws ConfigurationInstantiationException {

        TypesettingContext c;

        try {
            c = (TypesettingContext) (theClass.newInstance());
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        }
        
        c.set(context);
        c.setDirection(direction);

        return c;
    }

    /**
     * Factory method to acquire an instance of the TypesettingContext.
     *
     * @return an appropriate instance of the TypesettingContext.
     *
     * @throws ConfigurationInstantiationException in case that the
     *             instantiation of the class failed.
     */
    public TypesettingContext newInstance(final TypesettingContext context,
            final int angle)
            throws ConfigurationInstantiationException {

        TypesettingContext c;

        try {
            c = (TypesettingContext) (theClass.newInstance());
        } catch (InstantiationException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (IllegalAccessException e) {
            throw new ConfigurationInstantiationException(e);
        }
        
        c.set(context);
        c.setAngle(angle);

        return c;
    }

}
