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

package org.extex.interpreter;

/**
 * This interface describes a set of named flags.
 *
 * <p>
 * The flags correspond largely to the prefix macros like {@code \global}
 * The following flags are known:
 * </p>
 * <table> <caption>TBD</caption>
 *  <tr>
 *   <th>Flag</th>
 *   <th>Macro</th>
 *   <th>Description</th>
 *  </tr>
 *  <tr>
 *   <td><b>global</b></td>
 *   <td>{@code \global}</td>
 *   <td>The global flag controls the scope of assignments like {@code \def}s.
 *   </td>
 *  </tr>
 *  <tr>
 *   <td><b>immediate</b></td>
 *   <td>{@code \immediate}</td>
 *   <td>The immediate flag controls the delay of writing operations. This is
 *    used in {@code \write} and friends.
 *   </td>
 *  </tr>
 *  <tr>
 *   <td><b>long</b></td>
 *   <td>{@code \long}</td>
 *   <td>The long flag controls the matching of macro parameters.
 *   </td>
 *  </tr>
 *  <tr>
 *   <td><b>outer</b></td>
 *   <td>{@code \outer}</td>
 *   <td>The outer flag controls that the macro can not be used at places where
 *    tokens are absorbed at high speed.
 *   </td>
 *  </tr>
 *  <tr>
 *   <td><b>protected</b></td>
 *   <td>{@code \protected}</td>
 *   <td>The protected flag controls the expansion of the macro when the
 *    argument for {@code \edef} and friends is expanded.
 *   </td>
 *  </tr>
 * </table>
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public interface Flags {

    /**
     * The constant {@code NONE} contains an instance where no flags are set.
     * Beware of changing this instance!
     */
    Flags NONE = new Flags() {

        /**
         * @see org.extex.interpreter.Flags#clear()
         */
        public void clear() {

            // nothing to do
        }

        /**
         * @see org.extex.interpreter.Flags#clearGlobal()
         */
        public boolean clearGlobal() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#clearImmediate()
         */
        public boolean clearImmediate() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#clearLong()
         */
        public boolean clearLong() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#clearOuter()
         */
        public boolean clearOuter() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#clearProtected()
         */
        public boolean clearProtected() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#copy()
         */
        public Flags copy() {

            return null;
        }

        /**
         * @see org.extex.interpreter.Flags#get()
         */
        public String[] get() {

            return null;
        }

        /**
         * @see org.extex.interpreter.Flags#isDirty()
         */
        public boolean isDirty() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#isGlobal()
         */
        public boolean isGlobal() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#isImmediate()
         */
        public boolean isImmediate() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#isLong()
         */
        public boolean isLong() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#isOuter()
         */
        public boolean isOuter() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#isProtected()
         */
        public boolean isProtected() {

            return false;
        }

        /**
         * @see org.extex.interpreter.Flags#set(org.extex.interpreter.Flags)
         */
        public void set(Flags flags) {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setGlobal()
         */
        public void setGlobal() {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setGlobal(boolean)
         */
        public void setGlobal(boolean value) {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setImmediate()
         */
        public void setImmediate() {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setLong()
         */
        public void setLong() {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setOuter()
         */
        public void setOuter() {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#setProtected()
         */
        public void setProtected() {

            throw new UnsupportedOperationException();
        }

        /**
         * @see org.extex.interpreter.Flags#toText()
         */
        public String toText() {

            return "";
        }
    };

    /**
     * This method clears all flags.
     */
    void clear();

    /**
     * Setter for the global flag. The flag is reset to {@code false}.
     *
     * @return the old value of the global flag
     *
     * @see #isGlobal()
     * @see #setGlobal()
     * @see #setGlobal(boolean)
     */
    boolean clearGlobal();

    /**
     * Setter for the immediate flag. The flag is reset to {@code false}.
     *
     * @return the old value of the immediate flag
     *
     * @see #isImmediate()
     * @see #setImmediate()
     */
    boolean clearImmediate();

    /**
     * Setter for the long flag. The flag is reset to {@code false}.
     *
     * @return the old value of the long flag
     *
     * @see #isLong()
     * @see #setLong()
     */
    boolean clearLong();

    /**
     * Setter for the outer flag. The flag is reset to {@code false}.
     *
     * @return the old value of the outer flag
     *
     * @see #isOuter()
     * @see #setOuter()
     */
    boolean clearOuter();

    /**
     * Setter for the protected flag. The flag is reset to {@code false}.
     *
     * @return the old value of the protected flag
     *
     * @see #isProtected()
     * @see #setProtected()
     */
    boolean clearProtected();

    /**
     * Clone an instance.
     *
     * @return a copy of the instance
     */
    Flags copy();

    /**
     * Getter for the text representations of the flags currently set.
     *
     * @return the array of flag names
     */
    String[] get();

    /**
     * Test if all flags are cleared.
     *
     * @return {@code true} iff not all flags are cleared
     */
    boolean isDirty();

    /**
     * Getter for the global flag.
     *
     * @return the current value of the global flag
     *
     * @see #clearGlobal()
     * @see #setGlobal()
     * @see #setGlobal(boolean)
     */
    boolean isGlobal();

    /**
     * Getter for the immediate flag.
     *
     * @return the current value of the immediate flag
     *
     * @see #clearImmediate()
     * @see #setImmediate()
     */
    boolean isImmediate();

    /**
     * Getter for the long flag.
     *
     * @return the current value of the long flag
     *
     * @see #clearLong()
     * @see #setLong()
     */
    boolean isLong();

    /**
     * Getter for the outer flag.
     *
     * @return the current value of the outer flag
     *
     * @see #clearOuter()
     * @see #setOuter()
     */
    boolean isOuter();

    /**
     * Getter for the protected flag.
     *
     * @return the current value of the protected flag
     *
     * @see #clearProtected()
     * @see #setProtected()
     */
    boolean isProtected();

    /**
     * Copy the flag settings from a given instance int this instance.
     *
     * @param flags the flags to copy
     */
    void set(Flags flags);

    /**
     * Setter for the global flag. The flag is set to {@code true}.
     *
     * @see #clearGlobal()
     * @see #isGlobal()
     * @see #setGlobal(boolean)
     */
    void setGlobal();

    /**
     * Setter for the global flag. The flag is set to {@code true}.
     *
     * @param value the new value for the global flag
     *
     * @see #clearGlobal()
     * @see #isGlobal()
     * @see #setGlobal()
     */
    void setGlobal(boolean value);

    /**
     * Setter for the immediate flag. The flag is set to {@code true}.
     *
     * @see #clearImmediate()
     * @see #isImmediate()
     */
    void setImmediate();

    /**
     * Setter for the long flag. The flag is set to {@code true}.
     *
     * @see #clearLong()
     * @see #isLong()
     */
    void setLong();

    /**
     * Setter for the outer flag. The flag is set to {@code true}.
     *
     * @see #clearOuter()
     * @see #isOuter()
     */
    void setOuter();

    /**
     * Setter for the protected flag. The flag is set to {@code true}.
     *
     * @see #clearProtected()
     * @see #isProtected()
     */
    void setProtected();

    /**
     * Determine a printable representation of the flags set.
     * The representation takes into account the current locale.
     *
     * @return the list
     */
    String toText();

}
