#!/bin/bash

#运行命令：deploy.sh

TOMCAT_HOME=/usr/local/tomcat
WEB_DIR=$TOMCAT_HOME/webapps/ROOT
DEPLOY_DIR=/root/deploy/iwc-shop
GIT_DIR=$DEPLOY_DIR/iwc-shop-git
WAR_DIR=$DEPLOY_DIR/iwc-shop-war
DATE=`date "+%Y%m%d_%H%M%S"`

cd /tmp

echo "# 更新源码"
cd $GIT_DIR
git pull
cd /tmp

echo "# 复制源码"
rm -rf $WAR_DIR
cp -r $GIT_DIR $WAR_DIR
rm -rf $WAR_DIR/.git
rm -f $WAR_DIR/.gitignore

echo "# 备份网站"
cp -r $WEB_DIR $DEPLOY_DIR/iwc-shop_$DATE

echo "# 复制配置文件和图片"
rm -f $WAR_DIR/src/main/resources/shop.properties
cp $DEPLOY_DIR/config/shop.properties $WAR_DIR/src/main/resources/shop.properties

#rm -f $WAR_DIR/src/main/webapp/WEB-INF/views/include/taglib.jsp
#cp $DEPLOY_DIR/config/taglib.jsp $WAR_DIR/src/main/webapp/WEB-INF/views/include/taglib.jsp

rm -rf $WAR_DIR/src/main/webapp/userfiles/*
cp -r $WEB_DIR/userfiles/* $WAR_DIR/src/main/webapp/userfiles/

echo "# 编译, 打包成war包"
cd $WAR_DIR
mvn package
cd /tmp

echo "# 发布war包到tomcat目录"
mv -f $WAR_DIR/target/ROOT.war $TOMCAT_HOME/webapps/ROOT.war

echo "deploy finished."

# tomcat 要用绝对路径停止和启动, 要去终端逐条运行才行
#$TOMCAT_BIN/shutdown.sh
#sleep 2
#$TOMCAT_BIN/startup.sh
#echo "deploying..."
#sleep 8
#service httpd restart
#sleep 2
#echo "deploy finished"
