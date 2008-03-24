
ENTRY {a}{i}{s}

FUNCTION{book}{
}
FUNCTION{article}{
}

FUNCTION{list}{
	i #1 + 'i :=
	i int.to.str$ write$ newline$
	s "+" * 's :=
	s write$ newline$
	}

READ
ITERATE{list}


