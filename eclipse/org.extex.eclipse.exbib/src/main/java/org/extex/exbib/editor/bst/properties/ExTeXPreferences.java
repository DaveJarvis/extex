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

package org.extex.exbib.editor.bst.properties;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PathEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 1 $
 */
public class ExTeXPreferences extends FieldEditorPreferencePage
        implements
            IWorkbenchPreferencePage {

    /**
     * The field <tt>PREFERENCE_BASENAME</tt> contains the ...
     */
    private static final String PREFERENCE_BASENAME =
            ExTeXPreferences.class.getName();

    /**
     * The field <tt>fields</tt> contains the ...
     */
    private List<FieldEditor> fields = new ArrayList<FieldEditor>();

    /**
     * Creates a new object.
     * 
     */
    public ExTeXPreferences() {

        super(FieldEditorPreferencePage.GRID);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#addField(org.eclipse.jface.preference.FieldEditor)
     */
    @Override
    protected void addField(FieldEditor editor) {

        Assert.isNotNull(editor);
        fields.add(editor);
        super.addField(editor);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {

        IPreferenceStore store = getPreferenceStore();

        StringBuilder buffer = new StringBuilder(".");
        String inputs = determineTeXinputs();
        String pathSeparator = System.getProperty("path.separator");
        for (String s : inputs.split(pathSeparator)) {
            File f = new File(s);
            if (f.isDirectory()) {
                try {
                    String canonicalPath = f.getCanonicalPath();
                    buffer.append(pathSeparator);
                    buffer.append(canonicalPath);
                } catch (IOException e) {
                    Activator.getDefault().getLog().log(
                        new Status(Status.ERROR, Activator.PLUGIN_ID,
                            "Path entry " + s + " skipped", e));
                }
            } else {
                Activator.getDefault().getLog().log(
                    new Status(Status.ERROR, Activator.PLUGIN_ID, "Path entry "
                            + s + " ist not a directory: skipped"));
            }
        }
        store.setDefault(PREFERENCE_BASENAME + ".path", buffer.toString());
        PathEditor editor =
                new PathEditor(PREFERENCE_BASENAME + ".texinputs", "TEXINPUTS",
                    "Path to locate input files from", getFieldEditorParent());
        editor.setPreferenceStore(store);
        editor.load();
        addField(editor);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @return
     */
    private String determineTeXinputs() {

        String pathSeparator = System.getProperty("path.separator");
        String inputs;

        ProcessBuilder pb =
                new ProcessBuilder("kpsewhich", "-expand-path=$TEXMF");
        try {
            Process p = pb.start();
            StringBuilder list = new StringBuilder();
            LineNumberReader in = null;
            try {
                in = new LineNumberReader(
                    new InputStreamReader(p.getInputStream()));
                for (String line = in.readLine(); line != null; line =
                        in.readLine()) {
                    if (list.length() != 0) {
                        list.append(pathSeparator);
                    }
                    list.append(line);
                }
            } catch (IOException e) {
                Activator.getDefault().getLog().log(
                    new Status(Status.ERROR, Activator.PLUGIN_ID,
                        "Error while reading from process", e));
            } finally {
                try {
                    in.close();
                } catch (IOException e) {
                    Activator.getDefault().getLog().log(
                        new Status(Status.ERROR, Activator.PLUGIN_ID,
                            "Could not close stream", e));
                }
                p.destroy();
            }
            return list.toString();
        } catch (IOException e) {
            Activator.getDefault().getLog().log(
                new Status(Status.WARNING, Activator.PLUGIN_ID,
                    "Command could not be run", e));
        }

        inputs = System.getenv("TEXINPUTS");
        if (inputs != null) {
            return inputs;
        }
        return ".";
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#dispose()
     */
    @Override
    public void dispose() {

        for (FieldEditor editor : fields) {
            editor.dispose();
        }

        super.dispose();
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {

        setPreferenceStore(PlatformUI.getPreferenceStore());
    }

}
