/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
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

package org.extex.exindex.core;

import org.extex.exindex.core.parser.exindex.ExIndexParserFactory;
import org.extex.framework.configuration.exception.ConfigurationException;
import org.extex.logging.LogFormatter;
import org.extex.resource.ResourceFinder;
import org.extex.resource.io.NamedInputStream;
import org.junit.Ignore;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.Assert.assertEquals;

/**
 * This is a test suite for makeindex compatibility.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 6732 $
 */
public class Makeindex1Test {

    /**
     * The field <tt>FINDER</tt> contains the resource finder.
     */
    private static final ResourceFinder FINDER = new ResourceFinder() {

        public void enableTracing(boolean flag) {

            // nay
        }

        public NamedInputStream findResource(String name, String type)
                throws ConfigurationException {

            ClassLoader classLoader = getClass().getClassLoader();
            return new NamedInputStream(classLoader.getResourceAsStream(name),
                name + "." + type);
        }

    };

    /**
     * Read the contents of a resource.
     * 
     * @param referenceFile the name of the reference
     * 
     * @return the content of the reference
     * 
     * @throws IOException in case of an I/O error
     */
    private String readReference(String referenceFile) throws IOException {

        InputStream is =
                getClass().getClassLoader().getResourceAsStream(referenceFile);
        if (is == null) {
            throw new FileNotFoundException("Missing " + referenceFile);
        }
        StringBuilder sb = new StringBuilder();
        try( Reader r = new InputStreamReader( is, UTF_8 ) ) {
            for( int c = r.read(); c >= 0; c = r.read() ) {
                sb.append( (char) c );
            }
        }

        return sb.toString();
    }

    /**
     * <testcase> Test that doc.ist can be read and the index from the users
     * guide can be processed. </testcase>
     * 
     * @throws Exception in case of an error
     */
    @Test
    @Ignore
    public void test1() throws Exception {

        Logger logger = Logger.getLogger("test");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.SEVERE);
        ByteArrayOutputStream log = new ByteArrayOutputStream();
        Handler handler = new StreamHandler(log, new LogFormatter());
        handler.setLevel(Level.WARNING);
        logger.addHandler(handler);

        Indexer indexer = new Indexer();
        indexer.setResourceFinder(FINDER);
        ExIndexParserFactory parserFactory = new ExIndexParserFactory();
        parserFactory.setResourceFinder(FINDER);
        indexer.setParserFactory(parserFactory);
        indexer.load("xindy/makeidx.xdy");
        indexer.load("extex/doc.ist");

        List<String> list = new ArrayList<>();
        list.add("extex/extex-users.idx");
        indexer.process(list, logger);

        StringWriter writer = new StringWriter();
        indexer.markup(writer, logger);

        assertEquals(readReference("extex/extex-users.ind-reference"),
            writer.toString());
        // indexer.printBindings(System.out);
    }

}
