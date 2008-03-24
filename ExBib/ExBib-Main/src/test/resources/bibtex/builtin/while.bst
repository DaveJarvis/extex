 
ENTRY {title}{}{}
INTEGERS{x}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
		#3 'x :=
		{ x #0 > }
		{ x int.to.str$ write$ newline$
		  x #1 - 'x :=
		} while$
	}

READ
EXECUTE{testcase}

