/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.type.box;

import org.extex.core.exception.helping.HelpingException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.type.node.RuleNode;

/**
 * This interface describes the possibility to provide a rule as result of the
 * operation. Usually this box is gathered from the input stream and build by
 * the typesetter.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4399 $
 */
public interface RuleConvertible extends BoxOrRule {

    /**
     * Getter for the content as Rule.
     *
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter to use
     *
     * @return an appropriate Box
     * @throws ConfigurationException in case of an configuration error
     * @throws HelpingException TODO
     * @throws TypesetterException TODO
     */
    RuleNode getRule(Context context, TokenSource source, Typesetter typesetter)
            throws ConfigurationException, HelpingException, TypesetterException;

}
