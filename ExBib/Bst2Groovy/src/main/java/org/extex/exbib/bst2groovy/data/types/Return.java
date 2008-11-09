
package org.extex.exbib.bst2groovy.data.types;

import java.io.IOException;
import java.util.List;

import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.GenericCode;
import org.extex.exbib.bst2groovy.data.local.GLocal;
import org.extex.exbib.bst2groovy.data.local.InitLocal;
import org.extex.exbib.bst2groovy.io.CodeWriter;

/**
 * This class represents a return statement.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class Return extends GenericCode {

    /**
     * Creates a new object.
     * 
     * @param value the value
     */
    public Return(GCode value) {

        super(value.getType(), "return", value);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#optimize(java.util.List,
     *      int)
     */
    @Override
    public int optimize(List<GCode> list, int index) {

        if (index > 0 && getArg(0) instanceof GLocal) {
            GLocal var = (GLocal) getArg(0);
            GCode code = list.get(index - 1);
            if (code instanceof InitLocal && ((InitLocal) code).getVar() == var) {
                setArg(0, ((InitLocal) code).getValue());
                list.remove(index - 1);
                return index;
            }
        }
        return index + 1;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.data.GenericCode#print(org.extex.exbib.bst2groovy.io.CodeWriter,
     *      java.lang.String)
     */
    @Override
    public void print(CodeWriter writer, String prefix) throws IOException {

        writer.write(prefix, "return ");
        getArg(0).print(writer, prefix);
    }

}
