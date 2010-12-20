#******************************************************************************
#* $Id: Makefile,v 0.00 2010/12/14 20:00:16 gene Exp $
#******************************************************************************
#* Author: Gerd Neugebauer
#*=============================================================================

all: productbox.pdf

%.pdf: %.dtx %.sty %.ind
	pdflatex -draftmode $*.dtx
	pdflatex $*.dtx

%.sty: %.dtx *.ins
	rm -f $*.sty
	tex $*.ins

%.ind: %.idx
	makeindex -s gind $*

%.idx: %.dtx
	pdflatex -draftmode $*.dtx

parducbox.tgz dist:
	tar -cvzf parducbox.tgz productbox.dtx README Makefile productbox.pdf

#
