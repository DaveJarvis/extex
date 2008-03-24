 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#1 #2  < int.to.str$ write$ newline$
	#2 #1  < int.to.str$ write$ newline$
	#-1 #-2 < int.to.str$ write$ newline$
	#-2 #-1 < int.to.str$ write$ newline$
	#1 #12  < int.to.str$ write$ newline$
	#12 #1  < int.to.str$ write$ newline$
	#-1 #-12 < int.to.str$ write$ newline$
	#-12 #-1 < int.to.str$ write$ newline$
	#1 #1 < int.to.str$ write$ newline$
	#0 #0 < int.to.str$ write$ newline$
	#2 #2 < int.to.str$ write$ newline$
	#-2 #-2 < int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

