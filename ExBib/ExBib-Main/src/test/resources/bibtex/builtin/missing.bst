 
ENTRY {title author undef}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	author missing$ {"then"} {"else"} if$ write$ newline$
	title missing$ {"then"} {"else"} if$ write$ newline$
	undef missing$ {"then"} {"else"} if$ write$ newline$
	}

READ
ITERATE{testcase}

