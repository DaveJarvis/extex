
package org.extex.ocpware;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.PrintStream;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.extex.ocpware.engine.OcpReader;
import org.extex.ocpware.engine.OcpReaderObserver;

/**
 * This class provides a main program to run an otp or ocp file on some input.
 * <p>
 * The program takes as one argument the name of the otp file:
 * </p>
 * 
 * <pre
 * class="CLI"> java org.extex.ocpware.OtpDebugger &lang;<i>file</i>&rang;
 * </pre>
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision:6007 $
 */
public final class OcpDebugger {

    /**
     * TODO gene: missing JavaDoc.
     * 
     * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
     * @version $Revision:6459 $
     */
    protected class Observer implements OcpReaderObserver {

        /**
         * The field <tt>trace</tt> contains the ...
         */
        private boolean trace = true;

        /**
         * TODO gene: missing JavaDoc
         * 
         * @param arg
         * @param opcode
         * @param reader
         * 
         * @return the repeat indicator
         * 
         * @throws IOException in case of an I/O error
         */
        public boolean cli(OcpReader reader, int opcode, int arg)
                throws IOException {

            out.print("\n--> ");
            out.flush();
            String line = in.readLine().trim().replaceAll("[ \t]+", " ");
            if ("".equals(line)) {
                return true;
            }
            String[] args = line.split("[ \t]");

            if ("help".startsWith(args[0])) {
                help(args);
            } else if ("status".startsWith(args[0])) {
                status(args);
            } else if ("quit".startsWith(args[0]) || "halt".startsWith(args[0])
                    || "stop".startsWith(args[0]) || "exit".startsWith(args[0])) {
                return false;
            } else if ("run".startsWith(args[0])) {

            } else if ("step".startsWith(args[0])) {

            } else if ("trace".startsWith(args[0])) {
                trace = Boolean.getBoolean(args.length == 1 ? "true" : args[1]);
            } else {
                out.println("unknown command: " + args[0]);
            }
            return true;
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.ocpware.engine.OcpReaderObserver#close(org.extex.ocpware.engine.OcpReader)
         */
        @Override
        public void close(OcpReader reader) {

            // TODO gene: close unimplemented
        }

        /**
         * {@inheritDoc}
         * 
         * @see org.extex.ocpware.engine.OcpReaderObserver#step(org.extex.ocpware.engine.OcpReader,
         *      int, int)
         */
        @Override
        public void step(OcpReader reader, int opcode, int arg) {

            boolean om;
            try {
                om = cli(reader, opcode, arg);
            } catch (IOException e) {
                // TODO gene: error handling unimplemented
                e.printStackTrace();
                throw new RuntimeException("unimplemented");
            }
            if (!om) {
                // TODO gene: step unimplemented
                throw new RuntimeException("unimplemented");
            }
        }
    }

    /**
     * This is the command line interface.
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        System.exit(main(args, System.in, System.out));
    }

    /**
     * Process the command line options and return the exit code. As side effect
     * an error message might be written to the error stream.
     * 
     * @param args the command line arguments
     * @param in the input stream
     * @param out the output stream
     * 
     * @return the exit code
     */
    public static int main(String[] args, InputStream in, PrintStream out) {

        OcpDebugger debugger = new OcpDebugger(in, out);

        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                // ...
            } else {
                debugger.setFile(args[i]);
            }
        }

        return debugger.commandLineInterface();
    }

    /**
     * The field <tt>bundle</tt> contains the resource bundle.
     */
    private ResourceBundle bundle;

    /**
     * The field <tt>in</tt> contains the input stream.
     */
    private LineNumberReader in;

    /**
     * The field <tt>obs</tt> contains the observer.
     */
    private Observer obs = new Observer();

    /**
     * The field <tt>out</tt> contains the output stream.
     */
    private PrintStream out;

    /**
     * Creates a new object.
     * 
     * @param in the input stream
     * @param out the output stream
     */
    public OcpDebugger(InputStream in, PrintStream out) {

        super();
        bundle = ResourceBundle.getBundle(OcpDebugger.class.getName());
        this.in = new LineNumberReader(new InputStreamReader(in));
        this.out = out;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return the exit code
     */
    private int commandLineInterface() {

        try {
            while (obs.cli(null, 0, 0)) {
                // revolve
            }
        } catch (IOException e) {
            return -1;
        }
        out.println("\nbye");
        return 0;
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the arguments
     */
    private void help(String[] args) {

        String key = (args.length == 1 ? "help" : "help." + args[1]);
        try {
            out.println(bundle.getString(key));
        } catch (MissingResourceException e) {
            try {
                out.println(MessageFormat.format(bundle.getString("no.help"),
                    (args.length == 1 ? "???" : args[1])));
            } catch (MissingResourceException ex) {
                out.println("*** help error: " + ex.getLocalizedMessage());
            }
        }

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param string
     */
    private void setFile(String string) {

        // TODO gene: setFile unimplemented

    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param args the arguments
     */
    private void status(String[] args) {

        // TODO gene: status unimplemented

    }

}
