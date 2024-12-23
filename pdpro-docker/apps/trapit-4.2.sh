#!/bin/sh
### NAME:      trapit
###
### VERSION:   4.2
###
### AUTHOR:    Justin Fries (justinf@us.ibm.com)
###
### COPYRIGHT:
### 
### (C) COPYRIGHT International Business Machines Corp. 2005-2023
### All Rights Reserved
### Licensed Materials - Property of IBM
###
### US Government Users Restricted Rights - Use, duplication or
### disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
###
### SYNOPSIS:
###
###  trapit -e Error... -f File... [-i Interval] [-t Trigger]... [-v]
###
###         -e: The error text or pattern to find
###         -f: The file name or pattern to search
###         -i: The interval in seconds between scans
###         -t: A command to trigger when a match is found
###         -v: Turn on verbose trapit output for debugging
###
###
### DESCRIPTION:
###
### This script searches all of the requested files for any of the error
### strings or patterns.  If the errors do not exist, it waits until one
### appears before triggering the optional user commands and terminating
### with reason code zero.
###
### Any valid extended regular expression may be used as an error string
### and shell wildcards may be used as a file name pattern.  The trigger
### commands will run in the order specified, but it is generally easier
### to write a shell script and trigger it instead.
###
###
### CAVEATS/WARNINGS:
###
### If the error text already appears in one of the target files, trapit
### will trigger immediately.  It may be necessary to archive, delete or
### relocate preexisting files to avoid premature termination.
###
### The trapit script is efficient enough to check for matching patterns
### every second without incurring much overhead, unless the files it is
### monitoring are updated frequently (e.g. large SystemOut.log or trace
### files).  Increase the interval between scans in these cases to avoid
### wasting lots of CPU time scanning these files.
###
### Be sure to enclose error and file name patterns and trigger commands
### in quotation marks or otherwise escape special characters to prevent
### them from being interpreted before they are passed to this script.
###
###
### RETURNED VALUES:
###
###   0  - Success
###   1  - Failure
###
###
### EXAMPLES:
###
### 1. To turn off IBM MQ tracing when a new FFST is created with errors
###    lrcE_S_Q_MGR_UNRESPONSIVE or zrcX_QPUBSUB_CTRLR_UNAVAILABLE, with
###    a three second interval between checks:
###
###      trapit -i 3 -f "/var/mqm/errors/*.FDC"
###        -e lrcE_S_Q_MGR_UNRESPONSIVE
###        -e zrcX_QPUBSUB_CTRLR_UNAVAILABLE 
###        -t "endmqtrc -a"
###
###
###      trapit -i 3 -f /var/mqm/errors/\*.FDC
###        -e "zrcX_QPUBSUB_CTRLR_UNAVAILABLE|zrcX_QPUBSUB_CTRLR_UNAVAILABLE"
###        -t "endmqtrc -a"
###
###
### 2. To use trapit in your own data gathering script, simply ask it to
###    wait for an error to appear but give it no trigger command.  When
###    the error appears, the trapit command will finish and your script
###    can gather the data it needs:
###
###      #!/bin/sh
###        LOGFILE=/tmp/errordata.log
###        trapit -e "Reason=2009" -f /var/myapp/prod/error.log
###        printf "MQ error at `date`\n" >> $LOGFILE
###        dspmq >> $LOGFILE
###        ps -ef >> $LOGFILE
###        netstat -an >> $LOGFILE
###


### Print the syntax for the script.

  PrintSyntax() {
    printf "syntax: trapit -e Error... -f File... [-i Interval] [-t Trigger]...\n"
  }


### The Verbose function writes timestamped messages to stderr to assist
### in analyzing problems with missing or delayed triggers.  The VERBOSE
### variable holds the best possible date format for the current system.

  Verbose() {
    if [ -n "$VERBOSE" ] ; then
      printf "`date +$VERBOSE`  $@\n" 1>&2
    fi
  }


### Unset user-selectable options before parsing the command line.  When
### more than one error string, file name or trigger is given, add it to
### the corresponding variable with an appropriate separator.  Use pipes
### to separate extended regular expression components, spaces for files
### and semicolons to separate trigger commands.


  unset VERBOSE ERRORS FILES INTERVAL TRIGGERS

  while getopts :e:f:i:t:v OPT ; do
    case $OPT in
      \:) PrintSyntax && exit 1;;

      \?) PrintSyntax && exit 1;;

       e) if [ "${ERRORS:=$OPTARG}" != "$OPTARG" ] ; then
            ERRORS="$ERRORS|$OPTARG"
          fi;;

       f) if [ "${FILES:=$OPTARG}" != "$OPTARG" ] ; then
            FILES="$FILES $OPTARG"
          fi;;

       i) if [ "${INTERVAL:=$OPTARG}" != "$OPTARG" ] ; then
            PrintSyntax && exit 1
          fi;;

       t) if [ "${TRIGGERS:=$OPTARG}" != "$OPTARG" ] ; then
            TRIGGERS="$TRIGGERS;$OPTARG"
          fi;;

       v) case `uname -s` in
            Linux) VERBOSE=%T.%N;;
                *) VERBOSE=%T;;
          esac;;
    esac
  done


