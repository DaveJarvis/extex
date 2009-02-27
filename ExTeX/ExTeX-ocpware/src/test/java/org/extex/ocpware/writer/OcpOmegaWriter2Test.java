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

package org.extex.ocpware.writer;

import java.util.Locale;

import org.junit.Test;

/**
 * This is a test suite for OcpOmegaWriter2.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public class OcpOmegaWriter2Test extends WriterTester {

    /**
     * Run a test case.
     * 
     * @param name the base name
     * 
     * @throws Exception in case of an error
     */
    protected void run(String name) throws Exception {

        Locale.setDefault(Locale.ENGLISH);
        runOmega(name, "writer/omegaWriter2/", new OcpOmegaWriter2());
    }

    /**
     * 7arb2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7arb2uni() throws Exception {

        run("7arb2uni");
    }

    /**
     * 7ber2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7ber2uni() throws Exception {

        run("7ber2uni");
    }

    /**
     * 7cyr2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7cyr2uni() throws Exception {

        run("7cyr2uni");
    }

    /**
     * 7hma2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7hma2uni() throws Exception {

        run("7hma2uni");
    }

    /**
     * 7in88593.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7in88593() throws Exception {

        run("7in88593");
    }

    /**
     * 7lbe2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7lbe2uni() throws Exception {

        run("7lbe2uni");
    }

    /**
     * 7pap2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7pap2uni() throws Exception {

        run("7pap2uni");
    }

    /**
     * 7pas2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7pas2uni() throws Exception {

        run("7pas2uni");
    }

    /**
     * 7snd2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7snd2uni() throws Exception {

        run("7snd2uni");
    }

    /**
     * 7syr2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7syr2uni() throws Exception {

        run("7syr2uni");
    }

    /**
     * 7tbe2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7tbe2uni() throws Exception {

        run("7tbe2uni");
    }

    /**
     * 7urd2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test7urd2uni() throws Exception {

        run("7urd2uni");
    }

    /**
     * 8mac-arb2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test8macarb2uni() throws Exception {

        run("8mac-arb2uni");
    }

    /**
     * 8mac-cyr2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void test8maccyr2uni() throws Exception {

        run("8mac-cyr2uni");
    }

    /**
     * apostr2psili.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testApostr2psili() throws Exception {

        run("apostr2psili");
    }

    /**
     * cuni2acad.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2acad() throws Exception {

        run("cuni2acad");
    }

    /**
     * cuni2amal.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2amal() throws Exception {

        run("cuni2amal");
    }

    /**
     * cuni2asv.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2asv() throws Exception {

        run("cuni2asv");
    }

    /**
     * cuni2bout.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2bout() throws Exception {

        run("cuni2bout");
    }

    /**
     * cuni2mona.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2mona() throws Exception {

        run("cuni2mona");
    }

    /**
     * cuni2nar.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2nar() throws Exception {

        run("cuni2nar");
    }

    /**
     * cuni2nva.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2nva() throws Exception {

        run("cuni2nva");
    }

    /**
     * cuni2oar-novow.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2oarNovow() throws Exception {

        run("cuni2oar-novow");
    }

    /**
     * cuni2oar.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testCuni2oar() throws Exception {

        run("cuni2oar");
    }

    /**
     * dblquote-point.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDblquotePoint() throws Exception {

        run("dblquote-point");
    }

    /**
     * destroy.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testDestroy() throws Exception {

        run("destroy");
    }

    /**
     * french2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testFrench2uni() throws Exception {

        run("french2uni");
    }

    /**
     * grpo2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGrpo2uni() throws Exception {

        run("grpo2uni");
    }

    /**
     * grpotilde2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testGrpotilde2uni() throws Exception {

        run("grpotilde2uni");
    }

    /**
     * inverted-iota-upsilon.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testInvertedIotaUpsilon() throws Exception {

        run("inverted-iota-upsilon");
    }

    /**
     * isogr2uni-verbatim.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testIsogr2uniVerbatim() throws Exception {

        run("isogr2uni-verbatim");
    }

    /**
     * isogr2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testIsogr2uni() throws Exception {

        run("isogr2uni");
    }

    /**
     * lat2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLat2uni() throws Exception {

        run("lat2uni");
    }

    /**
     * lowercase.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLowercase() throws Exception {

        run("lowercase");
    }

    /**
     * lunatesigma.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testLunatesigma() throws Exception {

        run("lunatesigma");
    }

    /**
     * macgr2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testMacgr2uni() throws Exception {

        run("macgr2uni");
    }

    /**
     * medbeta.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testMedbeta() throws Exception {

        run("medbeta");
    }

    /**
     * mixedgreek2uni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testMixedgreek2uni() throws Exception {

        run("mixedgreek2uni");
    }

    /**
     * uni2cuni.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUni2cuni() throws Exception {

        run("uni2cuni");
    }

    /**
     * uni2greek-verbatim.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUni2greekVerbatim() throws Exception {

        run("uni2greek-verbatim");
    }

    /**
     * uni2greek.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUni2greek() throws Exception {

        run("uni2greek");
    }

    /**
     * uni2lat-noffi.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUni2latnoffi() throws Exception {

        run("uni2lat-noffi");
    }

    /**
     * uni2lat.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUni2lat() throws Exception {

        run("uni2lat");
    }

    /**
     * uppercase-no-accents.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUppercaseNoAccents() throws Exception {

        run("uppercase-no-accents");
    }

    /**
     * uppercase.otp
     * 
     * @throws Exception in case of an error
     */
    @Test
    public final void testUppercase() throws Exception {

        run("uppercase");
    }

}
