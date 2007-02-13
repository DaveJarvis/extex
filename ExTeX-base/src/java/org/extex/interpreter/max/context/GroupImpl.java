/*
 * Copyright (C) 2003-2007 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter.max.context;

import java.util.HashMap;
import java.util.Map;

import org.extex.font.type.other.NullFont;
import org.extex.interpreter.Namespace;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.observer.group.AfterGroupObserverList;
import org.extex.interpreter.context.tc.TypesettingContext;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.interpreter.type.count.Count;
import org.extex.interpreter.type.count.ImmutableCount;
import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.file.InFile;
import org.extex.interpreter.type.file.OutFile;
import org.extex.interpreter.type.font.Font;
import org.extex.interpreter.type.glue.Glue;
import org.extex.interpreter.type.math.MathClass;
import org.extex.interpreter.type.math.MathCode;
import org.extex.interpreter.type.math.MathDelimiter;
import org.extex.interpreter.type.muskip.Muskip;
import org.extex.interpreter.type.tokens.Tokens;
import org.extex.scanner.TokenStream;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.type.Locator;
import org.extex.type.UnicodeChar;
import org.extex.typesetter.type.noad.MathGlyph;

/**
 * This is a simple implementation for a group. The whole stack of groups is
 * implemented as a linked list. The list itself is mixed within the pure
 * elements of the linked list.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision: 4770 $
 */
public class GroupImpl implements Group {

    /**
     * The constant <tt>INVALID_CHAR_CODE</tt> contains the code for an
     * invalid character.
     */
    private static final int INVALID_CHAR_CODE = 127;

    /**
     * The constant <tt>serialVersionUID</tt> contains the id for serialization.
     */
    protected static final long serialVersionUID = 20060512L;

    /**
     * The field <tt>SFCODE_DEFAULT</tt> contains the default space factor code
     * for non-letters.
     */
    private static final Count SFCODE_DEFAULT = new ImmutableCount(1000);

    /**
     * The field <tt>SFCODE_LETTER</tt> contains the default space factor code
     * for letters.
     */
    private static final Count SFCODE_LETTER = new ImmutableCount(999);

    /**
     * The field <tt>afterGroup</tt> contains the tokens to be inserted after
     * the group has been closed.
     */
    private Tokens afterGroup = null;

    /**
     * The field <tt>afterGroupObservers</tt> contains the list of observers
     * to be invoked after the group has been closed.
     */
    private transient AfterGroupObserver afterGroupObservers = null;

    /**
     * The field <tt>boxMap</tt> contains the map for the boxes.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map boxMap;

    /**
     * The field <tt>catcodeMap</tt> contains the map for the catcodes.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map catcodeMap;

    /**
     * The field <tt>codeMap</tt> contains the map for the active characters and
     * macros. The key is a Token. The value is a Code.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map codeMap;

    /**
     * The field <tt>countMap</tt> contains the map for the count registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map countMap;

    /**
     * The field <tt>delcodeMap</tt> contains the map for the delimiter code
     * of the characters.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map delcodeMap;

    /**
     * The field <tt>dimenMap</tt> contains the map for the dimen registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map dimenMap;

    /**
     * The field <tt>extensionMap</tt> contains the mapping from extension to
     * their HashMap.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map extensionMap;

    /**
     * The field <tt>fontMap</tt> contains the map for the fonts.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map fontMap;

    /**
     * The field <tt>ifMap</tt> contains the map for the booleans.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map ifMap;

    /**
     * The field <tt>inFileMap</tt> contains the map for the input files.
     * The field is initialized lacy. Thus new groups come up faster.
     * The map is not stored in the format file since files can not be kept
     * open.
     */
    private transient Map inFileMap;

    /**
     * The field <tt>lccodeMap</tt> contains the map for the translation to
     * lower case.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map lccodeMap;

    /**
     * The field <tt>locator</tt> contains the locator to determine the
     * position a token came from.
     */
    private transient Locator locator;

    /**
     * The field <tt>mathcodeMap</tt> contains the map for the category codes.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map mathcodeMap;

    /**
     * The field <tt>muskipMap</tt> contains the map for the muskip registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map muskipMap;

    /**
     * The field <tt>namespace</tt> contains the current name space.
     */
    private String namespace = null;

    /**
     * The field <tt>next</tt> contains the next group in the linked list.
     */
    private Group next = null;

