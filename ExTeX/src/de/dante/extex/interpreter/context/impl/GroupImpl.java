/*
 * Copyright (C) 2003-2004 The ExTeX Group and individual authors listed below
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
package de.dante.extex.interpreter.context.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import de.dante.extex.interpreter.Code;
import de.dante.extex.interpreter.Interaction;
import de.dante.extex.interpreter.Tokenizer;
import de.dante.extex.interpreter.context.TypesettingContext;
import de.dante.extex.interpreter.context.TypesettingContextImpl;
import de.dante.extex.interpreter.type.Box;
import de.dante.extex.interpreter.type.Count;
import de.dante.extex.interpreter.type.Dimen;
import de.dante.extex.interpreter.type.Glue;
import de.dante.extex.interpreter.type.ImmutableCount;
import de.dante.extex.interpreter.type.InFile;
import de.dante.extex.interpreter.type.Muskip;
import de.dante.extex.interpreter.type.OutFile;
import de.dante.extex.interpreter.type.Tokens;
import de.dante.extex.scanner.Catcode;
import de.dante.extex.scanner.Token;
import de.dante.extex.typesetter.Typesetter;
import de.dante.util.GeneralException;
import de.dante.util.UnicodeChar;
import de.dante.util.observer.Observable;
import de.dante.util.observer.Observer;
import de.dante.util.observer.ObserverList;

/**
 * This is a simple implementation for a group. The whole stack of group is
 * implemented as a linked list. The list itself is mixed with the pure
 * elements of the linked list.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @author <a href="m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class GroupImpl implements Group, Tokenizer, Serializable {

    /**
     * The field <tt>DELCODE_DEFAULT</tt> contains the default delcode.
     */
    private static final Count DELCODE_DEFAULT = new ImmutableCount(-1);

    /**
     * The field <tt>SFCODE_DEFAULT</tt> contains the default sfcode for
     * non-letters.
     */
    private static final Count SFCODE_DEFAULT = new ImmutableCount(1000);

    /**
     * The field <tt>SFCODE_LETTER</tt> contains the default sfcode for letters.
     */
    private static final Count SFCODE_LETTER = new ImmutableCount(999);

    /**
     * The field <tt>next</tt> contains the next group in the linked list.
     */
    private Group next = null;

    /**
     * The field <tt>interaction</tt> contains the currently active
     * intercation mode.
     */
    private Interaction interaction = null;

    /**
     * The field <tt>activeMap</tt> contains the map for the active characters.
     */
    private Map activeMap = new HashMap();

    /**
     * The field <tt>boxMap</tt> contains the map for the boxes.
     */
    private Map boxMap = new HashMap();

    /**
     * The field <tt>catcodeMap</tt> contains the map for the catcodes.
     */
    private Map catcodeMap = new HashMap();

    /**
     * The field <tt>countMap</tt> contains the map for the count registers.
     */
    private Map countMap = new HashMap();

    /**
     * The field <tt>delcodeMap</tt> contains the map for ...
     */
    private Map delcodeMap = new HashMap();

    /**
     * The field <tt>dimenMap</tt> contains the map for the dimen registers.
     */
    private Map dimenMap = new HashMap();

    /**
     * The field <tt>fontMap</tt> contains the map for the fonts.
     */
    private Map fontMap = new HashMap();

    /**
     * The field <tt>ifMap</tt> contains the map for the booleans.
     */
    private Map ifMap = new HashMap();

    /**
     * The field <tt>inFileMap</tt> contains the map for the input files.
     */
    private Map inFileMap = new HashMap();

    /**
     * The field <tt>lccodeMap</tt> contains the map for the translation to
     * lower case.
     */
    private Map lccodeMap = new HashMap();

    /**
     * The field <tt>macroMap</tt> contains the map for the macros.
     */
    private Map macroMap = new HashMap();
    /**
     * The field <tt>mathcodeMap</tt> contains the map for the catcodes.
     */
    private Map mathcodeMap = new HashMap();

    /**
     * The field <tt>muskipMap</tt> contains the map for the muskip registers.
     */
    private Map muskipMap = new HashMap();

    /**
     * The field <tt>outFileMap</tt> contains the map for the output files.
     */
    private Map outFileMap = new HashMap();

    /**
     * The field <tt>sfcodeMap</tt> contains the map for the space factor.
     */
    private Map sfcodeMap = new HashMap();

    /**
     * The field <tt>skipMap</tt> contains the map for the skip registers
     */
    private Map skipMap = new HashMap();

    /**
     * The field <tt>toksMap</tt> contains the map for the toks registers.
     */
    private Map toksMap = new HashMap();

    /**
     * The field <tt>uccodeMap</tt> contains the map for the translation to
     * upper case.
     */
    private Map uccodeMap = new HashMap();

    /**
     * The field <tt>afterGroup</tt> contains the tokens to be inserted after
     * the group has been closed.
     */
    private Tokens afterGroup = null;

    /**
     * The field <tt>afterGroupObservers</tt> contains the ...
     */
    private ObserverList afterGroupObservers = new ObserverList();

    /**
     * The field <tt>typesettingContext</tt> contains the ...
     */
    private TypesettingContext typesettingContext = null;

    /**
     * Creates a new object.
     *
     * @param aNext the next group in the stack. If the value is
     *      <code>null</code> then this is the global base
     */
    public GroupImpl(final Group aNext) {

        super();
        this.next = aNext;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setActive(java.lang.String,
     *      de.dante.extex.interpreter.Code)
     */
    public void setActive(final String name, final Code code) {

        activeMap.put(name, code);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setActive(java.lang.String,
     *      de.dante.extex.interpreter.Code, boolean)
     */
    public void setActive(final String name, final Code code,
        final boolean global) {

        activeMap.put(name, code);

        if (global && next != null) {
            next.setActive(name, code, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getActive(java.lang.String)
     */
    public Code getActive(final String name) {

        Code code = (Code) (activeMap.get(name));
        return (code != null ? code //
                : next != null ? next.getActive(name) //
                        : null);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getAfterGroup()
     */
    public Tokens getAfterGroup() {

        return afterGroup;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setBox(java.lang.String,
     *      de.dante.extex.interpreter.type.Box)
     */
    public void setBox(final String name, final Box value) {

        boxMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setBox(java.lang.String,
     *      de.dante.extex.interpreter.type.Box, boolean)
     */
    public void setBox(final String name, final Box value,
            final boolean global) {

        setBox(name, value);

        if (global && next != null) {
            next.setBox(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getBox(java.lang.String)
     */
    public Box getBox(final String name) {

        Box box = (Box) (boxMap.get(name));

        if (box == null && next != null) {
            box = next.getBox(name);
        }

        return box;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setCatcode(UnicodeChar,
     *      de.dante.extex.scanner.Catcode)
     */
    public void setCatcode(final UnicodeChar c, final Catcode code) {

        catcodeMap.put(c, code);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setCatcode(de.dante.util.UnicodeChar,
     *      de.dante.extex.scanner.Catcode, boolean)
     */
    public void setCatcode(final UnicodeChar c, final Catcode value,
        final boolean global) {

        setCatcode(c, value);

        if (global && next != null) {
            next.setCatcode(c, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.Tokenizer#getCatcode(de.dante.util.UnicodeChar)
     */
    public Catcode getCatcode(final UnicodeChar c) {

        Catcode value = (Catcode) catcodeMap.get(c);

        if (value != null) {
            return value;
        } else if (next != null) {
            return next.getCatcode(c);
        }

        // Fallback for predefined catcodes
        if (c.isLetter()) {
            return Catcode.LETTER;
        }

        switch (c.getCodePoint()) {
            case ' ' :
                return Catcode.SPACE;
            case '\\' :
                return Catcode.ESCAPE;
            case '\r' :
            case '\n' :
                return Catcode.CR;
            case '%' :
                return Catcode.COMMENT;
            case 0 :
                return Catcode.IGNORE;
            case 127 :
                return Catcode.INVALID;
            default:
                return Catcode.OTHER;
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setCount(java.lang.String,
     *      de.dante.extex.interpreter.type.Count)
     */
    public void setCount(final String name, final Count value) {

        countMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setCount(java.lang.String,
     *      de.dante.extex.interpreter.type.Count, boolean)
     */
    public void setCount(final String name, final Count value,
        final boolean global) {

        countMap.put(name, value);

        if (global && next != null) {
            next.setCount(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getCount(java.lang.String)
     */
    public Count getCount(final String name) {

        Count count = (Count) (countMap.get(name));

        if (count == null) {
            if (next != null) {
                count = next.getCount(name);
            } else {
                count = new Count(0);
                setCount(name, count);
            }
        }

        return count;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getDelcode(de.dante.util.UnicodeChar)
     */
    public Count getDelcode(final UnicodeChar c) {

        Count delcode = (Count) (delcodeMap.get(c));

        if (delcode != null) {
            return delcode;
        } else if (next != null) {
            return next.getSfcode(c);
        }

        // Fallback for predefined delcodes
        return DELCODE_DEFAULT;

    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setDelcode(de.dante.util.UnicodeChar, de.dante.extex.interpreter.type.Count, boolean)
     */
    public void setDelcode(final UnicodeChar c, final Count code, final boolean global) {

        delcodeMap.put(c, code);

        if (global && next != null) {
            next.setDelcode(c, code, global);
        }
    }


    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setDimen(java.lang.String,
     *         de.dante.extex.interpreter.type.Dimen)
     */
    public void setDimen(final String name, final Dimen value) {

        dimenMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setDimen(java.lang.String,
     *      de.dante.extex.interpreter.type.Dimen, boolean)
     */
    public void setDimen(final String name, final Dimen value,
        final boolean global) {

        dimenMap.put(name, value);

        if (global && next != null) {
            next.setDimen(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getDimen(java.lang.String)
     */
    public Dimen getDimen(final String name) {

        Dimen dimen = (Dimen) (dimenMap.get(name));

        if (dimen == null) {
            if (next != null) {
                dimen = next.getDimen(name);
            } else {
                dimen = new Dimen();
                setDimen(name, dimen);
            }
        }

        return dimen;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setIf(java.lang.String,
     *         boolean)
     */
    public void setIf(final String name, final boolean value) {

        ifMap.put(name, (value ? Boolean.TRUE : Boolean.FALSE));
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setIf(java.lang.String,
     *      boolean, boolean)
     */
    public void setIf(final String name, final boolean value,
        final boolean global) {

        ifMap.put(name, (value ? Boolean.TRUE : Boolean.FALSE));

        if (global && next != null) {
            next.setIf(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getIf(java.lang.String)
     */
    public boolean getIf(final String name) {

        Boolean b = (Boolean) (ifMap.get(name));
        return b != null ? b.booleanValue() : next != null ? next.getIf(name)
                : false;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getInFile(java.lang.String)
     */
    public InFile getInFile(final String name) {

        InFile file = (InFile) (inFileMap.get(name));

        return file;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setInFile(java.lang.String,
     *      de.dante.extex.interpreter.type.InFile)
     */
    public void setInFile(final String name, final InFile file) {

        inFileMap.put(name, file);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setInFile(java.lang.String,
     *      de.dante.extex.interpreter.type.InFile, boolean)
     */
    public void setInFile(final String name, final InFile file,
        final boolean global) {

        inFileMap.put(name, file);

        if (global && next != null) {
            next.setInFile(name, file, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getMathcode(de.dante.util.UnicodeChar)
     */
    public Count getMathcode(final UnicodeChar c) {

        Count mathcode = (Count) (mathcodeMap.get(c));

        if (mathcode == null) {
            if (next != null) {
                return next.getMathcode(c);
            } else if (c.isDigit()) {
                return new Count(c.getCodePoint() + 0x7000);
            } else if (c.isLetter()) {
                return new Count(c.getCodePoint() + 0x7100);
            } else {
                return new Count(c.getCodePoint());
            }
        }

        return mathcode;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setMathcode(de.dante.util.UnicodeChar, Count, boolean)
     */
    public void setMathcode(final UnicodeChar c, final Count code, final boolean global) {

        mathcodeMap.put(c, code);

        if (global && next != null) {
            next.setMathcode(c, code, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getOutFile(java.lang.String)
     */
    public OutFile getOutFile(final String name) {

        OutFile file = (OutFile) (outFileMap.get(name));

        return file;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setOutFile(java.lang.String,
     *      de.dante.extex.interpreter.type.OutFile, boolean)
     */
    public void setOutFile(final String name, final OutFile file,
        final boolean global) {

        outFileMap.put(name, file);

        if (global && next != null) {
            next.setOutFile(name, file, global);
        }
    }
    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setOutFile(java.lang.String,
     *      de.dante.extex.interpreter.type.OutFile)
     */
    public void setOutFile(final String name, final OutFile file) {

        outFileMap.put(name, file);
    }
    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setInteraction(de.dante.extex.interpreter.Interaction)
     */
    public void setInteraction(final Interaction aInteraction) {

        this.interaction = aInteraction;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setInteraction(de.dante.extex.interpreter.Interaction,
     *      boolean)
     */
    public void setInteraction(final Interaction aInteraction,
        final boolean global) {

        this.interaction = aInteraction;

        if (global && next != null) {
            next.setInteraction(interaction, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getInteraction()
     */
    public Interaction getInteraction() {

        return interaction;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getLccode(de.dante.util.UnicodeChar)
     */
    public UnicodeChar getLccode(final UnicodeChar lc) {

        UnicodeChar value = (UnicodeChar) lccodeMap.get(lc);

        if (value != null) {
            return value;
        } else if (next != null) {
            return next.getLccode(lc);
        }

        // Fallback for predefined lccodes
        if (lc.isLetter()) {
            value = new UnicodeChar(lc.toLowerCase());
            // the value is stored to avoid constructing UnicodeChars again
            lccodeMap.put(lc, value);
            return value;
        }
        return UnicodeChar.NULL;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setLccode(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public void setLccode(final UnicodeChar lc, final UnicodeChar uc) {

        lccodeMap.put(lc, uc);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setMacro(java.lang.String,
     *      de.dante.extex.interpreter.Code)
     */
    public void setMacro(final String name, final Code code) {

        macroMap.put(name, code);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setMacro(java.lang.String,
     *      de.dante.extex.interpreter.Code, boolean)
     */
    public void setMacro(final String name, final Code value,
        final boolean global) {

        macroMap.put(name, value);

        if (global && next != null) {
            next.setMacro(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getMacro(java.lang.String)
     */
    public Code getMacro(final String name) {

        Code value = (Code) macroMap.get(name);
        return (value == null && next != null ? next.getMacro(name) : value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setMuskip(java.lang.String,
     *         de.dante.extex.interpreter.type.Muskip)
     */
    public void setMuskip(final String name, final Muskip value) {

        muskipMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setMuskip(java.lang.String,
     *      de.dante.extex.interpreter.type.Muskip, boolean)
     */
    public void setMuskip(final String name, final Muskip value,
        final boolean global) {

        muskipMap.put(name, value);

        if (global && next != null) {
            next.setMuskip(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getMuskip(java.lang.String)
     */
    public Muskip getMuskip(final String name) {

        Muskip muskip = (Muskip) (muskipMap.get(name));
        return muskip != null ? muskip : next != null ? next.getMuskip(name)
                : new Muskip();
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getNext()
     */
    public Group getNext() {

        return next;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getSfcode(de.dante.util.UnicodeChar)
     */
    public Count getSfcode(final UnicodeChar c) {

        Count sfcode = (Count) (sfcodeMap.get(c));

        if (sfcode != null) {
            return sfcode;
        } else if (next != null) {
            return next.getSfcode(c);
        }

        // Fallback for predefined sfcodes
        if (c.isLetter()) {
            return SFCODE_LETTER;
        }
        return SFCODE_DEFAULT;

    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setSfcode(de.dante.util.UnicodeChar, de.dante.extex.interpreter.type.Count, boolean)
     */
    public void setSfcode(final UnicodeChar c, final Count code, final boolean global) {

        sfcodeMap.put(c, code);

        if (global && next != null) {
            next.setSfcode(c, code, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setSkip(java.lang.String,
     *         de.dante.extex.interpreter.type.Glue)
     */
    public void setSkip(final String name, final Glue value) {

        skipMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setSkip(java.lang.String,
     *      de.dante.extex.interpreter.type.Glue, boolean)
     */
    public void setSkip(final String name, final Glue value,
        final boolean global) {

        skipMap.put(name, value);

        if (global && next != null) {
            next.setSkip(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getSkip(java.lang.String)
     */
    public Glue getSkip(final String name) {

        Glue skip = (Glue) (skipMap.get(name));
        return skip != null ? skip : next != null ? next.getSkip(name)
                : new Glue(0);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setToks(java.lang.String,
     *         de.dante.extex.interpreter.type.Tokens)
     */
    public void setToks(final String name, final Tokens value) {

        toksMap.put(name, value);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setToks(java.lang.String,
     *      de.dante.extex.interpreter.type.Tokens, boolean)
     */
    public void setToks(final String name, final Tokens value,
        final boolean global) {

        toksMap.put(name, value);

        if (global && next != null) {
            next.setToks(name, value, global);
        }
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getToks(java.lang.String)
     */
    public Tokens getToks(final String name) {

        Tokens toks = (Tokens) (toksMap.get(name));
        return toks != null ? toks : next != null ? next.getToks(name)
                : new Tokens();
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setTypesettingContext(de.dante.extex.interpreter.context.TypesettingContext,
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
     * @see de.dante.extex.interpreter.context.impl.Group#setTypesettingContext(de.dante.extex.interpreter.context.TypesettingContext)
     */
    public void setTypesettingContext(final TypesettingContext context) {

        typesettingContext = context;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getTypesettingContext()
     */
    public TypesettingContext getTypesettingContext() {

        TypesettingContext context = typesettingContext;
        return context != null //
            ? context //
            : next != null ? next.getTypesettingContext()
                : new TypesettingContextImpl();
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#afterGroup(de.dante.extex.scanner.Token)
     */
    public void afterGroup(final Token t) {

        if (afterGroup == null) {
            afterGroup = new Tokens();
        }

        afterGroup.add(t);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#afterGroup(de.dante.util.Observer)
     */
    public void afterGroup(final Observer observer) {

        afterGroupObservers.add(observer);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#runAfterGroup(de.dante.util.Observable,
     *      de.dante.extex.typesetter.Typesetter)
     */
    public void runAfterGroup(final Observable source,
        final Typesetter typesetter) throws GeneralException {

        afterGroupObservers.update(source, typesetter);
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#getUccode(de.dante.util.UnicodeChar)
     */
    public UnicodeChar getUccode(final UnicodeChar uc) {

        UnicodeChar value = (UnicodeChar) uccodeMap.get(uc);

        if (value != null) {
            return value;
        } else if (next != null) {
            return next.getLccode(uc);
        }

        // Fallback for predefined lccodes
        if (uc.isLetter()) {
            value = new UnicodeChar(uc.toUpperCase());
            // the value is stored to avoid constructing UnicodeChars again
            lccodeMap.put(uc, value);
            return value;
        }
        return UnicodeChar.NULL;
    }

    /**
     * @see de.dante.extex.interpreter.context.impl.Group#setUccode(de.dante.util.UnicodeChar,
     *      de.dante.util.UnicodeChar)
     */
    public void setUccode(final UnicodeChar uc, final UnicodeChar lc) {

        uccodeMap.put(uc, lc);
    }

}
