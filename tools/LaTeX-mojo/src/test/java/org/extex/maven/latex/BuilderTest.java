/*
 * Copyright (C) 2008 The ExTeX Group and individual authors listed below
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package org.extex.maven.latex;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.extex.maven.latex.make.DependencyNet;
import org.extex.maven.latex.make.action.LaTeXAction;
import org.extex.maven.latex.make.artifact.Artifact;
import org.junit.Test;

/**
 * TODO gene: missing JavaDoc.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision$
 */
public class BuilderTest {

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test1() {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        new DependencyNet().print(w, "");
        assertEquals("", out.toString());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test2() throws Exception {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        DependencyNet builder = new DependencyNet();
        builder.addArtifact(new Artifact(new File("abc")));

        builder.print(w, "");
        assertEquals("abc\n", out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test3() throws Exception {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        DependencyNet builder = new DependencyNet();
        builder.addArtifact(new Artifact(new File("abc")));
        builder.getArtifact(new File("abc")).dependsOn(
            builder.getArtifact(new File("abc")));

        builder.print(w, "");
        assertEquals("abc\nabc\n\t-> abc\n", out.toString()
            .replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test4() throws Exception {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        DependencyNet builder = new DependencyNet();
        builder.addArtifact(new Artifact(new File("abc")));
        builder.getArtifact(new File("abc")).dependsOn(
            builder.getArtifact(new File("def")));

        builder.print(w, "");
        assertEquals("def\nabc\nabc\n\t-> def\n", //
            out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void test5() throws Exception {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        DependencyNet builder = new DependencyNet();
        builder.getArtifact(new File("abc")).dependsOn(
            builder.getArtifact(new File("def")));
        builder.getArtifact(new File("abc")).dependsOn(
            builder.getArtifact(new File("xyz")));
        builder.getArtifact(new File("xyz")).dependsOn(
            builder.getArtifact(new File("def")));
        builder.print(w, "");
        assertEquals("def\n" //
                + "xyz\n\t-> def\n" //
                + "abc\n\t-> def\n\t-> xyz\n", //
            out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws Exception in case of an error
     */
    @Test
    public void testRun1() throws Exception {

        DependencyNet builder = new DependencyNet();
        Artifact abc = builder.getArtifact(new File("abc"));
        abc.provideActions(new LaTeXAction(abc));
        Artifact def = builder.getArtifact(new File("def"));
        // def.dependsOn(new Exists());
        Artifact xyz = builder.getArtifact(new File("xyz"));
        // xyz.dependsOn(new Exists());

        abc.dependsOn(def);
        abc.dependsOn(xyz);
        xyz.dependsOn(def);

        builder.build(new File("abc").getAbsolutePath(), true);
    }

}
