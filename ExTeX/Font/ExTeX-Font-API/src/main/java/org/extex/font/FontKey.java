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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.extex.core.count.Count;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.core.glue.Glue;

/**
 * Class for a font key.
 * 
 * <p>
 * Font key is a collection several attributes. The central attribute is the
 * name. If the name is {@code null}, then the special null font is meant.
 * </p>
 * 
 * <p>
 * properties:
 * </p>
 * <ul>
 * <li>size: The size of the font</li>
 * <li>scale: The scaling factor of the font. If the scale factor is set, then
 * the size-parameter is ignored!</li>
 * <li>letterspaced: ...</li>
 * <li>language: The language for the font (e.g. for OpenType)</li>
 * <li>ligatures: If {@code true}, the ligature information are used.</li>
 * <li>kerning: If {@code true}, the kerning information are used.</li>
 * </ul>
 * 
 * <p>
 * Tags:
 * </p>
 * <p>
 * Each font can get several tags (e.g. OpenType: latn, kern)
 * </p>
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
*/

public class FontKey implements Serializable {

    /**
     * Use the kerning information of the font.
     */
    public static final String KERNING = "kerning";

    /**
     * The language for the font.
     */
    public static final String LANGUAGE = "language";

    /**
     * Use the letterspaced information of the font.
     */
    public static final String LETTERSPACE = "letterspaced";

    /**
     * Use the ligature information of the font.
     */
    public static final String LIGATURES = "ligatures";

    /**
     * The scaling factor of the font.
     */
    public static final String SCALE = "scale";

    /**
     * The field {@code serialVersionUID}.
     */
    private static final long serialVersionUID = 1L;

    /**
     * The size of the font.
     */
    public static final String SIZE = "size";

    /**
     * Map for boolean values.
     */
    private final Map<String, Boolean> booleanMap;

    /**
     * Map for count values.
     */
    private final Map<String, FixedCount> countMap;

    /**
     * Map for {@link Dimen} values.
     */
    private final Map<String, FixedDimen> dimenMap;

    /**
     * Map for {@link Glue} values.
     */
    private final Map<String, FixedGlue> glueMap;

    /**
     * The name of the font.
     */
    private final String name;

    /**
     * Map for string values.
     */
    private final Map<String, String> stringMap;

    /**
     * The tags (script, feature and language).
     */
    private final List<String> tags;

    /**
     * Create a new object (only in the same name space!).
     * 
     * @param fk The font key.
     */
    protected FontKey(FontKey fk) {

        name = fk.getName();
        stringMap = new HashMap<String, String>(fk.getStringMap());
        dimenMap = new HashMap<String, FixedDimen>(fk.getDimenMap());
        glueMap = new HashMap<String, FixedGlue>(fk.getGlueMap());
        countMap = new HashMap<String, FixedCount>(fk.getCountMap());
        booleanMap = new HashMap<String, Boolean>(fk.getBooleanMap());
        tags = new ArrayList<String>(fk.getTags());
    }

    /**
     * Create a new object (only in the same name space!).
     * 
     * If the name is a empty string, then the key for the null font is
     * returned; i.e. the name is treated as null.
     * 
     * @param theName The name of the font.
     */
    protected FontKey(String theName) {

        name = "".equals(theName) ? null : theName;

        stringMap = new HashMap<String, String>();
        dimenMap = new HashMap<String, FixedDimen>();
        glueMap = new HashMap<String, FixedGlue>();
        countMap = new HashMap<String, FixedCount>();
        booleanMap = new HashMap<String, Boolean>();
        tags = new ArrayList<String>();

    }

    /**
     * Add a list of tags.
     * 
     * @param theTags The list of tags.
     */
    protected void add(List<String> theTags) {

        tags.addAll(theTags);
    }

