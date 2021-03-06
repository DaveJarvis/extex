 %%
 %% Copyright (C) 2005-2008 The ExTeX Group and individual authors listed below
 %%
 %% This library is free software; you can redistribute it and/or modify it
 %% under the terms of the GNU Lesser General Public License as published by the
 %% Free Software Foundation; either version 2.1 of the License, or (at your
 %% option) any later version.
 %%
 %% This library is distributed in the hope that it will be useful, but WITHOUT
 %% ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 %% FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 %% for more details.
 %%
 %% You should have received a copy of the GNU Lesser General Public License
 %% along with this library; if not, write to the Free Software Foundation,
 %% Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 %%
 %%
 %% @author <a href="mailto:gene@gerd-neugebauer.de">Gerd Neugebauer</a>
 %%
\NeedsTeXFormat{LaTeX2e}
\LoadClass[12pt,div12,a4paper]{scrbook}

\usepackage[latin1]{inputenc}
\usepackage[T1]{fontenc}
\usepackage{booktabs}
\usepackage{multicol}
\usepackage{listings}
\usepackage{graphicx,pgf,pgfshade}

\definecolor{hellmagenta}{rgb}{1,0.75,0.9}
\definecolor{hellcyan}{rgb}{0.75,1,0.9}
\definecolor{hellgelb}{rgb}{1,1,0.8}
\definecolor{backgroundColor}{rgb}{1,1,0.8}
\definecolor{javaBackgroundColor}{rgb}{0.8,1,0.8}
\definecolor{colKeys}{rgb}{0,0,.8}
\definecolor{colIdentifier}{rgb}{0,0,0}
\definecolor{colComments}{rgb}{.7,.2,.2}
\definecolor{colString}{rgb}{0,0.5,0}
\definecolor{darkyellow}{rgb}{1,0.9,0}
\definecolor{colRule}{rgb}{.8,.8,.64}
%
\usepackage{listings}
\lstset{%
    language=[AlLaTeX]TEX,%
    float=hbp,%
    basicstyle=\ttfamily\small, %
    identifierstyle=\color{colIdentifier}, %
    keywordstyle=\color{colKeys}\bf\tt, %
    stringstyle=\color{colString}, %
    commentstyle=\color{colComments}, %
    columns=flexible, %
    tabsize=3, %
    frame=single, %
    extendedchars=true, %
    showspaces=false, %
    showstringspaces=false, %
    numbers=none,% left, %
    numberstyle=\tiny\sf, %
    breaklines=true, %
    backgroundcolor=\color{backgroundColor}, %
    breakautoindent=true, %
    captionpos=b,%
    xleftmargin=\fboxsep,%
    xrightmargin=\fboxsep%
}

\lstset{%
    language=Java,%
    float=hbp,%
    basicstyle=\ttfamily\small, %
    identifierstyle=\color{colIdentifier}, %
    keywordstyle=\color{colKeys}\bf\tt, %
    stringstyle=\color{colString}, %
    commentstyle=\itshape\color{colComments}, %
    columns=flexible, %
    tabsize=4, %
    frame=single, %
    extendedchars=true, %
    showspaces=false, %
    showstringspaces=false, %
    numbers=none,% =left, %
    numberstyle=\tiny\sf, %
    breaklines=true, %
    backgroundcolor=\color{javaBackgroundColor}, %
    breakautoindent=true, %
    captionpos=b,%
    xleftmargin=\fboxsep,%
    xrightmargin=\fboxsep%
}

\lstset{%
    float=hbp,%
    basicstyle=\ttfamily\small, %
    identifierstyle=\color{colIdentifier}, %
    keywordstyle=\color{colKeys}\bf\tt, %
    stringstyle=\color{colString}, %
    commentstyle=\bfseries\color{colComments}, %
    rulecolor=\color{colRule}, %
    columns=flexible, %
    tabsize=4, %
    frame=single, %
    extendedchars=true, %
    showspaces=false, %
    showstringspaces=false, %
    numbers=none,% =left, %
    numberstyle=\tiny\sf, %
    breaklines=true, %
    backgroundcolor=\color{backgroundColor}, %
    breakautoindent=true, %
    captionpos=b,%
    xleftmargin=\fboxsep,%
    xrightmargin=\fboxsep%
}