    /**
     * The field <tt>outFileMap</tt> contains the map for the output files.
     * The field is initialized lacy. Thus new groups come up faster.
     * The map is not stored in the format file since files can not be kept
     * open.
     */
    private transient Map outFileMap;

    /**
     * The field <tt>sfcodeMap</tt> contains the map for the space factor.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map sfcodeMap;

    /**
     * The field <tt>skipMap</tt> contains the map for the skip registers
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map skipMap;

    /**
     * The field <tt>standardTokenStream</tt> contains the standard token
     * stream.
     */
    private transient TokenStream standardTokenStream = null;

    /**
     * The field <tt>start</tt> contains the start token.
     */
    private Token start;

    /**
     * The field <tt>toksMap</tt> contains the map for the tokens registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map toksMap;

    /**
     * The field <tt>type</tt> contains the type number of the group as returned
     * by <tt>\currentgrouptype</tt>.
     */
    private GroupType type = GroupType.BOTTOM_LEVEL_GROUP;

    /**
     * The field <tt>typesettingContext</tt> contains the typesetting context
     * to be used.
     */
    private TypesettingContext typesettingContext = null;

    /**
     * The field <tt>uccodeMap</tt> contains the map for the translation to
     * upper case.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map uccodeMap;

    /**
     * Creates a new object.
     *
     * @param nextGroup the next group in the stack. If the value is
     *      <code>null</code> then this is the global base
     */
    public GroupImpl(final Group nextGroup) {

        super();
        this.next = nextGroup;
    }

    /**
     * Register an observer to be invoked after the group has been closed.
     *
     * @param observer the observer to register
     *
     * @see org.extex.interpreter.max.context.Group#afterGroup(
     *      AfterGroupObserver)
     */
    public void afterGroup(final AfterGroupObserver observer) {

        afterGroupObservers =
                AfterGroupObserverList.register(afterGroupObservers, observer);
    }

    /**
     * Add the token to the tokens to be inserted after the group is closed.
     *
     * @param t the token to add
     *
     * @see org.extex.interpreter.max.context.Group#afterGroup(
     *      org.extex.scanner.type.token.Token)
     */
    public void afterGroup(final Token t) {

        if (afterGroup == null) {
            afterGroup = new Tokens();
        }

        afterGroup.add(t);
    }

    /**
     * @see org.extex.interpreter.max.context.Group#get(
     *      java.lang.Object,
     *      java.lang.Object)
     */
    public Object get(final Object extension, final Object key) {

        Map map;
        Object value;

        if (extensionMap != null
                && (map = (Map) extensionMap.get(extension)) != null
                && (value = map.get(key)) != null) {
            return value;
        }
        if (next != null) {
            return next.get(extension, key);
        }

        return null;
    }

    /**
     * Getter for the tokens which are inserted after the group has been
     * closed.
     *
     * @return the after group tokens
     *
     * @see org.extex.interpreter.max.context.Group#getAfterGroup()
     */
    public Tokens getAfterGroup() {

        return afterGroup;
    }

    /**
     * Getter for the {@link org.extex.interpreter.type.box.Box box}register.
     * Count registers are named, either with a number or an arbitrary string.
     * The numbered registers where limited to 256 in <logo>TeX</logo>. This
     * restriction does no longer hold for <logo>ExTeX</logo>.
     *
     * @param name the name or number of the count register
     *
     * @return the count register or <code>null</code> if it is not defined
     *
     * @see org.extex.interpreter.max.context.Group#getBox(
     *      java.lang.String)
     */
    public Box getBox(final String name) {

        if (boxMap != null) {
            Box box = (Box) (boxMap.get(name));
            if (box != null) {
                return box;
            }
        }
        if (next != null) {
            return next.getBox(name);
        }

        return null;
    }

    /**
     * Getter for the category code of a character.
     *
     * @param c the Unicode character to analyze
     *
     * @return the category code of a character
     *
     * @see org.extex.interpreter.Tokenizer#getCatcode(
     *      org.extex.type.UnicodeChar)
     */
    public Catcode getCatcode(final UnicodeChar c) {

        if (catcodeMap != null) {
            Catcode value = (Catcode) catcodeMap.get(c);

            if (value != null) {
                return value;
            }
        }
        if (next != null) {
            return next.getCatcode(c);
        }

        // Fallback for predefined catcodes
        if (c.isLetter()) {
            return Catcode.LETTER;
        }

        switch (c.getCodePoint()) {
            case ' ':
                return Catcode.SPACE;
            case '\\':
                return Catcode.ESCAPE;
            case 13:
                return Catcode.CR;
            case '%':
                return Catcode.COMMENT;
            case 0:
                return Catcode.IGNORE;
            case INVALID_CHAR_CODE:
                return Catcode.INVALID;
            default:
                return Catcode.OTHER;
        }
    }

