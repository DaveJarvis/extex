/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max.context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.extex.interpreter.context.group.GroupType;
import org.extex.scanner.type.token.Token;
import org.extex.type.Locator;
import org.extex.util.framework.configuration.Configuration;
import org.extex.util.framework.configuration.exception.ConfigurationClassNotFoundException;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.configuration.exception.ConfigurationInstantiationException;
import org.extex.util.framework.configuration.exception.ConfigurationMissingAttributeException;

/**
 * This class provides a factory for a
 * {@link org.extex.interpreter.max.context.Group Group}.
 *
 *
 * <pre>
 *  &lt;Group class="the.package.TheClass"&gt;
 *  &lt;/Group&gt;
 * </pre>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4770 $
 */
public class GroupFactory {

    /**
     * The constant <tt>CLASS_ATTRIBUTE</tt> contains the name of the attribute
     * for the class name.
     */
    private static final String CLASS_ATTRIBUTE = "class";

    /**
     * The field <tt>constructor</tt> contains the constructor of the class to
     * instantiate. It is kept here to speed up the method
     * {@link #newInstance(org.extex.interpreter.max.context.Group)
     *  newInstance}.
     */
    private Constructor constructor;

    /**
     * Creates a new object.
     *
     * @param config the configuration for this factory
     *
     * @throws ConfigurationException in case of an error in the configuration.
     */
    public GroupFactory(final Configuration config) {

        super();

        String classname = config.getAttribute(CLASS_ATTRIBUTE);
        if (classname == null) {
            throw new ConfigurationMissingAttributeException(CLASS_ATTRIBUTE,
                config);
        }

        try {
            constructor =
                    Class.forName(classname).getConstructor(
                        new Class[]{Group.class});
        } catch (SecurityException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (NoSuchMethodException e) {
            throw new ConfigurationInstantiationException(e);
        } catch (ClassNotFoundException e) {
            throw new ConfigurationClassNotFoundException(classname, config);
        }
    }

    /**
     * Get a instance of a
     * {@link org.extex.interpreter.max.context.Group Group}.
     *
     * @param next the next group
     * @param locator the locator for the start of the group
     * @param start the token which started the group
     * @param type the group type
     *
     * @return a new instance for the interface Group
     *
     * @throws ConfigurationException in case of an error in the configuration.
     */
    public Group newInstance(final Group next, final Locator locator,
            final Token start, final GroupType type) {

        Group group;

        try {
            group = (Group) constructor.newInstance(new Object[]{next});
            group.setType(type);
            group.setLocator(locator);
            group.setStart(start);
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

        return group;
    }

}