\usepackage{hyperref}
\definecolor{linkColor}{rgb}{.2,.2,.7}
\hypersetup{colorlinks, 
            citecolor=linkColor,
            filecolor=linkColor,
            linkcolor=linkColor,
            urlcolor=linkColor}
           
\providecommand*{\ExTeX}{\ifx\texorpdfstring\undefined
  \textrm{% the logo comes always with serifs
    \ensuremath{\textstyle\varepsilon_{\kern-0.15em\mathcal{X}}}%
    \kern-.15em\TeX}%
  \else\texorpdfstring{%
  \textrm{% the logo comes always with serifs
    \ensuremath{\textstyle\varepsilon_{\kern-0.15em\mathcal{X}}}%
    \kern-.15em\TeX
  }}{ExTeX}%
  \fi
}
\providecommand*{\ExBib}{\ifx\texorpdfstring\undefined
  \textrm{% the logo comes always with serifs
    \ensuremath{\textstyle\varepsilon_{\kern-0.15em\mathcal{X}}}%
    \kern-.15em\Bib}%
  \else\texorpdfstring{%
  \textrm{% the logo comes always with serifs
    \ensuremath{\textstyle\varepsilon_{\kern-0.15em\mathcal{X}}}%
    \kern-.15em Bib
  }}{ExBib}%
  \fi
}
\newcommand\ExTeXbox{%
  \begin{pgfpicture}{0mm}{0mm}{128mm}{64mm}
    \pgfdeclarehorizontalshading{thisShading}{120mm}%
    {color(0mm)=(white); rgb(160mm)=(.65,.65,.65)}%

    \pgfrect[fill]{\pgfpoint{0.5mm}{-0.5mm}}{\pgfpoint{128.5mm}{63.5mm}}

    \begin{pgfscope}
      \pgfmoveto{\pgforigin}
      \pgflineto{\pgfpoint{0mm}{64mm}}
      \pgflineto{\pgfpoint{128mm}{64mm}}
      \pgflineto{\pgfpoint{128mm}{0mm}}
      \pgfclosepath
      \pgfclip
      \pgfputat{\pgfpoint{64mm}{32mm}}{%
        \begin{pgfrotateby}{\pgfdegree{340}}
          \pgfbox[center,center]{\pgfuseshading{thisShading}}
        \end{pgfrotateby}}
    \end{pgfscope}
    
    \begin{pgfmagnify}{4}{4}
      \pgfputat{\pgfpoint{15.85mm}{8.15mm}}{%
        \pgfbox[center,center]{\color{white}\Huge\ExTeX}}
      \pgfputat{\pgfpoint{16.15mm}{7.85mm}}{%
        \pgfbox[center,center]{\color{white!25!black}\Huge\ExTeX}}
      \pgfputat{\pgfpoint{16mm}{8mm}}{%
        \pgfbox[center,center]{\color{white!80!black}\Huge\ExTeX}}
    \end{pgfmagnify}
    
    %  \pgfrect[stroke]{\pgfpoint{0mm}{0mm}}{\pgfpoint{128mm}{64mm}}
  \end{pgfpicture}%
}
\newcommand\ExBibbox{%
  \begin{pgfpicture}{0mm}{0mm}{128mm}{64mm}
    \pgfdeclarehorizontalshading{thisShading}{120mm}%
    {color(0mm)=(white); rgb(160mm)=(.65,.65,.65)}%

    \pgfrect[fill]{\pgfpoint{0.5mm}{-0.5mm}}{\pgfpoint{128.5mm}{63.5mm}}

    \begin{pgfscope}
      \pgfmoveto{\pgforigin}
      \pgflineto{\pgfpoint{0mm}{64mm}}
      \pgflineto{\pgfpoint{128mm}{64mm}}
      \pgflineto{\pgfpoint{128mm}{0mm}}
      \pgfclosepath
      \pgfclip
      \pgfputat{\pgfpoint{64mm}{32mm}}{%
        \begin{pgfrotateby}{\pgfdegree{340}}
          \pgfbox[center,center]{\pgfuseshading{thisShading}}
        \end{pgfrotateby}}
    \end{pgfscope}
    
    \begin{pgfmagnify}{4}{4}
      \pgfputat{\pgfpoint{17.85mm}{8.15mm}}{%
        \pgfbox[center,center]{\color{white}\Huge\ExBib}}
      \pgfputat{\pgfpoint{18.15mm}{7.85mm}}{%
        \pgfbox[center,center]{\color{white!25!black}\Huge\ExBib}}
      \pgfputat{\pgfpoint{18mm}{8mm}}{%
        \pgfbox[center,center]{\color{white!80!black}\Huge\ExBib}}
    \end{pgfmagnify}
    
    %  \pgfrect[stroke]{\pgfpoint{0mm}{0mm}}{\pgfpoint{128mm}{64mm}}
  \end{pgfpicture}%
}