    /**
     * Getter for the definition of an active character or macro.
     *
     * @param token the name of the active character or macro
     *
     * @return the code associated to the name or <code>null</code> if none
     *         is defined yet
     *
     * @see org.extex.interpreter.max.context.Group#getCode(
     *      CodeToken)
     */
    public Code getCode(final CodeToken token) {

        Code code = getCodeForToken(token);

        if (Namespace.SUPPORT_NAMESPACE_DEF && code == null
                && token instanceof CodeToken) {
            CodeToken t = token.cloneInDefaultNamespace();
            if (t != token) {
                code = getCodeForToken(t);
            }
        }
        return code;
    }

    /**
     * Recurse down the group stack and search for the definition of a token.
     *
     * @param token the token to look-up the definition for
     *
     * @return the code assigned to the token or <code>null</code> if none is
     *  found.
     */
    protected Code getCodeForToken(final CodeToken token) {

        if (codeMap != null) {
            Code code = (Code) (codeMap.get(token));
            if (code != null) {
                return code;
            }
        }
        return next != null ? ((GroupImpl) next).getCodeForToken(token) : null;
    }

    /**
     * Getter for the named count register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered count registers is emulated. The
     * other case can be used to store special count values.
     * <p>
     * Note: The number of count registers is not limited to 256 as in
     *  <logo>TeX</logo>.
     * </p><p>
     * As a default value 0 is returned.
     * </p>
     *
     * @param name the name of the count register
     *
     * @return the value of the count register or its default
     *
     * @see org.extex.interpreter.max.context.Group#getCount(
     *      java.lang.String)
     */
    public Count getCount(final String name) {

        if (countMap != null) {
            Count count = (Count) (countMap.get(name));

            if (count != null) {
                return count;
            }
        }
        if (next != null) {
            return next.getCount(name);
        } else {
            Count count = new Count(0);
            if (countMap == null) {
                countMap = new HashMap();
            }
            countMap.put(name, count);
            return count;
        }
    }

    /**
     * Getter for the delcode of a character.
     * The delcode is -1 unless changed explicitly.
     *
     * @param c the character to get the delcode for
     *
     * @return the delcode for the given character
     *
     * @see org.extex.interpreter.max.context.Group#getDelcode(
     *      org.extex.type.UnicodeChar)
     */
    public MathDelimiter getDelcode(final UnicodeChar c) {

        if (delcodeMap != null) {
            MathDelimiter delcode = (MathDelimiter) (delcodeMap.get(c));

            if (delcode != null) {
                return delcode;
            }
        }
        if (next != null) {
            return next.getDelcode(c);
        }

        // Fallback for predefined delimiter codes
        if (c.getCodePoint() == '.') {
            MathDelimiter del = new MathDelimiter(null, null, null);
            // delcodeMap.put(UnicodeChar.get('.'), del);
            return del;
        }
        return null;

    }

    /**
     * Getter for the named dimen register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered dimen registers is emulated. The
     * other case can be used to store special dimen values.
     * <p>
     * Note: The number of dimen registers is not limited to 256 as in
     *  <logo>TeX</logo>.
     * </p><p>
     * As a default value 0 is returned.
     * </p>
     *
     * @param name the name of the dimen register
     *
     * @return the value of the dimen register or its default
     *
     * @see org.extex.interpreter.max.context.Group#getDimen(
     *      java.lang.String)
     */
    public Dimen getDimen(final String name) {

        if (dimenMap != null) {
            Dimen dimen = (Dimen) (dimenMap.get(name));

            if (dimen != null) {
                return dimen;
            }
        }
        if (next != null) {
            return next.getDimen(name);
        } else {
            Dimen dimen = new Dimen();
            if (dimenMap == null) {
                dimenMap = new HashMap();
            }
            dimenMap.put(name, dimen);
            return dimen;
        }
    }

    /**
     * Getter for the current font.
     *
     * @param name the name of the font
     *
     * @return the current font
     *
     * @see org.extex.interpreter.max.context.Group#getFont(
     *      java.lang.String)
     */
    public Font getFont(final String name) {

        if (fontMap != null) {
            Font font = (Font) (fontMap.get(name));

            if (font != null) {
                return font;
            }
        }
        return next != null ? next.getFont(name) : new NullFont();
    }

