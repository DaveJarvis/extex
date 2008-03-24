 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#-37 int.to.str$ write$ newline$
	#-1 int.to.str$ write$ newline$
	#0 int.to.str$ write$ newline$
	#1 int.to.str$ write$ newline$
	#37 int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

