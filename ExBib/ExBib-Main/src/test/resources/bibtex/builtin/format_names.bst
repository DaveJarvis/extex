
ENTRY {author}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

INTEGERS{a n}
STRINGS{s fmt suite}

FUNCTION{tc}{
	'fmt :=
	'a :=
	's :=
	n #1 + 'n :=
	"  public void testFormat" write$ 
	suite write$
	n int.to.str$ write$
	"() throws Exception {" write$ newline$
	"    testFormat(" write$
	quote$ write$
	s write$
	quote$ write$
	", " write$
	newline$ "               " write$
	a int.to.str$ write$
	", " write$
	newline$ "               " write$
	quote$ write$
	fmt write$
	", " write$
	quote$ write$
	newline$ "               " write$
	quote$ write$
	s a fmt format.name$ write$
	quote$ write$
	");" write$ newline$
	"  }" write$ newline$
}

FUNCTION{testsuite1}{
  "01" 'suite :=
  #1 'n :=
  "Gerd Neugebauer and others" #2 "{ll}" tc
  "Gerd Neugebauer and others" #1 "abcd" tc
  "Gerd Neugebauer and others" #1 "ab{ll}cd" tc
  "Gerd Neugebauer" #1 "{ll}" tc
  "Gerd Neugebauer" #1 "{ff}" tc
  "Gerd Neugebauer" #1 "{vv}" tc
  "Gerd Neugebauer" #1 "{jj}" tc
  "Gerd Neugebauer" #1 "{l}" tc
  "Gerd Neugebauer" #1 "{f}" tc
  "Gerd Neugebauer" #1 "{v}" tc
  "Gerd Neugebauer" #1 "{j}" tc
}
FUNCTION{testsuite2}{
  "02" 'suite :=
  #1 'n :=
  "Cyrano de Bergerac" #1 "{ll}" tc
  "Cyrano de Bergerac" #1 "{vv}" tc
  "Cyrano de Bergerac" #1 "{ff}" tc
}
FUNCTION{testsuite3}{
  "03" 'suite :=
  #1 'n :=
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{ll-}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{vv-}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{ff-}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{ll~}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{vv~}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{ff~}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{l.}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{v.}" tc
  "Charles Louis Xavier Joseph de la Vall{\'e}e Poussin" #1 "{f.~}" tc
}
FUNCTION{testsuite4}{
  "04" 'suite :=
  #1 'n :=
  "Neugebauer, Gerd" #1 "{ll}" tc
  "Neugebauer, Gerd" #1 "{ff}" tc
  "Neugebauer, Gerd" #1 "{vv}" tc
  "Neugebauer, Gerd" #1 "{jj}" tc
  "Neugebauer, Gerd" #1 "{l}" tc
  "Neugebauer, Gerd" #1 "{f}" tc
  "Neugebauer, Gerd" #1 "{v}" tc
  "Neugebauer, Gerd" #1 "{j}" tc
}
FUNCTION{testsuite5}{
  "05" 'suite :=
  #1 'n :=
  "de Bergerac, Cyrano" #1 "{ll}" tc
  "de Bergerac, Cyrano" #1 "{vv}" tc
  "de Bergerac, Cyrano" #1 "{ff}" tc
}
FUNCTION{testsuite6}{
  "06" 'suite :=
  #1 'n :=
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{ll }" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{vv }" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{ff }" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{ll~}" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{vv~}" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{ff~}" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{l.}" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{v.}" tc
  "de la Vall{\'e}e Poussin, Charles Louis Xavier Joseph" #1 "{f.~}" tc
}
FUNCTION{testsuite7}{
  "07" 'suite :=
  #1 'n :=
  " Neugebauer ,  Gerd " #1 "{ll}" tc
  " Neugebauer ,  Gerd " #1 "{ff}" tc
  " Neugebauer ,  Gerd " #1 "{vv}" tc
  " Neugebauer ,  Gerd " #1 "{jj}" tc
  " Neugebauer ,  Gerd " #1 "{l}" tc
  " Neugebauer ,  Gerd " #1 "{f}" tc
  " Neugebauer ,  Gerd " #1 "{v}" tc
  " Neugebauer ,  Gerd " #1 "{j}" tc
}
FUNCTION{testsuite8}{
  "08" 'suite :=
  #1 'n :=
  "  de  Bergerac ,  Cyrano " #1 "{ll}" tc
  "  de  Bergerac ,  Cyrano " #1 "{vv}" tc
  "  de  Bergerac ,  Cyrano " #1 "{ff}" tc
}
FUNCTION{testsuite9a}{
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ll }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{vv }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ff }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ll~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{vv~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ff~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{l.}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{v.}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{f.~}" tc
}
FUNCTION{testsuite9b}{
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ll{:} }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{vv{:} }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ff{:} }" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ll{:}~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{vv{:}~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{ff{:}~}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{l{.:}.}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{v{.:}.}" tc
  "  de  la  Vall{\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph " #1 "{f{.:}.~}" tc
}
FUNCTION{testsuite9}{
  "09" 'suite :=
  #1 'n :=
  testsuite9a
  testsuite9b
}
FUNCTION{testsuite10}{
  "10" 'suite :=
  #1 'n :=
  "S.O. Meone" #1 "{ff~}{vv~}{ll}{, jj}" tc
  "A. Nother" #1 "{ff~}{vv~}{ll}{, jj}" tc
  "{A.} Nother" #1 "{ff~}{vv~}{ll}{, jj}" tc
  "{A.}~Nother" #1 "{ff~~}{vv~}{ll}{, jj}" tc
  "S. O. Meone and A. Nother and others" #1 "{ff~}{vv~}{ll}{, jj}" tc
  "Xavier Oscar Handley and S.O. Meone and A. Nother" #1 "{ff~}{vv~}{ll}{, jj}" tc
  "{\'{E}}. Masterly" #1 "{f.~}{vv~}{ll}{, jj}" tc
  "S.O. Meone" #1 "{{}f.~}{vv~}{ll}{, jj}" tc
}

READ
EXECUTE{testsuite1}
EXECUTE{testsuite2}
EXECUTE{testsuite3}
EXECUTE{testsuite4}
EXECUTE{testsuite5}
EXECUTE{testsuite6}
EXECUTE{testsuite7}
EXECUTE{testsuite8}
EXECUTE{testsuite9}
EXECUTE{testsuite10}
