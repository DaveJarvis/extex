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

package org.extex.unit.pdftex;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.core.count.Count;
import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;
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
*/
public class PdftexUnitInfo extends UnitInfo
        implements
            Configurable,
            Loader,
            LoadedObserver {

    /**
     * This observer takes care to enable the appropriate document writer when
     * \pdfoutput is modified.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private final class Observer implements CountObserver {

        /**
         * The field {@code typesetter} contains the typesetter.
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
    *      org.extex.interpreter.context.ContextInternals,
         *      java.lang.String, org.extex.core.count.Count)
         */
        public void receiveCountChange(ContextInternals ctx, String name,
                Count value) throws Exception {

            Long val = Long.valueOf(value.getValue());
            String writer = modeMap.get(val);
            if (writer == null) {
                writer = modeMap.get(Long.valueOf(1));
            }
            typesetter.getBackendDriver().setDocumentWriterType(writer);
        }

        /**
         * Setter for the typesetter.
         * 
         * @param t the typesetter
         */
        public void setTypsetter(Typesetter t) {

            this.typesetter = t;
        }
    }

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code context} contains the interpreter context.
     */
    private transient Context context;

    /**
     * The field {@code modeMap} contains the ...
     */
    private final Map<Long, String> modeMap = new HashMap<Long, String>();

    /**
     * The field {@code observer} contains the count observer.
     */
    private transient Observer observer = null;

    /**
     * The field {@code source} contains the source for new tokens.
     */
    private transient TokenSource source;


    public PdftexUnitInfo() {

    }

    /**
*      org.extex.framework.configuration.Configuration)
     */
    public void configure(Configuration config) throws ConfigurationException {

        Iterator<Configuration> iterator = config.iterator("pdfoutput");
        while (iterator.hasNext()) {
            Configuration cfg = iterator.next();
            String value = cfg.getAttribute("value");
            String writer = cfg.getAttribute("writer");
            modeMap.put(Long.valueOf(value), writer);
        }
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void load(Context ctx, TokenSource src, Typesetter typesetter)
            throws HelpingException {

        this.context = ctx;
        this.source = src;
        receiveLoaded(ctx, src, typesetter);
    }

    /**
     * If all preconditions are fulfilled then register an observer for
     * {@code \pdfoutput}.
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
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, Typesetter)
     */
    public void receiveLoaded(Context ctx, TokenSource src,
            Typesetter typesetter) throws HelpingException {

        this.context = ctx;
        this.source = src;
        observePdfoutput(typesetter);
    }

    /**
*      org.extex.typesetter.Typesetter)
     */
    @Override
    public void setTypesetter(Typesetter typesetter) {

        observePdfoutput(typesetter);
        observer.setTypsetter(typesetter);
    }

}
