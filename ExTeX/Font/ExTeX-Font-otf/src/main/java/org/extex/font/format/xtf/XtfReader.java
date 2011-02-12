/*
 * Copyright (C) 2004-2007 The ExTeX Group and individual authors listed below
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
 *
 */

package org.extex.font.format.xtf;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.extex.font.format.xtf.tables.OtfTableACNT;
import org.extex.font.format.xtf.tables.OtfTableAVAR;
import org.extex.font.format.xtf.tables.OtfTableBASE;
import org.extex.font.format.xtf.tables.OtfTableBDAT;
import org.extex.font.format.xtf.tables.OtfTableBLOC;
import org.extex.font.format.xtf.tables.OtfTableBSLN;
import org.extex.font.format.xtf.tables.OtfTableCFF;
import org.extex.font.format.xtf.tables.OtfTableCVAR;
import org.extex.font.format.xtf.tables.OtfTableDSIG;
import org.extex.font.format.xtf.tables.OtfTableFDSC;
import org.extex.font.format.xtf.tables.OtfTableFEAT;
import org.extex.font.format.xtf.tables.OtfTableFMTX;
import org.extex.font.format.xtf.tables.OtfTableFVAR;
import org.extex.font.format.xtf.tables.OtfTableGDEF;
import org.extex.font.format.xtf.tables.OtfTableGVAR;
import org.extex.font.format.xtf.tables.OtfTableJSTF;
import org.extex.font.format.xtf.tables.OtfTableJUST;
import org.extex.font.format.xtf.tables.OtfTableLCAR;
import org.extex.font.format.xtf.tables.OtfTableMMFX;
import org.extex.font.format.xtf.tables.OtfTableMMSD;
import org.extex.font.format.xtf.tables.OtfTableMORT;
import org.extex.font.format.xtf.tables.OtfTablePROP;
import org.extex.font.format.xtf.tables.OtfTableTRAK;
import org.extex.font.format.xtf.tables.OtfTableTYP1;
import org.extex.font.format.xtf.tables.TtfTableCMAP;
import org.extex.font.format.xtf.tables.TtfTableCVT;
import org.extex.font.format.xtf.tables.TtfTableEBDT;
import org.extex.font.format.xtf.tables.TtfTableEBLC;
import org.extex.font.format.xtf.tables.TtfTableEBSC;
import org.extex.font.format.xtf.tables.TtfTableFPGM;
import org.extex.font.format.xtf.tables.TtfTableGASP;
import org.extex.font.format.xtf.tables.TtfTableGLYF;
import org.extex.font.format.xtf.tables.TtfTableHDMX;
import org.extex.font.format.xtf.tables.TtfTableHEAD;
import org.extex.font.format.xtf.tables.TtfTableHHEA;
import org.extex.font.format.xtf.tables.TtfTableHMTX;
import org.extex.font.format.xtf.tables.TtfTableKERN;
import org.extex.font.format.xtf.tables.TtfTableLOCA;
import org.extex.font.format.xtf.tables.TtfTableLTSH;
import org.extex.font.format.xtf.tables.TtfTableMAXP;
import org.extex.font.format.xtf.tables.TtfTableNAME;
import org.extex.font.format.xtf.tables.TtfTableOS2;
import org.extex.font.format.xtf.tables.TtfTablePCLT;
import org.extex.font.format.xtf.tables.TtfTablePOST;
import org.extex.font.format.xtf.tables.TtfTablePREP;
import org.extex.font.format.xtf.tables.TtfTableVDMX;
import org.extex.font.format.xtf.tables.TtfTableVHEA;
import org.extex.font.format.xtf.tables.TtfTableVMTX;
import org.extex.font.format.xtf.tables.XtfBoundingBox;
import org.extex.font.format.xtf.tables.XtfConstants;
import org.extex.font.format.xtf.tables.XtfGlyph;
import org.extex.font.format.xtf.tables.XtfTable;
import org.extex.font.format.xtf.tables.XtfTableDirectory;
import org.extex.font.format.xtf.tables.XtfTableMap;
import org.extex.font.format.xtf.tables.TtfTableCMAP.Format;
import org.extex.font.format.xtf.tables.TtfTableGLYF.Descript;
import org.extex.font.format.xtf.tables.gps.OtfTableGPOS;
import org.extex.font.format.xtf.tables.gps.OtfTableGSUB;
import org.extex.font.format.xtf.tables.gps.XtfScriptList.LangSys;
import org.extex.font.format.xtf.tables.tag.LanguageSystemTag;
import org.extex.font.format.xtf.tables.tag.ScriptTag;
import org.extex.util.file.random.RandomAccessInputFile;
import org.extex.util.file.random.RandomAccessInputStream;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;
import org.extex.util.xml.XMLWriterConvertible;

