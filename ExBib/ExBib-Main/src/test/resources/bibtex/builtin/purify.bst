 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abcdefghijklmnopqrstuvwxyz" purify$ write$ newline$
	"ABCDEFGHIJKLMNOPQRSTUVWXYZ" purify$ write$ newline$
	"0123456789" purify$ write$ newline$
	"a  b~c-d" purify$ write$ newline$
	"[]()" purify$ write$ newline$
	"{}" purify$ write$ newline$
	"{\ae}\ae{\AE}\AE" purify$ write$ newline$
	"{\oe}\oe{\OE}\OE" purify$ write$ newline$
	"{\ss}\ss" purify$ write$ newline$
	"{\'\i}\'\i" purify$ write$ newline$
	"{\'\j}\'\j" purify$ write$ newline$
	"{{\LaTeX}}123{x\LaTeX}123\LaTeX" purify$ write$ newline$
	"{{\TeX}}123{xxx\TeX}123\TeX" purify$ write$ newline$
	"{\TeX}123{xxx\TeX}123\TeX" purify$ write$ newline$
	}

READ
EXECUTE{testcase}

