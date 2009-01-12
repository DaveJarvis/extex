
package org.extex.exbib.bst2groovy.compiler;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.BinaryInfix;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.bool.GBoolean;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for the = built-in.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class EqualsCompiler implements Compiler {

    /**
     * This inner class is the expression for the = built-in in the target
     * program.
     */
    public static class Equals extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public Equals(GCode a, GCode b) {

            super(a, b, "==", 700);
        }
    }

    /**
     * This inner class is the expression for the != in the target program.
     */
    public static class NotEquals extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public NotEquals(GCode a, GCode b) {

            super(a, b, "!=", 700);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode a = state.pop();
        GCode b = state.pop();
        state.push(new GBoolean(new Equals(a, b)));
    }
}