    /**
     * Getter for the boolean value.
     *
     * @param name the name of the boolean
     *
     * @return the value
     *
     * @see org.extex.interpreter.max.context.Group#getIf(
     *      java.lang.String)
     */
    public boolean getIf(final String name) {

        if (ifMap != null) {
            Boolean b = (Boolean) (ifMap.get(name));
            if (b != null) {
                return b.booleanValue();
            }
        }
        return next != null ? next.getIf(name) : false;
    }

    /**
     * Getter for a input file register.  In the case that the named
     * descriptor does not exist yet <code>null</code> is returned.
     * If the name is <code>null</code> then the default input stream is used.
     *
     * @param name the name or the number of the file register
     *
     * @return the input file descriptor
     *
     * @see org.extex.interpreter.max.context.Group#getInFile(
     *      java.lang.String)
     */
    public InFile getInFile(final String name) {

        if (name == null) {
            return new InFile(standardTokenStream, true);
        }

        if (inFileMap != null) {
            InFile inFile = (InFile) (inFileMap.get(name));

            if (null != inFile) {
                return inFile;
            }
        }

        return next != null ? next.getInFile(name) : null;
    }

    /**
     * Getter for the lccode mapping of upper case characters to their
     * lower case equivalent.
     *
     * @param lc the upper case character
     *
     * @return the lower case equivalent or null if none exists
     *
     * @see org.extex.interpreter.max.context.Group#getLccode(
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getLccode(final UnicodeChar lc) {

        if (lccodeMap != null) {
            UnicodeChar value = (UnicodeChar) lccodeMap.get(lc);

            if (value != null) {
                return value;
            }
        }
        if (next != null) {
            return next.getLccode(lc);
        }

        // Fallback for predefined lccodes
        if (lc.isLetter()) {
            UnicodeChar value = lc.lower();
            // the value is stored to avoid constructing UnicodeChars again
            if (lccodeMap == null) {
                lccodeMap = new HashMap();
            }
            lccodeMap.put(lc, value);
            return value;
        }
        return null;
    }

    /**
     * Getter for the group level. The group level is the number of groups which
     * are currently open. Thus this number of groups can be closed. Since the
     * top-level group can not be closed this group counts as 0.
     *
     * @return the group level
     *
     * @see org.extex.interpreter.max.context.Group#getLevel()
     */
    public long getLevel() {

        return (next == null ? 0 : 1 + next.getLevel());
    }

    /**
     * Getter for the locator describing where the group started.
     *
     * @return the locator
     *
     * @see org.extex.interpreter.max.context.Group#getLocator()
     */
    public Locator getLocator() {

        return this.locator;
    }

    /**
     * Getter for the math code of a character.
     *
     * @param c the character to get the math code for
     *
     * @return the math code for the given character
     *
     * @see org.extex.interpreter.max.context.Group#getMathcode(
     *      org.extex.type.UnicodeChar)
     */
    public MathCode getMathcode(final UnicodeChar c) {

        if (mathcodeMap != null) {
            MathCode mathcode = (MathCode) (mathcodeMap.get(c));
            if (mathcode != null) {
                return mathcode;
            }
        }

        MathCode mc;

        if (next != null) {
            return next.getMathcode(c);

        } else if (c.isDigit()) {
            //return new Count(c.getCodePoint() + MATHCODE_DIGIT_OFFSET);
            mc = new MathCode(MathClass.VARIABLE, new MathGlyph(0, c));
        } else if (c.isLetter()) {
            //return new Count(c.getCodePoint() + MATHCODE_LETTER_OFFSET);
            mc = new MathCode(MathClass.VARIABLE, new MathGlyph(1, c));
        } else {
            mc = new MathCode(MathClass.ORDINARY, new MathGlyph(0, c));
        }

        if (mathcodeMap == null) {
            mathcodeMap = new HashMap();
        }
        mathcodeMap.put(c, mc);
        return mc;
    }

    /**
     * Getter for the named muskip register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered muskip registers is emulated.
     * The other case can be used to store special muskip values.
     * <p>
     * As a default value 0 is returned.
     * </p>
     *
     * @param name the name of the count register
     *
     * @return the value of the count register or its default
     *
     * @see org.extex.interpreter.max.context.Group#getMuskip(
     *      java.lang.String)
     */
    public Muskip getMuskip(final String name) {

        if (muskipMap != null) {
            Muskip muskip = (Muskip) (muskipMap.get(name));
            if (muskip != null) {
                return muskip;
            }
        }
        return next != null ? next.getMuskip(name) : new Muskip();
    }

