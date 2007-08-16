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

package org.extex.font.format.vf.command;

import java.io.IOException;

import org.extex.font.exception.FontException;
import org.extex.font.format.dvi.DviInterpreter;
import org.extex.font.format.dvi.command.DviBOP;
import org.extex.font.format.dvi.command.DviChar;
import org.extex.font.format.dvi.command.DviCommand;
import org.extex.font.format.dvi.command.DviDown;
import org.extex.font.format.dvi.command.DviEOP;
import org.extex.font.format.dvi.command.DviExecuteCommand;
import org.extex.font.format.dvi.command.DviFntDef;
import org.extex.font.format.dvi.command.DviFntNum;
import org.extex.font.format.dvi.command.DviNOP;
import org.extex.font.format.dvi.command.DviPOP;
import org.extex.font.format.dvi.command.DviPost;
import org.extex.font.format.dvi.command.DviPostPost;
import org.extex.font.format.dvi.command.DviPre;
import org.extex.font.format.dvi.command.DviPush;
import org.extex.font.format.dvi.command.DviRight;
import org.extex.font.format.dvi.command.DviRule;
import org.extex.font.format.dvi.command.DviW;
import org.extex.font.format.dvi.command.DviX;
import org.extex.font.format.dvi.command.DviXXX;
import org.extex.font.format.dvi.command.DviY;
import org.extex.font.format.dvi.command.DviZ;
import org.extex.framework.i18n.Localizer;
import org.extex.framework.i18n.LocalizerFactory;
import org.extex.util.file.random.RandomAccessR;
import org.extex.util.xml.XMLStreamWriter;

/**
 * Convert the dvi values to xml for a virtual font.
 * 
 * @author <a href="mailto:m.g.n@gmx.de">Michael Niedermair</a>
 * @version $Revision$
 */
public class VfDviXml implements DviInterpreter, DviExecuteCommand {

    /**
     * The xml writer.
     */
    private XMLStreamWriter writer;

    /**
     * Creates a new object.
     * 
     * @param writer The xml writer.
     */
    public VfDviXml(XMLStreamWriter writer) {

        if (writer == null) {
            throw new IllegalArgumentException("writer");
        }
        this.writer = writer;

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.DviInterpreter#interpret(org.extex.util.file.random.RandomAccessR)
     */
    public void interpret(RandomAccessR rar) throws IOException, FontException {

        while (!rar.isEOF()) {
            DviCommand command = DviCommand.getNextCommand(rar);

            if (command instanceof DviChar) {
                DviChar cc = (DviChar) command;
                execute(cc);
                continue;
            } else if (command instanceof DviRight) {
                DviRight cc = (DviRight) command;
                execute(cc);
                continue;
            } else if (command instanceof DviDown) {
                DviDown cc = (DviDown) command;
                execute(cc);
                continue;
            } else if (command instanceof DviW) {
                DviW cc = (DviW) command;
                execute(cc);
                continue;
            } else if (command instanceof DviX) {
                DviX cc = (DviX) command;
                execute(cc);
                continue;
            } else if (command instanceof DviY) {
                DviY cc = (DviY) command;
                execute(cc);
                continue;
            } else if (command instanceof DviZ) {
                DviZ cc = (DviZ) command;
                execute(cc);
                continue;
            } else if (command instanceof DviBOP) {
                DviBOP cc = (DviBOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviEOP) {
                DviEOP cc = (DviEOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPOP) {
                DviPOP cc = (DviPOP) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPush) {
                DviPush cc = (DviPush) command;
                execute(cc);
                continue;
            } else if (command instanceof DviRule) {
                DviRule cc = (DviRule) command;
                execute(cc);
                continue;
            } else if (command instanceof DviXXX) {
                DviXXX cc = (DviXXX) command;
                execute(cc);
                continue;
            } else if (command instanceof DviFntDef) {
                DviFntDef cc = (DviFntDef) command;
                execute(cc);
                continue;
            } else if (command instanceof DviFntNum) {
                DviFntNum cc = (DviFntNum) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPost) {
                DviPost cc = (DviPost) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPostPost) {
                DviPostPost cc = (DviPostPost) command;
                execute(cc);
                continue;
            } else if (command instanceof DviPre) {
                DviPre cc = (DviPre) command;
                execute(cc);
                continue;
            }
            throw new FontException(localizer.format("DVI.forgetOpcode", String
                .valueOf(command.getOpcode())));
        }
    }

    /**
     * The field <tt>localizer</tt> contains the localizer. It is initiated
     * with a localizer for the name of this class.
     */
    private Localizer localizer = LocalizerFactory.getLocalizer(VfDviXml.class);

    /**
     * write the cmd to the writer.
     * 
     * @param cmd The command.
     * @throws IOException if a io error occurred.
     */
    private void write(DviCommand cmd) throws IOException {

        writer.writeStartElement(cmd.getName());
        writer.writeAttribute("opcode", cmd.getOpcode());
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviChar)
     */
    public void execute(DviChar command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("char", command.getCh());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviBOP)
     */
    public void execute(DviBOP command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviDown)
     */
    public void execute(DviDown command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviEOP)
     */
    public void execute(DviEOP command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviFntDef)
     */
    public void execute(DviFntDef command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("name", command.getFName());
            writer.writeAttribute("number", command.getFont());
            writer.writeAttribute("designsize", command.getDesignsize());
            writer.writeAttribute("scale", command.getScale());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviFntNum)
     */
    public void execute(DviFntNum command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("number", command.getFont());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPOP)
     */
    public void execute(DviPOP command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviNOP)
     */
    public void execute(DviNOP command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPost)
     */
    public void execute(DviPost command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPostPost)
     */
    public void execute(DviPostPost command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPre)
     */
    public void execute(DviPre command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviPush)
     */
    public void execute(DviPush command) throws FontException {

        try {
            write(command);

            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviRight)
     */
    public void execute(DviRight command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviRule)
     */
    public void execute(DviRule command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("width", command.getWidth());
            writer.writeAttribute("height", command.getHeight());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviW)
     */
    public void execute(DviW command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviX)
     */
    public void execute(DviX command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviXXX)
     */
    public void execute(DviXXX command) throws FontException {

        try {
            write(command);
            writer.writeComment(command.getXXXString());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviY)
     */
    public void execute(DviY command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

    /**
     * {@inheritDoc}
     * 
     * @see org.extex.font.format.dvi.command.DviExecuteCommand#execute(org.extex.font.format.dvi.command.DviZ)
     */
    public void execute(DviZ command) throws FontException {

        try {
            write(command);
            writer.writeAttribute("value", command.getValue());
            writer.writeEndElement();

        } catch (IOException e) {
            throw new FontException(e.getLocalizedMessage());
        }

    }

}
