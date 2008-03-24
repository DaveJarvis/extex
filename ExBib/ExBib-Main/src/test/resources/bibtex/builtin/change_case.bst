
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"abc" "l" change.case$	write$ newline$
	"ABC" "l" change.case$	write$ newline$
	"abc" "L" change.case$	write$ newline$
	"ABC" "L" change.case$	write$ newline$
	"abc" "u" change.case$	write$ newline$
	"ABC" "u" change.case$	write$ newline$
	"abc" "U" change.case$	write$ newline$
	"ABC" "U" change.case$	write$ newline$
	"abc" "t" change.case$	write$ newline$
	"ABC" "t" change.case$	write$ newline$
	"abc" "t" change.case$	write$ newline$
	"ABC" "t" change.case$	write$ newline$
}
FUNCTION{testcase1}{
	"abc" "l" change.case$	write$ newline$
	"ABC" "l" change.case$	write$ newline$
	"abc" "L" change.case$	write$ newline$
	"ABC" "L" change.case$	write$ newline$
	"abc" "u" change.case$	write$ newline$
	"ABC" "u" change.case$	write$ newline$
	"abc" "U" change.case$	write$ newline$
	"ABC" "U" change.case$	write$ newline$
	"abc" "t" change.case$	write$ newline$
	"ABC" "t" change.case$	write$ newline$
	"abc" "t" change.case$	write$ newline$
	"ABC" "t" change.case$	write$ newline$
}
FUNCTION{testcase2}{
	"a{b}c" "l" change.case$	write$ newline$
	"A{B}C" "l" change.case$	write$ newline$
	"a{b}c" "L" change.case$	write$ newline$
	"A{B}C" "L" change.case$	write$ newline$
	"a{b}c" "u" change.case$	write$ newline$
	"A{B}C" "u" change.case$	write$ newline$
	"a{b}c" "U" change.case$	write$ newline$
	"A{B}C" "U" change.case$	write$ newline$
	"a{b}c" "t" change.case$	write$ newline$
	"A{B}C" "t" change.case$	write$ newline$
	"a{b}c" "t" change.case$	write$ newline$
	"A{B}C" "t" change.case$	write$ newline$
	}
FUNCTION{testcase3}{
	"ABC {TeX}DEF" "l" change.case$	write$ newline$
	"ABC {TeX}DEF" "t" change.case$	write$ newline$
	"ABC {TeX}def" "u" change.case$	write$ newline$
	}

READ
EXECUTE{testcase}
EXECUTE{testcase1}
EXECUTE{testcase2}
EXECUTE{testcase3}

