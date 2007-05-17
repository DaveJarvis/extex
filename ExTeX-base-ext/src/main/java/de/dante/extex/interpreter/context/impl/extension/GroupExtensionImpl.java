/*
 * Copyright (C) 2004 The ExTeX Group and individual authors listed below
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

package de.dante.extex.interpreter.context.impl.extension;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.extex.baseext.exception.InterpreterExtensionException;
import org.extex.core.exception.GeneralException;
import org.extex.interpreter.max.context.Group;
import org.extex.interpreter.max.context.GroupImpl;
import org.extex.scanner.Tokenizer;

import de.dante.extex.interpreter.type.bool.Bool;
import de.dante.extex.interpreter.type.hash.toks.HashToks;
import de.dante.extex.interpreter.type.pair.Pair;
import de.dante.extex.interpreter.type.real.Real;
import de.dante.extex.interpreter.type.transform.Transform;

/**
 * This is a simple implementation for a group with ExTeX-functions.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class GroupExtensionImpl extends GroupImpl
        implements
            Tokenizer,
            Group,
            GroupExtension,
            Serializable {

    /**
     * The field <tt>serialVersionUID</tt> ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * The map for the real registers
     */
    private Map<String, Real> realMap = new HashMap<String, Real>();

    /**
     * The map for the bool registers
     */
    private Map<String, Bool> boolMap = new HashMap<String, Bool>();

    /**
     * The map for the pair registers
     */
    private Map<String, Pair> pairMap = new HashMap<String, Pair>();

    /**
     * The map for the transform registers
     */
    private Map<String, Transform> transformMap =
            new HashMap<String, Transform>();

    /**
     * The map for the hash-toks registers
     */
    private Map<String, HashToks> hashtoksMap = new HashMap<String, HashToks>();

    /**
     * The next group in the linked list
     */
    private GroupExtension nextext = null;

    /**
     * Creates a new object.
     * 
     * @param next the next group in the stack. If the value is
     *        <code>null</code> then this is the global base
     * @throws GeneralException if the group is not a groupextension
     */
    public GroupExtensionImpl(Group next) throws GeneralException {

        super(next);
        if (next != null && !(next instanceof GroupExtension)) {
            throw new InterpreterExtensionException();
        }
        nextext = (GroupExtension) next;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#getReal(
     *      java.lang.String)
     */
    public Real getReal(String name) {

        Real real = (realMap.get(name));

        if (real == null) {
            if (nextext != null) {
                real = nextext.getReal(name);
            } else {
                real = new Real(0);
                setReal(name, real);
            }
        }

        return real;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setReal(
     *      java.lang.String, de.dante.extex.interpreter.type.real.Real,
     *      boolean)
     */
    public void setReal(String name, Real value, boolean global) {

        setReal(name, value);

        if (global && nextext != null) {
            nextext.setReal(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setReal(
     *      java.lang.String, de.dante.extex.interpreter.type.real.Real)
     */
    public void setReal(String name, Real value) {

        realMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#getBool(
     *      java.lang.String)
     */
    public Bool getBool(String name) {

        Bool bool = (boolMap.get(name));

        if (bool == null) {
            if (nextext != null) {
                bool = nextext.getBool(name);
            } else {
                bool = new Bool();
                setBool(name, bool);
            }
        }

        return bool;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setBool(
     *      java.lang.String, de.dante.extex.interpreter.type.bool.Bool,
     *      boolean)
     */
    public void setBool(String name, Bool value, boolean global) {

        setBool(name, value);

        if (global && nextext != null) {
            nextext.setBool(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setBool(
     *      java.lang.String, de.dante.extex.interpreter.type.bool.Bool)
     */
    public void setBool(String name, Bool value) {

        boolMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#getPair(
     *      java.lang.String)
     */
    public Pair getPair(String name) {

        Pair pair = (pairMap.get(name));

        if (pair == null) {
            if (nextext != null) {
                pair = nextext.getPair(name);
            } else {
                pair = new Pair();
                setPair(name, pair);
            }
        }

        return pair;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setPair(
     *      java.lang.String, de.dante.extex.interpreter.type.pair.Pair,
     *      boolean)
     */
    public void setPair(String name, Pair value, boolean global) {

        setPair(name, value);

        if (global && nextext != null) {
            nextext.setPair(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setPair(
     *      java.lang.String, de.dante.extex.interpreter.type.pair.Pair)
     */
    public void setPair(String name, Pair value) {

        pairMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#getTransform(
     *      java.lang.String)
     */
    public Transform getTransform(String name) {

        Transform transform = (transformMap.get(name));

        if (transform == null) {
            if (nextext != null) {
                transform = nextext.getTransform(name);
            } else {
                transform = new Transform();
                setTransform(name, transform);
            }
        }

        return transform;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setTransform(
     *      java.lang.String,
     *      de.dante.extex.interpreter.type.transform.Transform, boolean)
     */
    public void setTransform(String name, Transform value, boolean global) {

        setTransform(name, value);

        if (global && nextext != null) {
            nextext.setTransform(name, value, global);
        }

    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setTransform(
     *      java.lang.String,
     *      de.dante.extex.interpreter.type.transform.Transform)
     */
    public void setTransform(String name, Transform value) {

        transformMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#getHashToks(
     *      java.lang.String)
     */
    public HashToks getHashToks(String name) {

        HashToks hashtoks = hashtoksMap.get(name);

        if (hashtoks == null) {
            if (nextext != null) {
                hashtoks = nextext.getHashToks(name);
            } else {
                hashtoks = new HashToks();
                setHashToks(name, hashtoks);
            }
        }

        return hashtoks;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setHashToks(
     *      java.lang.String,
     *      de.dante.extex.interpreter.type.hash.toks.HashToks, boolean)
     */
    public void setHashToks(String name, HashToks value, boolean global) {

        setHashToks(name, value);

        if (global && nextext != null) {
            nextext.setHashToks(name, value, global);
        }

    }

    /**
     * @see de.dante.extex.interpreter.context.impl.extension.GroupExtension#setHashToks(
     *      java.lang.String,
     *      de.dante.extex.interpreter.type.hash.toks.HashToks)
     */
    public void setHashToks(String name, HashToks value) {

        hashtoksMap.put(name, value);
    }
}
