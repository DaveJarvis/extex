#!C:\opt\cygwin\bin\perl.exe -w
##*****************************************************************************
## $Id: check.pl,v 0.00 2007/09/14 21:08:37 gene Exp $
##*****************************************************************************
## Author: Gerd Neugebauer
##=============================================================================

=head1 NAME

check.pl - ...

=head1 SYNOPSIS

check.pl [-v|--verbose] 

check.pl [-h|-help]

=head1 DESCRIPTION

=head1 OPTIONS

=head1 AUTHOR

Gerd Neugebauer

=head1 BUGS

=over 4

=item *

...

=back

=cut

use strict;
use File::Find;

#------------------------------------------------------------------------------
# Function:	usage
# Arguments:	
# Returns:	
# Description:	
#
sub usage
{ use Pod::Text;
  Pod::Text->new()->parse_from_filehandle(new FileHandle($0,'r'),\*STDERR);
}

#------------------------------------------------------------------------------
# Variable:	$verbose
# Description:	
#
my $verbose = 0;

use Getopt::Long;
GetOptions("h|help"	=> \&usage,
	   "v|verbose"	=> \$verbose,
	  );

find { 
  no_chdir => 1,
  wanted   => sub {
    return if m/\.svn/;
    return if not -f $_;
    return if m/\.svn/;
    return if not m/\.java$/;
    return if not m|main/java|;
    print "$_\n";
  }}, @ARGV;


#------------------------------------------------------------------------------
# Local Variables: 
# mode: perl
# End: 