    /**
     * Getter for the name space.
     *
     * @return the name space
     *
     * @see org.extex.interpreter.max.context.Group#getNamespace()
     */
    public String getNamespace() {

        if (namespace == null) {
            if (next == null) {
                namespace = Namespace.DEFAULT_NAMESPACE;
            } else {
                namespace = next.getNamespace();
            }
        }
        return this.namespace;
    }

    /**
     * Getter for the next group in the linked list. Maybe this method should
     * be hidden.
     *
     * @return the next group
     *
     * @see org.extex.interpreter.max.context.Group#getNext()
     */
    public Group getNext() {

        return next;
    }

    /**
     * Getter for the output file descriptor.
     *
     * @param name the name of the descriptor to get
     *
     * @return the output file descriptor
     *
     * @see org.extex.interpreter.max.context.Group#getOutFile(
     *      java.lang.String)
     */
    public OutFile getOutFile(final String name) {

        if (outFileMap != null) {
            return (OutFile) (outFileMap.get(name));
        }
        return null;
    }

    /**
     * Getter for the space factor code of a character.
     * The sfcode is 999 for letters and 1000 for other characters unless
     * changed explicitly.
     *
     * @param c the character for which the sfcode is requested
     *
     * @return the sfcode of the given character
     *
     * @see org.extex.interpreter.max.context.Group#getSfcode(
     *      org.extex.type.UnicodeChar)
     */
    public Count getSfcode(final UnicodeChar c) {

        if (sfcodeMap != null) {
            Count sfcode = (Count) (sfcodeMap.get(c));
            if (sfcode != null) {
                return sfcode;
            }
        }
        if (next != null) {
            return next.getSfcode(c);
        }

        // Fallback for predefined space factor codes
        if (c.isLetter()) {
            return SFCODE_LETTER;
        }
        return SFCODE_DEFAULT;

    }

    /**
     * Getter for the named skip register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered skip registers is emulated. The
     * other case can be used to store special skip values.
     * <p>
     * As a default value 0 is returned.
     * </p>
     *
     * @param name the name of the count register
     *
     * @return the value of the count register or its default
     *
     * @see org.extex.interpreter.max.context.Group#getSkip(
     *      java.lang.String)
     */
    public Glue getSkip(final String name) {

        if (skipMap != null) {
            Glue skip = (Glue) (skipMap.get(name));
            if (skip != null) {
                return skip;
            }
        }
        return next != null ? next.getSkip(name) : new Glue(0);
    }

    /**
     * Getter for standardTokenStream.
     *
     * @return the standardTokenStream
     *
     * @see org.extex.interpreter.max.context.Group#getStandardTokenStream()
     */
    public TokenStream getStandardTokenStream() {

        return this.standardTokenStream;
    }

    /**
     * Getter for the token which started the group.
     *
     * @return the start token
     *
     * @see org.extex.interpreter.max.context.Group#getStart()
     */
    public Token getStart() {

        return this.start;
    }

    /**
     * Getter for the named toks register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered toks registers is emulated. The
     * other case can be used to store special toks values.
     * <p>
     * As a default value the empty toks register is returned.
     * </p>
     *
     * @param name the name of the toks register
     *
     * @return the value of the toks register or its default
     *
     * @see org.extex.interpreter.max.context.Group#getToks(
     *      java.lang.String)
     */
    public Tokens getToks(final String name) {

        if (toksMap != null) {
            Tokens toks = (Tokens) (toksMap.get(name));
            if (toks != null) {
                return toks;
            }
        }
        return next != null ? next.getToks(name) : new Tokens();
    }

    /**
     * Getter for the named toks register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered toks registers is emulated. The
     * other case can be used to store special toks values.
     *
     * @param name the name of the toks register
     *
     * @return the value of the toks register or <code>null</code> if none is
     *  defined
     *
     * @see org.extex.interpreter.max.context.Group#getToksOrNull(java.lang.String)
     */
    public Tokens getToksOrNull(final String name) {

        if (toksMap != null) {
            Tokens toks = (Tokens) (toksMap.get(name));
            if (toks != null) {
                return toks;
            }
        }
        return next != null ? next.getToks(name) : null;
    }

    /**
     * Getter for the group type.
     *
     * @return the group type
     *
     * @see org.extex.interpreter.max.context.Group#getType()
     */
    public GroupType getType() {

        return type;
    }

