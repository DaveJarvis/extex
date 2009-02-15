package org.extex.maven.latex.make.artifact.latex;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class LatexReader extends PushbackReader {

    /**
     * The field <tt>atLetter</tt> contains the indicator to treat @ as
     * letter.
     */
    private boolean atLetter = false;

    /**
     * Getter for the atLetter.
     * 
     * @return the atLetter
     */
    public boolean isAtLetter() {

        return atLetter;
    }

    /**
     * Setter for the atLetter.
     * 
     * @param atLetter the atLetter to set
     */
    public void setAtLetter(boolean atLetter) {

        this.atLetter = atLetter;
    }

    /**
     * Creates a new object.
     * 
     * @param in
     */
    public LatexReader(Reader in) {

        super(in, 1);
    }

    /**
     * Scan a block with matching braces or a single character if the
     * opening brace is not found.
     * 
     * @return the value of the block
     * 
     * @throws IOException in case of an I/O error
     */
    public String scanBlock() throws IOException {

        StringBuilder buffer = new StringBuilder();

        int c = scanNext();
        if (c < 0) {
            return null;
        } else if (c == '{') {
            int level = 1;
            for (c = read(); c >= 0; c = read()) {
                if (c == '{') {
                    level++;
                } else if (c == '}') {
                    level--;
                    if (level == 0) {
                        break;
                    }
                } else if (c == '\\') {
                    buffer.append((char) c);
                    c = read();
                    if (c < 0) {
                        return null;
                    }
                }
                buffer.append((char) c);
            }
        } else {
            buffer.append((char) c);
        }
        return buffer.toString();
    }

    /**
     * Scan to the next control sequence.
     * 
     * @return the next control sequence
     * 
     * @throws IOException in case of an I/O error
     */
    public String scanControlSequence() throws IOException {

        int c;
        for (c = read(); c != '\\';) {
            if (c < 0) {
                return null;
            } else if (c == '%') {
                unread(c);
                c = scanNext();
            } else {
                c = read();
            }
        }

        StringBuilder buffer = new StringBuilder("\\");

        c = read();
        if (c < 0) {
            return null;
        }

        buffer.append((char) c);

        if (Character.isLetter(c) || (c == '@' && atLetter)) {

            for (c = read(); Character.isLetter(c)
                    || (c == '@' && atLetter); c = read()) {
                buffer.append((char) c);
            }

            if (c >= 0) {
                unread(c);
            }
        }

        return buffer.toString();
    }

    /**
     * Scan to the next non-space character. On the tour comments are
     * consumed as well.
     * 
     * @return the next non-space character
     * 
     * @throws IOException in case of an I/O error
     */
    int scanNext() throws IOException {

        int c = read();

        for (;;) {
            if (c == '%') {
                do {
                    c = read();
                } while (c >= 0 && c != '\n' && c != '\r');
            } else if (!Character.isWhitespace(c)) {
                return c;
            }
        }
    }

    /**
     * Scan an optional argument in brackets.
     * 
     * @return the value of the option
     * 
     * @throws IOException in case of an I/O error
     */
    public String scanOption() throws IOException {

        StringBuilder buffer = new StringBuilder();

        int c = scanNext();
        if (c < 0) {
            return null;
        } else if (c == '[') {
            int level = 0;
            for (c = read(); c >= 0; c = read()) {
                if (c == '{') {
                    level++;
                } else if (c == '}') {
                    level--;
                } else if (c == ']' && level == 0) {
                    break;
                } else if (c == '\\') {
                    buffer.append((char) c);
                    c = read();
                    if (c < 0) {
                        return null;
                    }
                }
                buffer.append((char) c);
            }
        } else {
            buffer.append((char) c);
        }
        return buffer.toString();
    }

    /**
     * Consume all characters until the end text is found.
     * 
     * @param text the end text. It should not be empty since the behavior
     *        of the method is undefined in this case
     * 
     * @throws IOException in case of an I/O error
     */
    public void scanTo(String text) throws IOException {

        char[] ca = text.toCharArray();
        int i = 0;
        for (;;) {
            int c = read();
            if (c < 0) {
                return;
            } else if (c == ca[i]) {
                i++;
                if (i >= ca.length) {
                    return;
                }
            } else {
                i = 0;
            }
        }
    }

}