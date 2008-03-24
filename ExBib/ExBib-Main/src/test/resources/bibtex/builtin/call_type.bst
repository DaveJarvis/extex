 
ENTRY {title}{}{}

FUNCTION{book}{"a Book" write$ newline$}
FUNCTION{article}{"an Article" write$ newline$}

FUNCTION{testcase}{
	call.type$
	}

READ
ITERATE{testcase}

