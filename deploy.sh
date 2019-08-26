#!/usr/bin/env bash

killTomcat(){
  pid=`ps -ef|grep tomcat|grep java|awk '{print $2}'`
  echo "tomcat Id lisy:$pid"
  if [ "$pid" = "" ]
  then
    echo "no tomcat pid alive"
  else
    kill -9 $pid
  fi
}
# 进入zion工程根目录,注意不是部署目录
cd $PROJ_PATH/zion
#maven 构建项目
mvn clean install

#停止tomcat
killTomcat

# 删除原有工程
rm -rf $TOMCAT_APP_PATH/webapps/ROOT
rm -f $TOMCAT_APP_PATH/webapps/ROOT.war
rm -f $TOMCAT_APP_PATH/webapps/zion.war

#复制新工程到tomcat的部署目录
cd $PROJ_PATH/zion/target/zion.war $TOMCAT_APP_PATH/webapps/

# 进入tomcat部署目录
cd $TOMCAT_APP_PATH/webapps/
#给新的war包改名
mv zion.war ROOT.war

#启动Tomcat
cd $TOMCAT_APP_PATH/
sh bin/startup.sh




