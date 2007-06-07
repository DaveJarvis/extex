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

import org.extex.core.Locator;
import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.count.CountConstant;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.exception.helping.HelpingException;
import org.extex.core.exception.helping.NoHelpException;
import org.extex.core.glue.Glue;
import org.extex.core.muskip.Muskip;
import org.extex.interpreter.context.group.GroupType;
import org.extex.interpreter.context.observer.group.AfterGroupObserver;
import org.extex.interpreter.context.observer.group.AfterGroupObserverList;
import org.extex.interpreter.type.Code;
import org.extex.interpreter.type.box.Box;
import org.extex.scanner.TokenStream;
import org.extex.scanner.Tokenizer;
import org.extex.scanner.exception.ScannerException;
import org.extex.scanner.type.Catcode;
import org.extex.scanner.type.Namespace;
import org.extex.scanner.type.file.InFile;
import org.extex.scanner.type.file.OutFile;
import org.extex.scanner.type.token.CodeToken;
import org.extex.scanner.type.token.Token;
import org.extex.scanner.type.token.TokenFactory;
import org.extex.scanner.type.tokens.Tokens;
import org.extex.typesetter.tc.TypesettingContext;
import org.extex.typesetter.tc.font.Font;
import org.extex.typesetter.type.math.MathClass;
import org.extex.typesetter.type.math.MathCode;
import org.extex.typesetter.type.math.MathDelimiter;
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
     * The constant <tt>serialVersionUID</tt> contains the id for
     * serialization.
     */
    protected static final long serialVersionUID = 20060512L;

    /**
     * The field <tt>SFCODE_DEFAULT</tt> contains the default space factor
     * code for non-letters.
     */
    private static final FixedCount SFCODE_DEFAULT = new CountConstant(1000);

    /**
     * The field <tt>SFCODE_LETTER</tt> contains the default space factor code
     * for letters.
     */
    private static final FixedCount SFCODE_LETTER = new CountConstant(999);

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
     * The field <tt>boxMap</tt> contains the map for the boxes. The field is
     * initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Box> boxMap;

    /**
     * The field <tt>catcodeMap</tt> contains the map for the category codes.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map<UnicodeChar, Catcode> catcodeMap;

    /**
     * The field <tt>codeMap</tt> contains the map for the active characters
     * and macros. The key is a Token. The value is a Code. The field is
     * initialized lacy. Thus new groups come up faster.
     */
    private Map<Token, Code> codeMap;

    /**
     * The field <tt>countMap</tt> contains the map for the count registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Count> countMap;

    /**
     * The field <tt>delcodeMap</tt> contains the map for the delimiter code
     * of the characters. The field is initialized lacy. Thus new groups come up
     * faster.
     */
    private Map<UnicodeChar, MathDelimiter> delcodeMap;

    /**
     * The field <tt>dimenMap</tt> contains the map for the dimen registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Dimen> dimenMap;

    /**
     * The field <tt>extensionMap</tt> contains the mapping from extension to
     * their HashMap. The field is initialized lacy. Thus new groups come up
     * faster.
     */
    private HashMap<Object, Map<Object, Object>> extensionMap;

    /**
     * The field <tt>fontMap</tt> contains the map for the fonts. The field is
     * initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Font> fontMap;

    /**
     * The field <tt>ifMap</tt> contains the map for the booleans. The field
     * is initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Boolean> ifMap;

    /**
     * The field <tt>inFileMap</tt> contains the map for the input files. The
     * field is initialized lacy. Thus new groups come up faster. The map is not
     * stored in the format file since files can not be kept open.
     */
    private transient Map<String, InFile> inFileMap;

    /**
     * The field <tt>lccodeMap</tt> contains the map for the translation to
     * lower case. The field is initialized lacy. Thus new groups come up
     * faster.
     */
    private Map<UnicodeChar, UnicodeChar> lccodeMap;

    /**
     * The field <tt>locator</tt> contains the locator to determine the
     * position a token came from.
     */
    private transient Locator locator;

    /**
     * The field <tt>mathcodeMap</tt> contains the map for the category codes.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map<UnicodeChar, MathCode> mathcodeMap;

    /**
     * The field <tt>muskipMap</tt> contains the map for the muskip registers.
     * The field is initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Muskip> muskipMap;

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
     * The field is initialized lacy. Thus new groups come up faster. The map is
     * not stored in the format file since files can not be kept open.
     */
    private transient Map<String, OutFile> outFileMap;

    /**
     * The field <tt>sfcodeMap</tt> contains the map for the space factor. The
     * field is initialized lacy. Thus new groups come up faster.
     */
    private Map<UnicodeChar, Count> sfcodeMap;

    /**
     * The field <tt>skipMap</tt> contains the map for the skip registers The
     * field is initialized lacy. Thus new groups come up faster.
     */
    private Map<String, Glue> skipMap;

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
    private Map<String, Tokens> toksMap;

    /**
     * The field <tt>type</tt> contains the type number of the group as
     * returned by <tt>\currentgrouptype</tt>.
     */
    private GroupType type = GroupType.BOTTOM_LEVEL_GROUP;

    /**
     * The field <tt>typesettingContext</tt> contains the typesetting context
     * to be used.
     */
    private TypesettingContext typesettingContext = null;

    /**
     * The field <tt>uccodeMap</tt> contains the map for the translation to
     * upper case. The field is initialized lacy. Thus new groups come up
     * faster.
     */
    private Map<UnicodeChar, UnicodeChar> uccodeMap;

    /**
     * Creates a new object.
     * 
     * @param nextGroup the next group in the stack. If the value is
     *        <code>null</code> then this is the global base
     */
    public GroupImpl(Group nextGroup) {

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
    public void afterGroup(AfterGroupObserver observer) {

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
    public void afterGroup(Token t) {

        if (afterGroup == null) {
            afterGroup = new Tokens();
        }

        afterGroup.add(t);
    }

    /**
     * Get some extension object stored in the group.
     * 
     * @param extension the reference for the extension
     * @param key the key of the object
     * 
     * @return the object stored for the extension under the given key or
     *         <code>null</code> if none is there
     * 
     * @see org.extex.interpreter.max.context.Group#get( java.lang.Object,
     *      java.lang.Object)
     */
    public Object get(Object extension, Object key) {

        if (extensionMap != null) {
            Map<?, ?> map = extensionMap.get(extension);
            if (map != null) {
                Object value = map.get(key);
                if (value != null) {
                    return value;
                }
            }
        }

        return (next != null ? next.get(extension, key) : null);
    }

    /**
     * Getter for the tokens which are inserted after the group has been closed.
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
     * @see org.extex.interpreter.max.context.Group#getBox( java.lang.String)
     */
    public Box getBox(String name) {

        if (boxMap != null) {
            Box box = boxMap.get(name);
            if (box != null) {
                return box;
            }
        }
        return (next != null ? next.getBox(name) : null);
    }

    /**
     * Getter for the category code of a character.
     * 
     * @param c the Unicode character to analyze
     * 
     * @return the category code of a character
     * 
     * @see org.extex.scanner.Tokenizer#getCatcode( org.extex.core.UnicodeChar)
     */
    public Catcode getCatcode(UnicodeChar c) {

        if (catcodeMap != null) {
            Catcode value = catcodeMap.get(c);

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
     * @return the code associated to the name or <code>null</code> if none is
     *         defined yet
     * 
     * @see org.extex.interpreter.max.context.Group#getCode( CodeToken)
     */
    public Code getCode(CodeToken token) {

        Code code = getCodeForToken(token);

        if (Namespace.SUPPORT_NAMESPACE_DEF && code == null) {
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
     *         found.
     */
    protected Code getCodeForToken(CodeToken token) {

        if (codeMap != null) {
            Code code = codeMap.get(token);
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
     * Note: The number of count registers is not limited to 256 as in <logo>TeX</logo>.
     * </p>
     * <p>
     * As a default value 0 is returned.
     * </p>
     * 
     * @param name the name of the count register
     * 
     * @return the value of the count register or its default
     * 
     * @see org.extex.interpreter.max.context.Group#getCount( java.lang.String)
     */
    public Count getCount(String name) {

        if (countMap != null) {
            Count count = countMap.get(name);

            if (count != null) {
                return count;
            }
        }
        if (next != null) {
            return next.getCount(name);
        }

        Count count = new Count(0);
        if (countMap == null) {
            countMap = new HashMap<String, Count>();
        }
        countMap.put(name, count);
        return count;
    }

    /**
     * Getter for the delimiter code of a character. The delimiter code is -1
     * unless changed explicitly.
     * 
     * @param c the character to get the delimiter code for
     * 
     * @return the delimiter code for the given character
     * 
     * @see org.extex.interpreter.max.context.Group#getDelcode(
     *      org.extex.core.UnicodeChar)
     */
    public MathDelimiter getDelcode(UnicodeChar c) {

        if (delcodeMap != null) {
            MathDelimiter delcode = delcodeMap.get(c);

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
     * Note: The number of dimen registers is not limited to 256 as in <logo>TeX</logo>.
     * </p>
     * <p>
     * As a default value 0 is returned.
     * </p>
     * 
     * @param name the name of the dimen register
     * 
     * @return the value of the dimen register or its default
     * 
     * @see org.extex.interpreter.max.context.Group#getDimen( java.lang.String)
     */
    public Dimen getDimen(String name) {

        if (dimenMap != null) {
            Dimen dimen = dimenMap.get(name);

            if (dimen != null) {
                return dimen;
            }
        }
        if (next != null) {
            return next.getDimen(name);
        }

        Dimen dimen = new Dimen();
        if (dimenMap == null) {
            dimenMap = new HashMap<String, Dimen>();
        }
        dimenMap.put(name, dimen);
        return dimen;
    }

    /**
     * Getter for the current font.
     * 
     * @param name the name of the font
     * 
     * @return the current font
     * 
     * @see org.extex.interpreter.max.context.Group#getFont( java.lang.String)
     */
    public Font getFont(String name) {

        if (fontMap != null) {
            Font font = fontMap.get(name);

            if (font != null) {
                return font;
            }
        }
        return next != null ? next.getFont(name) : null;
    }

    /**
     * Getter for the boolean value.
     * 
     * @param name the name of the boolean
     * 
     * @return the value
     * 
     * @see org.extex.interpreter.max.context.Group#getIf( java.lang.String)
     */
    public boolean getIf(String name) {

        if (ifMap != null) {
            Boolean b = ifMap.get(name);
            if (b != null) {
                return b.booleanValue();
            }
        }
        return next != null ? next.getIf(name) : false;
    }

    /**
     * Getter for a input file register. In the case that the named descriptor
     * does not exist yet <code>null</code> is returned. If the name is
     * <code>null</code> then the default input stream is used.
     * 
     * @param name the name or the number of the file register
     * 
     * @return the input file descriptor
     * 
     * @see org.extex.interpreter.max.context.Group#getInFile( java.lang.String)
     */
    public InFile getInFile(String name) {

        if (name == null) {
            // return new InputFile(standardTokenStream, true);
            return new InFile() {

                /**
                 * The field <tt>serialVersionUID</tt> contains the version
                 * number for serialization.
                 */
                private static final long serialVersionUID = 2007L;

                private boolean closed = false;

                public void close() {

                    closed = true;
                }

                public boolean isEof() throws HelpingException {

                    try {
                        return closed || standardTokenStream.isEof();
                    } catch (ScannerException e) {
                        throw new NoHelpException(e);
                    }
                }

                public boolean isFileStream() {

                    return false;
                }

                public boolean isOpen() {

                    return !closed;
                }

                public boolean isStandardStream() {

                    return true;
                }

                public Tokens read(TokenFactory factory, Tokenizer tokenizer)
                        throws HelpingException {

                    if (closed) {
                        return null;
                    }
                    Tokens toks = new Tokens();
                    Token t;

                    try {
                        for (;;) {
                            t = standardTokenStream.get(factory, tokenizer);
                            if (t == null) {
                                return (toks.length() > 0 ? toks : null);
                            } else if (standardTokenStream.isEol()) {
                                return toks;
                            }
                            toks.add(t);
                        }
                    } catch (ScannerException e) {
                        throw new NoHelpException(e);
                    }
                }

            };
        }

        if (inFileMap != null) {
            InFile inFile = inFileMap.get(name);

            if (null != inFile) {
                return inFile;
            }
        }

        return next != null ? next.getInFile(name) : null;
    }

    /**
     * Getter for the lccode mapping of upper case characters to their lower
     * case equivalent.
     * 
     * @param lc the upper case character
     * 
     * @return the lower case equivalent or null if none exists
     * 
     * @see org.extex.interpreter.max.context.Group#getLccode(
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getLccode(UnicodeChar lc) {

        if (lccodeMap != null) {
            UnicodeChar value = lccodeMap.get(lc);

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
                lccodeMap = new HashMap<UnicodeChar, UnicodeChar>();
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
     *      org.extex.core.UnicodeChar)
     */
    public MathCode getMathcode(UnicodeChar c) {

        if (mathcodeMap != null) {
            MathCode mathcode = mathcodeMap.get(c);
            if (mathcode != null) {
                return mathcode;
            }
        }

        MathCode mc;

        if (next != null) {
            return next.getMathcode(c);

        } else if (c.isDigit()) {
            // return new Count(c.getCodePoint() + MATHCODE_DIGIT_OFFSET);
            mc = new MathCode(MathClass.VARIABLE, new MathGlyph(0, c));
        } else if (c.isLetter()) {
            // return new Count(c.getCodePoint() + MATHCODE_LETTER_OFFSET);
            mc = new MathCode(MathClass.VARIABLE, new MathGlyph(1, c));
        } else {
            mc = new MathCode(MathClass.ORDINARY, new MathGlyph(0, c));
        }

        if (mathcodeMap == null) {
            mathcodeMap = new HashMap<UnicodeChar, MathCode>();
        }
        mathcodeMap.put(c, mc);
        return mc;
    }

    /**
     * Getter for the named muskip register in the current group. The name can
     * either be a string representing a number or an arbitrary string. In the
     * first case the behavior of the numbered muskip registers is emulated. The
     * other case can be used to store special muskip values.
     * <p>
     * As a default value 0 is returned.
     * </p>
     * 
     * @param name the name of the count register
     * 
     * @return the value of the count register or its default
     * 
     * @see org.extex.interpreter.max.context.Group#getMuskip( java.lang.String)
     */
    public Muskip getMuskip(String name) {

        if (muskipMap != null) {
            Muskip muskip = muskipMap.get(name);
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
     * Getter for the next group in the linked list. Maybe this method should be
     * hidden.
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
    public OutFile getOutFile(String name) {

        if (outFileMap != null) {
            return outFileMap.get(name);
        }
        return null;
    }

    /**
     * Getter for the space factor code of a character. The sfcode is 999 for
     * letters and 1000 for other characters unless changed explicitly.
     * 
     * @param c the character for which the sfcode is requested
     * 
     * @return the sfcode of the given character
     * 
     * @see org.extex.interpreter.max.context.Group#getSfcode(
     *      org.extex.core.UnicodeChar)
     */
    public FixedCount getSfcode(UnicodeChar c) {

        if (sfcodeMap != null) {
            Count sfcode = sfcodeMap.get(c);
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
     * @see org.extex.interpreter.max.context.Group#getSkip( java.lang.String)
     */
    public Glue getSkip(String name) {

        if (skipMap != null) {
            Glue skip = skipMap.get(name);
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
     * @see org.extex.interpreter.max.context.Group#getToks( java.lang.String)
     */
    public Tokens getToks(String name) {

        if (toksMap != null) {
            Tokens toks = toksMap.get(name);
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
     *         defined
     * 
     * @see org.extex.interpreter.max.context.Group#getToksOrNull(java.lang.String)
     */
    public Tokens getToksOrNull(String name) {

        if (toksMap != null) {
            Tokens toks = toksMap.get(name);
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
     * Getter for the uccode mapping of lower case characters to their upper
     * case equivalent.
     * 
     * @param uc the upper case character
     * 
     * @return the upper case equivalent or null if none exists
     * 
     * @see org.extex.interpreter.max.context.Group#getUccode(
     *      org.extex.core.UnicodeChar)
     */
    public UnicodeChar getUccode(UnicodeChar uc) {

        if (uccodeMap != null) {
            UnicodeChar value = uccodeMap.get(uc);
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
                uccodeMap = new HashMap<UnicodeChar, UnicodeChar>();
            }
            uccodeMap.put(uc, value);
            return value;
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#runAfterGroup()
     */
    public void runAfterGroup() throws HelpingException {

        if (afterGroupObservers != null) {
            afterGroupObservers.update();
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#set( java.lang.Object,
     *      java.lang.Object, java.lang.Object, boolean)
     */
    public void set(Object extension, Object key, Object value, boolean global) {

        if (extensionMap == null) {
            extensionMap = new HashMap<Object, Map<Object, Object>>();
        }
        Map<Object, Object> map = extensionMap.get(extension);
        if (map == null) {
            map = new HashMap<Object, Object>();
            extensionMap.put(extension, map);
        }

        map.put(key, value);

        if (global && next != null) {
            next.set(extension, key, value, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setBox( java.lang.String,
     *      org.extex.interpreter.type.box.Box, boolean)
     */
    public void setBox(String name, Box value, boolean global) {

        if (boxMap == null) {
            boxMap = new HashMap<String, Box>();
        }

        boxMap.put(name, value);

        if (global && next != null) {
            next.setBox(name, value, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setCatcode(
     *      org.extex.core.UnicodeChar, org.extex.scanner.type.Catcode, boolean)
     */
    public void setCatcode(UnicodeChar uc, Catcode code, boolean global) {

        if (catcodeMap == null) {
            catcodeMap = new HashMap<UnicodeChar, Catcode>();
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
     *        string
     * @param code the new code
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setCode(
     *      org.extex.scanner.type.token.Token, org.extex.interpreter.type.Code,
     *      boolean)
     */
    public void setCode(Token token, Code code, boolean global) {

        if (codeMap == null) {
            codeMap = new HashMap<Token, Code>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setCount( java.lang.String,
     *      org.extex.core.count.Count, boolean)
     */
    public void setCount(String name, Count value, boolean global) {

        if (countMap == null) {
            countMap = new HashMap<String, Count>();
        }

        countMap.put(name, value);

        if (global && next != null) {
            next.setCount(name, value, global);
        }
    }

    /**
     * Setter for the delimiter code of a character.
     * 
     * @param uc the character to set the delimiter code for
     * @param code the new delimiter code
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setDelcode(
     *      org.extex.core.UnicodeChar, MathDelimiter, boolean)
     */
    public void setDelcode(UnicodeChar uc, MathDelimiter code, boolean global) {

        if (delcodeMap == null) {
            delcodeMap = new HashMap<UnicodeChar, MathDelimiter>();
        }

        delcodeMap.put(uc, code);

        if (global && next != null) {
            next.setDelcode(uc, code, global);
        }
    }

    /**
     * Setter for a dimen register in the requested groups.
     * 
     * @param name the name of the count register
     * @param value the value of the count register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setDimen( java.lang.String,
     *      org.extex.core.dimen.Dimen, boolean)
     */
    public void setDimen(String name, Dimen value, boolean global) {

        if (dimenMap == null) {
            dimenMap = new HashMap<String, Dimen>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setFont( java.lang.String,
     *      org.extex.typesetter.tc.font.Font, boolean)
     */
    public void setFont(String name, Font font, boolean global) {

        if (fontMap == null) {
            fontMap = new HashMap<String, Font>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setIf( java.lang.String,
     *      boolean, boolean)
     */
    public void setIf(String name, boolean value, boolean global) {

        if (ifMap == null) {
            ifMap = new HashMap<String, Boolean>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setInFile( java.lang.String,
     *      org.extex.scanner.type.file.InFile, boolean)
     */
    public void setInFile(String name, InFile file, boolean global) {

        if (inFileMap == null) {
            inFileMap = new HashMap<String, InFile>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setLccode(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
     */
    public void setLccode(UnicodeChar lc, UnicodeChar uc, boolean global) {

        if (lccodeMap == null) {
            lccodeMap = new HashMap<UnicodeChar, UnicodeChar>();
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
    public void setLocator(Locator locator) {

        this.locator = locator;
    }

    /**
     * Setter for the math code of a character.
     * 
     * @param uc the character to set the math code for
     * @param code the new math code
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setMathcode(
     *      org.extex.core.UnicodeChar, MathCode, boolean)
     */
    public void setMathcode(UnicodeChar uc, MathCode code, boolean global) {

        if (mathcodeMap == null) {
            mathcodeMap = new HashMap<UnicodeChar, MathCode>();
        }

        mathcodeMap.put(uc, code);

        if (global && next != null) {
            next.setMathcode(uc, code, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setMuskip( java.lang.String,
     *      org.extex.core.muskip.Muskip, boolean)
     */
    public void setMuskip(String name, Muskip value, boolean global) {

        if (muskipMap == null) {
            muskipMap = new HashMap<String, Muskip>();
        }

        muskipMap.put(name, value);

        if (global && next != null) {
            next.setMuskip(name, value, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setNamespace(
     *      java.lang.String, boolean)
     */
    public void setNamespace(String theNamespace, boolean global) {

        this.namespace = theNamespace;

        if (global && next != null) {
            next.setNamespace(theNamespace, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setOutFile(
     *      java.lang.String, org.extex.scanner.type.file.OutFile, boolean)
     */
    public void setOutFile(String name, OutFile file, boolean global) {

        if (outFileMap == null) {
            outFileMap = new HashMap<String, OutFile>();
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
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setSfcode(
     *      org.extex.core.UnicodeChar, org.extex.core.count.Count, boolean)
     */
    public void setSfcode(UnicodeChar uc, Count code, boolean global) {

        if (sfcodeMap == null) {
            sfcodeMap = new HashMap<UnicodeChar, Count>();
        }

        sfcodeMap.put(uc, code);

        if (global && next != null) {
            next.setSfcode(uc, code, global);
        }
    }

    /**
     * Setter for a skip register in all groups.
     * 
     * @param name the name of the count register
     * @param value the value of the count register
     * @param global the indicator for the scope; <code>true</code> means all
     *        groups; otherwise the current group is affected only
     * 
     * @see org.extex.interpreter.max.context.Group#setSkip( java.lang.String,
     *      org.extex.core.glue.Glue, boolean)
     */
    public void setSkip(String name, Glue value, boolean global) {

        if (skipMap == null) {
            skipMap = new HashMap<String, Glue>();
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
    public void setStandardTokenStream(TokenStream standardTokenStream) {

        this.standardTokenStream = standardTokenStream;
    }

    /**
     * Setter for start token.
     * 
     * @param start the start token to set
     */
    public void setStart(Token start) {

        this.start = start;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setToks( java.lang.String,
     *      org.extex.scanner.type.tokens.Tokens, boolean)
     */
    public void setToks(String name, Tokens value, boolean global) {

        if (toksMap == null) {
            toksMap = new HashMap<String, Tokens>();
        }

        toksMap.put(name, value);

        if (global && next != null) {
            next.setToks(name, value, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setType(
     *      org.extex.interpreter.context.group.GroupType)
     */
    public void setType(GroupType type) {

        this.type = type;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setTypesettingContext(
     *      org.extex.typesetter.tc.TypesettingContext, boolean)
     */
    public void setTypesettingContext(TypesettingContext context, boolean global) {

        typesettingContext = context;

        if (global && next != null) {
            next.setTypesettingContext(context, global);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.interpreter.max.context.Group#setUccode(
     *      org.extex.core.UnicodeChar, org.extex.core.UnicodeChar, boolean)
     */
    public void setUccode(UnicodeChar uc, UnicodeChar lc, boolean global) {

        if (uccodeMap == null) {
            uccodeMap = new HashMap<UnicodeChar, UnicodeChar>();
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
