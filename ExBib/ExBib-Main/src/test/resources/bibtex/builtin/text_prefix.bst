 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"123" #2 text.prefix$ write$ newline$
	"123" #5 text.prefix$ write$ newline$
	"1{23}4567" #1 text.prefix$ write$ newline$
	"1{23}4567" #2 text.prefix$ write$ newline$
	"1{23}4567" #3 text.prefix$ write$ newline$
	"1{23}4567" #4 text.prefix$ write$ newline$

	"+*@" #2 text.prefix$ write$ newline$
	"+*@" #5 text.prefix$ write$ newline$
	"+{*@}abc7" #1 text.prefix$ write$ newline$
	"+{*@}abc7" #2 text.prefix$ write$ newline$
	"+{*@}abc7" #3 text.prefix$ write$ newline$
	"+{*@}abc7" #4 text.prefix$ write$ newline$

	"Kn{\sadfdsa{12}}xx" #4 text.prefix$ write$ newline$
	}

READ
EXECUTE{testcase}

