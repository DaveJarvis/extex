/*
 * Copyright (C) 2005-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.interpreter.type.dimen.Dimen;
import org.extex.interpreter.type.dimen.FixedDimen;

/**
 * Class for a font key.
 *
 * Font key is a collection several attributes. The central attribute
 * is the name. If the name is <code>null</code>, then the special null font
 * is meant.
 *
 * <p>properties:</p>TODO mgn: erg�nzen
 * <ul>
 * <li>size:         The size of the font</li>
 * <li>scale:        The scaling factr of the font.</li>
 * <li>letterspaced: ...</li>
 * <li>ligatures:    If <code>true</code>, the ligature information are used.</li>
 * <li>kerning:      If <code>true</code>, the kerning information are used.</li>
 * </ul>
 *
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */

public class FontKey implements Serializable {

    /**
     * The size of the font.
     */
    public static final String SIZE = "size";

    /**
     * The scaling factor of the font.
     */
    public static final String SCALE = "scale";

    /**
     * Use the ligature information of the font.
     */
    public static final String LIGATURES = "ligatures";

    /**
     * Use the kerning information of the font.
     */
    public static final String KERNING = "kerning";

    /**
     * The field <tt>serialVersionUID</tt>.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Map for boolean values.
     */
    private Map booleanMap;

    /**
     * Map for dimen values.
     */
    private Map dimenMap;

    /**
     * The name of the font.
     */
    private String name;

    /**
     * Map for string values.
     */
    private Map stringMap;

    /**
     * Create a new object (only in the same name space!).
     *
     * @param fk   The font key.
     */
    protected FontKey(final FontKey fk) {

        name = fk.getName();
        stringMap = new HashMap(fk.getStringMap());
        dimenMap = new HashMap(fk.getDimenMap());
        booleanMap = new HashMap(fk.getBooleanMap());
    }

    /**
     * Create a new object (only in the same name space!).
     *
     * If the name is a empty string, then the key for
     * the null font is returned; i.e. the name is treated as null.
     * 
     * @param theName   The name of the font.
     */
    protected FontKey(final String theName) {

        name = "".equals(theName) ? null : theName;

        stringMap = new HashMap();
        dimenMap = new HashMap();
        booleanMap = new HashMap();

    }

    /**
     * Returns the value for the key or <code>false</code>,
     * if no key exists in the map.
     * @param key The key.
     * @return Returns the value for the key.
     */
    public boolean getBoolean(final String key) {

        Boolean b = (Boolean) booleanMap.get(key);
        if (b == null) {
            return false;
        }
        return b.booleanValue();
    }

    /**
     * Returns the booleanMap.
     * @return Returns the booleanMap.
     */
    protected Map getBooleanMap() {

        return booleanMap;
    }

    /**
     * Returns the value for the key or <code>null</code>,
     * if no key exists in the map.
     * @param key The key.
     * @return Returns the value for the key.
     */
    public FixedDimen getDimen(final String key) {

        return (FixedDimen) dimenMap.get(key);
    }

    /**
     * Returns the dimenMap.
     * @return Returns the dimenMap.
     */
    protected Map getDimenMap() {

        return dimenMap;
    }

    /**
     * Returns the name.
     * @return Returns the name.
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the value for the key or <code>null</code>,
     * if no key exists in the map.
     * @param key The key.
     * @return Returns the value for the key.
     */
    public String getString(final String key) {

        return (String) stringMap.get(key);
    }

    /**
     * Returns the stringMap.
     * @return Returns the stringMap.
     */
    protected Map getStringMap() {

        return stringMap;
    }

    /**
     * Put an key values pair on the map.
     * @param theMap    The map.
     */
    public void put(final Map theMap) {

        Iterator it = theMap.keySet().iterator();

        while (it.hasNext()) {
            String key = (String) it.next();
            Object obj = theMap.get(key);
            if (obj instanceof String) {
                put(key, (String) obj);
            } else if (obj instanceof Dimen) {
                put(key, (Dimen) obj);
            } else if (obj instanceof Boolean) {
                put(key, ((Boolean) obj).booleanValue());
            }
        }
    }

    /**
     * Put an key values pair on the map.
     * @param key   The key.
     * @param value The value.
     */
    public void put(final String key, final boolean value) {

        booleanMap.put(key, new Boolean(value));
    }

    /**
     * Put an key values pair on the map.
     * @param key   The key.
     * @param value The value.
     */
    public void put(final String key, final FixedDimen value) {

        dimenMap.put(key, value);
    }

    /**
     * Put an key values pair on the map.
     * @param key   The key.
     * @param value The value.
     */
    public void put(final String key, final String value) {

        stringMap.put(key, value);
    }

    /**
     * @see java.lang.Object#toString()
     */
    public String toString() {

        StringBuffer buf = new StringBuffer(getName());

        Iterator it = dimenMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            FixedDimen d = getDimen(key);

            buf.append(" ").append(key).append("=");
            d.toString(buf);
        }

        it = stringMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            String s = getString(key);

            buf.append(" ").append(key).append("=").append(s);
        }

        it = booleanMap.keySet().iterator();
        while (it.hasNext()) {
            String key = (String) it.next();
            boolean b = getBoolean(key);

            buf.append(" ").append(key).append("=").append(b);
        }

        return buf.toString();
    }

}
