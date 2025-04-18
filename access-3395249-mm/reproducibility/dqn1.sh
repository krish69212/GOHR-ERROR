#!/bin/bash

#-- The directory where this script is
sc=$(pwd)
echo "Script directory (sc): $sc"

cd captive/game

h=$(pwd)
echo "Main directory (h): $h"

# Check if the script exists before sourcing
if [ -e "$h/scripts/bash-set-var-captive.sh" ]; then
    echo "Sourcing $h/scripts/bash-set-var-captive.sh"
    source "$h/scripts/bash-set-var-captive.sh"
    echo "Sourced bash-set-var-captive.sh successfully."
    echo "CLASSPATH is set to: $CLASSPATH"
else
    echo "Error: $h/scripts/bash-set-var-captive.sh not found."
    exit 1
fi

cd ../..
echo "current directory: $PWD"
echo "h=$h ;  sc=$sc;"
# Use to run the driver script
echo "Running Python command"
python experiment_driver.py "./captive/game/game-data/rules" "./params/dqn.yaml"