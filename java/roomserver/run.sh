#########################################################################
# File Name: run.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Fri 21 Aug 2015 08:42:19 AM PDT
#########################################################################
#!/bin/bash

PROJECT_PATH=.
JAR_PATH=$PROJECT_PATH/libs
BIN_PATH=$PROJECT_PATH/bin  
  
# Run the project as a background process  
java -classpath $BIN_PATH:$JAR_PATH/commons-beanutils-1.7.0.jar:$JAR_PATH/commons-collections-3.2.jar:$JAR_PATH/commons-lang.jar:$JAR_PATH/commons-logging.jar:$JAR_PATH/ezmorph-1.0.6.jar:$JAR_PATH/Java-WebSocket-1.3.1-SNAPSHOT.jar:$JAR_PATH/json-lib-2.2.3-jdk15.jar .bin.RunServer