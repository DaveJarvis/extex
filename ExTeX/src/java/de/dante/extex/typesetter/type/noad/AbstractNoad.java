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
package de.dante.extex.typesetter.type.noad;


/**
 * This is the abstract base class for Noads.
 * A {@link de.dante.extex.typesetter.type.noad.Noad Noad} is the intermediate
 * data structure which is used for processing mathamatical material. Finally
 * Noads are translated into {@link de.dante.extex.typesetter.Node Node}s. Thus
 * Noad will never arrive at the DocumentWriter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class AbstractNoad implements Noad, NSSNoad {

    /**
     * The field <tt>nucleus</tt> contains the nuclues noad.
     */
    private Noad nucleus = null;

    /**
     * The field <tt>subscript</tt> contains the subscript noad.
     */
    private Noad subscript = null;

    /**
     * The field <tt>superscript</tt> contains the superscript noad.
     */
    private Noad superscript = null;

    /**
     * Creates a new object.
     *
     */
    public AbstractNoad() {

        super();
    }

    /**
     * Creates a new object.
     *
     * @param theNucleus the nucleus
     */
    public AbstractNoad(final Noad theNucleus) {

        super();
        nucleus = theNucleus;
    }

    /**
     * Getter for the nucleus.
     *
     * @return the nucleus.
     */
    public Noad getNucleus() {

        return this.nucleus;
    }

    /**
     * Getter for the subscript.
     *
     * @return the subscript.
     */
    public Noad getSubscript() {

        return this.subscript;
    }

    /**
     * Getter for the superscript.
     *
     * @return the superscript.
     */
    public Noad getSuperscript() {

        return this.superscript;
    }

    protected abstract String stringName();

    /**
     * @see "TTP [696]"
     * @see de.dante.extex.typesetter.type.noad.Noad#toString(java.lang.StringBuffer)
     */
    public void toString(final StringBuffer sb) {

        sb.append("\\");
        sb.append(stringName());
        // TODO unimplemented

    }
    /**
     * Setter for the nucleus.
     *
     * @param nucleus the nucleus to set.
     */
    public void setNucleus(final Noad nucleus) {

        this.nucleus = nucleus;
    }

    /**
     * Setter for the subscript.
     *
     * @param subscript the subscript to set.
     */
    public void setSubscript(final Noad subscript) {

        this.subscript = subscript;
    }

    /**
     * Setter for the superscript.
     *
     * @param superscript the superscript to set.
     */
    public void setSuperscript(final Noad superscript) {

        this.superscript = superscript;
    }

}
