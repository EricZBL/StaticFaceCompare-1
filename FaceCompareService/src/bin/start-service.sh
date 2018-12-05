#!/bin/bash
################################################################################
## Copyright:   HZGOSUN Tech. Co, BigData
## Filename:    start-service.sh
## Description: 启动FaceCompareService
## Author:      wujiaqi
## Created:     2018-11-28
################################################################################
#set -x  ## 用于调试用，不用的时候可以注释掉


cd `dirname $0`
BIN_DIR=`pwd`                                               ### bin目录
cd ..
COMPARE_DIR=`pwd`                                           ### compare目录
LOG_DIR=${COMPARE_DIR}/log                                  ### log目录
CONF_DIR=${COMPARE_DIR}/conf                                ### conf目录
LIB_DIR=${COMPARE_DIR}/lib                                  ### lib目录
LIB_JARS=`ls $LIB_DIR|grep .jar|awk '{print "'$LIB_DIR'/"$0}'|tr "\n" ":"`

#SERVICE_ID=$1
SERVICE_NAME=service${SERVICE_ID}
LOG_FILE_STDERR=${LOG_DIR}/${SERVICE_NAME}/stderr.log

if [ ! -d ${LOG_DIR} ]; then
    mkdir ${LOG_DIR}
fi

if [ ! -d ${LOG_DIR}/${SERVICE_NAME} ]; then
    mkdir ${LOG_DIR}/${SERVICE_NAME}
fi

#####################################################################
# 函数名: start_service
# 描述: 启动Service
# 参数: N/A
# 返回值: N/A
# 其他: N/A
#####################################################################
function start_service()
{

    echo "To Start Compare Service ${SERVICE_NAME}"
    nohup java -server -Dzk.address=${ZK_ADDRESS} -Des.hosts=${ES_HOST} -Dservice.name=${SERVICE_NAME} -Xms1g -Xmx4g -classpath $CONF_DIR:$LIB_JARS com.hzgc.compare.ServiceMain ${SERVICE_ID} 2>&1 > $LOG_FILE_STDERR
    echo "start Compare Service ..."
}

start_service