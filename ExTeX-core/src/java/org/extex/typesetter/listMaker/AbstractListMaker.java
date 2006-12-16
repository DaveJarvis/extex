/*
 * Copyright (C) 2003-2006 The ExTeX Group and individual authors listed below
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

package org.extex.typesetter.listMaker;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.exception.helping.MissingMathException;
import org.extex.interpreter.type.count.FixedCount;
import org.extex.interpreter.type.dimen.FixedDimen;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.Token;
import org.extex.type.Locator;
import org.extex.typesetter.ListMaker;
import org.extex.typesetter.Mode;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.InvalidSpacefactorException;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.exception.TypesetterHelpingException;
import org.extex.typesetter.exception.TypesetterUnsupportedException;
import org.extex.typesetter.listMaker.math.DisplaymathListMaker;
import org.extex.typesetter.listMaker.math.MathListMaker;
import org.extex.util.framework.configuration.exception.ConfigurationException;
import org.extex.util.framework.i18n.Localizer;
import org.extex.util.framework.i18n.LocalizerFactory;



/**
 * This abstract class provides some methods common to all ListMakers.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4775 $
 */
public abstract class AbstractListMaker implements ListMaker {

    /**
     * The field <tt>locator</tt> contains the locator pointing to the start.
     */
    private Locator locator;

    /**
     * The field <tt>manager</tt> contains the manager to ask for global
     * changes.
     */
    private ListManager manager;

    /**
     * Creates a new object.
     *
     * @param theManager the manager to ask for global changes
     * @param locator the locator
     */
    public AbstractListMaker(final ListManager theManager, final Locator locator) {

        super();
        this.manager = theManager;
        this.locator = locator;
    }

    /**
     * Getter for the localizer.
     *
     * @return the localizer
     */
    protected Localizer getLocalizer() {

        return LocalizerFactory.getLocalizer(this.getClass());
    }

    /**
     * @see org.extex.typesetter.ListMaker#getLocator()
     */
    public Locator getLocator() {

        return locator;
    }

    /**
     * Getter for manager.
     *
     * @return the manager.
     */
    public ListManager getManager() {

        return manager;
    }

    /**
     * @see org.extex.typesetter.ListMaker#getMode()
     */
    public abstract Mode getMode();

    /**
     * Getter for the localizer.
     *
     * @return the localizer
     */
    protected Localizer getMyLocalizer() {

        return LocalizerFactory.getLocalizer(AbstractListMaker.class);
    }

    /**
     * @see org.extex.typesetter.ListMaker#getPrevDepth()
     */
    public FixedDimen getPrevDepth() throws TypesetterUnsupportedException {

        throw new TypesetterUnsupportedException();
    }

    /**
     * @see org.extex.typesetter.ListMaker#getSpacefactor()
     */
    public long getSpacefactor() throws TypesetterUnsupportedException {

        throw new TypesetterUnsupportedException();
    }

    /**
     * @see org.extex.typesetter.ListMaker#leftBrace()
     */
    public void leftBrace() {

    }

    /**
     * @see org.extex.typesetter.ListMaker#mathShift(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    public void mathShift(final Context context, final TokenSource source,
            final Token t) throws TypesetterException, ConfigurationException {

        try {
            Token next = source.getToken(context);

            if (next == null) {
                throw new TypesetterException(new MissingMathException(t
                        .toString()));
            } else if (!next.isa(Catcode.MATHSHIFT)) {
                source.push(next);
                manager.push(new MathListMaker(manager, source.getLocator()));
                source.push(context.getToks("everymath"));
            } else {
                manager.push(new DisplaymathListMaker(manager, source
                        .getLocator()));
                source.push(context.getToks("everydisplay"));
            }
            context.setCount("fam", -1, false);

        } catch (TypesetterException e) {
            throw e;
        } catch (InterpreterException e) {
            throw new TypesetterException(e);
        }
    }

    /**
     * @see org.extex.typesetter.ListMaker#rightBrace()
     */
    public void rightBrace() throws TypesetterException {

    }

    /**
     * @see org.extex.typesetter.ListMaker#setPrevDepth(
     *      org.extex.interpreter.type.dimen.FixedDimen)
     */
    public void setPrevDepth(final FixedDimen pd)
            throws TypesetterUnsupportedException {

        throw new TypesetterUnsupportedException();
    }

    /**
     * @see org.extex.typesetter.ListMaker#setSpacefactor(
     *      org.extex.interpreter.type.count.FixedCount)
     */
    public void setSpacefactor(final FixedCount f)
            throws TypesetterUnsupportedException,
                InvalidSpacefactorException {

        throw new TypesetterUnsupportedException();
    }

    /**
     * @see org.extex.typesetter.ListMaker#subscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public void subscriptMark(final Context context, final TokenSource source,
            final Typesetter typesetter, final Token token)
            throws TypesetterException {

        throw new TypesetterException(
                new MissingMathException(token.toString()));
    }

    /**
     * @see org.extex.typesetter.ListMaker#superscriptMark(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.typesetter.Typesetter,
     *      org.extex.scanner.type.token.Token)
     */
    public void superscriptMark(final Context context,
            final TokenSource source, final Typesetter typesetter,
            final Token token) throws TypesetterException {

        throw new TypesetterException(
                new MissingMathException(token.toString()));
    }

    /**
     * @see org.extex.typesetter.ListMaker#tab(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource,
     *      org.extex.scanner.type.token.Token)
     */
    public void tab(final Context context, final TokenSource source,
            final Token token)
            throws TypesetterException,
                ConfigurationException {

        throw new TypesetterHelpingException(getMyLocalizer(),
                "TTP.MisplacedTabMark", token.toString());
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        String name = getClass().getName();
        return name.substring(name.lastIndexOf('.') + 1);
    }

}
