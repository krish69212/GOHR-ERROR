#!/bin/bash

#---------------------------------------------------------------------
# Usage examples:
#
#   ./captive-full.sh /opt/tomcat/game-data/rules/vm/TD-01.txt 6
#   ./captive-full.sh -inputDir /home/vmenkov/my-game-data ~/my-game-data/rules/vm/TD-01.txt 6
#   ./captive-full.sh /opt/tomcat/game-data/trial-lists/vmColorTest/trial_1.csv 1
#   ./captive-full.sh -inputDir /home/vmenkov/my-game-data ~/my-game-data/trial-lists/vmColorTest/trial_1.csv 1
#   ./captive-full.sh inputDir=game-data log=sample.csv log.nickname=JohnDoe log.run=0 R:MLC/BMK/colOrd_nearby.txt:MLC/BMK/bmk.csv
#---------------------------------------------------------------------

# The directory where this script is
echo "current directory: $PWD"
# sc=$(dirname "$0")
# h=$(cd "$sc" && pwd)

h=$(pwd)
cd 'scripts'
sc=$(pwd)
cd ..
# Load environment variables
export CLASSPATH="$h\\scripts\\bash-set-var-captive.sh"

# Default JVM options
opt="-Doutput=FULL"

echo "Script directory (sc): $sc"
echo "Main directory (h): $h"

# Check for -inputDir option
if [ $# -ge 2 ] && [ "$1" = "-inputDir" ]; then
    shift
    inputDir="$1"
    shift
    opt="$opt -DinputDir=$inputDir"
fi

echo "Options are $opt"
echo "Current directory: $PWD"

# Run the Java application with remaining arguments
java $opt edu.wisc.game.engine.Captive "$@"
