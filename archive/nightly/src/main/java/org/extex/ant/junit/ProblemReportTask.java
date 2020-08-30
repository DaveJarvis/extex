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

package org.extex.ant.junit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Add the current date to the build tag.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class ProblemReportTask extends Task {

    /**
     * The field {@code file} contains the name of the file to be processed.
     */
    private String file = null;

    /**
     * Perform the actions.
     * 
     * @throws BuildException in case of an error
     * 
     * @see org.apache.tools.ant.Task#execute()
     */
    @Override
    public void execute() throws BuildException {

        if (file == null) {
            throw new BuildException("missing file attribute");
        }
        File f = new File(file);
        if (!f.isFile()) {
            throw new BuildException("'" + file + "' is not a plain file.");
        }
        if (!f.canRead()) {
            throw new BuildException("file '" + file + "' not readable.");
        }

        InputStream stream = null;

        try {
            stream = new FileInputStream(f);
            DocumentBuilder builder =
                    DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Element root = builder.parse(stream).getDocumentElement();
            NodeList nl = root.getElementsByTagName("testsuite");
            int len = nl.getLength();
            for (int i = 0; i < len; i++) {
                Node node = nl.item(i);
                NamedNodeMap attributes = node.getAttributes();
                String errors =
                        attributes.getNamedItem("errors").getNodeValue();
                String failures =
                        attributes.getNamedItem("failures").getNodeValue();
                if (!errors.equals("0") || !failures.equals("0")) {
                    String name =
                            attributes.getNamedItem("package").getNodeValue()
                                    + "."
                                    + attributes.getNamedItem("name")
                                        .getNodeValue().replaceFirst("Test$",
                                            "");
                    log(errors + "/" + failures + " " + name);
                }
            }

        } catch (IOException e) {
            throw new BuildException(e.toString());
        } catch (ParserConfigurationException e) {
            throw new BuildException(e.toString());
        } catch (SAXException e) {
            throw new BuildException(e.toString());
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    throw new BuildException(e.toString());
                }
            }
        }
    }

    /**
     * Getter for file.
     * 
     * @return the file
     */
    public String getFile() {

        return file;
    }

    /**
     * Setter for file.
     * 
     * @param file the file to set
     */
    public void setFile(String file) {

        this.file = file;
    }

}
