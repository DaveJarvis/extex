/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.translation;

import org.extex.core.exception.helping.HelpingException;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.resource.ResourceAware;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.token.CodeToken;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.unit.omega.OmegaExtension;
import org.extex.unit.omega.mode.AbstractModeCode;
import org.extex.unit.omega.mode.OmegaMode;
import org.extex.unit.omega.ocp.util.Ocp;
import org.extex.unit.omega.ocp.util.OcpUtil;

/**
 * This class provides an implementation for the primitive
 * {@code \DefaultOutputTranslation}.
 * 
 * <p>The Primitive {@code \DefaultOutputTranslation}</p>
 * <p>
 * TODO missing documentation
 * </p>
 * <p>Syntax</p>
 The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;DefaultOutputTranslation&rang;
 *      &rarr; {@code \DefaultOutputTranslation} &lang;mode&rang; &lang;ocp&rang;
 *
 *    &lang;mode&rang;
 *      &rarr; {@code onebyte}
 *       |  {@code ebcdic}
 *       |  {@code twobyte}
 *       |  {@code twobyteLE}   </pre>
 * 
 * <p>Examples</p>

 * 
 * <pre class="TeXSample">
 *   \DefaultOutputTranslation onebyte \OCPebcdic  </pre>
 * 
 *
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class DefaultOutputTranslation extends AbstractModeCode
        implements
            ResourceAware {

    /**
     * The field {@code serialVersionUID} contains the version number for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field {@code finder} contains the resource finder.
     */
    private transient ResourceFinder finder;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public DefaultOutputTranslation(CodeToken token) {

        super(token);
    }

    /**
*      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void execute(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        OmegaMode mode = scanOutputMode(context, source);
        Ocp ocp = OcpUtil.scanOcp(context, source, typesetter, finder);

        context.set(OmegaExtension.NAME,
            DEFAULT_OUTPUT_TRANSLATION + mode.toString(),
            ocp, prefix.clearGlobal());
    }

@Override
    public void setResourceFinder(ResourceFinder resourceFinder) {

        this.finder = resourceFinder;
    }

}
