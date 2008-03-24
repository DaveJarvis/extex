 
ENTRY {title}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

INTEGERS{a b n}
STRINGS{s id}

FUNCTION{tc}{
	'a :=
	'b :=
	's :=
	n #1 + 'n :=
	"  public void testSubstring" write$ 
	id write$
	n int.to.str$ write$
	"() throws Exception {" write$ newline$
	"    testSubstring(" write$
	quote$ write$
	s write$
	quote$ write$
	", " write$
	b int.to.str$ write$
	", " write$
	a int.to.str$ write$
	", " write$
	quote$ write$
	s b a substring$ write$
	quote$ write$
	");" write$ newline$
	"  }" write$ newline$
}

FUNCTION{testsuite0}{
	"Zero" 'id :=
	#0 'n :=
    "" #0 #0 tc
    "" #1 #1 tc
    "" #-1 #1 tc
    "" #2 #1 tc
    "" #-2 #1 tc
    "" #1 #2 tc
    "" #-1 #2 tc
}
    
FUNCTION{testsuite1}{
	"One" 'id :=
	#0 'n :=
    "a" #0 #0 tc
    "a" #1 #1 tc
    "a" #-1 #1 tc
    "a" #2 #1 tc
    "a" #-2 #1 tc
    "a" #1 #2 tc
    "a" #-1 #2 tc
    "a" #-2 #-2 tc
    "a" #-2 #-3 tc
    "a" #-2 #-4 tc
    "a" #-3 #-6 tc
}
    
FUNCTION{testsuite2}{
	"Two" 'id :=
	#0 'n :=
    "ab" #0 #0 tc
    "ab" #1 #1 tc
    "ab" #-1 #1 tc
    "ab" #2 #1 tc
    "ab" #-2 #1 tc
    "ab" #1 #2 tc
    "ab" #-1 #2 tc
}

FUNCTION{testsuite3}{
	"Three" 'id :=
	#0 'n :=
    "abc" #0 #0 tc
    "abc" #1 #1 tc
    "abc" #-1 #1 tc
    "abc" #2 #1 tc
    "abc" #-2 #1 tc
    "abc" #1 #2 tc
    "abc" #-1 #2 tc
}
    
FUNCTION{testsuite4}{
	"Four" 'id :=
	#0 'n :=
    "abcd" #0 #0 tc
    "abcd" #1 #1 tc
    "abcd" #-1 #1 tc
    "abcd" #2 #1 tc
    "abcd" #-2 #1 tc
    "abcd" #1 #2 tc
    "abcd" #-1 #2 tc
}

FUNCTION{testsuite26}{
	"Twentysix" 'id :=
	#0 'n :=
	"abcdefghijklmnopqrstuvwxyz" #0 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #1 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #2 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #25 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #26 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #27 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #-1 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #-2 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #-25 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #-26 #2 tc
	"abcdefghijklmnopqrstuvwxyz" #-27 #2 tc
	}



READ
EXECUTE{testsuite0}
EXECUTE{testsuite1}
EXECUTE{testsuite2}
EXECUTE{testsuite3}
EXECUTE{testsuite4}
EXECUTE{testsuite26}

