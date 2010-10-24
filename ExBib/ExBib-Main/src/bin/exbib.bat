@echo off
rem ---------------------------------------------------------------------------
rem  This is the start script for ExBib
rem ---------------------------------------------------------------------------
rem  Copyright (C) 2008-2010 The ExTeX Group
rem 
rem  This library is free software; you can redistribute it and/or modify it
rem  under the terms of the GNU Lesser General Public License as published by
rem  the Free Software Foundation; either version 2.1 of the License, or (at
rem  your option) any later version.
rem
rem  This library is distributed in the hope that it will be useful, but
rem  WITHOUT ANY WARRANTY; without even the implied warranty of
rem  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser
rem  General Public License for more details.
rem
rem  You should have received a copy of the GNU Lesser General Public License
rem  along with this library; if not, write to the Free Software Foundation,
rem  Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
rem 
rem ---------------------------------------------------------------------------

if "%E_X_B_I_B%"=="yes" goto start

cmd /V /C "SET E_X_B_I_B=yes&&%0 %*"

goto end

:start

if "%JAVA_HOME%"=="" (
  echo ERROR: JAVA_HOME not found in your environment.
  echo.
  echo Please, set the JAVA_HOME variable in your environment to match the
  echo location of the Java Virtual Machine you want to use.
  goto end
  )

set EXBIB_HOME=$INSTALL_PATH
set LIBDIR=%EXBIB_HOME%\lib
set LOCALCLASSPATH=.;%EXBIB_HOME%\classes

for %%i in (%LIBDIR%\*.jar) do (
  set LOCALCLASSPATH=!LOCALCLASSPATH!;%%i
)

%JAVA_HOME%\bin\java %EXBIB_JAVA_OPTS% -classpath %LOCALCLASSPATH% org.extex.exbib.main.ExBibMain %*

:end

rem 
