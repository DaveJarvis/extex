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

package org.extex.unit.base.register.font;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.glue.Glue;
import org.extex.font.CoreFontFactory;
import org.extex.font.ExtexFont;
import org.extex.font.FontKey;
import org.extex.font.exception.FontException;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.framework.configuration.exception.ConfigurationIOException;
import org.extex.framework.logger.LogEnabled;
import org.extex.interpreter.Flags;
import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.type.AbstractAssignment;
import org.extex.interpreter.type.font.FontConvertible;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;
import org.extex.typesetter.Typesetter;
import org.extex.typesetter.exception.TypesetterException;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.tc.font.impl.FontImpl;

/**
 * This class provides an implementation for the primitive <code>\font</code>.
 * 
 * <doc name="font">
 * <h3>The Primitive <tt>\font</tt></h3>
 * <p>
 * The primitive <tt>\font</tt> can be used to load a font with some specified
 * properties and assign it to a control sequence. The primary option is the
 * specification of a size for the font. If no size is given then the font is
 * loaded at its design size.
 * </p>
 * <p>
 * An exact size can be specified with the <tt>at</tt> keyword. The dimension
 * following this keyword determines the size of the font.
 * </p>
 * <p>
 * The design size can be multiplied by a scale factor. This scale factor is
 * given as number after the keyword <tt>scaled</tt>. The value given is 1000
 * times the scale factor to be used.
 * </p>
 * TODO missing documentation of the extensions
 * <p>
 * This primitive is an assignment.
 * </p>
 * 
 * <h4>Syntax</h4>
 * The formal description of this primitive is the following:
 * 
 * <pre class="syntax">
 *    &lang;font&rang;
 *      &rarr; &lang;prefix&rang; <tt>\font</tt> {@linkplain
 *       org.extex.interpreter.TokenSource#getControlSequence(Context, Typesetter)
 *       &lang;control sequence&rang;} {@linkplain
 *        org.extex.interpreter.TokenSource#getOptionalEquals(Context)
 *        &lang;equals&rang;} &lang;font name&rang; &lang;options&rang;
 *
 *    &lang;prefix&rang;
 *      &rarr;
 *       |  <tt>\global</tt>
 *
 *    &lang;options&rang;
 *      &rarr; &lang;option&rang;
 *       |  &lang;option&rang; &lang;options&rang;
 *
 *    &lang;option&rang;
 *      &rarr; [scaled] {@linkplain
 *        org.extex.base.parser.ConstantCountParser#parseNumber(Context,TokenSource,Typesetter)
 *        &lang;number&rang;}
 *       | [at] {@linkplain
 *           org.extex.core.dimen#Dimen(org.extex.interpreter.context.Context,org.extex.interpreter.TokenSource)
 *           &lang;size&rang;}
 *       | [noligatures]
 *       | [nokerning]
 *       | [letterspaced]  </pre>
 * 
 * 
 * <h4>Examples</h4>
 * <p>
 * In the following example the font cmr12 is loaded at its design size. The
 * macro <tt>\myfont</tt> is bound to this font.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \font\myfont=cmr12  </pre>
 * 
 * <p>
 * In the following example the font cmr12 is loaded at the size 15pt. The macro
 * <tt>\myfont</tt> is bound to this font.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \font\myfont=cmr12 at 15pt  </pre>
 * 
 * <p>
 * In the following example the font cmr12 is loaded at the double design size.
 * The scale factor 2000 is divided by 1000 to get the effective scaling factor.
 * The macro <tt>\magnifiedfiverm</tt> is bound to this font.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \font\magnifiedfiverm=cmr5 scaled 2000  </pre>
 * 
 * <p>
 * In the following example the font cmr10 is loaded at the size of 12 true pt.
 * The macro <tt>\second</tt> is bound to this font.
 * </p>
 * 
 * <pre class="TeXSample">
 *   \font\second=cmr10 at 12truept  </pre>
 * 
 * </doc>
 * 
 * 
 * <h3>Possible Extension</h3>
 * <p>
 * Example
 * </p>
 * 
 * <pre>
 * \font\myfont=cmr12 at 15pt letterspaced 10sp plus 3sp minus 2sp
 * \font\myfont=cmr12 at 15pt letterspaced 10sp plus 3sp minus 2sp noligatures
 * \font\myfont=cmr12 at 15pt noligatures
 * \font\myfont=cmr12 at 15pt noligatures nokerning
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision:4431 $
 */
