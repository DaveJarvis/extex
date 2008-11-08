
package org.extex.exbib.bst2groovy.compiler;

import java.io.IOException;

import org.extex.exbib.bst2groovy.Compiler;
import org.extex.exbib.bst2groovy.data.GCode;
import org.extex.exbib.bst2groovy.data.VoidGCode;
import org.extex.exbib.bst2groovy.data.processor.EntryRefernce;
import org.extex.exbib.bst2groovy.data.processor.Evaluator;
import org.extex.exbib.bst2groovy.data.processor.ProcessorState;
import org.extex.exbib.bst2groovy.data.types.GFunction;
import org.extex.exbib.bst2groovy.data.types.GQuote;
import org.extex.exbib.bst2groovy.data.types.GStringConstant;
import org.extex.exbib.bst2groovy.exception.UnknownVariableException;
import org.extex.exbib.bst2groovy.io.CodeWriter;
import org.extex.exbib.bst2groovy.linker.LinkContainer;
import org.extex.exbib.core.bst.FunctionContainer;
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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, entry, ".set(", GStringConstant
                .translate(name), ", ");
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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, GFunction.translate(name), " = ");
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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, entry, ".setLocal(", GStringConstant
                .translate(name), ", ");
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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix, entry, ".setLocal(", GStringConstant
                .translate(name), ", ");
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
         * @see org.extex.exbib.bst2groovy.data.GCode#print(CodeWriter,
         *      java.lang.String)
         */
        public void print(CodeWriter writer, String prefix) throws IOException {

            writer.write(prefix);
            writer.write(GFunction.translate(name));
            writer.write(" = ");
            value.print(writer, prefix);
        }
    }

    /**
     * The field <tt>functionContainer</tt> contains the reference to the
     * compiler.
     */
    private final FunctionContainer functionContainer;

    /**
     * Creates a new object.
     * 
     * @param functionContainer reference object for getting information
     */
    public AssignCompiler(FunctionContainer functionContainer) {

        this.functionContainer = functionContainer;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.exbib.bst2groovy.Compiler#evaluate(org.extex.exbib.bst2groovy.data.processor.EntryRefernce,
     *      org.extex.exbib.bst2groovy.data.processor.ProcessorState,
     *      org.extex.exbib.bst2groovy.data.processor.Evaluator,
     *      org.extex.exbib.bst2groovy.linker.LinkContainer)
     */
    public void evaluate(EntryRefernce entryRefernce, ProcessorState state,
            Evaluator evaluator, LinkContainer linkData) {

        GCode q = state.pop();
        GCode value = state.pop();
        if (!(q instanceof GQuote)) {
            throw new UnknownVariableException(q.toString());
        }
        String v = ((GQuote) q).getToken().getValue();
        Code f = functionContainer.getFunction(v);

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
