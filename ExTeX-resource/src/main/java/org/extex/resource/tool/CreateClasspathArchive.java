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

package org.extex.resource.tool;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;

/**
 * Create a jar archive with the toc index for the
 * <code>ClasspathArchiveFinder</code>.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class CreateClasspathArchive {

    /**
     * The file buffer.
     */
    private static final int BUFFERSIZE = 0xffff;

    /**
     * parameter.
     */
    private static final int PARAMETER = 1;

    /**
     * The main method.
     * 
     * @param args the command line
     * @throws Exception if an error occurred.
     */
    public static void main(String[] args) throws Exception {

        CreateClasspathArchive cca = new CreateClasspathArchive();

        if (args.length < PARAMETER) {
            cca.getLogger().severe(
                cca.getLocalizer().format("CreateClasspathArchive.Call"));
            System.exit(1);
        }

        cca.doIt(args[0], args[1]);

    }

    /**
     * The base directory.
     */
    private File basedir;

    /**
     * The console handler.
     */
    private Handler consoleHandler;

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated with
     * a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory
        .getLocalizer(CreateClasspathArchive.class);

    /**
     * The field <tt>logger</tt> contains the logger currently in use.
     */
    private Logger logger;

    /**
     * The toc index properties.
     */
    private Properties tocIndexProps;


    public CreateClasspathArchive() {

        // logger
        logger = Logger.getLogger(getClass().getName());
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.ALL);

        consoleHandler = new ConsoleHandler();
        // consoleHandler.setFormatter(new LogFormatter());
        consoleHandler.setLevel(Level.INFO);
        logger.addHandler(consoleHandler);

        localizer = LocalizerFactory.getLocalizer(getClass());

    }

    /**
     * Add a file to the archive.
     * 
     * @param out The output.
     * @param f The file object.
     * @param name The name of the entry.
     * @throws IOException if an IO-error occurred.
     */
    private void addFile(JarOutputStream out, File f, String name)
            throws IOException {

        // source
        BufferedInputStream in =
                new BufferedInputStream(new FileInputStream(f), BUFFERSIZE);

        out.putNextEntry(new JarEntry(name));

        int bytesRead;
        byte[] buffer = new byte[BUFFERSIZE];

        // copy
        while ((bytesRead = in.read(buffer)) != -1) {
            out.write(buffer, 0, bytesRead);
        }

        in.close();
        out.closeEntry();
    }

    /**
     * Recursive procedure to traverse a directory tree and collect the files
     * for the index.
     * 
     * @param file the current file to consider
     */
    private void collect(File file) {

        String f = file.getAbsolutePath();
        f =
                f.substring(basedir.getAbsolutePath().length()).replaceAll(
                    "^/", "");

        if (file.isDirectory()) {
            File[] list = file.listFiles();
            for (int i = 0; i < list.length; i++) {
                collect(list[i]);
            }
        } else if (file.isFile()) {
            tocIndexProps.setProperty(file.getName(), f);
        }
    }

    /**
     * Create the jar archive.
     * 
     * @param jarname The name of the jar archive
     * @param tocindex The toc index file
     * 
     * @throws FileNotFoundException in case the file has not been found
     * @throws IOException if an I/O error occurs
     */
    private void createJarFile(String jarname, File tocindex)
            throws FileNotFoundException,
                IOException {

        JarOutputStream out =
                new JarOutputStream(new BufferedOutputStream(
                    new FileOutputStream(jarname), BUFFERSIZE));

        // tocindex
        addFile(out, tocindex, "toc.index");

        // files
        Enumeration<Object> e = tocIndexProps.keys();
        while (e.hasMoreElements()) {
            String key = e.nextElement().toString();
            String value = tocIndexProps.getProperty(key);
            File f = new File(basedir + File.separator + value);
            addFile(out, f, value);
            getLogger().info(
                getLocalizer().format("CreateClasspathArchive.adding",
                    f.getAbsolutePath()));

        }

        out.close();
    }

    /**
     * Create the toc index file.
     * 
     * @param dir The directory
     * @return Returns the file handle for the toc index.
     * @throws IOException if an IO-error occurred.
     */
    private File createTocindex(String dir) throws IOException {

        tocIndexProps = new Properties();
        File tocindex = File.createTempFile("toc", ".index");

        FileOutputStream tocIndexOut = new FileOutputStream(tocindex);
        basedir = new File(dir);
        collect(basedir);
        tocIndexProps.store(tocIndexOut,
            getLocalizer().format("CreateClasspathArchive.created"));
        tocIndexOut.close();
        return tocindex;
    }

    /**
     * Do it!
     * 
     * @param dir The directory to read.
     * @param jarname The name of the jar file.
     * @throws IOException if a IO-error occurred.
     */
    public void doIt(String jarname, String dir) throws IOException {

        getLogger().info(
            getLocalizer().format("CreateClasspathArchive.start", dir));
        File tocindex = createTocindex(dir);

        createJarFile(jarname, tocindex);

        if (!tocindex.delete()) {
            getLogger().info(
                getLocalizer().format("CreateClasspathArchive.not.deleted",
                    tocindex.toString()));
        }
        getLogger().info(
            getLocalizer().format("CreateClasspathArchive.finished", jarname));
    }

    /**
     * Getter for localizer.
     * 
     * @return Returns the localizer.
     */
    public Localizer getLocalizer() {

        return localizer;
    }

    /**
     * Getter for logger.
     * 
     * @return Returns the logger.
     */
    public Logger getLogger() {

        return logger;
    }

}
