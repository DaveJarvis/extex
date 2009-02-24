
package org.extex.builder.maven;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.apache.maven.plugin.logging.Log;

/**
 * This class is a handler which passes the log requests to an underlying Maven
 * {@link Log}
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
    public void close() {

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
     * Publish a <tt>LogRecord</tt>. If the log level of the log record is less
     * than the log level of the handler then the record is discarded. The
     * mapping of the log levels is the following:
     * <dl>
     * <dt>SEVERE</dt>
     * <dd>is mapped to {@linkplain Log#error(CharSequence) error()}</dd>
     * <dt>WARNING</dt>
     * <dd>is mapped to {@linkplain Log#warn(CharSequence) warn()}</dd>
     * <dt>INFO</dt>
     * <dd>is mapped to {@linkplain Log#info(CharSequence) info()}</dd>
     * <dt>others</dt>
     * <dd>are mapped to {@linkplain Log#debug(CharSequence) debug()}</dd>
     * </dl>
     * 
     * {@inheritDoc}
     * 
     * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
     */
    @Override
    public void publish(LogRecord record) {

        Level level = record.getLevel();
        int levelValue = level.intValue();
        if (levelValue < getLevel().intValue()) {
            return;
        } else if (levelValue >= Level.SEVERE.intValue()) {
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
