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

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.jface.preference.FieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.FontFieldEditor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.extex.exbib.editor.Activator;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
*/
public class BstEditorPreferences extends FieldEditorPreferencePage
        implements
            IWorkbenchPreferencePage {

    /**
     * The constant {@code PREFERENCE_BASENAME} contains the ...
     */
    private static final String PREFERENCE_BASENAME =
            BstEditorPreferences.class.getName();

    public static final String PREFERENCE_FG_STRING =
            PREFERENCE_BASENAME + ".fg.string";

    public static final String PREFERENCE_BG_COMMENT =
            PREFERENCE_BASENAME + ".bg.comment";

    public static final String PREFERENCE_FG_COMMENT =
            PREFERENCE_BASENAME + ".fg.comment";

    public static final String PREFERENCE_FG_COMMAND =
            PREFERENCE_BASENAME + ".fg.command";

    public static final String PREFERENCE_FG_KEYWORD =
            PREFERENCE_BASENAME + ".fg.keyword";

    public static final String PREFERENCE_FG_QUOTE =
            PREFERENCE_BASENAME + ".fg.quote";

    public static final String PREFERENCE_FG_NUMBER =
            PREFERENCE_BASENAME + ".fg.number";

    public static final String PREFERENCE_FG_ERROR =
            PREFERENCE_BASENAME + ".fg.error";

    public static final String PREFERENCE_FG_DEFAULT =
            PREFERENCE_BASENAME + ".fg.default";

    public static final String PREFERENCE_FG_FUNCTION =
            PREFERENCE_BASENAME + ".fg.function";

    public static final String PREFERENCE_FG_FIELD =
            PREFERENCE_BASENAME + ".fg.field";

    public static final String PREFERENCE_FG_MACRO =
            PREFERENCE_BASENAME + ".fg.macro";

    public static final String PREFERENCE_FG_GLOBAL_STRING =
            PREFERENCE_BASENAME + ".fg.global.string";

    public static final String PREFERENCE_FG_LOCAL_STRING =
            PREFERENCE_BASENAME + ".fg.local.string";

    public static final String PREFERENCE_FG_GLOBAL_NUMBER =
            PREFERENCE_BASENAME + ".fg.global.number";

    public static final String PREFERENCE_FG_LOCAL_NUMBER =
            PREFERENCE_BASENAME + ".fg.local.number";

    static {
        IPreferenceStore store = Activator.getDefault().getPreferenceStore();
        PreferenceConverter.setDefault(store, PREFERENCE_FG_COMMENT,
            new RGB(63, 127, 95));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_STRING,
            new RGB(42, 0, 255));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_COMMAND,
            new RGB(127, 0, 85));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_KEYWORD,
            new RGB(127, 0, 85));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_QUOTE,
            new RGB(255, 128, 128));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_NUMBER,
            new RGB(127, 127, 255));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_ERROR,
            new RGB(255, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_FUNCTION,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_FIELD,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_GLOBAL_STRING,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_LOCAL_STRING,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_GLOBAL_NUMBER,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_LOCAL_NUMBER,
            new RGB(0, 0, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_MACRO,
            new RGB(128, 128, 0));
        PreferenceConverter.setDefault(store, PREFERENCE_FG_DEFAULT,
            new RGB(0, 0, 0));
    }

    private List<FieldEditor> fields = new ArrayList<FieldEditor>();

    /**
     * Creates a new object.
     * 
     */
    public BstEditorPreferences() {

        super(FieldEditorPreferencePage.GRID);
    }

@Override
    protected void addField(FieldEditor editor) {

        Assert.isNotNull(editor);
        fields.add(editor);
        super.addField(editor);
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param composite
     * @param key
     * @param label
     * @return
     */
    private ColorFieldEditor colorControl(Composite composite, String key,
            String label) {

        ColorFieldEditor cfe = new ColorFieldEditor(key, label, composite);
        cfe.setPreferenceStore(getPreferenceStore());
        cfe.load();
        return cfe;
    }

@Override
    protected void createFieldEditors() {

        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_COMMENT,
            "Comment foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_BG_COMMENT,
            "Comment background"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_COMMAND,
            "Command foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_KEYWORD,
            "Keyword foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_NUMBER,
            "Number foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_STRING,
            "String constant foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_FIELD,
            "Field foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_FUNCTION,
            "Function foreground"));
        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_MACRO,
            "Macro foreground"));
        addField(colorControl(getFieldEditorParent(),
            PREFERENCE_FG_LOCAL_NUMBER, "Local Number foreground"));
        addField(colorControl(getFieldEditorParent(),
            PREFERENCE_FG_LOCAL_STRING, "Local String foreground"));
        addField(colorControl(getFieldEditorParent(),
            PREFERENCE_FG_GLOBAL_NUMBER, "Global Number foreground"));
        addField(colorControl(getFieldEditorParent(),
            PREFERENCE_FG_GLOBAL_STRING, "Global String foreground"));

        addField(colorControl(getFieldEditorParent(), PREFERENCE_FG_ERROR,
            "Error foreground"));

        FontFieldEditor editor =
                new FontFieldEditor("xxxx", "Font", getFieldEditorParent());
        editor.setPreferenceStore(getPreferenceStore());
        editor.load();
        addField(editor);
    }

@Override
    public void dispose() {

        for (FieldEditor editor : fields) {
            editor.dispose();
        }

        super.dispose();
    }

@Override
    public void init(IWorkbench workbench) {

        setPreferenceStore(Activator.getDefault().getPreferenceStore());
    }

}
