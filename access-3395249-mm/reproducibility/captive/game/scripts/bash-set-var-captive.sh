#!/bin/bash

#-- $h is the main directory (~/w2020/game)

#-- The directory where this script is (e.g. ~/w2020/game/scripts)
h=`pwd`
echo "h=$h"
#-- $h is the main directory (~/w2020/game)
# h=`(cd $sc/..; pwd)`

sc=`(cd $h/scripts; pwd)`
echo "sc=$sc"

g=`(cd $h/..; pwd)`
echo "h=$h, g=$g"



export CLASSPATH="$h/lib/captive.jar:$g/jaxrs-ri/ext/*"
echo "CLASSPATH is set to: $CLASSPATH"




