#******************************************************************************
#* $Id$
#******************************************************************************
#* Author: Gerd Neugebauer
#*=============================================================================

MANUAL = extex-users
DEPLOYDIR = ../../build/target/application/html/documentation/man
SRCDIR = ../../ExTeX-*/src

all: $(MANUAL).pdf

extex-users.pdf: cfg syntax $(MANUAL).tex

%.pdf: %.toc %.ind
	@pdflatex $*
	@pdflatex $*
	@rm $*.out

%.ind: %.idx
	@makeindex -s doc.ist $*

%.idx: %.tex
	@pdflatex $*

%.toc: %.tex
	@pdflatex $*

tmp/configurations.tex cfg: bin/primitives.pl
	mkdir -p tmp
	perl bin/primitives.pl				\
		-sets tmp/configurations.tex		\
		-prim tmp/primitives.tex		\
		$(SRCDIR)/src/resources/config/*.xml 

tmp/syntax.tex syntax: bin/syntax.pl
	mkdir -p tmp
	perl bin/syntax.pl				\
		-out  tmp/syntax.tex			\
		$(SRCDIR)

deploy: $(MANUAL).pdf
	mkdir -p $(DEPLOYDIR)
	mv $(MANUAL).pdf $(DEPLOYDIR)

clean:
	rm -f *.ilg *.out *.toc *.aux *.ilg *.log *.dvi *.ind *~

veryclean distclean: clean
	rm -f $(MANUAL).pdf

#
