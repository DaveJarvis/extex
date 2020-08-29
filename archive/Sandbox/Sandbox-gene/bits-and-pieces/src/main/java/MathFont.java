
import org.extex.core.UnicodeChar;
import org.extex.core.count.FixedCount;
import org.extex.core.dimen.Dimen;
import org.extex.core.dimen.FixedDimen;
import org.extex.core.glue.FixedGlue;
import org.extex.font.FontKey;
import org.extex.typesetter.tc.font.Font;

/**
 * This class encapsulates a font and provides access to the font dimens with
 * convenience methods.
 * 
 * @see "TTP [700]"
 * 
 * @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 * 
 * @version $Revision:5555 $
 */
public class MathFont implements Font {

    /**
     * The field <tt>serialVersionUID</tt> contains the ...
     */
    private static final long serialVersionUID = 1L;

    /**
     * The field <tt>font</tt> contains the encapsulated font.
     */
    private Font font;

    /**
     * Creates a new object.
     * 
     * @param font the font encapsulated
     */
    public MathFont(Font font) {

        super();
        this.font = font;
    }

    /**
     * Returns the actual FontKey for this font. The font key may differ from
     * the one requested.
     * 
     * @return the actual FontKey for this font.
     * 
     * @see org.extex.typesetter.tc.font.Font#getActualFontKey()
     */
    @Override
    public FontKey getActualFontKey() {

        // TODO getActualFontKey unimplemented
        return null;
    }

    /**
     * Returns the actual size of the font.
     * 
     * @return the actual size of the font.
     * 
     * @see org.extex.typesetter.tc.font.Font#getActualSize()
     */
    @Override
    public FixedDimen getActualSize() {

        // TODO getActualsize unimplemented
        return null;
    }

    /**
     * Returns the check sum of the font.
     * 
     * @return the check sum of the font
     * 
     * @see org.extex.typesetter.tc.font.Font#getCheckSum()
     */
    @Override
    public int getCheckSum() {

        return 0; // this.font.getCheckSum();
    }

    /**
     * Returns the depth of the character.
     * 
     * @param uc the character
     * 
     * @return the depth of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getDepth(org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getDepth(UnicodeChar uc) {

        return font.getDepth(uc);
    }

    /**
     * Returns the design size of the font.
     * 
     * @return the design size of the font
     * 
     * @see org.extex.typesetter.tc.font.Font#getDesignSize()
     */
    @Override
    public FixedDimen getDesignSize() {

        return font.getDesignSize();
    }

    /**
     * Getter for the ef code.
     * 
     * @param uc the character
     * 
     * @return the ef code
     * 
     * @see org.extex.typesetter.tc.font.Font#getEfCode(org.extex.core.UnicodeChar)
     */
    @Override
    public long getEfCode(UnicodeChar uc) {

        return font.getEfCode(uc);
    }

    /**
     * Returns the size of 'M'.
     * 
     * @return the size of 'M'.
     * 
     * @see org.extex.typesetter.tc.font.Font#getEm()
     */
    @Override
    public FixedDimen getEm() {

        return this.font.getEm();
    }

    /**
     * Returns the size of 'x'.
     * 
     * @return Returns the size of 'x'.
     * 
     * @see org.extex.typesetter.tc.font.Font#getEx()
     */
    @Override
    public FixedDimen getEx() {

        return this.font.getEx();
    }

    /**
     * Returns the size of the parameter with the name 'name'.
     * <p>
     * The size are multiples of the design size!
     * </p>
     * 
     * @param key the name of the parameter.
     * 
     * @return the size of the parameter with the name 'name'.
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontDimen(String)
     */
    @Override
    public FixedDimen getFontDimen(String key) {

        return this.font.getFontDimen(key);
    }

    /**
     * Returns the FontKey for this font.
     * 
     * @return the FontKey for this font
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontKey()
     */
    @Override
    public FontKey getFontKey() {

        return null; // this.font.getFontKey();
    }

    /**
     * Returns the name of the font.
     * 
     * @return the name of the font
     * 
     * @see org.extex.typesetter.tc.font.Font#getFontName()
     */
    @Override
    public String getFontName() {

        return this.font.getFontName();
    }

    /**
     * Returns the height of a character.
     * 
     * @param uc the character
     * 
     * @return the height of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getHeight(org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getHeight(UnicodeChar uc) {

        return font.getHeight(uc);
    }

    /**
     * Returns the hyphen character.
     * 
     * @return the hyphen character
     * 
     * @see org.extex.typesetter.tc.font.Font#getHyphenChar()
     */
    @Override
    public UnicodeChar getHyphenChar() {

        return this.font.getHyphenChar();
    }

