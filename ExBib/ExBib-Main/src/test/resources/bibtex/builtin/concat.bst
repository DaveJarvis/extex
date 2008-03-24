 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abc" "def" * write$ newline$
	"" "def" * write$ newline$
	"abc" "" * write$ newline$
	"" "" * write$ newline$
	}

READ
EXECUTE{testcase}

