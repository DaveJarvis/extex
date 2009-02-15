
package org.extex.maven.latex;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.maven.plugin.logging.Log;

/**
 * This class is a handler which passes the log requests to an underlying Maven
 * Log.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public final class LogAdaptorHandler extends Handler {

    /**
     * The field <tt>log</tt> contains the maven log.
     */
    private Log log;

    /**
     * Creates a new object.
     * 
     * @param log the maven log
     */
    LogAdaptorHandler(Log log) {

        this.log = log;
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.logging.Handler#close()
     */
    @Override
    public void close() throws SecurityException {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.logging.Handler#flush()
     */
    @Override
    public void flush() {

        // nothing to do
    }

    /**
     * {@inheritDoc}
     * 
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(LogRecord record) {

        Level level = record.getLevel();
        int levelValue = level.intValue();

        if (levelValue >= Level.SEVERE.intValue()) {
            log.error(record.getMessage());
        } else if (levelValue >= Level.WARNING.intValue()) {
            log.warn(record.getMessage());
        } else if (levelValue >= Level.INFO.intValue()) {
            log.info(record.getMessage());
        } else {
            log.debug(record.getMessage());
        }
    }
}
