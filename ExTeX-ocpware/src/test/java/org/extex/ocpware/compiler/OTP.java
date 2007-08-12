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

package org.extex.ocpware.compiler;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides the contents of some OTP files as String.
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * @version $Revision:6007 $
 */
public final class OTP {

    /**
     * TODO gene: missing JavaDoc
     * 
     * @param name the name of the resource
     * 
     * @return the file contents
     */
    public static String omegaOtp(String name) {

        String out = "org/extex/ocpware/omega/otp/omega/" + name + ".otp";
        InputStream stream =
                OTP.class.getClassLoader().getResourceAsStream(out);

        if (stream == null) {
            return null;
        }

        try {
            StringBuffer sb = new StringBuffer();
            for (int c = stream.read(); c >= 0; c = stream.read()) {
                if (c != '\r') {
                    sb.append((char) c);
                }
            }
            stream.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException("missing " + out);
        }
    }

    /**
     * Creates a new object.
     * 
     */
    private OTP() {

        // unused
    }

}