    /**
     * Getter for the typesetting context.
     *
     * @return the typesetting context
     *
     * @see org.extex.interpreter.max.context.Group#getTypesettingContext()
     */
    public TypesettingContext getTypesettingContext() {

        TypesettingContext context = typesettingContext;

        return context != null //
                ? context //
                : next != null ? next.getTypesettingContext() : null;
    }

    /**
     * Getter for the uccode mapping of lower case characters to their
     * upper case equivalent.
     *
     * @param uc the upper case character
     *
     * @return the upper case equivalent or null if none exists
     *
     * @see org.extex.interpreter.max.context.Group#getUccode(
     *      org.extex.type.UnicodeChar)
     */
    public UnicodeChar getUccode(final UnicodeChar uc) {

        if (uccodeMap != null) {
            UnicodeChar value = (UnicodeChar) uccodeMap.get(uc);
            if (value != null) {
                return value;
            }
        }

        if (next != null) {
            return next.getUccode(uc);
        }

        // Fallback for predefined uc codes
        if (uc.isLetter()) {
            UnicodeChar value = uc.upper();
            // the value is stored to avoid constructing UnicodeChars again
            if (uccodeMap == null) {
                uccodeMap = new HashMap();
            }
            uccodeMap.put(uc, value);
            return value;
        }
        return null;
    }

    /**
     * Invoke all registered observers for the end-of-group event.
     *
     * @throws InterpreterException in case of an error
     *
     * @see org.extex.interpreter.max.context.Group#runAfterGroup()
     */
    public void runAfterGroup() throws InterpreterException {

        if (afterGroupObservers != null) {
            afterGroupObservers.update();
        }
    }

    /**
     * @see org.extex.interpreter.max.context.Group#set(
     *      java.lang.Object,
     *      java.lang.Object,
     *      java.lang.Object,
     *      boolean)
     */
    public void set(final Object extension, final Object key,
            final Object value, final boolean global) {

        if (extensionMap == null) {
            extensionMap = new HashMap();
        }
        Map map = (Map) extensionMap.get(extension);
        if (map == null) {
            map = new HashMap();
            extensionMap.put(extension, map);
        }

        map.put(key, value);

        if (global && next != null) {
            next.set(extension, key, value, global);
        }
    }

    /**
     * Setter for the {@link org.extex.interpreter.type.box.Box box}
     * register in all requested groups. Count registers are named, either with
     * a number or an arbitrary string.
     *
     * @param name the name or the number of the register
     * @param value the new value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setBox(
     *      java.lang.String, org.extex.interpreter.type.box.Box, boolean)
     */
    public void setBox(final String name, final Box value, final boolean global) {

        if (boxMap == null) {
            boxMap = new HashMap();
        }

        boxMap.put(name, value);

        if (global && next != null) {
            next.setBox(name, value, global);
        }
    }

    /**
     * Setter for the catcode of a character in the specified groups.
     *
     * @param c the character
     * @param code the catcode
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setCatcode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.scanner.type.Catcode,
     *      boolean)
     */
    public void setCatcode(final UnicodeChar uc, final Catcode code,
            final boolean global) {

        if (catcodeMap == null) {
            catcodeMap = new HashMap();
        }

        catcodeMap.put(uc, code);

        if (global && next != null) {
            next.setCatcode(uc, code, global);
        }
    }

    /**
     * Setter for active characters or macros in the requested group.
     *
     * @param token the name of the active character, i.e. a single letter
     *   string
     * @param code the new code
     * @param global the indicator for the scope; <code>true</code> means all
     *   groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setCode(
     *      org.extex.scanner.type.token.Token,
     *      org.extex.interpreter.type.Code, boolean)
     */
    public void setCode(final Token token, final Code code, final boolean global) {

        if (codeMap == null) {
            codeMap = new HashMap();
        }

        codeMap.put(token, code);

        if (global && next != null) {
            next.setCode(token, code, global);
        }
    }

    /**
     * Setter for a count register in the requested groups.
     *
     * @param name the name of the count register
     * @param value the value of the count register
     * @param global the indicator for the scope; <code>true</code> means all
     *            groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setCount(
     *      java.lang.String,
     *      org.extex.interpreter.type.count.Count, boolean)
     */
    public void setCount(final String name, final Count value,
            final boolean global) {

        if (countMap == null) {
            countMap = new HashMap();
        }

        countMap.put(name, value);

        if (global && next != null) {
            next.setCount(name, value, global);
        }
    }

