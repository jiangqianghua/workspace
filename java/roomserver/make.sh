#########################################################################
# File Name: make.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 21 Aug 2015 08:42:09 AM PDT
#########################################################################
#!/bin/bash

PROJECT_PATH=/home/jiangqianghua/Desktop/workspace/java/roomserver 
JAR_PATH=$PROJECT_PATH/libs  
BIN_PATH=$PROJECT_PATH/bin  
SRC_PATH=$PROJECT_PATH/src 
  
# First remove the sources.list file if it exists and then create the sources file of the project  
rm -f $SRC_PATH/sources  
find $SRC_PATH -name *.java > $SRC_PATH/sources.list  
  
# First remove the ONSServer directory if it exists and then create the bin directory of ONSServer  
rm -rf $BIN_PATH
mkdir $BIN_PATH
  
# Compile the project  
javac -encoding GBK -d $BIN_PATH -classpath $JAR_PATH/commons-beanutils-1.7.0.jar:$JAR_PATH/commons-collections-3.2.jar:$JAR_PATH/commons-lang.jar:$JAR_PATH/commons-logging.jar:$JAR_PATH/ezmorph-1.0.6.jar:$JAR_PATH/Java-WebSocket-1.3.1-SNAPSHOT.jar:$JAR_PATH/json-lib-2.2.3-jdk15.jar @$SRC_PATH/sources.list  