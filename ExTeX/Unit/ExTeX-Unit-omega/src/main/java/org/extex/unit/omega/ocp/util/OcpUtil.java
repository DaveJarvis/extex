/*
 * Copyright (C) 2006-2007 The ExTeX Group and individual authors listed below
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

package org.extex.unit.omega.ocp.util;

import org.extex.core.exception.helping.EofException;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.UndefinedControlSequenceException;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.Code;
import org.extex.ocpware.type.OcpProgram;
import org.extex.resource.ResourceFinder;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;

/**
 * This class contains utility methods.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:4411 $
 */
public final class OcpUtil {


    private OcpUtil() {


    }

    /**
     * Scan an &Omega;CP file name.
     * 
     * <doc type="syntax" name="filename"> This method parses the following
     * syntactic entity:
     * 
     * <pre class="syntax">
     *   &lang;ocp file name&rang; </pre>
     * 
     * The scanning is performed in one of two ways:
     * <ul>
     * <li>If the first token is a left brace then a block is read until the
     * matching right brace is found. On the way the tokens are expanded. </li>
     * <li>Otherwise tokens are read until a space token is encountered. </li>
     * </ul>
     * 
     * </doc>
     * 
     * @param context the processing context
     * @param source the source for new tokens
     * @param primitive the name of the primitive for reporting
     * 
     * @return the file name as string
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    public static String scanOcpFileName(Context context, TokenSource source,
            CodeToken primitive) throws HelpingException, TypesetterException {

        Token t = source.scanNonSpace(context);

        if (t == null) {
            throw new EofException(primitive.toText());
        } else if (t.isa(Catcode.LEFTBRACE)) {
            source.push(t);
            String name = source.scanTokensAsString(context, primitive);
            return name;

        }

        StringBuilder sb = new StringBuilder(t.toText());

        for (t = source.getToken(context);
        t != null && !(t instanceof SpaceToken);
        t = source.getToken(context)) {
            sb.append(t.toText());
        }

        return sb.toString();
    }

    /**
     * Get an ocp file name.
     * 
     * @param source the source for new tokens
     * @param context the interpreter context
     * 
     * @return the ocp file name
     * 
     * @throws HelpingException in case of an error
     */
    public static String scanOcpFileName(TokenSource source, Context context)
            throws HelpingException {

        StringBuilder sb = new StringBuilder();

        for (Token t = source.getToken(context);
        t != null && !(t instanceof SpaceToken);
        t = source.getToken(context)) {
            sb.append(t.toText());
        }

        return sb.toString();
    }

    /**
     * Get an ocp.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param finder the resource finder to use
     * 
     * @return the ocp file name
     * 
     * @throws HelpingException in case of an error
     */
    public static Ocp scanOcp(Context context, TokenSource source,
            Typesetter typesetter, ResourceFinder finder)
            throws HelpingException {

        Token t = source.getToken(context);
        if (t == null) {
            throw new EofException();
        } else if (t instanceof CodeToken) {
            Code code = context.getCode((CodeToken) t);
            if (code == null) {
                throw new UndefinedControlSequenceException(t.toString());
            } else if (code instanceof OcpConvertible) {
                return ((OcpConvertible) code).convertOcp(context, source,
                    typesetter);
            }

        } else {
            source.push(t);
            return Ocp.load(scanOcpFileName(source, context), finder);
        }

        source.push(t);
        throw new HelpingException(
            LocalizerFactory.getLocalizer(OcpUtil.class), "Omega.MissingOcp");
    }

    /**
     * San the input for a control sequence and extract an &Omega;CP from its
     * code value.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * @param primitive the name of the primitive for error messages
     * 
     * @return the &Omega;CP program stored in the code area
     * 
     * @throws HelpingException in case of an error
     */
    public static OcpProgram scanOcpCode(Context context, TokenSource source,
            Typesetter typesetter, String primitive) throws HelpingException {

        CodeToken cs = source.getControlSequence(context, typesetter);
        Code code = context.getCode(cs);
        if (!(code instanceof Ocp)) {
            throw new OmegaOcpException(primitive);
        }
        return ((Ocp) code).getProgram();

    }

}
