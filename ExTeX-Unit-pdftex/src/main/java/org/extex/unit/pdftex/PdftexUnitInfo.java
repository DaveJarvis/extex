/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.pdftex;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.ContextInternals;
import org.extex.interpreter.context.observer.count.CountObservable;
import org.extex.interpreter.context.observer.count.CountObserver;
import org.extex.interpreter.context.observer.load.LoadedObserver;
import org.extex.interpreter.unit.Loader;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.typesetter.Typesetter;

/**
 * This class provides the setup for the unit <b>pdftex</b>.
 * 
 * 
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4431 $
 */
public class PdftexUnitInfo extends UnitInfo implements Loader, LoadedObserver {

    /**
     * TODO gene: missing JavaDoc.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision$
     */
    private final class Observer implements CountObserver {

        /**
         * The field <tt>typesetter</tt> contains the typesetter.
         */
        private Typesetter typesetter;

        /**
         * Creates a new object.
         * 
         * @param source the source for more tokens
         * @param typesetter the typesetter
         */
        public Observer(TokenSource source, Typesetter typesetter) {

            this.typesetter = typesetter;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.interpreter.context.observer.count.CountObserver#receiveCountChange(
         *      org.extex.interpreter.context.ContextInternals,
         *      java.lang.String, org.extex.core.count.Count)
         */
        public void receiveCountChange(ContextInternals context, String name,
                Count value) throws Exception {

            //TODO gene: make use of \pdfoutput
            
//            System.err.print("Received ");
//            System.err.print(name);
//            System.err.print(" = ");
//            System.err.print(value.toString());
//            System.err.print(" ");
//            System.err.println(typesetter);
            
        }

        /**
         * Setter for the typesetter.
         * 
         * @param typesetter the typesetter
         */
        public void setTypsetter(Typesetter typesetter) {

            this.typesetter = typesetter;
        }
    }

    /**
     * The field <tt>serialVersionUID</tt> contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>TRACING_COMMANDS</tt> contains the name of the count
     * register controlling the activation of command tracing.
     */
    private static final String TRACING_COMMANDS = "tracingcommands";

    /**
     * The field <tt>observer</tt> contains the count observer.
     */
    private transient Observer observer = null;

    /**
     * The field <tt>context</tt> contains the ...
     */
    private Context context;

    /**
     * The field <tt>source</tt> contains the ...
     */
    private TokenSource source;

    /**
     * Creates a new object.
     */
    public PdftexUnitInfo() {

        super();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.unit.UnitInfo#setTypesetter(
     *      org.extex.typesetter.Typesetter)
     */
    public void setTypesetter(Typesetter typesetter) {

        observePdfoutput(typesetter);
        observer.setTypsetter(typesetter);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.unit.Loader#load(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void load(Context context, TokenSource source, Typesetter typesetter)
            throws HelpingException {

        this.context = context;
        this.source = source;
        receiveLoaded(context, source, typesetter);
    }

    /**
     * Return the singleton constant object after the serialized instance has
     * been read back in.
     * 
     * @return the instance of this object
     */
    protected Object readResolve() {

        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.context.observer.load.LoadedObserver#receiveLoaded(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public void receiveLoaded(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        this.context = context;
        this.source = source;
        observePdfoutput(typesetter);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param typesetter the typesetter
     */
    private void observePdfoutput(Typesetter typesetter) {

        if (typesetter != null && observer == null
                && context instanceof CountObservable) {
            observer = new Observer(source, typesetter);
            ((CountObservable) context).registerCountObserver("pdfoutput",
                observer);
        }
    }

}
