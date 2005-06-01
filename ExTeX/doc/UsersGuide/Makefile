#******************************************************************************
#* $Id$
#******************************************************************************
#* Author: Gerd Neugebauer
#*=============================================================================


all: extex-users.pdf

%.pdf:%.toc %.ind
	pdflatex $*
	pdflatex $*

%.ind:%.idx
	makeindex -s doc.ist $*

%.idx:%.tex
	pdflatex $*

%.toc:%.tex
	pdflatex $*

Configurations/configurations.tex cfg:
	perl Configurations/analyzeConfig ../../src/java/config/*.xml > Configurations/configurations.tex

clean:
	rm *.ilg *.out *.toc *.aux *.ilg *.log *.dvi *.ind

#
