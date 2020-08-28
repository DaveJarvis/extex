![ExBib](ExBib-core/src/image/ExBib-side.png){.left}

εχBib
=====

εχBib is a bibliography processor in the spirit of
B[IB]{.small}TeX and B[IB]{.small}TeX 8. It is
meant to be usable as a plug-in replacement for those. In addition it
provides some extensions which make it more usable.

One extension is the extension language usable for producing the output.
B[IB]{.small}TeX comes with a stack based extension
language. εχBib has an interpreter for this language. Additionally some
more languages can be used for the same purpose. Any language which has
a binding for [BSF](http://jakarta.apache.org/bsf/) can be used for this
purpose. For instance Java and Groovy are such languages.

In addition several attempts have been made to make εχBib highly
configurable.

It is meant to be in line with
[εχTeX](http://www.extex.org). Eventually it can be
integrated into εχTeX or other tools. Thus the functionality
of the command line program is separated from the core functionality.

Objectives
----------

The development of εχBib has been lead by several objectives:

1.  The result of εχBib in compatibility mode should imitate as much as
    possible the behavior of
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    -- for runs without errors or warnings. This should be valid for the
    output file only. εχBib should not be concerned about the exact
    reproduction of tracing messages or the contents of the log file.
2.  The compatibility mode should *not* be the default. The default mode
    should be the εχBib mode, where certain extensions are provided and
    some curious design decisions of
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    are not imitated.
3.  One objective of εχBib was to provide a clean design which would
    make it easy to understand and enhance the program. As a result the
    whole program has been structured into several components which are
    tied together with interfaces and factories.
4.  εχBib should be configurable. This means that it should be possible
    to use different implementations of some of the core components.
    This could be used for example to mimic the behavior of
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    0.99c or
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    1.0 (as described in a TUGboat article).
5.  As implementation language Perl has been used for a first prototype.
    Then the implementation language has been switched to Java to make
    use of the Unicode features of this languages as well as to be
    closer to
    εχT[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X.
6.  As much as possible the inherent knowledge about
    T[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height: 0;"}X
    should be eliminated. If not possible it should be encapsulated in
    separate components to be exchangeable.
7.  εχBib should be multi-lingual in the sense that all messages
    produced by the system should be adaptable to a local language. As a
    proof of concept the languages English and German are supported
    right from the start.
8.  An easy update path should be provided for
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    users: Old documents should be processable with the same results.
    Migration scripts should be provided whenever necessary.

Bug Fixes
---------

The following defects of B[IB]{.small}[T]{style="margin-left:
-0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
are cured:

1.  No restriction on the sequence of entries in the database. I.e.
    referenced entries may preceded or follow the referencing entries.
2.  Better sorting has to be provided taking into account the various
    national conventions for sorting.
3.  No limit to some magical internal constants -- like the line length.
4.  Bug fix: flush the output buffer at the end of the program and not
    only when `newline$` is encountered.

Extensions
----------

The following extensions can be envisaged:

1.  Implement all new features of
    B[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    1.0.
2.  Add new primitives to the BST language.
3.  Extend the syntax
    forB[IB]{.small}[T]{style="margin-left: -0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X
    files
    -   Allow structured description of names.
    -   Make provisions for multi-language entries
    -   Allow structured documents: i.e. articles in journals -- without
        `crossref`
4.  Implement all features of
    B[IB]{.small}[T]{style="margin-left:-0.15em;"}[e]{style="text-transform:uppercase;font-size:90%;vertical-align:-0.4ex;margin-left:-0.2em;margin-right:-0.1em;line-height:0;"}X 8.

The εχBib Documentation
-----------------------

The εχBib documentation can be found in the sub-directory `ExBib-doc`.
It is not created automatically. Read the introduction in
[ExBib-doc/](ExBib-doc/) for how to create the
user\'s manual.

The εχBib Installer
-------------------

εχBib is delivered in form of an installer. This installer is created
with the help of [IzPack](http://izpack.org). Read the introduction in
[ExBib-Installer/](ExBib-Installer/) for how to
create the installer.

© 2008-2011 [The ExTeX Group](mailto:extex@dante.de)
