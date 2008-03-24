 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	#1 #2  = int.to.str$ write$ newline$
	#-1 #-2 = int.to.str$ write$ newline$
	#1 #-2 = int.to.str$ write$ newline$
	#0 #0 = int.to.str$ write$ newline$
	#2 #2 = int.to.str$ write$ newline$
	#-2 #-2 = int.to.str$ write$ newline$
	"" "" = int.to.str$ write$ newline$
	"a" "ab" = int.to.str$ write$ newline$
	"a" "a" = int.to.str$ write$ newline$
	"ab" "a" = int.to.str$ write$ newline$
	"abc" "abc" = int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

