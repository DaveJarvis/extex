/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.exbib.core.bst.code.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.extex.exbib.core.bst.BstProcessor;
import org.extex.exbib.core.bst.code.AbstractCode;
import org.extex.exbib.core.bst.token.impl.TString;
import org.extex.exbib.core.db.Entry;
import org.extex.exbib.core.exceptions.ExBibException;
import org.extex.exbib.core.io.Locator;
import org.extex.framework.configuration.Configurable;
import org.extex.framework.configuration.Configuration;
import org.extex.framework.configuration.exception.ConfigurationException;

/**
 * <logo>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X</logo> built-in function <code>purify$</code>
 * 
 * This function takes a string valued argument and performs the following
 * transformations:
 * 
 * <ul>
 * <li>Any known TeX macro at brace level 1 is expanded.</li>
 * <li>Any unknown TeX macro at brace level 1 is removed.</li>
 * <li>Any white-space character, the tilde <tt>~</tt>, and the hyphen
 * <tt>-</tt> are replaced by a single space character.</li>
 * <li>Any other non-alphanumeric characters are removed.</li>
 * </ul>
 * <p>
 * The known macros and their expansion text can be found in the following list:
 * </p>
 * <div style="margin-left:2em;"> <tt>\l</tt> &rarr; <tt>l</tt><br />
 * <tt>\L</tt> &rarr; <tt>L</tt><br />
 * <tt>\i</tt> &rarr; <tt>i</tt><br />
 * <tt>\j</tt> &rarr; <tt>j</tt><br />
 * <tt>\o</tt> &rarr; <tt>o</tt><br />
 * <tt>\O</tt> &rarr; <tt>O</tt><br />
 * <tt>\aa</tt> &rarr; <tt>a</tt><br />
 * <tt>\AA</tt> &rarr; <tt>A</tt><br />
 * <tt>\ss</tt> &rarr; <tt>ss</tt><br />
 * <tt>\oe</tt> &rarr; <tt>oe</tt><br />
 * <tt>\OE</tt> &rarr; <tt>OE</tt><br />
 * <tt>\ae</tt> &rarr; <tt>ae</tt><br />
 * <tt>\AE</tt> &rarr; <tt>AE</tt><br />
 * </div>
 * 
 * <img src="doc-files/purify.png"/>
 * 
 * <p>
 * The following example is taken from <tt>alpha.bst</tt>:
 * </p>
 * 
 * <pre>
 * FUNCTION {sortify}
 * { purify$
 *   "l" change.case$
 * }
 * </pre>
 * 
 * <hr />
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X documentation:
 * <dt>
 * <dd>Pops the top (string) literal, removes non alphanumeric characters except
 * for white-space characters and hyphens and ties (these all get converted to a
 * space), removes certain alphabetic characters contained in the control
 * sequences associated with a ``macro character'', and pushes the resulting
 * string.</dd>
 * </dl>
 * 
 * <dl>
 * <dt>B<small>IB</small><span style="margin-left: -0.15em;" >T</span><span
 * style=
 * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
 * >e</span>X web documentation:</dt>
 * <dd>The <code>built_in</code> function <code>purify$</code> pops the top
 * (string) literal, removes non alphanumeric characters except for
 * <code>white_space</code> and <code>sep_char</code> characters (these get
 * converted to a <code>space</code>) and removes certain alphabetic characters
 * contained in the control sequences associated with a macro character, and
 * pushes the resulting string. If the literal isn't a string, it complains and
 * pushes the null string.</dd>
 * </dl>
 * 
 * 
 * <h3>Configuration</h3>
 * <p>
 * The configuration can take embedded elements with the name <tt>map</tt> to
 * specify the mapping of known macros. The attribute <tt>name</tt> names the
 * macro &ndash; without leading backslash. The body contains the replacement
 * text.
 * </p>
 * <p>
 * Initially the mapping contains the known macros shown above. Specified
 * mappings are added to the predefined list. older mappings are overwritten by
 * newer ones. If you want to remove the predefined mappings before starting to
 * define new ones you can use the attribute <tt>clear</tt>. If it is present
 * &ndash; no matter what its value may be &ndash; the predefined mappings re
 * discarded.
 * </p>
 * 
 * <pre>
 *  &lt;function name="purify$"
 *           class="org.extex.exbib.core.bst.code.impl.Purify"
 *           clear="true"&gt;
 *    &lt;map name="ae"&gt;ae&lt;/map&gt;
 *    &lt;map name="AE"&gt;AE&lt;/map&gt;
 *    &lt;map name="oe"&gt;oe&lt;/map&gt;
 *    &lt;map name="OE"&gt;OE&lt;/map&gt;
 *    &lt;map name="ss"&gt;ss&lt;/map&gt;
 *    &lt;map name="i"&gt;i&lt;/map&gt;
 *    &lt;map name="j"&gt;j&lt;/map&gt;
 *    &lt;map name="l"&gt;l&lt;/map&gt;
 *    &lt;map name="L"&gt;L&lt;/map&gt;
 *    &lt;map name="aa"&gt;a&lt;/map&gt;
 *    &lt;map name="AA"&gt;A&lt;/map&gt;
 *    &lt;map name="o"&gt;o&lt;/map&gt;
 *    &lt;map name="O"&gt;O&lt;/map&gt;
 *  &lt;/function&gt;
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class Purify extends AbstractCode implements Configurable {

    /**
     * The field <tt>instance</tt> contains the instance for the static method.
     */
    private static Purify instance = null;

    /**
     * Purify the string according to the definition of B<small>IB</small><span
     * style="margin-left: -0.15em;" >T</span><span style=
     * "text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"
     * >e</span>X.
     * 
     * @param s the argument to purify
     * 
     * @return the purified string
     */
    public static String purify(String s) {

        if (instance == null) {
            instance = new Purify();
        }

        return instance.purifyIt(s);
    }

    /**
     * The field <tt>macro</tt> contains the mapping of macro characters.
     */
    private final Map<String, String> macro = new HashMap<String, String>();

    /**
     * Create a new object.
     */
    public Purify() {

        this("");
    }

    /**
     * Creates a new object.
     * 
     * @param name the function name in the processor context
     */
    public Purify(String name) {

        super(name);
        macro.put("l", "l");
        macro.put("L", "L");
        macro.put("i", "i");
        macro.put("j", "j");
        macro.put("o", "o");
        macro.put("O", "O");
        macro.put("aa", "a");
        macro.put("AA", "A");
        macro.put("ss", "ss");
        macro.put("oe", "oe");
        macro.put("OE", "OE");
        macro.put("ae", "ae");
        macro.put("AE", "AE");
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.framework.configuration.Configurable#configure(org.extex.framework.configuration.Configuration)
     */
    @Override
    public void configure(Configuration config) throws ConfigurationException {

        if (config == null) {
            return;
        }
        if (config.getAttribute("clear") != null) {
            macro.clear();
        }
        Iterator<Configuration> it = config.iterator("macro");
        while (it.hasNext()) {
            Configuration cfg = it.next();
            String s = cfg.getValue();
            macro.put(s, s);
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.core.bst.code.AbstractCode#execute(BstProcessor,
     *      org.extex.exbib.core.db.Entry, org.extex.exbib.core.io.Locator)
     */
    @Override
    public void execute(BstProcessor processor, Entry entry, Locator locator)
            throws ExBibException {

        String value = processor.popString(locator).getValue();
        processor.push(new TString(purifyIt(value), locator));
    }

    /**
     * Apply the core functionality of this BST code outside the processor
     * context.
     * 
     * @param value the value to purify
     * 
     * @return the purified value
     */
    public String purifyIt(String value) {

        StringBuilder sb = new StringBuilder(value);
        int level = 0;

        for (int i = 0; i < sb.length();) {
            char c = sb.charAt(i);

            if (Character.isWhitespace(c) || c == '-' || c == '~') {
                sb.setCharAt(i++, ' ');
            } else if (c == '{') {
                sb.deleteCharAt(i);
                level++;

                if (level == 1 && i < sb.length() - 1 && sb.charAt(i) == '\\') {
                    int j = i;
                    i++;

                    while (i < sb.length() && Character.isLetter(sb.charAt(i))) {
                        i++;
                    }

                    String ctrl = macro.get(sb.substring(j + 1, i));

                    if (ctrl == null) {
                        sb.delete(j, i);
                        i = j;
                    } else {
                        sb.replace(j, i, ctrl);
                        i = j + ctrl.length();
                    }
                }
            } else if (c == '}') {
                sb.deleteCharAt(i);
                level--;
            } else if (!Character.isLetterOrDigit(c)) {
                sb.deleteCharAt(i);
            } else {
                i++;
            }
        }

        return sb.toString();
    }

}
