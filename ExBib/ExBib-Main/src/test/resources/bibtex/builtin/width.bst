 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

INTEGERS{n}
STRINGS{s suite}

FUNCTION{tc}{
	's :=
	n #1 + 'n :=
	"  public void testWidth" write$ 
	suite write$
	n int.to.str$ write$
	"() throws Exception {" write$ newline$
	"    testWidth(" write$
	quote$ write$
	s write$
	quote$ write$
	", " write$
	s width$ int.to.str$ write$
	");" write$ newline$
	"  }" write$ newline$
}

FUNCTION{testsuite1}{
  "01" 'suite :=
  #1 'n :=
  
  "abc" tc
  "abcdefghijklmnopqrstuvwxyz" tc
  ".,-=)(/&%$§!" tc
  "{}" tc
  "AA" tc
  "\AA" tc
  "ss" tc
  "\ss" tc
  "ae" tc
  "\AE" tc
  "oe" tc
  "\OE" tc
  "\singleletter" tc
  ".\singleletter." tc
  "OX{\singleletter{stoc}}83b" tc
  "OXstoc83b" tc
  "{\AE}" tc
}
READ
EXECUTE{testsuite1}

