/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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

package org.extex.interpreter;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * This class implements a set of flags. This is needed to pass controlling
 * information to primitives.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class FlagsImpl implements Flags {

    /**
     * The field {@code globalP} contains the global flag.
     */
    private boolean globalP = false;

    /**
     * The field {@code immediateP} contains the immediate flag.
     */
    private boolean immediateP = false;

    /**
     * The field {@code longP} contains the long flag.
     */
    private boolean longP = false;

    /**
     * The field {@code outerP} contains the outer flag.
     */
    private boolean outerP = false;

    /**
     * The field {@code protectedP} contains the protected flag.
     */
    private boolean protectedP = false;

    /**
     * Creates a new object. Initially no flags are set.
     */
    public FlagsImpl() {

    }

    /**
     * This method clears all flags.
     * 
     * @see org.extex.interpreter.Flags#clear()
     */
    @Override
    public void clear() {

        globalP = false;
        longP = false;
        outerP = false;
        immediateP = false;
        protectedP = false;
    }

    /**
     * Setter for the global flag. The flag is reset to {@code false}.
     * 
     * @return the old value of the global flag
     * 
     * @see org.extex.interpreter.Flags#clearGlobal()
     */
    @Override
    public boolean clearGlobal() {

        boolean flag = globalP;
        globalP = false;
        return flag;
    }

    /**
     * Setter for the immediate flag. The flag is reset to {@code false}.
     * 
     * @return the old value of the immediate flag
     * 
     * @see org.extex.interpreter.Flags#clearImmediate()
     */
    @Override
    public boolean clearImmediate() {

        boolean flag = immediateP;
        immediateP = false;
        return flag;
    }

    /**
     * Setter for the long flag. The flag is reset to {@code false}.
     * 
     * @return the old value of the long flag
     * 
     * @see org.extex.interpreter.Flags#clearLong()
     */
    @Override
    public boolean clearLong() {

        boolean flag = longP;
        longP = false;
        return flag;
    }

    /**
     * Setter for the outer flag. The flag is reset to {@code false}.
     * 
     * @return the old value of the outer flag
     * 
     * @see org.extex.interpreter.Flags#clearOuter()
     */
    @Override
    public boolean clearOuter() {

        boolean flag = outerP;
        outerP = false;
        return flag;
    }

    /**
     * Setter for the protected flag. The flag is reset to {@code false}.
     * 
     * @return the old value of the protected flag
     * 
     * @see org.extex.interpreter.Flags#clearProtected()
     */
    @Override
    public boolean clearProtected() {

        boolean flag = protectedP;
        protectedP = false;
        return flag;
    }

    /**
     * Clone an instance.
     * 
     * @return a copy of the instance
     * 
     * @see org.extex.interpreter.Flags#copy()
     */
    @Override
    public Flags copy() {

        Flags f = new FlagsImpl();
        if (globalP) {
            f.setGlobal();
        }
        if (longP) {
            f.setLong();
        }
        if (outerP) {
            f.setOuter();
        }
        if (immediateP) {
            f.setImmediate();
        }
        if (protectedP) {
            f.setProtected();
        }
        return f;
    }

    /**
     * Getter for the text representations of the flags currently set.
     * 
     * @return the array of flag names
     * 
     * @see org.extex.interpreter.Flags#get()
     */
    @Override
    public String[] get() {

        Localizer localizer = LocalizerFactory.getLocalizer(FlagsImpl.class);
        String[] result = new String[((globalP ? 1 : 0)
                + (longP ? 1 : 0)
                + (outerP ? 1 : 0)
                + (immediateP ? 1 : 0)
        + (protectedP ? 1 : 0))];
        int i = 0;
        if (globalP) {
            result[i++] = localizer.format("global.text");
        }
        if (longP) {
            result[i++] = localizer.format("long.text");
        }
        if (outerP) {
            result[i++] = localizer.format("outer.text");
        }
        if (immediateP) {
            result[i++] = localizer.format("immediate.text");
        }
        if (protectedP) {
            result[i++] = localizer.format("protected.text");
        }
        return result;
    }

    /**
     * Test if all flags are cleared.
     * 
     * @return {@code true} iff not all flags are cleared
     * 
     * @see org.extex.interpreter.Flags#isDirty()
     */
    @Override
    public boolean isDirty() {

        return globalP || longP || immediateP || outerP || protectedP;
    }

    /**
     * Getter for the global flag.
     * 
     * @return the current value of the global flag
     */
    @Override
    public boolean isGlobal() {

        return globalP;
    }

    /**
     * Getter for the immediate flag.
     * 
     * @return the current value of the immediate flag
     */
    @Override
    public boolean isImmediate() {

        return immediateP;
    }

    /**
     * Getter for the long flag.
     * 
     * @return the current value of the long flag
     */
    @Override
    public boolean isLong() {

        return longP;
    }

    /**
     * Getter for the outer flag.
     * 
     * @return the current value of the outer flag
     */
    @Override
    public boolean isOuter() {

        return outerP;
    }

    /**
     * Getter for the protected flag.
     * 
     * @return the current value of the protected flag
     * 
     * @see org.extex.interpreter.Flags#isProtected()
     */
    @Override
    public boolean isProtected() {

        return protectedP;
    }

    /**
     * Copy the flag settings from a given instance int this instance.
     * 
     * @param flags the flags to copy
     * 
     * @see org.extex.interpreter.Flags#set(org.extex.interpreter.Flags)
     */
    @Override
    public void set(Flags flags) {

        globalP = flags.isGlobal();
        immediateP = flags.isImmediate();
        longP = flags.isLong();
        outerP = flags.isOuter();
        protectedP = flags.isProtected();
    }

    /**
     * Setter for the global flag.
     */
    @Override
    public void setGlobal() {

        globalP = true;
    }

    /**
     * Setter for the global flag.
     * 
     * @param value the new value for the global flag
     */
    @Override
    public void setGlobal(boolean value) {

        globalP = value;
    }

    /**
     * Setter for the immediate flag.
     */
    @Override
    public void setImmediate() {

        immediateP = true;
    }

    /**
     * Setter for the long flag.
     */
    @Override
    public void setLong() {

        longP = true;
    }

    /**
     * Setter for the outer flag.
     */
    @Override
    public void setOuter() {

        outerP = true;
    }

    /**
     * @see org.extex.interpreter.Flags#setProtected()
     */
    @Override
    public void setProtected() {

        protectedP = true;
    }

    /**
     * Determine a printable representation of the instance.
     * 
     * @return the printable representation
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append(globalP ? 'G' : '-');
        sb.append(longP ? 'L' : '-');
        sb.append(outerP ? 'O' : '-');
        sb.append(immediateP ? 'I' : '-');
        sb.append(protectedP ? 'P' : '-');
        return sb.toString();
    }

    /**
     * Determine a printable representation of the flags set. The representation
     * takes into account the current locale.
     * 
     * @return the list
     * 
     * @see org.extex.interpreter.Flags#toText()
     */
    @Override
    public String toText() {

        String[] s = get();
        return LocalizerFactory.getLocalizer(FlagsImpl.class).format(
            "text." + Integer.toString(s.length), s);
    }

}
