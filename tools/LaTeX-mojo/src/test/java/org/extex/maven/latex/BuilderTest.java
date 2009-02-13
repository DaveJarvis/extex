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

import org.extex.maven.latex.make.Artifact;
import org.extex.maven.latex.make.Net;
import org.extex.maven.latex.make.action.LaTeXAction;
import org.extex.maven.latex.make.dependency.ArtifactDependency;
import org.extex.maven.latex.make.dependency.Exists;
import org.extex.maven.latex.make.exception.MakeException;
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
        new Net().print(w, "");
        assertEquals("", out.toString());
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test2() {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        Net builder = new Net();
        builder.putArtifact(new Artifact(new File("abc")));

        builder.print(w, "");
        assertEquals("abc\n", out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test3() {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        Net builder = new Net();
        builder.putArtifact(new Artifact(new File("abc")));
        builder.getArtifact(new File("abc")).addDependencies(
            new ArtifactDependency(builder.getArtifact(new File("abc"))));

        builder.print(w, "");
        assertEquals("abc\n\t-> abc\n", out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test4() {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        Net builder = new Net();
        builder.putArtifact(new Artifact(new File("abc")));
        builder.getArtifact(new File("abc")).addDependencies(
            new ArtifactDependency(builder.getArtifact(new File("def"))));

        builder.print(w, "");
        assertEquals("abc\n\t-> def\ndef\n", //
            out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     */
    @Test
    public void test5() {

        StringWriter out = new StringWriter();
        PrintWriter w = new PrintWriter(out);
        Net builder = new Net();
        builder.getArtifact(new File("abc")).addDependencies(
            new ArtifactDependency(builder.getArtifact(new File("def"))));
        builder.getArtifact(new File("abc")).addDependencies(
            new ArtifactDependency(builder.getArtifact(new File("xyz"))));
        builder.getArtifact(new File("xyz")).addDependencies(
            new ArtifactDependency(builder.getArtifact(new File("def"))));

        builder.print(w, "");
        assertEquals("abc\n\t-> def\n\t-> xyz\n" + "def\n" + "xyz\n\t-> def\n", //
            out.toString().replaceAll("\r", ""));
    }

    /**
     * TODO gene: missing JavaDoc
     * 
     * @throws MakeException in case of an error
     */
    @Test
    public void testRun1() throws MakeException {

        Net builder = new Net();
        Artifact abc = builder.getArtifact(new File("abc"));
        abc.addAction(new LaTeXAction());
        Artifact def = builder.getArtifact(new File("def"));
        def.addDependencies(new Exists());
        Artifact xyz = builder.getArtifact(new File("xyz"));
        xyz.addDependencies(new Exists());

        abc.addDependencies(new ArtifactDependency(def));
        abc.addDependencies(new ArtifactDependency(xyz));
        xyz.addDependencies(new ArtifactDependency(def));

        builder.build("abc", true);
    }

}