\providecommand\eTeX{\ensuremath{\varepsilon}-\TeX}

\newcommand\INCOMPLETE{\bigskip\par\noindent{\unitlength=1mm
    \framebox(60,20){\color{gray}To be completed.}}\bigskip\par}
\newcommand\Incomplete{\bigskip\par\noindent{\unitlength=1mm
    \framebox(60,8){\color{gray}To be completed.}}\bigskip\par}

\newenvironment{abstract}{\begin{quotation}}{\end{quotation}}

\usepackage{multicol}
\renewenvironment{theindex}{%
  \setchapterpreamble{\index@preamble}%
  \if@openright\cleardoublepage\else\clearpage\fi%
  \chapter*{\indexname}\@mkboth{\indexname}{\indexname}%
  \addcontentsline{toc}{chapter}{\indexname}
  \begin{multicols}3
  \thispagestyle{\indexpagestyle}\parindent\z@
  \setlength{\parskip}{\z@ \@plus .3\p@}
  \setlength{\parfillskip}{\z@ \@plus 1fil}
  \let\item\@idxitem}
{\end{multicols}\clearpage}

\providecommand\indexGroup[1]{\vskip 12pt plus 4pt minus 2pt
  \textbf{\sffamily\normalsize #1}%
  \vskip 6pt plus 3pt minus 1pt
  \nopagebreak
}
\def\+#1+{#1\index{#1}}

\def\SVN@empty{???}
\let\SVNdate\SVN@empty
\def\SVN$Id{\@ifnextchar:{\SVN@}{}}
\def\SVN@: #1 #2 #3-#4-#5 #6${\ifx\SVNdate\SVN@empty
  \global\def\SVNdate{#3-#4-#5}%
  \immediate\write\@auxout{\string\gdef\string\SVNdate{#3-#4-#5}}%
  \else\expandafter\SVN@@\SVNdate.#3-#4-#5.\fi}
\def\SVN@@#1-#2-#3.#4-#5-#6.{%
  \ifnum #4#5#6>#1#2#3
    \global\def\SVNdate{#4-#5-#6}%
    \immediate\write\@auxout{\string\gdef\string\SVNdate{#4-#5-#6}}%
  \fi
}

\let\titleArt=\ExTeXbox
\newcommand\versionname{Version}

\renewcommand\maketitle{
  \begin{center}
  \parindent=0pt
  \vspace*{1pt}
  \vfill
  \titleArt
  \vfill
  \textsf{\bfseries\Huge \@title}
  \vfill
  \textsf{\large \versionname \Version}
  \vfill
  \textsf{\large \@author}
  \vfill
  \vfill
  \end{center}
}

\newcommand\File{\@ifstar{\File@Star}{\File@NoStar}}
\newcommand\File@Star[1]{\texttt{#1}}
\newcommand\File@NoStar[1]{\texttt{#1}\index{#1|texttt}}

\newcommand\Dir{\@ifstar{\Dir@Star}{\Dir@NoStar}}
\newcommand\Dir@Star[1]{\texttt{#1/}}
\newcommand\Dir@NoStar[1]{\texttt{#1/}\index{#1/|texttt}}

\newcommand\Macro[1]{\texttt{\char`\\ #1}}

\ProvidesClass{extex-doc}

