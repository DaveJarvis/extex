
package org.extex.exbib.bst2groovy.compiler;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.BinaryInfix;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GBoolean;
import org.extex.exbib.bst2groovy.linker.LinkContainer;

/**
 * This class implements the analyzer for the > builtin.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class GreaterCompiler implements Compiler {

    /**
     * This inner class is the expression for the > builtin in the target
     * program.
     */
    public static class Greater extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public Greater(GCode a, GCode b) {

            super(a, b, ">", 700);
        }
    }

    /**
     * This inner class is the expression for >= in the target program.
     */
    public static class GreaterEqual extends BinaryInfix {

        /**
         * Creates a new object.
         * 
         * @param a the first argument
         * @param b the second argument
         */
        public GreaterEqual(GCode a, GCode b) {

            super(a, b, ">=", 700);
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
        state.push(new GBoolean(new Greater(a, b)));
    }
}
