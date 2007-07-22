
package org.extex.ocpware.compiler.arith;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public enum Op {

    /**
     * The field <tt>PLUS</tt> contains the ...
     */
    PLUS {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.ocpware.compiler.arith.Op#eval(
         *      org.extex.ocpware.compiler.arith.ArithExpr,
         *      org.extex.ocpware.compiler.arith.ArithExpr)
         */
        @Override
        public ArithExpr eval(ArithExpr left, ArithExpr right) {

            return new Plus(left, right);
        }
    },

    /**
     * The field <tt>MINUS</tt> contains the ...
     */
    MINUS {

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.ocpware.compiler.arith.Op#eval(
         *      org.extex.ocpware.compiler.arith.ArithExpr,
         *      org.extex.ocpware.compiler.arith.ArithExpr)
         */
        @Override
        public ArithExpr eval(ArithExpr left, ArithExpr right) {

            return new Minus(left, right);
        }
    };

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param left the left argument
     * @param right the right argument
     * 
     * @return the combined term
     */
    public abstract ArithExpr eval(ArithExpr left, ArithExpr right);

}