    /**
     * Setter for the delcode of a character.
     *
     * @param uc the character to set the delcode for
     * @param code the new delcode
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setDelcode(
     *      org.extex.type.UnicodeChar,
     *      MathDelimiter, boolean)
     */
    public void setDelcode(final UnicodeChar c, final MathDelimiter code,
            final boolean global) {

        if (delcodeMap == null) {
            delcodeMap = new HashMap();
        }

        delcodeMap.put(c, code);

        if (global && next != null) {
            next.setDelcode(c, code, global);
        }
    }

    /**
     * Setter for a dimen register in the requested groups.
     *
     * @param name the name of the count register
     * @param value the value of the count register
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setDimen(
     *      java.lang.String,
     *      org.extex.interpreter.type.dimen.Dimen, boolean)
     */
    public void setDimen(final String name, final Dimen value,
            final boolean global) {

        if (dimenMap == null) {
            dimenMap = new HashMap();
        }

        dimenMap.put(name, value);

        if (global && next != null) {
            next.setDimen(name, value, global);
        }
    }

    /**
     * Setter for the font with a given name.
     *
     * @param name the name of the font
     * @param font the new font
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setFont(
     *      java.lang.String, org.extex.interpreter.type.font.Font, boolean)
     */
    public void setFont(final String name, final Font font, final boolean global) {

        if (fontMap == null) {
            fontMap = new HashMap();
        }

        fontMap.put(name, font);

        if (global && next != null) {
            next.setFont(name, font, global);
        }
    }

    /**
     * Setter for the value of the booleans in all groups.
     *
     * @param name the name of the boolean
     * @param value the truth value
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setIf(
     *      java.lang.String,
     *      boolean, boolean)
     */
    public void setIf(final String name, final boolean value,
            final boolean global) {

        if (ifMap == null) {
            ifMap = new HashMap();
        }

        ifMap.put(name, (value ? Boolean.TRUE : Boolean.FALSE));

        if (global && next != null) {
            next.setIf(name, value, global);
        }
    }

    /**
     * Setter for an input file.
     *
     * @param name the name of the input file
     * @param file the input file specification
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setInFile(
     *      java.lang.String,
     *      org.extex.interpreter.type.file.InFile, boolean)
     */
    public void setInFile(final String name, final InFile file,
            final boolean global) {

        if (inFileMap == null) {
            inFileMap = new HashMap();
        }

        inFileMap.put(name, file);

        if (global && next != null) {
            next.setInFile(name, file, global);
        }
    }

    /**
     * Declare the translation from an upper case character to a lower case
     * character.
     *
     * @param uc upper case character
     * @param lc lower case equivalent
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setLccode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      boolean)
     */
    public void setLccode(final UnicodeChar lc, final UnicodeChar uc,
            final boolean global) {

        if (lccodeMap == null) {
            lccodeMap = new HashMap();
        }

        lccodeMap.put(lc, uc);

        if (global && next != null) {
            next.setLccode(lc, uc, global);
        }
    }

    /**
     * Setter for locator.
     *
     * @param locator the locator to set
     */
    public void setLocator(final Locator locator) {

        this.locator = locator;
    }

    /**
     * Setter for the math code of a character.
     *
     * @param uc the character to set the math code for
     * @param code the new math code
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setMathcode(
     *      org.extex.type.UnicodeChar,
     *      MathCode, boolean)
     */
    public void setMathcode(final UnicodeChar c, final MathCode code,
            final boolean global) {

        if (mathcodeMap == null) {
            mathcodeMap = new HashMap();
        }

        mathcodeMap.put(c, code);

        if (global && next != null) {
            next.setMathcode(c, code, global);
        }
    }

    /**
     * Setter for the muskip register in the requested groups.
     *
     * @param name the name of the register
     * @param value the value of the register
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setMuskip(
     *      java.lang.String,
     *      org.extex.interpreter.type.muskip.Muskip, boolean)
     */
    public void setMuskip(final String name, final Muskip value,
            final boolean global) {

        if (muskipMap == null) {
            muskipMap = new HashMap();
        }

        muskipMap.put(name, value);

        if (global && next != null) {
            next.setMuskip(name, value, global);
        }
    }

    /**
     * Setter for the name space.
     *
     * @param theNamespace the new value for the name space
     * @param global the scoping of the assignment
     *
     * @see org.extex.interpreter.max.context.Group#setNamespace(
     *      java.lang.String, boolean)
     */
    public void setNamespace(final String theNamespace, final boolean global) {

        this.namespace = theNamespace;

        if (global && next != null) {
            next.setNamespace(theNamespace, global);
        }
    }

