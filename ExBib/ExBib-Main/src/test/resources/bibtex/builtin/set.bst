 
ENTRY {title}{}{}

INTEGERS{abc}
STRINGS{def}


FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#-2 'abc := abc int.to.str$ write$ newline$
	"xyz" 'def := def write$ newline$
	}

READ
EXECUTE{testcase}

