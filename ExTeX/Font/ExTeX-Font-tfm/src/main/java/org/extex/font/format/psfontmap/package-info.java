/*
 * Copyright (C) 2003-2011 The ExTeX Group and individual authors listed below
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
/**
 *Contains definitions and implementations for the
 * psfonts.map-file.
 *
 * <p>Fileformat</p>
 *
 * <p>Example:</p>
 *
 * <pre>
 *   cmb10 CMB10 &quot; TeXf7b6d320Encoding ReEncodeFont &quot; &lt;f7b6d320.enc &lt;cmb10.pfb
 *   cmbsy10 CMBSY10 &quot; TeX10037936Encoding ReEncodeFont &quot; &lt;10037936.enc &lt;cmbsy10.pfb
 *   cmbsy5 CMBSY5 &lt;cmbsy5.pfb
 *   cmbsy6 CMBSY7 &lt;cmbsy7.pfb
 * </pre>
 *
 * <dl>
 * 	<dt>cmb10</dt>
 * 	<dd>The filebname of the font.</dd>
 * 	<dt>CMB10</dt>
 * 	<dd>The name of the font.</dd>
 * 	<dt>&quot; TeXf7b6d320Encoding ReEncodeFont &quot;</dt>
 * 	<dd>Encoding (optional)</dd>
 * 	<dt>&lt;f7b6d320.enc</dt>
 * 	<dd>The encodingfile for the font. (optional)</dd>
 * 	<dt>&lt;cmb10.pfb</dt>
 * 	<dd>The corespondig pfb-file. (optional)</dd>
 * </dl>
 */

package org.extex.font.format.psfontmap;
