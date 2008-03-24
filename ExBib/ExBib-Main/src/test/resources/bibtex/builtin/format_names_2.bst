
ENTRY {author}{}{}

FUNCTION{book}{}
FUNCTION{article}{}

FUNCTION{testcase0}{
  "Gerd Neugebauer and others" #2 "{ll}" duplicate$ write$ format.name$ write$ " // others" write$ newline$
  "Gerd Neugebauer and others" #1 "abcd" duplicate$ write$ format.name$ write$ " // abcd" write$ newline$
  "Gerd Neugebauer and others" #1 "ab{ll}cd" duplicate$ write$ format.name$ write$ " // abNeugebauercd" write$ newline$
}
FUNCTION{testcase1}{
  "Gerd Neugebauer" #1 "{ll}" duplicate$ write$ format.name$ write$ " // Neugebauer" write$ newline$
  "Gerd Neugebauer" #1 "{ff}" duplicate$ write$ format.name$ write$ " // Gerd" write$ newline$
  "Gerd Neugebauer" #1 "{vv}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Gerd Neugebauer" #1 "{jj}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Gerd Neugebauer" #1 "{l}" duplicate$ write$ format.name$ write$ " // N" write$ newline$
  "Gerd Neugebauer" #1 "{f}" duplicate$ write$ format.name$ write$ " // G" write$ newline$
  "Gerd Neugebauer" #1 "{v}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Gerd Neugebauer" #1 "{j}" duplicate$ write$ format.name$ write$ " // " write$ newline$
}
FUNCTION{testcase2}{
  "Cyrano de Bergerac" #1 "{ll}" duplicate$ write$ format.name$ write$ " // Bergerac" write$ newline$
  "Cyrano de Bergerac" #1 "{vv}" duplicate$ write$ format.name$ write$ " // de" write$ newline$
  "Cyrano de Bergerac" #1 "{ff}" duplicate$ write$ format.name$ write$ " // Cyrano" write$ newline$
}
FUNCTION{testcase3}{
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{ll-}" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e Poussin" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{vv-}" duplicate$ write$ format.name$ write$ " // de la" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{ff-}" duplicate$ write$ format.name$ write$ " // Charles Louis Xavier Joseph" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{ll~}" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e~Poussin" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{vv~}" duplicate$ write$ format.name$ write$ " // de~la" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{ff~}" duplicate$ write$ format.name$ write$ " // Charles~Louis~Xavier~Joseph" write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{l.}" duplicate$ write$ format.name$ write$ " // V.P." write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{v.}" duplicate$ write$ format.name$ write$ " // d.l." write$ newline$
  "Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin" #1 "{f.~}" duplicate$ write$ format.name$ write$ " // C.~L.~X.~J" write$ newline$
}
FUNCTION{testcase4}{
  "Neugebauer, Gerd" #1 "{ll}" duplicate$ write$ format.name$ write$ " // Neugebauer" write$ newline$
  "Neugebauer, Gerd" #1 "{ff}" duplicate$ write$ format.name$ write$ " // Gerd" write$ newline$
  "Neugebauer, Gerd" #1 "{vv}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Neugebauer, Gerd" #1 "{jj}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Neugebauer, Gerd" #1 "{l}" duplicate$ write$ format.name$ write$ " // N" write$ newline$
  "Neugebauer, Gerd" #1 "{f}" duplicate$ write$ format.name$ write$ " // G" write$ newline$
  "Neugebauer, Gerd" #1 "{v}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  "Neugebauer, Gerd" #1 "{j}" duplicate$ write$ format.name$ write$ " // " write$ newline$
}
FUNCTION{testcase5}{
  "de Bergerac, Cyrano" #1 "{ll}" duplicate$ write$ format.name$ write$ " // Bergerac" write$ newline$
  "de Bergerac, Cyrano" #1 "{vv}" duplicate$ write$ format.name$ write$ " // de" write$ newline$
  "de Bergerac, Cyrano" #1 "{ff}" duplicate$ write$ format.name$ write$ " // Cyrano" write$ newline$

  "Bergerac, Cyrano de" #1 "{ll}" duplicate$ write$ format.name$ write$ " // Bergerac" write$ newline$
  "Bergerac, Cyrano de" #1 "{vv}" duplicate$ write$ format.name$ write$ " // de" write$ newline$
  "Bergerac, Cyrano de" #1 "{ff}" duplicate$ write$ format.name$ write$ " // Cyrano" write$ newline$
}
FUNCTION{testcase6}{
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{ll }" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e Poussin" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{vv }" duplicate$ write$ format.name$ write$ " // de la" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{ff }" duplicate$ write$ format.name$ write$ " // Charles Louis Xavier Joseph" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{ll~}" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e~Poussin" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{vv~}" duplicate$ write$ format.name$ write$ " // de~la" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{ff~}" duplicate$ write$ format.name$ write$ " // Charles~Louis~Xavier~Joseph" write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{l.}" duplicate$ write$ format.name$ write$ " // V.P." write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{v.}" duplicate$ write$ format.name$ write$ " // d.l." write$ newline$
  "Vall{\\'e}e Poussin, Charles Louis Xavier Joseph de la" #1 "{f.~}" duplicate$ write$ format.name$ write$ " // C.L.X.J" write$ newline$
}
FUNCTION{testcase7}{
  " Neugebauer ,  Gerd " #1 "{ll}" duplicate$ write$ format.name$ write$ " // Neugebauer" write$ newline$
  " Neugebauer ,  Gerd " #1 "{ff}" duplicate$ write$ format.name$ write$ " // Gerd" write$ newline$
  " Neugebauer ,  Gerd " #1 "{vv}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  " Neugebauer ,  Gerd " #1 "{jj}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  " Neugebauer ,  Gerd " #1 "{l}" duplicate$ write$ format.name$ write$ " // N" write$ newline$
  " Neugebauer ,  Gerd " #1 "{f}" duplicate$ write$ format.name$ write$ " // G" write$ newline$
  " Neugebauer ,  Gerd " #1 "{v}" duplicate$ write$ format.name$ write$ " // " write$ newline$
  " Neugebauer ,  Gerd " #1 "{j}" duplicate$ write$ format.name$ write$ " // " write$ newline$
}
FUNCTION{testcase8}{
  " Bergerac ,  Cyrano  de " #1 "{ll}" duplicate$ write$ format.name$ write$ " // Bergerac" write$ newline$
  " Bergerac ,  Cyrano  de " #1 "{vv}" duplicate$ write$ format.name$ write$ " // de" write$ newline$
  " Bergerac ,  Cyrano  de " #1 "{ff}" duplicate$ write$ format.name$ write$ " // Cyrano" write$ newline$
}
FUNCTION{testcase9}{
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{ll }" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e Poussin" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{vv }" duplicate$ write$ format.name$ write$ " // de la" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{ff }" duplicate$ write$ format.name$ write$ " // Charles Louis Xavier Joseph" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{ll~}" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e~Poussin" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{vv~}" duplicate$ write$ format.name$ write$ " // de~la" write$ newline$
}
FUNCTION{testcase9a}{
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{ff~}" duplicate$ write$ format.name$ write$ " // Charles~Louis~Xavier~Joseph" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{l.}" duplicate$ write$ format.name$ write$ " // V.P." write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{v.}" duplicate$ write$ format.name$ write$ " // d.l." write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{f.~}" duplicate$ write$ format.name$ write$ " // C.~L.~X.~J" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{ll{:} }" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e Poussin:" write$ newline$
  " Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  de  la  " #1 "{vv{:} }" duplicate$ write$ format.name$ write$ " // de la:" write$ newline$
}
FUNCTION{testcase9b}{
  " de  la   Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{ff{:} }" duplicate$ write$ format.name$ write$ " // Charles Louis Xavier Joseph:" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{ll{:}~}" duplicate$ write$ format.name$ write$ " // Vall{\\'e}e:Poussin~" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{vv{:}~}" duplicate$ write$ format.name$ write$ " // de:la~" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{ff{:}~}" duplicate$ write$ format.name$ write$ " // Charles~Louis~Xavier~Joseph:" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{l{.:}.}" duplicate$ write$ format.name$ write$ " // V.P.:" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{v{.:}.}" duplicate$ write$ format.name$ write$ " // d.l.:" write$ newline$
  " de  la  Vall{\\'e}e  Poussin ,  Charles  Louis  Xavier  Joseph  " #1 "{f{.:}.~}" duplicate$ write$ format.name$ write$ " // C.~L.~X.~J.:" write$ newline$
}
FUNCTION{testcase10}{
  "S.O. Meone" #1 "{ff~}{vv~}{ll}{, jj}" duplicate$ write$ format.name$ write$ " // S.O. Meone" write$ newline$
  "A. Nother" #1 "{ff~}{vv~}{ll}{, jj}" duplicate$ write$ format.name$ write$ " // A.~Nother" write$ newline$
  "S. O. Meone and A. Nother and others" #1 "{ff~}{vv~}{ll}{, jj}" duplicate$ write$ format.name$ write$ " // S.~O. Meone" write$ newline$
  "Xavier Oscar Handley and S.O. Meone and A. Nother" #1 "{ff~}{vv~}{ll}{, jj}" duplicate$ write$ format.name$ write$ " // Xavier~Oscar Handley" write$ newline$
}
FUNCTION{testsuite}{
  testcase0
  testcase1
  testcase2
  testcase3
  testcase4
  testcase5
  testcase6
  testcase7
  testcase8
  testcase9
  testcase9a
  testcase9b
  testcase10
}


READ
EXECUTE{testsuite}
