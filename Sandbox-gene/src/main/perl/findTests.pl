#!C:\opt\cygwin\bin\perl.exe -w
##*****************************************************************************
## $Id: findTest.pl,v 0.00 2007/09/14 21:08:37 gene Exp $
##*****************************************************************************
## Author: Gerd Neugebauer
##=============================================================================

=head1 NAME

findTest.pl - ...

=head1 SYNOPSIS

findTest.pl [-v|--verbose] 

findTest.pl [-h|-help]

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
use FileHandle;

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

my $count = 0;

find { 
  no_chdir => 1,
  wanted   => sub {
    if (m/\.svn/ or m|/target|) {
      $File::Find::prune = 1;
      return;
    }
    return if not -f $_ or not m|test/java.*Test.*\.java$|;
    my $file = $_;
    print "--- $file\n" if $verbose;
    my $t  = undef;
    my $fd = new FileHandle($file, "r") || die "$!\n";
    while(<$fd>) {
      if (m|\@Test|) {
	$t = 1;
      } elsif (m|\@Ignore| or m|\@Over| or m|\@SuppressWarning|) {
      } elsif (m|public void .*test.*()|) {
	if (not $t) {
	  print "$file: $_";
	  $count++;
	}
      } else {
	$t = undef;
      }
    }
    $fd->close();
  }}, @ARGV;

print "[$count]\n";

#------------------------------------------------------------------------------
# Local Variables: 
# mode: perl
# End: 