### Make sure the PATH variable is properly set up for the system.  This
### is particularly important on Solaris to pick up the make program.

  case `uname -s` in
      AIX) PATH=/bin:/usr/bin:$PATH;;
    Linux) PATH=/bin:/usr/bin:$PATH;;
    HP-UX) PATH=/bin:/usr/bin:$PATH;;
    SunOS) PATH=/usr/xpg4/bin:/usr/ccs/bin:/bin:/usr/bin:$PATH;;
  esac

  export PATH


### Complain if there are any dangling command-line arguments, then make
### sure all of the information required was given.  Verify the interval
### and set a default of one second, and terminate the command list with
### the 'true' command to indicate success.

  if [ $OPTIND -le $# ] ; then
    shift `expr $OPTIND - 1`
    printf "trapit: Unexpected arguments found: $@\n" >&2
    PrintSyntax && exit 1
  fi

  if [ -z "$ERRORS" ] ; then
    printf "trapit: Provide an error on which to search\n" >&2
    PrintSyntax && exit 1
  fi

  if [ -z "$FILES" ] ; then
    printf "trapit: Provide or more files to search\n" >&2
    PrintSyntax && exit 1
  fi

  expr ${INTERVAL:=5} + 0 1>/dev/null 2>&1 || {
    printf "trapit: The interval must be a number\n" >&2
    PrintSyntax && exit 1
  }

  if [ "$INTERVAL" -lt 1 ]; then
    printf "trapit: The interval must be 1 or more\n" >&2
    PrintSyntax && exit 1
  fi

  TRIGGERS="${TRIGGERS:=:};exec true"

  Verbose "Running trapit 4.2"
  Verbose "Pattern to find: $ERRORS"
  Verbose "Files to search: $FILES"
  Verbose "Commands to run: $TRIGGERS"


### This script uses a makefile to determine which files have changed on
### each iteration.  Make compares the timestamp on a temporary tag file
### to the user files and prints the name of any recently changed files.
### Unfortunately file timestamps are reliably accurate only down to the
### second and there is some danger that file updates made while make is
### running may be missed.  To solve both issues we save in the MAKETIME
### variable a timestamp at least one second prior to the last make run.
### Initialize the tag file timestamp well in the past so that all files
### are scanned at least once; Set MAKETIME and impose a mandatory delay
### of one second so it will precede the first make attempt.  Any of the
### temporary directories will suffice for the tag file; Issue a message
### if none of them are writeable.  Trap terminating signals in order to
### clean up the temporary tag file if someone cancels the script.

  TEMPDIR="/tmp /var/tmp . ~"

  for DIR in $TEMPDIR ; do
    eval TAGFILE=$DIR/.trapit.$$
    trap 'rm -f $TAGFILE 1>/dev/null 2>&1; exit 1' HUP INT QUIT TERM

    touch -t 197411040000.00 $TAGFILE 1>/dev/null 2>&1 || {
      Verbose "Failed to create file $TAGFILE"
      rm -f $TAGFILE 1>/dev/null 2>&1
      continue
    }

    break
  done

  if [ ! -f $TAGFILE ] ; then
    printf "trapit: Failed to create temporary file\n" >&2 && exit 1
  fi

  Verbose "Successfully created file $TAGFILE"
  MAKETIME=`date +%m%d%H%M.%S 2>/dev/null`
  sleep 1


### On every call to this function we run the makefile to identify files
### newer than the current tag file timestamp.  Afterward, the timestamp
### is updated to a value before our make attempt so that any concurrent
### file updates will not be missed on the next iteration.

  ListUpdatedFiles() {
    Verbose "--> ListUpdatedFiles"
    printf "$TAGFILE: $FILES\n	+echo \$?\n\n" |
      make -sf - $TAGFILE 2>/dev/null | grep -v "is up to date"
    Verbose "    Finished running Makefile"

    touch -t $MAKETIME $TAGFILE 1>/dev/null 2>&1
    Verbose "    Updated $TAGFILE to $MAKETIME, rc=$?"
    Verbose "<-- ListUpdatedFiles"
  }


### Search the list of updated files for any matching patterns.  Use the
### xargs program as the number of files may be very large, particularly
### on the first attempt.  If no match is found, update the MAKETIME and
### sleep for at least one second.  Otherwise remove the tag file, print
### a message and run the trigger commands.  The final command will exit
## this script with a successful return code.

  while true ; do
    MATCH=`ListUpdatedFiles | xargs grep -El "$ERRORS" 2>/dev/null`
    if [ -z "$MATCH" ] ; then
      Verbose "No matching files found, sleeping ${INTERVAL}s"
      MAKETIME=`date +%m%d%H%M.%S 2>/dev/null`
      sleep $INTERVAL
    else
      Verbose "Match found in $MATCH"
      Verbose "Removing file $TAGFILE"
      rm -f $TAGFILE 1>/dev/null 2>&1 ||
        Verbose "Failed to remove file $TAGFILE"
      printf "trapit: Match found in $MATCH" | fmt -72
      Verbose "Running $TRIGGERS"
      eval $TRIGGERS
      exit 1
    fi
  done

