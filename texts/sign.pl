#!/usr/bin/perl

use strict;
use warnings;

use Getopt::Long;
use Archive::Zip;
use File::Temp qw(tempfile tempdir);
use Digest::MD5 qw(md5_hex);
use MIME::Base64 qw(encode_base64);
use Crypt::RC4;

# ============================================================= #

my $SUFFIX = '.icp';
my $HASH_PLACEHOLDER = '0123456789ABCDEFGHIJKLMNOPQRSTUV';
my $KEY = 'ichingoracle';

# ============================================================= #

my $license;
my $class;
my $id;

do
{
	print "Usage: $0 --license <csv> --class <cls> --id <id> plugin$SUFFIX\n";
	exit;
} if not GetOptions(
    "license=s" => \$license,
    "class=s" => \$class,
    "id=s" => \$id
) or not defined($license) or not defined($class) or not defined($id)
  or @ARGV != 1 or $ARGV[0] !~ /$SUFFIX$/;

$class =~ s!\.!/!g;
$class .= '.class';

my $file = $ARGV[0];

# ============================================================= #

my $hash = md5_hex($license);
my $icp = Archive::Zip->new($file);
my $liccont = encode_base64(RC4($KEY, $license));

my $pluginClass = $icp->contents($class);

$pluginClass =~ s/$HASH_PLACEHOLDER/$hash/g;

$icp->contents($class, $pluginClass);
$icp->addString($liccont, $id);
$icp->overwrite;

