
package org.extex.interpreter.expression;

import org.extex.core.exception.helping.HelpingException;

/**
 * This interface describes an operation object without any arguments.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 4733 $
 */
public interface ConstantFunction {

    /**
     * Return the value in the accumulator overwriting the value stored therein.
     *
     * @return the operation result, i.e. the constant
     * @throws HelpingException in case of an error
     */
    EType apply() throws HelpingException;

}