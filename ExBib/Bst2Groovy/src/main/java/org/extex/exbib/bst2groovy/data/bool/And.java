
package org.extex.exbib.bst2groovy.data.bool;

import java.io.IOException;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.types.ReturnType;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a conjunction.
 * 
 */
public final class And extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param code1 the left side
     * @param code2 the right side
     */
    public And(GCode code1, GCode code2) {

        super(ReturnType.INT, "", code1, code2);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize()
     */
    @Override
    public GCode optimize() {

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

        getArg(0).print(writer, prefix);
        writer.write(" && ");
        getArg(1).print(writer, prefix);
    }

}
