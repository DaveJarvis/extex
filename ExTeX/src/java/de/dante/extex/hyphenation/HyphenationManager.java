/*
 * Copyright (C) 2003-2005 The ExTeX Group and individual authors listed below
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

package de.dante.extex.hyphenation;

import de.dante.util.configuration.ConfigurationException;

/**
 * This class manages the <code>HyphenationTable</code>s. It is a container
 * which can be asked to provide an appropriate instance. This instance is
 * either taken from existing instances or a new instance is created.
 *
 * <h2>Configuration</h2>
 *
 * This instance is configurable. The configuration is used to select the
 * appropriate classs and optional parameters for a requested instance. In this
 * respect this class makes best use of the infrastructure of the
 * {@link de.dante.util.framework.AbstractFactory AbstractFactory}.
 *
 *
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public interface HyphenationManager {

    /**
     * Return the <code>HyphernationTable</code> for a given name.
     * <p>
     *  If there is no table present with the given name then
     *  a new one is created.
     * </p>
     * <p>
     *  The index in TeX is the language number as <code>String</code>. This
     *  implementation does not have this restriction. The name can be any
     *  string.
     * </p>
     * <p>
     *  The proposal is to use a natural number for backward compatibility and
     *  ISO language codes otherwise.
     * </p>
     *
     * @param index the name for which the hyphenation table is requested
     *
     * @return the hyphenation table for the given name
     *
     * @throws ConfigurationException in case of an error in the configuration
     *
     * @see de.dante.extex.hyphenation.HyphenationManager#getHyphenationTable(
     *      java.lang.String)
     */
    HyphenationTable getHyphenationTable(String index)
            throws ConfigurationException;

    /**
     * Return the <code>HyphernationTable</code> for a given name.
     * <p>
     *  If there is no table present with the given name then
     *  an attempt is made to load one. As a last resort an empty hyphenation
     *  table is created.
     * </p>
     * <p>
     *  The index in TeX is the language number as <code>String</code>. This
     *  implementation does not have this restriction. The name can be any
     *  string.
     * </p>
     * <p>
     *  The proposal is to use a natural number for backward compatibility and
     *  ISO language codes otherwise.
     * </p>
     *
     * @param index the name for which the hyphenation table is requested
     *
     * @return the hyphenation table for the given name
     *
     * @throws ConfigurationException in case of an error in the configuration
     *
     * @see de.dante.extex.hyphenation.HyphenationManager#getHyphenationTable(
     *      java.lang.String)
     */
    HyphenationTable useHyphenationTable(String index)
            throws ConfigurationException;

}