 
ENTRY {title author undef}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	author empty$ {"then"} {"else"} if$ write$ newline$
	undef empty$ {"then"} {"else"} if$ write$ newline$
	}

READ
ITERATE{testcase}

