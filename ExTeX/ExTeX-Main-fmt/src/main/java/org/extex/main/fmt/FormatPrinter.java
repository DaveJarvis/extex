/*
 * Copyright (C) 2006-2011 The ExTeX Group and individual authors listed below
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

package org.extex.main.fmt;

import java.io.BufferedInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.extex.core.UnicodeChar;
import org.extex.core.count.Count;
import org.extex.core.dimen.Dimen;
import org.extex.core.glue.Glue;
import org.extex.interpreter.loader.LoaderException;
import org.extex.scanner.type.token.ControlSequenceToken;
import org.extex.scanner.type.token.LeftBraceToken;
import org.extex.scanner.type.token.LetterToken;
import org.extex.scanner.type.token.MacroParamToken;
import org.extex.scanner.type.token.MathShiftToken;
import org.extex.scanner.type.token.OtherToken;
import org.extex.scanner.type.token.RightBraceToken;
import org.extex.scanner.type.token.SpaceToken;
import org.extex.scanner.type.token.Token;

/**
 * Load a format file and print it in a reasonable form.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public final class FormatPrinter {

    /**
     * This interface describes a function object for printing a data type.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
    */
    protected interface PrintRoutine {

        /**
         * Printing method.
         * 
         * @param out the output stream
         * @param obj the object to print
         */
        void print(PrintStream out, Object obj);
    }

  /**
     * The field {@code deep} contains the indicator for a deep dump.
     */
    private static boolean deep = true;

    /**
     * The field {@code listLimit} contains the limit to restrict the printing
     * of lists.
     */
    private static int listLimit = Integer.MAX_VALUE;

    /**
     * The field {@code mapLimit} contains the limit to restrict the printing
     * of map.
     */
    private static int mapLimit = Integer.MAX_VALUE;

    /**
     * The field {@code printerMap} contains the mapping from class name to
     * printing routine.
     */
    private static final Map<Class<?>, PrintRoutine> printerMap =
            new HashMap<Class<?>, PrintRoutine>();

    /**
     * The field {@code verbose} contains the verbosity indicator.
     */
    private static boolean verbose = false;

    static {
        printerMap.put(String.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" \"");
                out.print(obj);
                out.print("\"");
            }
        });
        printerMap.put(Long.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(((Long) obj).longValue());
            }
        });
        printerMap.put(Short.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(((Short) obj).shortValue());
            }
        });
        printerMap.put(Integer.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(((Integer) obj).intValue());
            }
        });
        printerMap.put(Boolean.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(((Boolean) obj).booleanValue() ? "t" : "nil");
            }
        });
        printerMap.put(Count.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(obj.toString());
            }
        });
        printerMap.put(Glue.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(obj.toString());
            }
        });
        printerMap.put(Dimen.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" ");
                out.print(obj.toString());
            }
        });
        printerMap.put(Character.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" '");
                char c = ((Character) obj).charValue();
                switch (c) {
                    case '\b':
                        out.print("\\b");
                        break;
                    case '\f':
                        out.print("\\f");
                        break;
                    case '\n':
                        out.print("\\n");
                        break;
                    case '\r':
                        out.print("\\r");
                        break;
                    case '\t':
                        out.print("\\t");
                        break;
                    default:
                        if (c < ' ') {
                            out.print("\\");
                            out.print(Integer.toOctalString(c));
                        } else {
                            out.print(c);
                        }
                }
                out.print("'");
            }
        });
        printerMap.put(UnicodeChar.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print(" \\u'");
                out.print(obj.toString());
                out.print("'");
            }
        });
        printerMap.put(OtherToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(o '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(LetterToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(l '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(SpaceToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(s '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(MacroParamToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(p '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(MathShiftToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(m '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(LeftBraceToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(lb '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(RightBraceToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                out.print("(rb '");
                out.print(((Token) obj).getChar().toString());
                out.print("')");
            }
        });
        printerMap.put(ControlSequenceToken.class, new PrintRoutine() {

            @Override
            public void print(PrintStream out, Object obj) {

                ControlSequenceToken cs = (ControlSequenceToken) obj;
                out.print("(cs ");
                UnicodeChar c = cs.getChar();
                out.print((c == null ? "nil" : "'" + c.toString() + "'"));
                out.print(" \"");
                out.print(cs.getNamespace());
                out.print("\" \"");
                out.print(cs.getName());
                out.print("\")");
            }
        });

    }

    /**
     * command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        PrintStream err = System.err;
        int i = 0;

        try {
            for (i = 0; i < args.length; i++) {
                String a = args[i];
                if (!a.startsWith("-")) {
                    break;
                } else if ("-help".startsWith(a)) {
                    err.println("FormatPrinter [options] <file>\n" + "");
                    // TODO
                    return;
                } else if ("-listlimit".startsWith(a)) {
                    if (++i >= args.length) {
                        err.println("*** Missing argument");
                        System.exit(-1);
                    }
                    listLimit = Integer.parseInt(args[i]);

                } else if ("-maplimit".startsWith(a)) {
                    if (++i >= args.length) {
                        err.println("*** Missing argument");
                        System.exit(-1);
                    }
                    mapLimit = Integer.parseInt(args[i]);

                } else if ("-narrow".startsWith(a)) {
                    deep = false;

                } else if ("-verbose".startsWith(a)) {
                    verbose = true;

                } else if ("-quiet".startsWith(a)) {
                    verbose = false;

                } else {
                    break;
                }
            }
        } catch (NumberFormatException e) {
            err.println("*** Non-numeric argument: " + args[i]);
            System.exit(-1);
        }

        if (i >= args.length) {
            err.println("*** Missing format file argument");
            System.exit(-1);
        }

        String file = args[i];
        File fmt = new File(file);

        if (!fmt.exists()) {
            err.println("*** Format file does not exist: " + file);
            System.exit(-1);
        } else if (!fmt.canRead()) {
            err.println("*** Format file is not readable: " + file);
            System.exit(-1);
        }

        PrintStream out = System.out;
        try {
            InputStream inStream =
                    new BufferedInputStream(new FileInputStream(fmt));

            for (int c = inStream.read(); c != '\n'; c = inStream.read()) {
                if (c < 0) {
                    throw new LoaderException("EOF");
                }
            }

            ObjectInputStream in =
                    new ObjectInputStream(new GZIPInputStream(inStream));

            for (Object obj = in.readObject(); obj != null; obj =
                    in.readObject()) {
                print(out, "\n", obj);

            }

        } catch (EOFException e) {
            // ignored
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }

        out.println();
    }

    /**
     * Print an object.
     * 
     * @param out the target stream
     * @param prefix the prefix to print after any new line
     * @param obj the object to print
     */
    @SuppressWarnings("unchecked")
    private static void print(PrintStream out, String prefix, Object obj) {

        if (obj == null) {
            out.print(" nil");
            return;
        }
        String pre = prefix + "  ";
        String pre2 = prefix + "    ";
        Class<?> c = obj.getClass();
        if (c.isArray()) {
            int len = Array.getLength(obj);
            out.print(" [");
            if (len > 0) {
                print(out, pre, Array.get(obj, 0));
                for (int i = 1; i < len; i++) {
                    out.print(pre2);
                    print(out, pre, Array.get(obj, i));
                }
            }
            out.print("]");
            // TODO
            return;
        } else if (obj instanceof Map) {
            Map<Object, Object> m = (Map<Object, Object>) obj;
            int size = m.size();
            if (size == 0) {
                out.print(" (map)");
            } else {
                out.print(" (map ");
                int limit = mapLimit;
                for (Object key : m.keySet()) {
                    if (limit-- <= 0) {
                        break;
                    }
                    out.print(pre);
                    print(out, pre, key);
                    out.print(" ");
                    print(out, pre, m.get(key));
                }
                if (limit <= 0) {
                    out.print(pre);
                    out.print("; ");
                    out.print(mapLimit);
                    out.print(" of ");
                    out.print(size);
                }
                out.print(")");
            }
            return;
        } else if (obj instanceof List) {
            List<Object> l = (List<Object>) obj;
            int size = l.size();
            if (size == 0) {
                out.print("(list)");
            } else {
                out.print(pre);
                out.print("(list ");
                int limit = listLimit;
                for (Object ob : l) {
                    if (limit-- <= 0) {
                        break;
                    }
                    print(out, pre2, ob);
                }
                if (limit <= 0) {
                    out.print(pre);
                    out.print("; ");
                    out.print(listLimit);
                    out.print(" of ");
                    out.print(size);
                }
                out.print(")");
            }
            return;
        }

        PrintRoutine pr = printerMap.get(c);
        if (pr != null) {
            pr.print(out, obj);
            return;
        }

        out.print(prefix);
        out.print("(");
        out.print(c.getName());

        boolean indirect = false;

        do {
            Field[] fields = c.getDeclaredFields();
            if (fields.length > 0) {
                if (indirect && verbose) {
                    out.print(prefix);
                    out.print("; from ");
                    out.print(c.getName());
                }

                for (int i = 0; i < fields.length; i++) {
                    Field f = fields[i];
                    if ((f.getModifiers() & (Modifier.STATIC | Modifier.TRANSIENT)) != 0) {
                        continue;
                    }
                    f.setAccessible(true);

                    try {
                        out.print(pre);
                        out.print("'");
                        out.print(f.getName());
                        print(out, pre2, f.get(obj));
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            c = c.getSuperclass();
            indirect = true;
        } while (deep && c != null);

        out.print(prefix);
        out.print(")");
    }

    /**
     * Creates a new object.
     * 
     */
    private FormatPrinter() {

    }

}
