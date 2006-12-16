
package org.extex.interpreter.type.dimen.parser;

import org.extex.interpreter.TokenSource;
import org.extex.interpreter.context.Context;
import org.extex.interpreter.exception.InterpreterException;
import org.extex.typesetter.Typesetter;


/**
 * This interface describes a function object which is able to parse its
 * arguments from a toke stream.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4404 $
 */
public interface Function {

    /**
     * Acquire arguments and compute a function.
     *
     * @param accumulator the accumulator to receive the result
     * @param context the interpreter context
     * @param source the source for new tokens
     * @param typesetter the typesetter
     *
     * @throws InterpreterException in case of an error
     */
    public void apply(Accumulator accumulator, Context context,
            TokenSource source, Typesetter typesetter)
            throws InterpreterException;

}