    /**
     * Check, if the font keys are equals.
     * 
     * @param k The font key for the check.
     * @return Returns {@code true}, if the keys are equals.
     */
    public boolean eq(FontKey k) {

        if (k == null) {
            return false;
        }
        if (!getName().equals(k.getName())) {
            return false;
        }

        for (String key : dimenMap.keySet()) {
            if (!getDimen(key).eq(k.getDimen(key))) {
                return false;
            }
        }

        for (String key : countMap.keySet()) {
            if (!getCount(key).eq(k.getCount(key))) {
                return false;
            }
        }

        for (String key : booleanMap.keySet()) {
            if (getBoolean(key) != k.getBoolean(key)) {
                return false;
            }
        }

        for (String key : glueMap.keySet()) {
            if (!getGlue(key).eq(k.getGlue(key))) {
                return false;
            }
        }

        for (String key : stringMap.keySet()) {
            if (!getString(key).equals(k.getString(key))) {
                return false;
            }
        }

        // tags
        if (k.getTags().size() != tags.size()) {
            return false;
        }
        for (String feat : tags) {
            if (!k.getTags().contains(feat)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the value for the key or {@code false}, if no key exists in
     * the map.
     * 
     * @param key The key.
     * @return Returns the value for the key.
     */
    public boolean getBoolean(String key) {

        Boolean b = booleanMap.get(key);
        if (b == null) {
            return false;
        }
        return b.booleanValue();
    }

    /**
     * Returns the booleanMap.
     * 
     * @return Returns the booleanMap.
     */
    protected Map<String, Boolean> getBooleanMap() {

        return booleanMap;
    }

    /**
     * Returns the value for the key or {@code null}, if no key exists in
     * the map.
     * 
     * @param key The key.
     * @return Returns the value for the key.
     */
    public FixedCount getCount(String key) {

        return countMap.get(key);
    }

    /**
     * Returns the countMap.
     * 
     * @return Returns the countMap.
     */
    protected Map<String, FixedCount> getCountMap() {

        return countMap;
    }

    /**
     * Returns the value for the key or {@code null}, if no key exists in
     * the map.
     * 
     * @param key The key.
     * @return Returns the value for the key.
     */
    public FixedDimen getDimen(String key) {

        return dimenMap.get(key);
    }

    /**
     * Returns the dimenMap.
     * 
     * @return Returns the dimenMap.
     */
    protected Map<String, FixedDimen> getDimenMap() {

        return dimenMap;
    }

    /**
     * Returns the value for the key or {@code null}, if no key exists in
     * the map.
     * 
     * @param key The key.
     * @return Returns the value for the key.
     */
    public FixedGlue getGlue(String key) {

        return glueMap.get(key);
    }

    /**
     * Returns the glueMap.
     * 
     * @return Returns the glueMap.
     */
    protected Map<String, FixedGlue> getGlueMap() {

        return glueMap;
    }

    /**
     * Returns the name.
     * 
     * @return Returns the name.
     */
    public String getName() {

        return name;
    }

    /**
     * Returns the value for the key or {@code null}, if no key exists in
     * the map.
     * 
     * @param key The key.
     * @return Returns the value for the key.
     */
    public String getString(String key) {

        return stringMap.get(key);
    }

    /**
     * Returns the stringMap.
     * 
     * @return Returns the stringMap.
     */
    protected Map<String, String> getStringMap() {

        return stringMap;
    }

    /**
     * Getter for the tags.
     * 
     * @return Returns the tags.
     */
    public List<String> getTags() {

        return new ArrayList<String>(tags);
    }

    /**
     * Check, if the key exists in the map.
     * 
     * @param key The key.
     * @return Returns {@code true}, if the key exists.
     */
    public boolean hasBoolean(String key) {

        Boolean b = booleanMap.get(key);
      return b != null;
    }

    /**
     * Check, if the key exists in the map.
     * 
     * @param key The key.
     * @return Returns {@code true}, if the key exists.
     */
    public boolean hasCount(String key) {

        FixedCount v = countMap.get(key);
      return v != null;
    }

    /**
     * Check, if the key exists in the map.
     * 
     * @param key The key.
     * @return Returns {@code true}, if the key exists.
     */
    public boolean hasDimen(String key) {

        FixedDimen v = dimenMap.get(key);
      return v != null;
    }

    /**
     * Check, if the key exists in the map.
     * 
     * @param key The key.
     * @return Returns {@code true}, if the key exists.
     */
    public boolean hasGlue(String key) {

        FixedGlue v = glueMap.get(key);
      return v != null;
    }

    /**
     * Check, if the key exists in the map.
     * 
     * @param key The key.
     * @return Returns {@code true}, if the key exists.
     */
    public boolean hasString(String key) {

        String v = stringMap.get(key);
        return v != null;
    }

    /**
     * Returns {@code true}, if the tag is set.
     * 
     * @param name The name of the tag.
     * @return Returns {@code true}, if the tag is set.
     */
    public boolean hasTag(String name) {

        return tags.contains(name);
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param theMap The map.
     */
    protected void put(Map<String, ?> theMap) {

        Iterator<String> it = theMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            Object obj = theMap.get(key);
            if (obj instanceof String) {
                put(key, (String) obj);
            } else if (obj instanceof Dimen) {
                put(key, (Dimen) obj);
            } else if (obj instanceof Glue) {
                put(key, (Glue) obj);
            } else if (obj instanceof Count) {
                put(key, (Count) obj);
            } else if (obj instanceof Boolean) {
                put(key, ((Boolean) obj).booleanValue());
            }
        }
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param key The key.
     * @param value The value.
     */
    protected void put(String key, boolean value) {

        booleanMap.put(key, Boolean.valueOf(value));
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param key The key.
     * @param value The value.
     */
    protected void put(String key, FixedCount value) {

        countMap.put(key, value);
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param key The key.
     * @param value The value.
     */
    protected void put(String key, FixedDimen value) {

        dimenMap.put(key, value);
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param key The key.
     * @param value The value.
     */
    protected void put(String key, FixedGlue value) {

        glueMap.put(key, value);
    }

    /**
     * Put an key values pair on the map.
     * 
     * @param key The key.
     * @param value The value.
     */
    protected void put(String key, String value) {

        stringMap.put(key, value);
    }

    /**
     * Returns the value of the {@code FontKey} as {@code String}. If
     * a value is {@code null}, then the text 'null' is returned.
     * 
     * @return the printable representation for this instance
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        StringBuilder buf = new StringBuilder(getName());

        Iterator<String> it = dimenMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            FixedDimen d = getDimen(key);

            buf.append(" ").append(key).append("=");
            if (d != null) {
                d.toString(buf);
            } else {
                buf.append("null");
            }
        }

        it = countMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            FixedCount c = getCount(key);

            buf.append(" ").append(key).append("=");
            if (c != null) {
                c.toString(buf);
            } else {
                buf.append("null");
            }
        }

        it = glueMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            FixedGlue g = getGlue(key);

            buf.append(" ").append(key).append("=");
            if (g != null) {
                buf.append(g.toString());
            } else {
                buf.append("null");
            }
        }

        it = stringMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            String s = getString(key);

            buf.append(" ").append(key).append("=");
            if (s != null) {
                buf.append(s);
            } else {
                buf.append("null");
            }
        }

        it = booleanMap.keySet().iterator();
        while (it.hasNext()) {
            String key = it.next();
            boolean b = getBoolean(key);

            buf.append(" ").append(key).append("=").append(b);
        }

        it = tags.iterator();
        while (it.hasNext()) {
            String key = it.next();
            buf.append(" ").append(key);
        }

        return buf.toString();
    }
}
