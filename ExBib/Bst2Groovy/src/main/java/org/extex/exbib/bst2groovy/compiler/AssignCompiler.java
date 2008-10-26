
package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;
import java.io.Writer;

import org.extex.exbib.bst2groovy.Bst2Groovy;
import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.LinkContainer;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GString;
import org.extex.exbib.bst2groovy.exception.UnknownVariableException;
import org.extex.exbib.core.bst.code.Code;
import org.extex.exbib.core.bst.code.MacroCode;
import org.extex.exbib.core.bst.token.Token;
import org.extex.exbib.core.bst.token.impl.TField;
import org.extex.exbib.core.bst.token.impl.TInteger;
import org.extex.exbib.core.bst.token.impl.TLocalInteger;
import org.extex.exbib.core.bst.token.impl.TLocalString;
import org.extex.exbib.core.bst.token.impl.TString;

/**
 * This class implements the analyzer for the := builtin.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class AssignCompiler implements Compiler {

    /**
     * This inner class is the expression for the setter of a field in the
     * target program.
     */
    private static class SetField extends VoidGCode {

        /**
         * The field <tt>name</tt> contains the name of the field.
         */
        private String name;

        /**
         * The field <tt>value</tt> contains the the new value.
         */
        private GCode value;

        /**
         * The field <tt>entry</tt> contains name of the entry variable.
         */
        private String entry;

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the filed
         * @param value the new value
         */
        public SetField(String entry, String name, GCode value) {

            this.entry = entry;
            this.name = name;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(entry);
            writer.write(".set(");
            writer.write(GString.translate(name));
            writer.write(", ");
            value.print(writer, prefix);
            writer.write(")");
        }
    }

    /**
     * This inner class is the expression for the setter of a global integer in
     * the target program.
     */
    private static class SetInteger extends VoidGCode {

        /**
         * The field <tt>name</tt> contains the name of the field.
         */
        private String name;

        /**
         * The field <tt>value</tt> contains the the new value.
         */
        private GCode value;

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetInteger(String name, GCode value) {

            this.name = name;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(GFunction.translate(name));
            writer.write(" = ");
            value.print(writer, prefix);
        }
    }

    /**
     * This inner class is the expression for the setter of a local integer in
     * the target program.
     */
    private static class SetLocalInteger extends VoidGCode {

        /**
         * The field <tt>name</tt> contains the name of the field.
         */
        private String name;

        /**
         * The field <tt>value</tt> contains the the new value.
         */
        private GCode value;

        /**
         * The field <tt>entry</tt> contains name of the entry variable.
         */
        private String entry;

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the filed
         * @param value the new value
         */
        public SetLocalInteger(String entry, String name, GCode value) {

            this.entry = entry;
            this.name = name;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(entry);
            writer.write(".setLocal(");
            writer.write(GString.translate(name));
            writer.write(", ");
            value.print(writer, prefix);
            writer.write(")");
        }
    }

    /**
     * This inner class is the expression for the setter of a local string in
     * the target program.
     */
    private static class SetLocalString extends VoidGCode {

        /**
         * The field <tt>name</tt> contains the name of the field.
         */
        private String name;

        /**
         * The field <tt>value</tt> contains the the new value.
         */
        private GCode value;

        /**
         * The field <tt>entry</tt> contains name of the entry variable.
         */
        private String entry;

        /**
         * Creates a new object.
         * 
         * @param entry the name of the variable for the entry
         * @param name the name of the filed
         * @param value the new value
         */
        public SetLocalString(String entry, String name, GCode value) {

            this.entry = entry;
            this.name = name;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(entry);
            writer.write(".setLocal(");
            writer.write(GString.translate(name));
            writer.write(", ");
            value.print(writer, prefix);
            writer.write(")");
        }
    }

    /**
     * This inner class is the expression for the setter of a global string in
     * the target program.
     */
    private static class SetString extends VoidGCode {

        /**
         * The field <tt>name</tt> contains the name of the field.
         */
        private String name;

        /**
         * The field <tt>value</tt> contains the the new value.
         */
        private GCode value;

        /**
         * Creates a new object.
         * 
         * @param name the name of the filed
         * @param value the new value
         */
        public SetString(String name, GCode value) {

            this.name = name;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.exbib.bst2groovy.data.GCode#print(java.io.Writer,
         *      java.lang.String)
         */
        public void print(Writer writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(GFunction.translate(name));
            writer.write(" = ");
            value.print(writer, prefix);
        }
    }

    /**
     * The field <tt>bst2Groovy</tt> contains the reference to the compiler.
     */
    private final Bst2Groovy bst2Groovy;

    /**
     * Creates a new object.
     * 
     * @param bst2Groovy
     */
    public AssignCompiler(Bst2Groovy bst2Groovy) {

        this.bst2Groovy = bst2Groovy;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode q = state.pop();
        GCode value = state.pop();
        if (!(q instanceof GQuote)) {
            throw new UnknownVariableException(q.toString());
        }
        String v = ((GQuote) q).getToken().getValue();
        Code f = this.bst2Groovy.getFunction(v);

        if (f instanceof Token) {
            makeAssignment((Token) f, entryRefernce, state, value, v);
        } else if (f instanceof MacroCode) {
            makeAssignment(((MacroCode) f).getToken(), entryRefernce, state,
                value, v);
        } else {
            throw new UnknownVariableException(v);
        }
    }

    /**
     * Analyze a token and generate a setter accordingly.
     * 
     * @param t the token
     * @param entry the entry reference
     * @param code the code
     * @param value the value
     * @param v the variable
     */
    private void makeAssignment(Token t, EntryRefernce entry,
            ProcessorState code, GCode value, String v) {

        if (t instanceof TField) {
            code.add(new SetField(entry.getName(), v, value));
        } else if (t instanceof TString) {
            code.add(new SetString(v, value));
        } else if (t instanceof TInteger) {
            code.add(new SetInteger(v, value));
        } else if (t instanceof TLocalString) {
            code.add(new SetLocalString(entry.getName(), v, value));
        } else if (t instanceof TLocalInteger) {
            code.add(new SetLocalInteger(entry.getName(), v, value));
        } else {
            throw new UnknownVariableException(v);
        }
    }
}
