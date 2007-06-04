/*
 * Copyright (C) 2007 The ExTeX Group and individual authors listed below
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

package org.extex.backend.documentWriter.xml;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.io.ByteArrayInputStream;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.extex.test.ExTeXLauncher;
import org.extex.test.Validator;
import org.xml.sax.InputSource;

/**
 * Validator which compares the xml result from the {@link ExTeXLauncher}.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XmlValidator implements Validator {

    /**
     * The comment for the error message.
     */
    private String[] comment;

    /**
     * The expected value.
     */
    private String[] expected;

    /**
     * The xpath string.
     */
    private String[] xpathstring;

    /**
     * Creates a new object.
     * 
     * @param comment The comment for the error message.
     * @param xpath The xpath string.
     * @param expected The expected value.
     */
    public XmlValidator(String comment, String xpath, String expected) {

        this.comment = new String[]{comment};
        this.xpathstring = new String[]{xpath};
        this.expected = new String[]{expected};
    }

    /**
     * Creates a new object.
     * 
     * @param comment The comment array for the error message.
     * @param xpath The xpath array.
     * @param expected The expected value array.
     */
    public XmlValidator(String[] comment, String[] xpath, String[] expected) {

        this.comment = comment;
        this.xpathstring = xpath;
        this.expected = expected;
    }

    /**
     * Returns <code>true</code> if the given string is not <code>null</code>
     * and if it is equal to the given result of the xpath result. Otherwise an
     * JUnit exception is raised.
     * 
     * {@inheritDoc}
     * 
     * @see org.extex.test.Validator#validate(java.lang.String)
     */
    public boolean validate(String s) {

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        for (int i = 0; i < comment.length; i++) {
            assertNotNull(comment[i], s);

            try {
                XPathExpression expression = xpath.compile(xpathstring[i]);

                String result =
                        expression.evaluate(new InputSource(
                            new ByteArrayInputStream(s.getBytes())));

                assertEquals(comment[i], expected[i], result);

            } catch (Exception e) {
                assertTrue(e.getMessage(), false);
            }
        }
        return true;
    }
}
