
ENTRY {a}{}{}

FUNCTION{book}{
}
FUNCTION{article}{
}

FUNCTION{def}{
	cite$ 'sort.key$ :=
	}

FUNCTION{list}{
	cite$ write$
	newline$
	}

READ
ITERATE{def}
SORT
ITERATE{list}

