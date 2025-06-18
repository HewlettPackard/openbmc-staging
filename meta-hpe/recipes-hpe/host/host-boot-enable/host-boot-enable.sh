#!/bin/sh
# By default all vendor specific services must be disabled
# they will be started later on based on requirement
if [ "$BOARD" != "" ]
then
	if [ -f "/usr/bin/host-boot-enable_$BOARD.sh" ]
	then
		/usr/bin/host-boot-enable_$BOARD.sh
	else
		echo "ERROR: BOARD file not found; is server $BOARD supported?"
	fi
else
	echo "ERROR: BOARD not set"
fi
