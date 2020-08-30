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

package org.extex.interpreter.context.impl.extension;

import java.io.Serializable;

import org.extex.baseext.exception.InterpreterExtensionException;
import org.extex.core.exception.GeneralException;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextExtension;
import org.extex.interpreter.max.context.ContextImpl;
import org.extex.interpreter.primitives.register.bool.Bool;
import org.extex.interpreter.primitives.register.hash.HashToks;
import org.extex.interpreter.primitives.register.pair.Pair;
import org.extex.interpreter.primitives.register.real.Real;
import org.extex.interpreter.primitives.register.transform.Transform;


/**
 * This is a reference implementation for an interpreter context with ExTeX
 * functions.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/
public class ContextExtensionImpl extends ContextImpl
        implements
            Context,
            ContextExtension,
            Serializable {

    /**
     * The field {@code serialVersionUID} contains the ...
     */
    private static final long serialVersionUID = 2006L;

    /**
     * Creates a new object.
     * 
     * @param config the configuration
     * @throws ConfigurationException ...
     * @throws GeneralException ...
     */
    public ContextExtensionImpl(Configuration config)
            throws ConfigurationException,
                GeneralException {

        configure(config);
        if (!(getGroup() instanceof GroupExtension)) {
            throw new InterpreterExtensionException();
        }
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#getReal(
     *      java.lang.String)
     */
    public Real getReal(String name) {

        return ((GroupExtension) getGroup()).getReal(name);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setReal(
     *      java.lang.String, org.extex.interpreter.primitives.register.real.Real,
     *      boolean)
     */
    public void setReal(String name, Real value, boolean global) {

        ((GroupExtension) getGroup()).setReal(name, value, global);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setReal(
     *      java.lang.String, org.extex.interpreter.primitives.register.real.Real)
     */
    public void setReal(String name, Real value) {

        ((GroupExtension) getGroup()).setReal(name, value);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#getBool(java.lang.String)
     */
    public Bool getBool(String name) {

        return ((GroupExtension) getGroup()).getBool(name);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setBool(
     *      java.lang.String, org.extex.interpreter.primitives.register.bool.Bool,
     *      boolean)
     */
    public void setBool(String name, Bool value, boolean global) {

        ((GroupExtension) getGroup()).setBool(name, value, global);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setBool(
     *      java.lang.String, org.extex.interpreter.primitives.register.bool.Bool)
     */
    public void setBool(String name, Bool value) {

        ((GroupExtension) getGroup()).setBool(name, value);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#getPair(
     *      java.lang.String)
     */
    public Pair getPair(String name) {

        return ((GroupExtension) getGroup()).getPair(name);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setPair(
     *      java.lang.String, org.extex.interpreter.primitives.register.pair.Pair,
     *      boolean)
     */
    public void setPair(String name, Pair value, boolean global) {

        ((GroupExtension) getGroup()).setPair(name, value, global);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setPair(
     *      java.lang.String, org.extex.interpreter.primitives.register.pair.Pair)
     */
    public void setPair(String name, Pair value) {

        ((GroupExtension) getGroup()).setPair(name, value);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#getTransform(
     *      java.lang.String)
     */
    public Transform getTransform(String name) {

        return ((GroupExtension) getGroup()).getTransform(name);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setTransform(
     *      java.lang.String,
     *      org.extex.interpreter.primitives.register.transform.Transform, boolean)
     */
    public void setTransform(String name, Transform value, boolean global) {

        ((GroupExtension) getGroup()).setTransform(name, value, global);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setTransform(
     *      java.lang.String,
     *      org.extex.interpreter.primitives.register.transform.Transform)
     */
    public void setTransform(String name, Transform value) {

        ((GroupExtension) getGroup()).setTransform(name, value);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#getHashToks(
     *      java.lang.String)
     */
    public HashToks getHashToks(String name) {

        return ((GroupExtension) getGroup()).getHashToks(name);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setHashToks(
     *      java.lang.String,
     *      org.extex.interpreter.primitives.register.hash.HashToks, boolean)
     */
    public void setHashToks(String name, HashToks value, boolean global) {

        ((GroupExtension) getGroup()).setHashToks(name, value, global);
    }

    /**
     * @see org.extex.interpreter.context.ContextExtension#setHashToks(
     *      java.lang.String,
     *      org.extex.interpreter.primitives.register.hash.HashToks)
     */
    public void setHashToks(String name, HashToks value) {

        ((GroupExtension) getGroup()).setHashToks(name, value);
    }
}