    /**
     * Returns the italic correction of a character.
     * 
     * @param uc the character
     * 
     * @return the italic correction of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getItalicCorrection(org.extex.core.UnicodeChar)
     */
    @Override
    public FixedDimen getItalicCorrection(UnicodeChar uc) {

        return font.getItalicCorrection(uc);
    }

    /**
     * Returns the kerning between two characters.
     * 
     * @param uc1 the first character
     * @param uc2 the second character
     * 
     * @return the kerning between two characters
     * 
     * @see org.extex.typesetter.tc.font.Font#getKerning(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public FixedDimen getKerning(UnicodeChar uc1, UnicodeChar uc2) {

        return font.getKerning(uc1, uc2);
    }

    /**
     * Returns the ligature for two characters.
     * 
     * @param uc1 the first character
     * @param uc2 the second character
     * 
     * @return Returns the ligature for two characters
     * 
     * @see org.extex.typesetter.tc.font.Font#getLigature(org.extex.core.UnicodeChar,
     *      org.extex.core.UnicodeChar)
     */
    @Override
    public UnicodeChar getLigature(UnicodeChar uc1, UnicodeChar uc2) {

        return font.getLigature(uc1, uc2);
    }

    /**
     * Returns the scale factor of the font.
     * 
     * @return the scale factor of the font
     * 
     * @see org.extex.typesetter.tc.font.Font#getScaleFactor()
     */
    @Override
    public FixedCount getScaleFactor() {

        return font.getScaleFactor();
    }

    /**
     * Returns the skew char.
     * 
     * @return the skew char
     * 
     * @see org.extex.typesetter.tc.font.Font#getSkewChar()
     */
    @Override
    public UnicodeChar getSkewChar() {

        return this.font.getSkewChar();
    }

    /**
     * Returns the size of the 'space'.
     * 
     * @return the size of the 'space'.
     * 
     * @see org.extex.typesetter.tc.font.Font#getSpace()
     */
    @Override
    public FixedGlue getSpace() {

        return this.font.getSpace();
    }

    /**
     * Returns the width of a character.
     * 
     * @param uc the character
     * 
     * @return the width of the character
     * 
     * @see org.extex.typesetter.tc.font.Font#getWidth(org.extex.core.UnicodeChar)
     */
    @Override
    public FixedGlue getWidth(UnicodeChar uc) {

        return font.getWidth(uc);
    }

    /**
     * Determine whether the glyph for a given character is present in this
     * font.
     * 
     * @param uc the character
     * 
     * @return <code>true</code> iff the glyph is present
     * 
     * @see org.extex.typesetter.tc.font.Font#hasGlyph(org.extex.core.UnicodeChar)
     */
    @Override
    public boolean hasGlyph(UnicodeChar uc) {

        return font.hasGlyph(uc);
    }

    /**
     * Setter for the ef code. The ef code influences the stretchability of
     * characters. It has a positive value. 1000 means "normal" stretchability.
     * 
     * @param uc the character
     * @param code the associated code
     * 
     * @see org.extex.typesetter.tc.font.Font#setEfCode(org.extex.core.UnicodeChar,
     *      long)
     */
    @Override
    public void setEfCode(UnicodeChar uc, long code) {

        font.setEfCode(uc, code);
    }

    /**
     * Set the new value for the font parameter.
     * 
     * @param key the name of the parameter
     * @param value the value to set
     * 
     * @see org.extex.typesetter.tc.font.Font#setFontDimen(String, Dimen)
     */
    @Override
    public void setFontDimen(String key, Dimen value) {

        this.font.setFontDimen(key, value);
    }

    /**
     * Set the hyphen character.
     * 
     * @param hyphen the hyphen character
     * 
     * @see org.extex.typesetter.tc.font.Font#setHyphenChar(org.extex.core.UnicodeChar)
     */
    @Override
    public void setHyphenChar(UnicodeChar hyphen) {

        this.font.setHyphenChar(hyphen);
    }

    /**
     * Set the skew character.
     * 
     * @param skew the skew character
     * 
     * @see org.extex.typesetter.tc.font.Font#setSkewChar(org.extex.core.UnicodeChar)
     */
    @Override
    public void setSkewChar(UnicodeChar skew) {

        this.font.setSkewChar(skew);
    }

    /**
     * Returns a string representation of the object.
     * 
     * @return a string representation of the object
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return this.font.toString();
    }

}