/**
 * Reader for a TrueType / OpenType font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class XtfReader implements XMLWriterConvertible {

    /**
     * BaseFont-Type.
     */
    private static class Type {

        /**
         * Create a new object.
         */
        public Type() {

            super();
        }
    }

    /* 51 tag types: from java.awt.font.OpenType */

    /**
     * Accent attachment. Table tag "acnt" in the Open Type Specification.
     */
    public static final int ACNT = 0x61636e74;

    /**
     * Axis variation. Table tag "avar" in the Open Type Specification.
     */
    public static final int AVAR = 0x61766172;

    /**
     * Baseline data. Table tag "BASE" in the Open Type Specification.
     */
    public static final int BASE = 0x42415345;

    /**
     * Bitmap data. Table tag "bdat" in the Open Type Specification.
     */
    public static final int BDAT = 0x62646174;

    /**
     * Bitmap location. Table tag "bloc" in the Open Type Specification.
     */
    public static final int BLOC = 0x626c6f63;

    /**
     * Baseline table. Table tag "bsln" in the Open Type Specification.
     */
    public static final int BSLN = 0x62736c6e;

    /**
     * Compact font format (Type1 font). Table tag "CFF " in the Open Type
     * Specification.
     */
    public static final int CFF = 0x43464620;

    /**
     * Character to glyph mapping. (TTF required) Table tag "cmap" in the Open
     * Type Specification.
     */
    public static final int CMAP = 0x636d6170;

    /**
     * CVT variation. Table tag "cvar" in the Open Type Specification.
     */
    public static final int CVAR = 0x63766172;

    /**
     * Control value table. (TTF optional) Table tag "cvt " in the Open Type
     * Specification.
     */
    public static final int CVT = 0x63767420;

    /**
     * Digital signature. Table tag "DSIG" in the Open Type Specification.
     */
    public static final int DSIG = 0x44534947;

    /**
     * Embedded bitmap data. (TTF optional) Table tag "EBDT" in the Open Type
     * Specification.
     */
    public static final int EBDT = 0x45424454;

    /**
     * Embedded bitmap location. (TTF optional) Table tag "EBLC" in the Open
     * Type Specification.
     */
    public static final int EBLC = 0x45424c43;

    /**
     * Embedded bitmap scaling. (TTF optional) Table tag "EBSC" in the Open Type
     * Specification.
     */
    public static final int EBSC = 0x45425343;

    /**
     * The field <tt>EXTEX_TRACE_FONT_FILES</tt>.
     */
    public static final String EXTEX_TRACE_FONT_FILES =
            "extex.trace.font.files";

    /**
     * BaseFont descriptors. Table tag "fdsc" in the Open Type Specification.
     */
    public static final int FDSC = 0x66647363;

    /**
     * Feature name. Table tag "feat" in the Open Type Specification.
     */
    public static final int FEAT = 0x66656174;

    /**
     * BaseFont metrics. Table tag "fmtx" in the Open Type Specification.
     */
    public static final int FMTX = 0x666d7478;

    /**
     * BaseFont program. (TTF optional) Table tag "fpgm" in the Open Type
     * Specification.
     */
    public static final int FPGM = 0x6670676d;

    /**
     * BaseFont variation. Table tag "fvar" in the Open Type Specification.
     */
    public static final int FVAR = 0x66766172;

    /**
     * Grid-fitting and scan conversion procedure. (TTF optional) Table tag
     * "gasp" in the Open Type Specification.
     */
    public static final int GASP = 0x67617370;

    /**
     * Glyph definition. Table tag "GDEF" in the Open Type Specification.
     */
    public static final int GDEF = 0x47444546;

    /**
     * Glyph data. (TTF required) Table tag "glyf" in the Open Type
     * Specification.
     */
    public static final int GLYF = 0x676c7966;

    /**
     * Glyph positioning. Table tag "GPOS" in the Open Type Specification.
     */
    public static final int GPOS = 0x47504f53;

    /**
     * Glyph substitution. Table tag "GSUB" in the Open Type Specification.
     */
    public static final int GSUB = 0x47535542;

    /**
     * Glyph variation. Table tag "gvar" in the Open Type Specification.
     */
    public static final int GVAR = 0x67766172;

    /**
     * Horizontal device metrics. (TTF optional) Table tag "hdmx" in the Open
     * Type Specification.
     */
    public static final int HDMX = 0x68646d78;

    /**
     * BaseFont header. (TTF required) Table tag "head" in the Open Type
     * Specification.
     */
    public static final int HEAD = 0x68656164;

    /**
     * Horizontal metrics header. (TTF required) Table tag "hhea" in the Open
     * Type Specification.
     */
    public static final int HHEA = 0x68686561;

    /**
     * Horizontal metrics. (TTF required) Table tag "hmtx" in the Open Type
     * Specification.
     */
    public static final int HMTX = 0x686d7478;

    /**
     * Justification. Table tag "JSTF" in the Open Type Specification.
     */
    public static final int JSTF = 0x4a535446;

    /**
     * Justification. Table tag "just" in the Open Type Specification.
     */
    public static final int JUST = 0x6a757374;

    /**
     * Kerning. (TTF optional) Table tag "kern" in the Open Type Specification.
     */
    public static final int KERN = 0x6b65726e;

    /**
     * Ligature caret. Table tag "lcar" in the Open Type Specification.
     */
    public static final int LCAR = 0x6c636172;

    /**
     * Index to location. (TTF required) Table tag "loca" in the Open Type
     * Specification.
     */
    public static final int LOCA = 0x6c6f6361;

    /**
     * Linear threshold. (TTF optional) Table tag "LTSH" in the Open Type
     * Specification.
     */
    public static final int LTSH = 0x4c545348;

    /**
     * Maximum profile. (TTF required) Table tag "maxp" in the Open Type
     * Specification.
     */
    public static final int MAXP = 0x6d617870;

    /**
     * Multiple master font metrics. Table tag "MMFX" in the Open Type
     * Specification.
     */
    public static final int MMFX = 0x4d4d4658;

    /**
     * Multiple master supplementary data. Table tag "MMSD" in the Open Type
     * Specification.
     */
    public static final int MMSD = 0x4d4d5344;

    /**
     * Glyph metamorphosis. Table tag "mort" in the Open Type Specification.
     */
    public static final int MORT = 0x6d6f7274;

    /**
     * Naming table. (TTF required) Table tag "name" in the Open Type
     * Specification.
     */
    public static final int NAME = 0x6e616d65;

    /**
     * OS/2 and Windows specific metrics. (TTF required) Table tag "OS/2" in the
     * Open Type Specification.
     */
    public static final int OS_2 = 0x4f532f32;

    /**
     * OTF.
     */
    public static final Type OTF = new Type();

    /**
     * PCL 5 data. (TTF optional) Table tag "PCLT" in the Open Type
     * Specification.
     */
    public static final int PCLT = 0x50434c54;

    /**
     * PostScript Information. (TTF required) Table tag "post" in the Open Type
     * Specification.
     */
    public static final int POST = 0x706f7374;

    /**
     * CVT preprogram. (TTF optional) Table tag "prep" in the Open Type
     * Specification.
     */
    public static final int PREP = 0x70726570;

    /**
     * Optical bounds. Table tag "opbd" in the Open Type Specification.
     */
    // public static final int OPBD = 0x6d6f7274;
    // entspricht mort
    /**
     * Glyph properties. Table tag "prop" in the Open Type Specification.
     */
    public static final int PROP = 0x70726f70;

    /**
     * Tracking. Table tag "trak" in the Open Type Specification.
     */
    public static final int TRAK = 0x7472616b;

    /**
     * TTF.
     */
    public static final Type TTF = new Type();

    /**
     * Adobe Type 1 font data. Table tag "typ1" in the Open Type Specification.
     */
    public static final int TYP1 = 0x74797031;

    /**
     * Vertical device metrics. (TTF optional) Table tag "VDMX" in the Open Type
     * Specification.
     */
    public static final int VDMX = 0x56444d58;

    /**
     * Vertical metrics header. (TTF optional) Table tag "vhea" in the Open Type
     * Specification.
     */
    public static final int VHEA = 0x76686561;

    /**
     * Vertical metrics. (TTF optional) Table tag "vmtx" in the Open Type
     * Specification.
     */
    public static final int VMTX = 0x766d7478;

    /**
     * Convert the internal long date format to a Date.
     * <p>
     * Calculate the difference between the original Mac epoch (1904) to the
     * epoch on this machine.
     * </p>
     * 
     * @param value the long format (mac)
     * @return Returns the calculate Date
     */
    public static Date convertDate(long value) {

        // TODO wrong!!!
        return new Date(value);
    }

    /**
     * seconds from 1904 to 1971
     */
    // private static final long SECONDS1904TO1971 = 60 * 60 * 24 * (365 * (1971
    // - 1904) + 17);
    /**
     * Convert a int value in a binary string.
     * 
     * @param value the int value
     * @return Returns the binary string
     */
    public static String convertIntToBinaryString(int value) {

        StringBuffer buf = new StringBuffer("00000000000000000000000000000000");
        buf.append(Integer.toBinaryString(value));
        return buf.substring(buf.length() - XtfConstants.SHIFT32).toString();
    }

    /**
     * Convert a int value in a hex string.
     * 
     * @param value the int value
     * @return Returns the hex string
     */
    public static String convertIntToHexString(int value) {

        StringBuffer buf = new StringBuffer("00000000");
        buf.append(Integer.toHexString(value));
        return "0x"
                + buf.substring(buf.length() - XtfConstants.SHIFT8).toString();

    }

    /**
     * Convert a Fixed value (Version).
     * 
     * @param value the fixed value
     * @return Returns the float-value
     */
    public static float convertVersion(int value) {

        int v1 = value >> XtfConstants.SHIFTX10;
        return v1;
    }

    /**
     * Table cmap (required).
     */
    private TtfTableCMAP cmap;

    /**
     * The font data.
     */
    private byte[] fontdata;

    /**
     * Table glyf (required).
     */
    private TtfTableGLYF glyf;

    /**
     * Table head (required).
     */
    private TtfTableHEAD head;

    /**
     * Table hhea (required).
     */
    private TtfTableHHEA hhea;

    /**
     * Table hmtx (required).
     */
    private TtfTableHMTX hmtx;

    /**
     * Table loca (required).
     */
    private TtfTableLOCA loca;

    /**
     * The logger.
     */
    private Logger logger;

    /**
     * Table maxp (required).
     */
    private TtfTableMAXP maxp;

    /**
     * Table name (required).
     */
    private TtfTableNAME name;

    /**
     * Table os2 (required).
     */
    private TtfTableOS2 os2;

    /**
     * Table post (required).
     */
    private TtfTablePOST post;

    /**
     * TableDirectory.
     */
    private XtfTableDirectory tableDirectory = null;

    /**
     * Map for all tables.
     */
    private XtfTableMap tablemap;

    /**
     * Trace the work (only if logger is enabled).
     */
    private boolean trace = false;

    /**
     * the type.
     */
    private Type type = TTF;

    /**
     * Create a new object.
     * 
     * @param file The file for input.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(File file) throws IOException {

        this(new RandomAccessInputFile(file), null);
    }

    /**
     * Create a new object.
     * 
     * @param file The file for input.
     * @param logger The logger.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(File file, Logger logger) throws IOException {

        this(new RandomAccessInputFile(file), logger);
    }

    /**
     * Create a new object.
     * 
     * @param iostream The stream for input.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(InputStream iostream) throws IOException {

        this(new RandomAccessInputStream(iostream), null);
    }

    /**
     * Create a new object.
     * 
     * @param iostream The stream for input.
     * @param logger The logger.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(InputStream iostream, Logger logger) throws IOException {

        this(new RandomAccessInputStream(iostream), logger);
    }

    /**
     * Create a new object.
     * 
     * @param rar The input.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(RandomAccessR rar) throws IOException {

        this(rar, null);
    }

    /**
     * Create a new object.
     * 
     * @param rar The input.
     * @param logger The logger.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(RandomAccessR rar, Logger logger) throws IOException {

        super();
        this.logger = logger;
        if (logger != null
                && System.getProperty(EXTEX_TRACE_FONT_FILES, "false").equals(
                    "true")) {
            trace = true;

        }
        type = TTF;
        tablemap = new XtfTableMap();
        read(rar);
    }

    /**
     * Create a new object.
     * 
     * @param filename The file name for input.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(String filename) throws IOException {

        this(new RandomAccessInputFile(filename), null);
    }

    /**
     * Create a new object.
     * 
     * @param filename The file name for input.
     * @param logger The logger.
     * @throws IOException if an IO-error occurs
     */
    public XtfReader(String filename, Logger logger) throws IOException {

        this(new RandomAccessInputFile(filename), logger);
    }

    /**
     * Create the table.
     * 
     * @param de directory entry
     * @param rar input
     * @return Returns the table
     * @throws IOException if an IO-error occurs
     */
    private XtfTable create(XtfTableDirectory.Entry de, RandomAccessR rar)
            throws IOException {

        XtfTable t = null;
        trace("read " + de.getTag());
        switch (de.getTag()) {
            case GPOS:
                t = new OtfTableGPOS(tablemap, de, rar);
                type = OTF;
                break;
            case GSUB:
                t = new OtfTableGSUB(tablemap, de, rar);
                type = OTF;
                break;
            case OS_2:
                t = new TtfTableOS2(tablemap, de, rar);
                break;
            case CMAP:
                t = new TtfTableCMAP(tablemap, de, rar);
                break;
            case CVT:
                t = new TtfTableCVT(tablemap, de, rar);
                break;
            case FPGM:
                t = new TtfTableFPGM(tablemap, de, rar);
                break;
            case GLYF:
                t = new TtfTableGLYF(tablemap, de, rar);
                break;
            case HEAD:
                t = new TtfTableHEAD(tablemap, de, rar);
                break;
            case HHEA:
                t = new TtfTableHHEA(tablemap, de, rar);
                break;
            case HMTX:
                t = new TtfTableHMTX(tablemap, de, rar);
                break;
            case KERN:
                t = new TtfTableKERN(tablemap, de, rar);
                break;
            case LOCA:
                t = new TtfTableLOCA(tablemap, de, rar);
                break;
            case MAXP:
                t = new TtfTableMAXP(tablemap, de, rar);
                break;
            case NAME:
                t = new TtfTableNAME(tablemap, de, rar);
                break;
            case PREP:
                t = new TtfTablePREP(tablemap, de, rar);
                break;
            case POST:
                t = new TtfTablePOST(tablemap, de, rar);
                break;
            case HDMX:
                t = new TtfTableHDMX(tablemap, de, rar);
                break;
            case GASP:
                t = new TtfTableGASP(tablemap, de, rar);
                break;
            case VDMX:
                t = new TtfTableVDMX(tablemap, de, rar);
                break;
            case VMTX:
                t = new TtfTableVMTX(tablemap, de, rar);
                break;
            case VHEA:
                t = new TtfTableVHEA(tablemap, de, rar);
                break;
            case TYP1:
                t = new OtfTableTYP1(tablemap, de, rar);
                type = OTF;
                break;
            case BSLN:
                t = new OtfTableBSLN(tablemap, de, rar);
                type = OTF;
                break;
            case DSIG:
                t = new OtfTableDSIG(tablemap, de, rar);
                type = OTF;
                break;
            case FVAR:
                t = new OtfTableFVAR(tablemap, de, rar);
                type = OTF;
                break;
            case GVAR:
                t = new OtfTableGVAR(tablemap, de, rar);
                type = OTF;
                break;
            case CFF:
                t = new OtfTableCFF(tablemap, de, rar);
                type = OTF;
                break;
            case MMSD:
                t = new OtfTableMMSD(tablemap, de, rar);
                type = OTF;
                break;
            case MMFX:
                t = new OtfTableMMFX(tablemap, de, rar);
                type = OTF;
                break;
            case GDEF:
                t = new OtfTableGDEF(tablemap, de, rar);
                type = OTF;
                break;
            case JSTF:
                t = new OtfTableJSTF(tablemap, de, rar);
                type = OTF;
                break;
            case EBDT:
                t = new TtfTableEBDT(tablemap, de, rar);
                break;
            case EBLC:
                t = new TtfTableEBLC(tablemap, de, rar);
                break;
            case EBSC:
                t = new TtfTableEBSC(tablemap, de, rar);
                break;
            case LTSH:
                t = new TtfTableLTSH(tablemap, de, rar);
                break;
            case PCLT:
                t = new TtfTablePCLT(tablemap, de, rar);
                break;
            case ACNT:
                t = new OtfTableACNT(tablemap, de, rar);
                type = OTF;
                break;
            case AVAR:
                t = new OtfTableAVAR(tablemap, de, rar);
                break;
            case BDAT:
                t = new OtfTableBDAT(tablemap, de, rar);
                type = OTF;
                break;
            case BLOC:
                t = new OtfTableBLOC(tablemap, de, rar);
                type = OTF;
                break;
            case CVAR:
                t = new OtfTableCVAR(tablemap, de, rar);
                type = OTF;
                break;
            case FEAT:
                t = new OtfTableFEAT(tablemap, de, rar);
                type = OTF;
                break;
            case FDSC:
                t = new OtfTableFDSC(tablemap, de, rar);
                type = OTF;
                break;
            case FMTX:
                t = new OtfTableFMTX(tablemap, de, rar);
                type = OTF;
                break;
            case JUST:
                t = new OtfTableJUST(tablemap, de, rar);
                type = OTF;
                break;
            case LCAR:
                t = new OtfTableLCAR(tablemap, de, rar);
                type = OTF;
                break;
            case MORT:
                t = new OtfTableMORT(tablemap, de, rar);
                type = OTF;
                break;
            // case OPBD :
            // t = new TTFTableOPBD(de, rar);
            // break;
            case PROP:
                t = new OtfTablePROP(tablemap, de, rar);
                type = OTF;
                break;
            case TRAK:
                t = new OtfTableTRAK(tablemap, de, rar);
                type = OTF;
                break;
            case BASE:
                t = new OtfTableBASE(tablemap, de, rar);
                type = OTF;
                break;
            default:
                t = null;
        }
        return t;
    }

    /**
     * Returns the ascent.
     * 
     * @return Returns the ascent
     */
    public int getAscent() {

        return hhea.getAscender();
    }

    /**
     * Returns the BoundingBox.
     * <p>
     * 0 xmin
     * </p>
     * <p>
     * 1 ymin
     * </p>
     * <p>
     * 2 xmax
     * </p>
     * <p>
     * 3 ymax
     * </p>
     * 
     * @return Returns the BoundingBox.
     */
    public int[] getBoundingBox() {

        int[] bb = new int[4];
        bb[0] = head.getXMin();
        bb[1] = head.getYMin();
        bb[2] = head.getXMax();
        bb[3] = head.getYMax();
        return bb;
    }

    /**
     * Returns the cmap table.
     * 
     * @return Returns the cmap table
     */
    public TtfTableCMAP getCmapTable() {

        return cmap;
    }

    /**
     * Returns the descent.
     * 
     * @return Returns the descent
     */
    public int getDescent() {

        return hhea.getDescender();
    }

    /**
     * Returns the data of the font.
     * 
     * @return the data of the font.
     */
    public byte[] getFontData() {

        return fontdata;
    }

    /**
     * Returns the font family name.
     * 
     * @return Returns the font family name.
     */
    public String getFontFamilyName() {

        return getNameTable().getRecord(TtfTableNAME.FONTFAMILYNAME);
    }

    /**
     * Returns the glyph.
     * 
     * @param i glyph number
     * @return Returns the glyph
     */
    public XtfGlyph getGlyph(int i) {

        return (glyf.getDescription(i) != null) ? new XtfGlyph(glyf
            .getDescription(i), hmtx.getLeftSideBearing(i), hmtx
            .getAdvanceWidth(i)) : null;
    }

    /**
     * Returns the head table.
     * 
     * @return Returns the head table
     */
    public TtfTableHEAD getHeadTable() {

        return head;
    }

    /**
     * Returns the hhea table.
     * 
     * @return Returns the hhea table
     */
    public TtfTableHHEA getHheaTable() {

        return hhea;
    }

    /**
     * Returns the hmtx table.
     * 
     * @return Returns the hmtx table
     */
    public TtfTableHMTX getHmtxTable() {

        return hmtx;
    }

    /**
     * Returns the italic angle.
     * 
     * @return Returns the italic angle.
     */
    public int getItalicAngle() {

        return post.getItalicAngle();
    }

    /**
     * Returns the loca table.
     * 
     * @return Returns the loca table
     */
    public TtfTableLOCA getLocaTable() {

        return loca;
    }

    /**
     * Returns the os2 table.
     * 
     * @return Returns the os2 table
     */
    public TtfTableMAXP getMaxpTable() {

        return maxp;
    }

    /**
     * Returns the maxp table.
     * 
     * @return Returns the maxp table
     */
    public TtfTableNAME getNameTable() {

        return name;
    }

    /**
     * Returns the number of glyphs.
     * 
     * @return Returns the number of glyphs.
     */
    public int getNumberOfGlyphs() {

        return maxp.getNumGlyphs();
    }

    /**
     * Returns the os2 table.
     * 
     * @return Returns the os2 table
     */
    public TtfTableOS2 getOS2Table() {

        return os2;
    }

    /**
     * Returns the post table.
     * 
     * @return Returns the post table
     */
    public TtfTablePOST getPostTable() {

        return post;
    }

    /**
     * Return the table with the special type.
     * 
     * @param tabletype the table type
     * @return Returns the table
     */
    public XtfTable getTable(int tabletype) {

        return tablemap.get(tabletype);
    }

    /**
     * Returns the table directory.
     * 
     * @return Returns the table directory
     */
    public XtfTableDirectory getTableDirectory() {

        return tableDirectory;
    }

    /**
     * Returns the font type.
     * 
     * @return Returns the font type
     */
    public Type getType() {

        return type;
    }

    /**
     * Return the underline position.
     * 
     * @return Return the underline position.
     */
    public int getUnderlinePos() {

        return post.getUnderlinePosition();
    }

    /**
     * Return the underline thickness.
     * 
     * @return Return the underline thickness.
     */
    public int getUnderlineThickness() {

        return post.getUnderlineThickness();
    }

    /**
     * Returns the units per em.
     * 
     * @return Returns the units per em.
     */
    public int getUnitsPerEm() {

        return head.getUnitsPerEm();
    }

    /**
     * Check, if the font has the glpyh.
     * 
     * @param glyphname The glyph name.
     * @param fontnumber The font number.
     * 
     * @return Returns <code>true</code>, if the font has the glyph.
     */
    public boolean hasGlyph(String glyphname, int fontnumber) {

        int pos = post.getGlyphNamePosition(glyphname);
        if (pos < 0) {
            // search in cff
            XtfTable cff = getTable(CFF);
            if (cff != null && cff instanceof OtfTableCFF) {
                OtfTableCFF cfftab = (OtfTableCFF) cff;
                pos = cfftab.mapGlyphNameToGlyphPos(glyphname, fontnumber);
            }
            if (pos < 0) {
                return false;
            }
        }
        return true;

    }

    /**
     * Returns the bounding box for the char by using the platform and encoding.
     * If no char is found, <code>null</code> be returned.
     * 
     * @param charCode The charCode.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the glyph bounding box for the char.
     */
    public XtfBoundingBox mapCharCodeToBB(int charCode, int fontnumber,
            short platformId, short encodingId) {

        // get format
        Format format = getCmapTable().getFormat(platformId, encodingId);
        if (format != null) {
            int glyphpos = format.mapCharCode(charCode);

            if (glyf != null) {
                Descript desc = glyf.getDescription(glyphpos);
                if (desc != null) {
                    return new XtfBoundingBox(desc.getXMin(), desc.getYMin(),
                        desc.getXMax(), desc.getYMax());
                }
            }
        }
        return null;
    }

    /**
     * Returns the glyph bounding box for the char by using the platform and
     * encoding. If no char is found, <code>null</code> be returned.
     * 
     * @param glypname The glyph name.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the glyph bounding box for the char.
     */
    public XtfBoundingBox mapCharCodeToBB(String glypname, int fontnumber,
            short platformId, short encodingId) {

        int pos = post.getGlyphNamePosition(glypname);
        if (pos < 0) {
            return null;
        }
        // get format
        Format format = getCmapTable().getFormat(platformId, encodingId);
        if (format != null) {

            if (glyf != null) {
                Descript desc = glyf.getDescription(pos);
                if (desc != null) {
                    return new XtfBoundingBox(desc.getXMin(), desc.getYMin(),
                        desc.getXMax(), desc.getYMax());
                }
            }
        }
        return null;
    }

    /**
     * Returns the glyph name for the char by using the platform and encoding.
     * If no char is found, <code>null</code> will be returned.
     * 
     * @param charCode The char code.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the glyph name for the char.
     */
    public String mapCharCodeToGlyphname(int charCode, int fontnumber,
            short platformId, short encodingId) {

        // get format
        Format format = getCmapTable().getFormat(platformId, encodingId);
        if (format != null) {
            int glyphpos = format.mapCharCode(charCode);

            if (glyphpos > 0) {
                String postGylphName = getPostTable().getGlyphName(glyphpos);
                if (postGylphName != null) {
                    return postGylphName;
                }
                // search in cff
                XtfTable cff = getTable(CFF);
                if (cff != null && cff instanceof OtfTableCFF) {
                    OtfTableCFF cfftab = (OtfTableCFF) cff;
                    return cfftab.mapGlyphPosToGlyphName(glyphpos, fontnumber);
                }
            }
        }
        return null;
    }

    /**
     * Returns the kerning for the two chars.
     * 
     * @param charcodeLeft The left char code.
     * @param charcodeRigth The right char code.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the kerning for the two chars or 0, if no char is found.
     */
    public int mapCharCodetoKerning(int charcodeLeft, int charcodeRigth,
            int fontnumber, short platformId, short encodingId) {

        TtfTableKERN kern = (TtfTableKERN) getTable(XtfReader.KERN);
        if (kern != null) {

            // get format
            Format format = getCmapTable().getFormat(platformId, encodingId);
            if (format != null) {
                int glyphposLeft = format.mapCharCode(charcodeLeft);
                int glyphcodeRight = format.mapCharCode(charcodeRigth);

                return kern.getKerning(glyphposLeft, glyphcodeRight);
            }
        }
        return 0;
    }

    /**
     * Returns the ligature for the two chars.
     * 
     * @param charcodeLeft The left char code.
     * @param charcodeRigth The right char code.
     * @param feature The feature tag.
     * @param language The language (<code>null</code> for default).
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the ligature for the two chars.
     */
    public int mapCharCodetoLigature(int charcodeLeft, int charcodeRigth,
            List<String> feature, String language, int fontnumber,
            short platformId, short encodingId) {

        OtfTableGSUB gsub = (OtfTableGSUB) getTable(XtfReader.GSUB);
        if (gsub != null) {

            // get format
            Format format = getCmapTable().getFormat(platformId, encodingId);
            if (format != null) {
                int glyphposLeft = format.mapCharCode(charcodeLeft);
                int glyphcodeRight = format.mapCharCode(charcodeRigth);

                for (Iterator<String> iterator = feature.iterator(); iterator
                    .hasNext();) {
                    String tag = iterator.next();
                    LangSys langsys =
                            gsub.findLangSys(ScriptTag.getInstance(tag),
                                LanguageSystemTag.getInstance(language));
                    if (langsys != null) {
                        //
                    }
                }

                // return kern.getKerning(glyphposLeft, glyphcodeRight);
            }
        }
        return -1;
    }

    /**
     * Returns the glyph width for the char by using the platform and encoding.
     * If no char is found, 0 be returned.
     * 
     * @param charCode The char code.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the glyph width for the char.
     */
    public int mapCharCodeToWidth(int charCode, int fontnumber,
            short platformId, short encodingId) {

        // get format
        Format format = getCmapTable().getFormat(platformId, encodingId);
        if (format != null) {
            int glyphpos = format.mapCharCode(charCode);

            if (glyphpos > 0) {
                return hmtx.getAdvanceWidth(glyphpos);
            }
        }
        return 0;
    }

    /**
     * Returns the glyph width for the char by using the platform and encoding.
     * If no char is found, 0 be returned.
     * 
     * @param glyphname The glyph name.
     * @param fontnumber The font number.
     * @param platformId The platform id.
     * @param encodingId The encoding id.
     * @return Returns the glyph width for the char.
     */
    public int mapCharCodeToWidth(String glyphname, int fontnumber,
            short platformId, short encodingId) {

        int pos = post.getGlyphNamePosition(glyphname);
        if (pos < 0) {
            // search in cff
            XtfTable cff = getTable(CFF);
            if (cff != null && cff instanceof OtfTableCFF) {
                OtfTableCFF cfftab = (OtfTableCFF) cff;
                pos = cfftab.mapGlyphNameToGlyphPos(glyphname, fontnumber);
            }
            if (pos < 0) {
                return 0;
            }
        }
        // get format
        Format format = getCmapTable().getFormat(platformId, encodingId);
        if (format != null) {
            return hmtx.getAdvanceWidth(pos);
        }
        return 0;
    }

    /**
     * Read the TTF.
     * 
     * @param rar input
     * @throws IOException if an IO-error occurs.
     */
    private void read(RandomAccessR rar) throws IOException {

        trace("read xtf");

        trace("read directory");
        tableDirectory = new XtfTableDirectory(rar);

        // Load each of the tables
        for (int i = 0; i < tableDirectory.getNumTables(); i++) {
            XtfTable t = create(tableDirectory.getEntry(i), rar);
            if (t != null) {
                tablemap.put(t.getType(), t);
            }
        }

        // Get references to required tables
        os2 = (TtfTableOS2) getTable(OS_2);
        cmap = (TtfTableCMAP) getTable(CMAP);
        glyf = (TtfTableGLYF) getTable(GLYF);
        head = (TtfTableHEAD) getTable(HEAD);
        hhea = (TtfTableHHEA) getTable(HHEA);
        hmtx = (TtfTableHMTX) getTable(HMTX);
        loca = (TtfTableLOCA) getTable(LOCA);
        maxp = (TtfTableMAXP) getTable(MAXP);
        name = (TtfTableNAME) getTable(NAME);
        post = (TtfTablePOST) getTable(POST);

        // sort tables for init
        XtfTable[] tabs = tablemap.getTables();

        Arrays.sort(tabs, new Comparator<XtfTable>() {

            /**
             * @see java.util.Comparator#compare(java.lang.Object,
             *      java.lang.Object)
             */
            public int compare(XtfTable arg0, XtfTable arg1) {

                if (arg0.getInitOrder() > arg1.getInitOrder()) {
                    return 1;
                }
                return 0;
            }
        });

        // Initialize tables
        for (int i = 0; i < tabs.length; i++) {
            tabs[i].init();
        }

        fontdata = rar.getData();

        // close
        rar.close();
    }

    /**
     * Trace a message.
     * 
     * @param msg The Message.
     */
    private void trace(String msg) {

        if (trace) {
            logger.info(msg);
        }
    }

    /**
     * @see org.extex.util.xml.XMLWriterConvertible#writeXML(
     *      org.extex.util.xml.XMLStreamWriter)
     */
    public void writeXML(XMLStreamWriter writer) throws IOException {

        writer.writeStartElement("font");
        writer.writeAttribute("type", type == TTF ? "ttf" : "otf");

        // name
        if (getFontFamilyName() != null) {
            writer.writeAttribute("name", getFontFamilyName());
        }

        // table directory
        tableDirectory.writeXML(writer);

        // tables
        int[] keys = tablemap.getKeys();
        for (int i = 0; i < keys.length; i++) {
            XtfTable t = tablemap.get(keys[i]);

            if (t != null && t instanceof XMLWriterConvertible) {
                XMLWriterConvertible xml = (XMLWriterConvertible) t;
                xml.writeXML(writer);
            } else {
                System.out.println(t.getShortcut());
            }
        }
        writer.writeEndElement();
    }

}
