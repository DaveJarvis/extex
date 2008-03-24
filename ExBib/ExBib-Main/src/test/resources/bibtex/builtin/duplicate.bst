 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abc" duplicate$ write$ write$ newline$
	#37 duplicate$ + int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

