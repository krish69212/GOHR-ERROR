#!/bin/csh
git pull origin master
/opt/ant/bin/ant clean javadoc war-dev
cp ../w2020-dev.war /opt/tomcat/webapps

