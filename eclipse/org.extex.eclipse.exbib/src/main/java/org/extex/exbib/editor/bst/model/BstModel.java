/*
 * Copyright (C) 2009-2010 The ExTeX Group and individual authors listed below
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

package org.extex.exbib.editor.bst.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class BstModel {

    /**
     * The field <tt>map</tt> contains the mapping from name to the named
     * entity.
     */
    private Map<String, BstModelNode> map;

    /**
     * The field <tt>list</tt> contains the ...
     */
    private List<BstModelNode> list;

    /**
     * The field <tt>array</tt> contains the ...
     */
    private BstModelNode[] array = null;

    /**
     * The field <tt>COMPERATOR</tt> contains the ...
     */
    private final static Comparator<BstModelNode> COMPERATOR =
            new Comparator<BstModelNode>() {

                @Override
                public int compare(BstModelNode o1, BstModelNode o2) {

                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            };

    /**
     * Creates a new object.
     * 
     */
    public BstModel() {

        list = new ArrayList<BstModelNode>();
        map = new HashMap<String, BstModelNode>();

        map.put("entry", new Command("entry", BstClass.COMMAND_ENTRY));
        map.put("execute", new Command("execute", BstClass.COMMAND_EXECUTE));
        map.put("function", new Command("function", BstClass.COMMAND_FUNCTION));
        map.put("integers", new Command("integers", BstClass.COMMAND_INTEGERS));
        map.put("iterate", new Command("iterate", BstClass.COMMAND_ITERATE));
        map.put("macro", new Command("macro", BstClass.COMMAND_MACRO));
        map.put("read", new Command("read", BstClass.COMMAND_READ));
        map.put("reverse", new Command("reverse", BstClass.COMMAND_REVERSE));
        map.put("sort", new Command("sort", BstClass.COMMAND_SORT));
        map.put("strings", new Command("strings", BstClass.COMMAND_STRINGS));

        map.put("+", new Builtin("+"));
        map.put("*", new Builtin("*"));
        map.put("-", new Builtin("-"));
        map.put(">", new Builtin(">"));
        map.put("<", new Builtin("<"));
        map.put("=", new Builtin("="));
        map.put(":=", new Builtin(":="));
        map.put("add.period$", new Builtin("add.period$"));
        map.put("call.type$", new Builtin("call.type$"));
        map.put("change.case$", new Builtin("change.case$"));
        map.put("chr.to.int$", new Builtin("chr.to.int$"));
        map.put("cite$", new Builtin("cite$"));
        map.put("duplicate$", new Builtin("duplicate$"));
        map.put("empty$", new Builtin("empty$"));
        map.put("format.name$", new Builtin("format.name$"));
        map.put("if$", new Builtin("if$"));
        map.put("int.to.chr$", new Builtin("int.to.chr$"));
        map.put("missing$", new Builtin("missing$"));
        map.put("newline$", new Builtin("newline$"));
        map.put("num.names$", new Builtin("num.names$"));
        map.put("pop$", new Builtin("pop$"));
        map.put("preamble$", new Builtin("preamble$"));
        map.put("purify$", new Builtin("purify$"));
        map.put("quote$", new Builtin("quote$"));
        map.put("skip$", new Builtin("skip$"));
        map.put("substring$", new Builtin("substring$"));
        map.put("swap$", new Builtin("swap$"));

        map.put("text.length$", new Builtin("text.length$"));
        map.put("text.prefix$", new Builtin("text.prefix$"));
        map.put("type$", new Builtin("type$"));
        map.put("warning$", new Builtin("warning$"));
        map.put("while$", new Builtin("while$"));
        map.put("width$", new Builtin("width$"));
        map.put("write$", new Builtin("write$"));

        map.put("crossref", new Field(-1, "crossref"));
        map.put("global.max$", new VarInteger(-1, "global.max$", false));
        map.put("entry.max$", new VarInteger(-1, "entry.max$", false));
        map.put("sort.key$", new VarString(-1, "sort.key$", true));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param command
     */
    public void add(Command command) {

        list.add(command);
        array = null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args
     */
    public void define(Field[] args) {

        for (Field field : args) {
            map.put(field.getName(), field);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param offset
     * @param local
     * @param args
     */
    public void define(int offset, boolean local, VarInteger[] args) {

        if (!local) {
            add(new Integers(offset, args));
        }
        for (VarInteger var : args) {
            map.put(var.getName(), var);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param marker
     * @param names
     */
    public void define(int offset, boolean local, VarString... names) {

        if (!local) {
            add(new Strings(offset, names));
        }
        for (VarString var : names) {
            map.put(var.getName(), var);
        }
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param function
     * 
     * @return
     */
    public BstModelNode defineFunction(Function function) {

        add(function);
        FunctionCall fct = function.getFct();
        return map.put(fct.getName(), fct);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param marker
     * @param name
     * 
     * @return
     */
    public BstModelNode defineMacro(Macro macro) {

        add(macro);
        VarMacro var = macro.getMacro();
        return map.put(var.getName(), var);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public BstClass getClassification(String name) {

        BstModelNode info = getNode(name);
        return info != null //
                ? info.getClassification()
                : BstClass.UNKNOWN;
    }

    /**
     * Getter for the command list.
     * 
     * @return the commands
     */
    public Object[] getElements() {

        if (array == null) {
            array = list.toArray(new BstModelNode[list.size()]);
        }
        return array;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name
     * @return
     */
    public BstModelNode getNode(String name) {

        BstModelNode info = map.get(name);
        if (info != null) {
            return info;
        }
        info = map.get(name.toLowerCase());
        return info != null && info instanceof Command ? info : null;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param prefix
     * 
     * @return
     */
    public BstModelNode[] getNodes(String prefix) {

        String s = prefix.toLowerCase();
        List<BstModelNode> list = new ArrayList<BstModelNode>();

        for (Map.Entry<String, BstModelNode> e : map.entrySet()) {
            BstModelNode node = e.getValue();
            if (node instanceof VarMacro) {
                continue;
            }
            String key = e.getKey();
            if (key.toLowerCase().startsWith(s)) {
                list.add(node);
            }
        }
        BstModelNode[] array = list.toArray(new BstModelNode[list.size()]);
        Arrays.sort(array, COMPERATOR);
        return array;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    public boolean hasChildren() {

        return !list.isEmpty();
    }
}
