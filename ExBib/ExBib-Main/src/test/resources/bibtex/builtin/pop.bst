 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abc" "def" pop$ write$ newline$ stack$
	"abc" #123  pop$ write$ newline$ stack$
	}

READ
EXECUTE{testcase}

