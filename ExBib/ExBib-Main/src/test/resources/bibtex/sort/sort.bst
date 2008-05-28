
ENTRY {value}{}{}
INTEGERS {x y}
STRINGS {u.v}

FUNCTION{item}{
  value write$
  newline$
}

FUNCTION{make.sort.key}{
  value 'sort.key$ :=
}

READ
ITERATE{make.sort.key}
SORT
ITERATE{call.type$}
