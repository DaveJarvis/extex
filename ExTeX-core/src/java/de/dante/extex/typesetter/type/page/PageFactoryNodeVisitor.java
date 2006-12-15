
package de.dante.extex.typesetter.type.page;

import org.extex.interpreter.context.Context;

import de.dante.extex.typesetter.Typesetter;
import de.dante.extex.typesetter.type.NodeVisitor;

/**
 * This interface describes a
 * {@link org.extex.typesetter.type.NodeVisitor NodeVisitor} which is able
 * to take a {@link org.extex.typesetter.type.page.Page Page},
 * a {@link org.extex.interpreter.context.Context Context},
 * and a  {@link org.extex.typesetter.Typesetter Typesetter}.
 *
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public interface PageFactoryNodeVisitor extends NodeVisitor {

    /**
     * Setter for the context.
     *
     * @param context the context
     */
    void setContext(Context context);

    /**
     * Setter for the page.
     *
     * @param page the page
     */
    void setPage(Page page);

    /**
     * Setter for the typesetter.
     *
     * @param typsetter the typesetter
     */
    void setTypesetter(Typesetter typsetter);

}
