#******************************************************************************
#* $Id$
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

productbox.tgz dist: productbox.pdf
	tar -cvzf productbox.tgz productbox.dtx README Makefile productbox.pdf

clean:
	rm productbox.aux productbox.ilg productbox.idx productbox.log productbox.toc

distclean: clean
	rm productbox.sty

#
