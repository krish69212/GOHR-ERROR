#!/bin/csh

#-- The directory where this script is
set sc=`dirname $0`
echo "Script directory (sc): $sc"

set h=`(cd $sc; cd captive/game; pwd)`
echo "Main directory (h): $h"

# Check if the script exists before sourcing
if (-e "$h/scripts/set-var-captive.sh") then
    echo "Sourcing $h/scripts/set-var-captive.sh"
    source "$h/scripts/set-var-captive.sh"
    echo "Sourced set-var-captive.sh successfully."
    echo "CLASSPATH is set to: $CLASSPATH"
else
    echo "Error: $h/scripts/set-var-captive.sh not found."
    exit 1
endif

# Use to run the driver script
echo "Running Python command"
python3 experiment_driver.py "/data/local/cat/GOHR/access-3395249-mm/captive/game/game-data/rules" "/data/local/cat/GOHR/access-3395249-mm/reproducibility/params/dqn.yaml"