public class FontPrimitive extends AbstractAssignment
        implements
            FontConvertible,
            LogEnabled {

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 2007L;

    /**
     * The field <tt>DEBUG</tt> contains the indicator that debug output is
     * desirable.
     */
    private static final boolean DEBUG = true;

    /**
     * The field <tt>logger</tt> contains the logger for debug output.
     */
    private transient Logger logger = null;

    /**
     * Creates a new object.
     * 
     * @param token the initial token for the primitive
     */
    public FontPrimitive(CodeToken token) {

        super(token);
    }

    /**
     * The method <tt>assign</tt> is the core of the functionality of
     * {@link #execute(Flags, Context, TokenSource, Typesetter) execute()}.
     * This method is preferable to <tt>execute()</tt> since the
     * <tt>execute()</tt> method provided in this class takes care of
     * <tt>\afterassignment</tt> and <tt>\globaldefs</tt> as well.
     * 
     * @param prefix the prefix controlling the execution
     * @param context the interpreter context
     * @param source the token source
     * @param typesetter the typesetter
     * @throws ConfigurationException in case of an configuration error
     * @see org.extex.interpreter.type.AbstractAssignment#assign(
     *      org.extex.interpreter.Flags, org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    @Override
    public void assign(Flags prefix, Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        CodeToken fontId = source.getControlSequence(context, typesetter);
        source.getOptionalEquals(context);
        String fontname = scanFontName(context, source);
        Dimen fontSize = null;
        Count scale = null;

        if (source.getKeyword(context, "at")) {
            fontSize =
                    new Dimen(source.parseDimen(context, source, typesetter));
            if (fontSize.lt(Dimen.ZERO_PT)) {
                throw new HelpingException(getLocalizer(), "TTP.ImproperAt",
                    fontSize.toString());
            }

        } else if (source.getKeyword(context, "scaled")) {
            long s = source.parseInteger(context, source, typesetter);
            if (s <= 0) {
                throw new HelpingException(getLocalizer(), "TTP.IllegalMag",
                    Long.toString(s), "32768"); // max, as reported by TeX
            }
            scale = new Count(s);
        }

        Glue letterspaced = new Glue(0);
        boolean ligatures = true;
        boolean kerning = true;
        List<String> features = null;

        for (;;) {

            if (source.getKeyword(context, "letterspaced")) {
                letterspaced = source.parseGlue(context, source, typesetter);
            } else if (source.getKeyword(context, "noligatures")) {
                ligatures = false;
            } else if (source.getKeyword(context, "nokerning")) {
                kerning = false;
            } else if (source.getKeyword(context, "tag")) {
                source.getOptionalEquals(context);
                if (features == null) {
                    features = new ArrayList<String>();
                }
                features.add(getTag(source, context));
            } else {
                break;
            }
        }

        CoreFontFactory factory = context.getFontFactory();
        ExtexFont fnt;
        FontKey fontKey;
        try {
            Map<String, Serializable> fontKeyMap =
                    new HashMap<String, Serializable>();
            fontKeyMap.put(FontKey.SCALE, scale);
            fontKeyMap.put(FontKey.LETTERSPACE, letterspaced);
            fontKeyMap.put(FontKey.LIGATURES, Boolean.valueOf(ligatures));
            fontKeyMap.put(FontKey.KERNING, Boolean.valueOf(kerning));
            fontKeyMap.put(FontKey.LANGUAGE, context.getTypesettingContext()
                .getLanguage().getName());
            if (features == null) {
                fontKey = factory.getFontKey(fontname, fontSize, fontKeyMap);
            } else {
                fontKey = factory.getFontKey(fontname, fontSize, fontKeyMap, //
                    features);
            }
            fnt = factory.getInstance(fontKey);
        } catch (FontException e) {
            if (logger != null) {
                logger.log(Level.FINE, "FontPrimitive", e);
            }
            throw new HelpingException(getLocalizer(), "TTP.TFMnotFound", //
                context.esc(fontId), fontname);
        } catch (ConfigurationIOException e) {
            if (logger != null) {
                logger.log(Level.FINE, "FontPrimitive", e);
            }
            throw new HelpingException(getLocalizer(), "TTP.TFMnotFound", //
                context.esc(fontId), fontname);
        }

        if (fnt == null) {
            throw new HelpingException(getLocalizer(), "TTP.TFMnotFound", //
                context.esc(fontId), fontname);
        }

        context.setCode(fontId, //
            new FontCode(fontId, new FontImpl(fnt)), //
            prefix.clearGlobal());
    }

    /**
     * Convert some primitive value into a font.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     * 
     * @return the converted value
     * 
     * @see org.extex.interpreter.type.font.FontConvertible#convertFont(
     *      org.extex.interpreter.context.Context,
     *      org.extex.interpreter.TokenSource, org.extex.typesetter.Typesetter)
     */
    public Font convertFont(Context context, TokenSource source,
            Typesetter typesetter) throws HelpingException, TypesetterException {

        return context.getTypesettingContext().getFont();
    }

    /**
     * Setter for the logger.
     * 
     * @param log the logger to use
     * 
     * @see org.extex.framework.logger.LogEnabled#enableLogging(
     *      java.util.logging.Logger)
     */
    public void enableLogging(Logger log) {

        if (DEBUG) {
            this.logger = log;
        }
    }

    /**
     * Read a list of letters or other characters but at most 4 of them.
     * 
     * @param source the source for new tokens
     * @param context the interpreter context
     * 
     * @return the four letters found
     * 
     * @throws HelpingException in case of an error
     */
    private String getTag(TokenSource source, Context context)
            throws HelpingException {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {

            Token t = source.getToken(context);
            if (t instanceof LetterToken || t instanceof OtherToken) {
                sb.append(t.toText());
            } else {
                source.push(t);
                break;
            }
        }
        return sb.toString();
    }

    /**
     * Scan the file name until a <code>SpaceToken</code> is found.
     * 
     * @param context the interpreter context
     * @param source the source for new tokens
     * 
     * @return the file name as string
     * 
     * @throws HelpingException in case of an error
     * @throws TypesetterException in case of an error in the typesetter
     */
    private String scanFontName(Context context, TokenSource source)
            throws HelpingException,
                TypesetterException {

        Token t = source.scanNonSpace(context);

        if (t == null) {
            throw new HelpingException(getLocalizer(), "TTP.EOFinDef",
                toText(context));
        }

        StringBuilder sb = new StringBuilder();

        while (t != null && !(t instanceof SpaceToken)) {
            if (t instanceof ControlSequenceToken) {
                source.push(t);
                break;
            }
            sb.append((char) t.getChar().getCodePoint());
            t = source.getToken(context);
        }

        return sb.toString();
    }

}
