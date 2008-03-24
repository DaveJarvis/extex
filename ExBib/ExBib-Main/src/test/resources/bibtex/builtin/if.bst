 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#1 {"then"} {"else"} if$ write$ newline$
	#0 {"then"} {"else"} if$ write$ newline$
	}

READ
EXECUTE{testcase}

