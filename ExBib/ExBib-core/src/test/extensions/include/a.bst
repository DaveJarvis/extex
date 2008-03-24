ENTRY
  {
  }
  {}
  { }

FUNCTION {article}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {book}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {booklet}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {inbook}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {incollection}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {inproceedings}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {conference} { inproceedings }

FUNCTION {manual}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {mastersthesis}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {misc}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {phdthesis}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {proceedings}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {techreport}
{ type$ " " * write$
  cite$ write$ newline$
}

FUNCTION {unpublished}
{ type$ " " * write$
  cite$ write$ newline$
}

READ

ITERATE {call.type$}
