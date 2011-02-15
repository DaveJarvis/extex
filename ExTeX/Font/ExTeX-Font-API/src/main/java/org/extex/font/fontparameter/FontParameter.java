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

package org.extex.font.fontparameter;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.extex.core.UnicodeChar;
import org.extex.font.unicode.GlyphName;
import org.extex.util.xml.XmlHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

/**
 * Manage additional font parameters.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class FontParameter {

    /**
     * Class for the handler.
     */
    private class MyHandler extends XmlHandler {

        @Override
        public void startElement(String uri, String localName, String name,
                Attributes attributes) throws SAXException {

            super.startElement(uri, localName, name, attributes);

            if (hasParentElement("glyphname")) {
                if (isInElement("name")) {
                    String id = attributes.getValue("id");
                    String val = attributes.getValue("value");
                    int value = Integer.parseInt(val, 16);
                    UnicodeChar uc = UnicodeChar.get(value);
                    glyphmap.put(id, uc);
                    unicodemap.put(uc, id);
                }
            } else if (hasParentElement("parameter")) {
                if (isInElement("param")) {
                    String id = attributes.getValue("id");
                    String val = attributes.getValue("value");
                    int value = 0;
                    try {
                        value = Integer.parseInt(val);
                    } catch (NumberFormatException e) {
                        // ignore
                        value = 0;
                    }
                    fontDimen.put(id, value);
                }
            }
        }
    }

    /**
     * The map fr the font dimen values.
     */
    private Map<String, Integer> fontDimen = new HashMap<String, Integer>();

    /**
     * The glyph name map.
     */
    private Map<String, UnicodeChar> glyphmap =
            new HashMap<String, UnicodeChar>();

    /**
     * The Unicode map.
     */
    private Map<UnicodeChar, String> unicodemap =
            new HashMap<UnicodeChar, String>();

    /**
     * Use the class GlyphName, if the name/value not yet set (default).
     */
    private boolean useGlyphName = true;

    /**
     * Creates a new object.
     */
    public FontParameter() {

    }

    /**
     * Creates a new object.
     * 
     * @param in The input.
     * @throws IOException if a io-error occurred.
     */
    public FontParameter(InputStream in) throws IOException {

        parseXml(in);
    }

    /**
     * Returns the font dimen value, or <code>null</code>, if not found.
     * 
     * @param name The name of the value.
     * @return Returns the font dimen value, or <code>null</code>, if not
     *         found.
     */
    public Integer getFontDimen(String name) {

        return fontDimen.get(name);
    }

    /**
     * Returns the name of the glyph, or <code>null</code>, if not found.
     * 
     * @param uc the Unicode char.
     * @return the name of the glyph, or <code>null</code>, if not found.
     */
    public String getGlyphname(UnicodeChar uc) {

        String name = unicodemap.get(uc);
        if (useGlyphName && name == null) {
            try {
                name = GlyphName.getInstance().getGlyphname(uc);
            } catch (IOException e) {
                // ignore
                return null;
            }
        }
        return name;
    }

    /**
     * Returns the <code>UnicodeChar</code> for the glyph name or
     * <code>null</code>, if not found.
     * 
     * @param name The glyph name.
     * @return Returns the <code>UnicodeChar</code> for the glyph name or
     *         <code>null</code>, if not found.
     */
    public UnicodeChar getUnicode(String name) {

        UnicodeChar uc = glyphmap.get(name);
        if (useGlyphName && uc == null) {
            try {
                uc = GlyphName.getInstance().getUnicode(name);
            } catch (IOException e) {
                // ignore
                return null;
            }
        }
        return uc;
    }

    /**
     * Getter for useGlyphName.
     * 
     * @return the useGlyphName
     */
    public boolean isUseGlyphName() {

        return useGlyphName;
    }

    /**
     * Parse the xml file.
     * 
     * @param in The input.
     * @throws IOException if an io-error occurred.
     */
    private void parseXml(InputStream in) throws IOException {

        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            MyHandler handler = new MyHandler();
            handler.setUseWriter(false);

            parser.parse(in, handler);

        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }

    }

    /**
     * Setter for useGlyphName.
     * 
     * @param useGlyphName the useGlyphName to set
     */
    public void setUseGlyphName(boolean useGlyphName) {

        this.useGlyphName = useGlyphName;
    }

    /**
     * Getter for fontDimen.
     * 
     * @return the fontDimen
     */
    public Map<String, Integer> getFontDimen() {

        return fontDimen;
    }

}
