package org.extex.exbib.bst2groovy.data.bool;

import java.io.IOException;

import org.extex.exbib.bst2groovy.compiler.EqualsCompiler;
import org.extex.exbib.bst2groovy.compiler.GreaterCompiler;
import org.extex.exbib.bst2groovy.compiler.LessCompiler;
import org.extex.exbib.bst2groovy.compiler.EqualsCompiler.Equals;
import org.extex.exbib.bst2groovy.compiler.EqualsCompiler.NotEquals;
import org.extex.exbib.bst2groovy.compiler.GreaterCompiler.Greater;
import org.extex.exbib.bst2groovy.compiler.GreaterCompiler.GreaterEqual;
import org.extex.exbib.bst2groovy.compiler.LessCompiler.Less;
import org.extex.exbib.bst2groovy.compiler.LessCompiler.LessEqual;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This inner class represents a negation.
 * 
 */
public final class Not extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param code the code
     */
    public Not(GCode code) {

        super(ReturnType.INT, "", code);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
     */
    @Override
    public GCode optimize() {

        GCode code = getArg(0);
        if (code instanceof GreaterCompiler.Greater) {
            return new GreaterCompiler.GreaterEqual(
                ((GreaterCompiler.Greater) code).getArg(0),
                ((GreaterCompiler.Greater) code).getArg(1));
        } else if (code instanceof LessCompiler.Less) {
            return new LessCompiler.LessEqual(
                //
                ((LessCompiler.Less) code).getArg(0),
                ((LessCompiler.Less) code).getArg(1));
        } else if (code instanceof EqualsCompiler.Equals) {
            return new EqualsCompiler.NotEquals(
                ((EqualsCompiler.Equals) code).getArg(0),
                ((EqualsCompiler.Equals) code).getArg(1));
        }
        return this;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write("! ");
        getArg(0).print(writer, prefix);
    }

}