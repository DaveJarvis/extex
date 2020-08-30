/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega;

import java.io.InputStream;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.context.observer.load.LoadedObserver;
import org.extex.interpreter.unit.UnitInfo;
import org.extex.scanner.stream.InputStreamInterceptor;
import org.extex.typesetter.Typesetter;

/**
 * This class provides the setup for the unit <b>omega</b>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class OmegaUnitInfo extends UnitInfo implements LoadedObserver {

    /**
     * This class provides a stream decorator which is capable to access the
     * context.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    private final class Interceptor implements InputStreamInterceptor {

        /**
         * The field {@code context} contains the interpreter context.
         */
        private Context context;

        /**
         * Creates a new object.
         * 
         * @param context the context
         */
        public Interceptor(Context context) {

            this.context = context;
        }

        /**
    *      java.io.InputStream)
         */
        public InputStream pipe(InputStream stream) {

            return new OmegaInputStream(stream, context);
        }
    }

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;


    public OmegaUnitInfo() {

    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public void receiveLoaded(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException {

        source.getTokenStreamFactory().register(new Interceptor(context));

    }

}
