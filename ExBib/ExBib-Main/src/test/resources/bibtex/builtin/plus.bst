 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#1 #2 + int.to.str$ write$ newline$
	#-1 #2 + int.to.str$ write$ newline$
	#1 #-2 + int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

