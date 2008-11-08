
ENTRY
  { address
    author
    booktitle
    chapter
    edition
    editor
    howpublished
    institution
    journal
    key
    month
    note
    number
    organization
    pages
    publisher
    school
    series
    title
    type
    volume
    year
  }
  {}
  { label extra.label sort.label }

INTEGERS { output.state before.all mid.sentence after.sentence after.block }

STRINGS{s t}

FUNCTION {new.block}
{ output.state before.all =
    'skip$
    { after.block 'output.state := }
  if$
}

FUNCTION {new.block.checka}
{ empty$
    'skip$
    'new.block
  if$
}

%FUNCTION {output.nonnull}
%{ 's :=
% pop$
%  s
%}

%FUNCTION {output}
% duplicate$ empty$
%   'pop$
%    'output.nonnull
%  if$
%}

%FUNCTION {w}
%{
%  ""
%  #12
% {newline$ duplicate$ #0 >}
%  {
%    duplicate$ write$
%    #-1 +
%    swap$
%   "x" *
%    swap$
%    duplicate$ write$
%  }
%  while$
%  pop$
%}
