/*
 * Copyright (C) 2009 The ExTeX Group and individual authors listed below
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

package org.extex.builder.latex.artifact.latex.macro;

import java.io.File;
import java.io.IOException;

import org.extex.builder.latex.DependencyNet;
import org.extex.builder.latex.Message;
import org.extex.builder.latex.artifact.Artifact;
import org.extex.builder.latex.artifact.latex.LatexReader;
import org.extex.builder.latex.artifact.latex.MacroWithArgs;

/**
 * This class implements a handler for <code>\documentclass</code>.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision: 7699 $
 */
public final class DocumentClass extends MacroWithArgs {

    /**
     * The field <tt>CLASSES</tt> contains the list of known classes which do
     * not lead to a warning.
     */
    private static final String[] CLASSES = new String[]{"ConcProg", // concprog
            "IEEEconf", // IEEEconf
            "IEEEtran", // IEEEtran
            "a0poster", // a0poster
            "aastex", // aastex
            "abstbook", // ltxmisc
            "acmconf", // acmconf
            "acmtrans2e", // acmtrans
            "active-conf", // active-conf
            "active-conf-2006", // active-conf
            "aguplus", // aguplus
            "aiaa-tc", // aiaa
            "ajae", // economic
            "akletter", // akletter
            "amsart", // amscls
            "amsbook", // amscls
            "amsdtx", // amscls
            "amsproc", // amscls
            "apa", // apa
            "article", // base
            "articoletteracdp", // cdpbundl
            "artikel1", // ntgclass
            "artikel2", // ntgclass
            "artikel3", // ntgclass
            "asaetr", // asaetr
            "ascelike", // ascelike
            "assignment", // assignment
            "beamer", // beamer
            "beletter", // ltxmisc
            "boek", // ntgclass
            "boek3", // ntgclass
            "book", // base
            "brief", // ntgclass
            "cc", // computational-complexity
            "cd", // cd
            "cd-cover", // cd-cover
            "cjw-env", // cjw
            "cjw-ltr", // cjw
            "combine", // combine
            "cours", // mafr
            "courseoutline", // courseoutline
            "coursepaper", // coursepaper
            "curve", // curve
            "dinbrief", // dinbrief
            "dtk", // dtk
            "dvdcoll", // dvdcoll
            "ebsthesis", // ebsthesis
            "elpres", // elpres
            "elsart", // elsevier
            "elsart1p", // elsevier
            "elsart3p", // elsevier
            "elsart5p", // elsevier
            "emulateapj", // emulateapj
            "erae", // economic
            "europecv", // europecv
            "exam", // exam
            "examdesign", // examdesign
            "extarticle", // extsizes
            "extbook", // extsizes
            "extletter", // extsizes
            "extproc", // extsizes
            "extreport", // extsizes
            "facsimile", // facsimile
            "fax", // fax
            "fiche", // mafr
            "filoaddr", // calendar
            "flashcard", // ltxmisc
            "flashcards", // flashcards
            "fribrief", // fribrief
            "g-brief", // g-brief
            "g-brief2", // g-brief
            "gatech-thesis", // gatech-thesis
            "gmdocc", // gmdoc
            "hcart", // hc
            "hcletter", // hc
            "hcreport", // hc
            "hcslides", // hc
            "hepthesis", // hepthesis
            "hitec", // hitec
            "hpsdiss", // hpsdiss
            "hssvita", // vita
            "iagproc", // ltxmisc
            "icsv", // icsv
            "ifacmtg", // ifacmtg
            "isov2", // iso
            "jhep", // jhep
            "journal", // paper
            "jpsj2", // jpsj
            "jura", // jura
            "jurabook", // juramisc
            "juraovw", // juramisc
            "juraurtl", // juramisc
            "k_fribri", // fribrief
            "kerntest", // kerntest
            "kluwer", // kluwer
            "l3doc", // expl3
            "labbook", // labbook
            "leaflet", // leaflet
            "lettcdpadi", // cdpbundl
            "letter", // base
            "letteracdp", // cdpbundl
            "lettre", // lettre
            "limap", // limap
            "ltnews", // base
            "ltugboat", // tugboat
            "ltugproc", // tugboat
            "ltxdoc", // base
            "ltxguide", // base
            "memoir", // memoir
            "mentis", // mentis
            "minimal", // base
            "moderncv", // moderncv
            "mtn", // maple
            "muthesis", // muthesis
            "my-thesis", // uaclasses
            "myletter", // akletter
            "nature", // nature
            "ncc", // ncclatex
            "nccproc", // ncclatex
            "newlfm", // newlfm
            "nih", // nih
            "nrc1", // nrc
            "nrc2", // nrc
            "octavo", // octavo
            "paper", // paper
            "papertex", // papertex
            "pbsheet", // pbsheet
            "pecha", // pecha
            "pittetd", // pittetd
            "plari", // plari
            "play", // play
            "postcards", // postcards
            "powerdot", // powerdot
            "powersem", // texpower
            "ppr-prv", // ppr-prv
            "pracjourn", // pracjourn
            "proc", // base
            "prosper", // prosper
            "protocol", // protocol
            "ptptex", // ptptex
            "qcm", // qcm
            "rapport1", // ntgclass
            "rapport3", // ntgclass
            "refart", // refman
            "refrep", // refman
            "report", // base
            "res", // resume
            "revtex4", // revtex
            "rtklage", // rtklage
            "sae", // sae
            "saeold", // sae
            "sciposter", // sciposter
            "scrartcl", // koma-script
            "scrbook", // koma-script
            "scrdoc", // koma-script
            "screenplay", // screenplay
            "script", // script
            "script_l", // script
            "script_s", // script
            "scrlettr", // koma-script
            "scrlttr2", // koma-script
            "scrreprt", // koma-script
            "seminar", // seminar
            "sffms", // sffms
            "sibjnm", // ncclatex
            "sides", // sides
            "siggraph", // siggraph
            "slidenotes", // slidenotes
            "slides", // base
            "smfart", // smflatex
            "smfbook", // smflatex
            "spie", // spie
            "stage", // stage
            "sugconf", // sugconf
            "talk", // talk
            "tcldoc", // tcldoc
            "tclldoc", // tcldoc
            "third-rep", // muthesis
            "timesht", // timesht
            "ua-thesis", // uaclasses
            "ucthesis", // ucthesis
            "uiucthesis", // uiucthesis
            "umich-thesis", // umich-thesis
            "usthesis", // stellenbosch
            "uwthesis", // uwthesis
            "vita", // vita
            "weekly", // calendar
            "york-thesis" // york-thesis
    };

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.builder.latex.artifact.latex.MacroWithArgs#expand(org.extex.builder.latex.artifact.latex.LatexReader,
     *      org.extex.builder.latex.DependencyNet,
     *      org.extex.builder.latex.artifact.Artifact, java.lang.String,
     *      java.lang.String)
     */
    @Override
    protected void expand(LatexReader reader, DependencyNet net,
            Artifact artifact, String opt, String arg) throws IOException {

        File f = net.searchFile(arg, new String[]{".cls"}, artifact.getFile());
        if (f == null) {
            for (String s : CLASSES) {
                if (arg.equals(s)) {
                    return;
                }
            }
            net.getLogger().info(Message.get("documentclass.ignored", arg));
            return;
        }
        Artifact a = new Artifact(f);
        net.addArtifact(a);
        net.getTarget().dependsOn(a);

        net.setAtLetter(true);
        net.analyzeLaTeX(a);
        net.setAtLetter(false);
    }

}
