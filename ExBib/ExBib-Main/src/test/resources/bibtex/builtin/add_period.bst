 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"{abc}" add.period$	write$ newline$
	"ABC" add.period$	write$ newline$
	"abc" add.period$	write$ newline$
	"abc." add.period$	write$ newline$
	"abc?" add.period$	write$ newline$
	"abc!" add.period$	write$ newline$
	"ab{c.}" add.period$	write$ newline$
	"ab{{c.}}" add.period$	write$ newline$
	"ab{c.1}" add.period$	write$ newline$
	}

READ
EXECUTE{testcase}

