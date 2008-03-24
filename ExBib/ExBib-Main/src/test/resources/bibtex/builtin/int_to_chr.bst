 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#64 int.to.str$ write$ newline$
	#65 int.to.str$ write$ newline$
	#48 int.to.str$ write$ newline$
	#32 int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