    /**
     * Setter for the output file for a given name.
     *
     * @param name the name of the output file
     * @param file the output file specification
     * @param global the indicator for the scope; <code>true</code> means all
     *            groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setOutFile(
     *       java.lang.String,
     *       org.extex.interpreter.type.file.OutFile, boolean)
     */
    public void setOutFile(final String name, final OutFile file,
            final boolean global) {

        if (outFileMap == null) {
            outFileMap = new HashMap();
        }

        outFileMap.put(name, file);

        if (global && next != null) {
            next.setOutFile(name, file, global);
        }
    }

    /**
     * Setter for the space factor code of a character.
     *
     * @param uc the character to set the space factor code for
     * @param code the new space factor code
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setSfcode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.interpreter.type.count.Count, boolean)
     */
    public void setSfcode(final UnicodeChar c, final Count code,
            final boolean global) {

        if (sfcodeMap == null) {
            sfcodeMap = new HashMap();
        }

        sfcodeMap.put(c, code);

        if (global && next != null) {
            next.setSfcode(c, code, global);
        }
    }

    /**
     * Setter for a skip register in all groups.
     *
     * @param name the name of the count register
     * @param value the value of the count register
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setSkip(
     *      java.lang.String,
     *      org.extex.interpreter.type.glue.Glue, boolean)
     */
    public void setSkip(final String name, final Glue value,
            final boolean global) {

        if (skipMap == null) {
            skipMap = new HashMap();
        }

        skipMap.put(name, value);

        if (global && next != null) {
            next.setSkip(name, value, global);
        }
    }

    /**
     * Setter for standard Token stream.
     *
     * @param standardTokenStream the standardTokenStream to set.
     *
     * @see org.extex.interpreter.max.context.Group#setStandardTokenStream(
     *      org.extex.scanner.TokenStream)
     */
    public void setStandardTokenStream(final TokenStream standardTokenStream) {

        this.standardTokenStream = standardTokenStream;
    }

    /**
     * Setter for start token.
     *
     * @param start the start token to set
     */
    public void setStart(final Token start) {

        this.start = start;
    }

    /**
     * Setter for a toks register in all groups.
     *
     * @param name the name of the toks register
     * @param value the value of the toks register
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setToks(
     *      java.lang.String,
     *      org.extex.interpreter.type.tokens.Tokens, boolean)
     */
    public void setToks(final String name, final Tokens value,
            final boolean global) {

        if (toksMap == null) {
            toksMap = new HashMap();
        }

        toksMap.put(name, value);

        if (global && next != null) {
            next.setToks(name, value, global);
        }
    }

    /**
     * Setter for the type.
     *
     * @param type the type of the group
     *
     * @see org.extex.interpreter.max.context.Group#setType(
     *      org.extex.interpreter.context.group.GroupType)
     */
    public void setType(final GroupType type) {

        this.type = type;
    }

    /**
     * Setter for the typesetting context in the specified groups.
     *
     * @param context the new typesetting context
     * @param global the indicator for the scope; <code>true</code> means all
     *            groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setTypesettingContext(
     *      org.extex.interpreter.context.tc.TypesettingContext,
     *      boolean)
     */
    public void setTypesettingContext(final TypesettingContext context,
            final boolean global) {

        typesettingContext = context;

        if (global && next != null) {
            next.setTypesettingContext(context, global);
        }
    }

    /**
     * Declare the translation from a lower case character to an upper case
     * character.
     *
     * @param lc lower case character
     * @param uc uppercase equivalent
     * @param global the indicator for the scope; <code>true</code> means all
     *  groups; otherwise the current group is affected only
     *
     * @see org.extex.interpreter.max.context.Group#setUccode(
     *      org.extex.type.UnicodeChar,
     *      org.extex.type.UnicodeChar,
     *      boolean)
     */
    public void setUccode(final UnicodeChar uc, final UnicodeChar lc,
            final boolean global) {

        if (uccodeMap == null) {
            uccodeMap = new HashMap();
        }

        uccodeMap.put(uc, lc);

        if (global && next != null) {
            next.setUccode(uc, lc, global);
        }
    }

    /**
     * Get the string representation of this object for debugging purposes.
     *
     * @return the string representation
     *
     * @see java.lang.Object#toString()
     */
    public String toString() {

        return locator != null ? "group " + locator.toString() : super
            .toString();
    }

}
