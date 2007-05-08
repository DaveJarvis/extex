
package org.extex.core.dimen.parser;

import org.extex.core.exception.helping.HelpingException;

/**
 * This interface describes a function object without any arguments.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface Function0 {

    /**
     * Return the value in the accumulator overwriting the value stored therein.
     *
     * @param accumulator the accumulator to receive the result
     *
     * @throws HelpingException in case of an error
     */
    void apply(Accumulator accumulator) throws HelpingException;

}