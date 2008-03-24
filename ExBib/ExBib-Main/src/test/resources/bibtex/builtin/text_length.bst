 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase}{
	"123"		text.length$ int.to.str$ write$ newline$
	"1{23}4567"	text.length$ int.to.str$ write$ newline$
	"+*@"		text.length$ int.to.str$ write$ newline$
	"+{*@}abc7"	text.length$ int.to.str$ write$ newline$
	"+{\ae}abc7"	text.length$ int.to.str$ write$ newline$
	}

READ
EXECUTE{testcase}

