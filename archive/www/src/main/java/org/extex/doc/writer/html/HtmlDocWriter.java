/*
 * Copyright (C) 2007-2011 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 2.1 of the License, or (at your
 * option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation,
 * Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 */

package org.extex.doc.writer.html;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.extex.doc.util.Doc;
import org.extex.doc.writer.AbstractDocWriter;
import org.extex.doc.writer.DocWriter;
import org.extex.doc.writer.EntityHandler;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class HtmlDocWriter extends AbstractDocWriter implements DocWriter {

    /**
     * The field <tt>ENTITIES</tt> contains the ...
     */
    private static final Map<String, String> ENTITIES =
            new HashMap<String, String>();

    /**
     * The field <tt>ENTITY_HANDLER</tt> contains the ...
     */
    private static final EntityHandler ENTITY_HANDLER = new EntityHandler() {

        public boolean handle(String s, OutputStream out)
                throws UnsupportedEncodingException,
                    IOException {

            String r = ENTITIES.get(s);
            if (r != null) {
                out.write(r.getBytes("UTF-8"));
                return true;
            }
            return false;
        }

    };

    static {
        ENTITIES.put("TeX", "TeX");
        ENTITIES.put("LaTeX", "LaTeX");
        ENTITIES.put("ExTeX", "ExTeX");
        ENTITIES.put("BibTeX", "BibTeX");
    }

    /**
     * The field <tt>loc</tt> contains the ...
     */
    private Locale loc = Locale.ENGLISH;

    /**
     * Creates a new object.
     * 
     */
    public HtmlDocWriter() {

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.doc.writer.DocWriter#execute(java.util.Map)
     */
    public void execute(Map<String, Doc> map) throws IOException {

        String lang = loc.getLanguage();
        copyTemplate(lang, "frameset.html", ENTITY_HANDLER);
        copyTemplate(lang, "header.html", ENTITY_HANDLER);
        copyTemplate(lang, "index.html", ENTITY_HANDLER);
        copyTemplate(lang, "header.css", ENTITY_HANDLER);
        copyTemplate(lang, "info.css", ENTITY_HANDLER);
        copyTemplate(lang, "navi.css", ENTITY_HANDLER);
        copyTemplate(lang, "reframe.js", ENTITY_HANDLER);

        processConfigs();
        processUnits("units.html");
        processDocs("docs.html");
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    private void processConfigs() {

        // TODO gene: processConfigs unimplemented

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name TODO
     * 
     * @throws IOException
     */
    private void processDocs(String name) throws IOException {

        OutputStream out = openOutputStream(name);
        try {
            // TODO gene: processDocs unimplemented
        } finally {
            out.close();
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     */
    private void processUnits(String name) {

        // TODO gene: processUnits unimplemented

    }

}
