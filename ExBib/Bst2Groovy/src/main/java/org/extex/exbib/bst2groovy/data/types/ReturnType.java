/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.bst2groovy.data.types;

/**
 * This enumeration represents the return types in the target language.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public enum ReturnType {

    /**
     * The field <tt>INT</tt> contains the indicator for an integer.
     */
    INT {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#getArg()
         */
        @Override
        public String getArg() {

            return "0";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#toString()
         */
        @Override
        public String toString() {

            return "int";
        }

    },
    /**
     * The field <tt>STRING</tt> contains the indicator for a String.
     */
    STRING {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#getArg()
         */
        @Override
        public String getArg() {

            return "\"\"";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#toString()
         */
        @Override
        public String toString() {

            return "String";
        }

    },
    /**
     * The field <tt>VOID</tt> contains the indicator for a procedure.
     */
    VOID {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#getArg()
         */
        @Override
        public String getArg() {

            return "\"\"";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#toString()
         */
        @Override
        public String toString() {

            return "void";
        }

    },
    /**
     * The field <tt>CODE</tt> contains the indicator for a definition.
     */
    CODE {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#getArg()
         */
        @Override
        public String getArg() {

            return "\"\"";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#toString()
         */
        @Override
        public String toString() {

            return "def";
        }

    },
    /**
     * The field <tt>UNKNOWN</tt> contains the indicator for an unknown type.
     */
    UNKNOWN {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#getArg()
         */
        @Override
        public String getArg() {

            return "''";
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.types.ReturnType#toString()
         */
        @Override
        public String toString() {

            return "???";
        }

    };

    /**
     * Getter for the neutral element.
     * 
     * @return the neutral argument
     */
    public abstract String getArg();
}
