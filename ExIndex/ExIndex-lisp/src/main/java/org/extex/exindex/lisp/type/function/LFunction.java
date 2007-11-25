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

package org.extex.exindex.lisp.type.function;

import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.extex.exindex.lisp.LInterpreter;
import org.extex.exindex.lisp.exception.LException;
import org.extex.exindex.lisp.exception.LMissingArgumentsException;
import org.extex.exindex.lisp.exception.LUndefinedFlagException;
import org.extex.exindex.lisp.type.value.LSymbol;
import org.extex.exindex.lisp.type.value.LValue;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public abstract class LFunction {

    /**
     * The field <tt>flags</tt> contains the mapping of flag to specs.
     */
    private Map<String, Arg> flags = new HashMap<String, Arg>();

    /**
     * The field <tt>index</tt> contains the mapping of flag to argument
     * position.
     */
    private Map<String, Integer> index = new HashMap<String, Integer>();

    /**
     * The field <tt>method</tt> contains the evaluate method to invoke.
     */
    private Method method;

    /**
     * The field <tt>specs</tt> contains the specification of the arguments.
     */
    private Arg[] specs;

    /**
     * The field <tt>name</tt> contains the name of the function for error
     * messages.
     */
    private String name;

    /**
     * Creates a new object.
     * 
     * @param name the name of the function
     * @param args the argument descriptors
     * 
     * @throws NoSuchMethodException
     * @throws SecurityException
     */
    public LFunction(String name, Arg[] args)
            throws SecurityException,
                NoSuchMethodException {

        super();
        this.name = name;
        this.specs = args;
        Class<?>[] cargs = new Class[args.length + 1];
        int i = 1;
        cargs[0] = LInterpreter.class;
        for (Arg as : args) {
            String flag = as.getFlag();
            if (flag != null) {
                flags.put(flag, as);
                index.put(flag, Integer.valueOf(i));
            }
            cargs[i++] = as.getResultClass();
        }

        method = getClass().getMethod("evaluate", cargs);
    }

    /**
     * Evaluate an sexpression and return the result.
     * 
     * @param interpreter the interpreter
     * @param args the arguments
     * 
     * @return the result
     * 
     * @throws LException in case of an error
     */
    public LValue eval(LInterpreter interpreter, List<LValue> args)
            throws LException {

        Object[] arguments = new Object[specs.length + 1];
        arguments[0] = interpreter;
        int ai = 1;

        for (int i = 0; i < specs.length; i++) {
            Arg spec = specs[i];
            if (spec.getFlag() != null) {
                arguments[i + 1] = spec.getDefault();
            } else if (ai >= args.size()) {
                throw new LMissingArgumentsException(name);
            } else if (spec.isQuoted()) {
                arguments[i + 1] = spec.validate(args.get(ai++));
            } else {
                arguments[i + 1] =
                        spec.validate(interpreter.eval(args.get(ai++)));
            }
        }

        while (ai < args.size()) {
            LValue arg = args.get(ai);
            if (!(arg instanceof LSymbol)) {
                // TODO gene: eval unimplemented
                throw new RuntimeException(name + " unimplemented");
            }
            String argName = ((LSymbol) arg).getValue();
            Arg fct = flags.get(argName);
            if (fct == null) {
                throw new LUndefinedFlagException(argName, name);
            }
            ai = fct.parse(args, ai + 1, //
                arguments, index.get(argName).intValue());
        }

        try {
            return (LValue) method.invoke(this, arguments);
        } catch (IllegalArgumentException e) {
            throw new LException(e);
        } catch (IllegalAccessException e) {
            throw new LException(e);
        } catch (InvocationTargetException e) {
            throw new LException(e);
        }
    }

    /**
     * Print the function to a stream.
     * 
     * @param stream the output stream
     */
    public void print(PrintStream stream) {

        stream.print("...");
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return "#" + name;
    }
}
