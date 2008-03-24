@echo off
rem ---------------------------------------------------------------------------
rem  This file is part of bcd a BibTeX compatible database.
rem  Copyright (C) 2003-2008 Gerd Neugebauer
rem
rem  This library is free software; you can redistribute it and/or
rem  modify it under the terms of the GNU Lesser General Public
rem  License as published by the Free Software Foundation; either
rem  version 2.1 of the License, or (at your option) any later version.
rem
rem  This library is distributed in the hope that it will be useful,
rem  but WITHOUT ANY WARRANTY; without even the implied warranty of
rem  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
rem  Lesser General Public License for more details.
rem
rem  You should have received a copy of the GNU Lesser General Public
rem  License along with this library; if not, write to the Free Software
rem  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA
rem
rem ---------------------------------------------------------------------------
set BCDDIR=c:/gene/src/jbcd/
set JAVA=java

%JAVA% -classpath %BCDDIR%\bin de.gene.bcd.Main %1 %2 %3 %4 %5 %6 %7 %8 %9

rem
