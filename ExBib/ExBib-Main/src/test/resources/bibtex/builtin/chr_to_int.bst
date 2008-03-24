 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"a"	chr.to.int$ int.to.str$ write$ newline$
	"0"	chr.to.int$ int.to.str$ write$ newline$
	","	chr.to.int$ int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

