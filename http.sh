#!/bin/bash

# JVM
JVM_MEMORY="-Xms2048m -Xmx2048m -Xmn1024m -XX:SurvivorRatio=4 -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=512m"
# JVM_GC="-XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSFullGCsBeforeCompaction=0 -XX:CMSInitiatingOccupancyFraction=75"
# 推荐使用G1 (兼顾吞吐量和响应时间的收集器)
JVM_GC="-XX:+UseG1GC -XX:MaxGCPauseMillis=500"
JVM_GC_LOG="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+PrintHeapAtGC -XX:+PrintGCApplicationStoppedTime -Xloggc:logs/gc.log"
JVM_PROPERTIES="test"
JVM_ARGS=${JVM_MEMORY}" "${JVM_GC}" "${JVM_GC_LOG}" -XX:+HeapDumpOnOutOfMemoryError"

# 获得当前执行的脚本文件的父目录
proj_path=$(dirname $0)/
pid_file=${proj_path}/pid
VERSION=0.0.1

#如果本地环境不是jdk8，需要配置该项目
export JAVA_HOME=/usr/java/jdk1.8.0_181-amd64/

function usage() {
    cat <<EOF
Usage:
    http.sh <command> [-P key=value] [-JAVA_HOME <java_home>] [-JVM_ARGS <"jvm_args">] [args...]
The commands are:
    start   start service
    stop    stop service
    build   build service
Examples
    http.sh start [-P key=value] [args...]
    http.sh stop
    http.sh build [-P key=value] [args...]
EOF
}
## 判断pid是否存在
function check_process_is_running() {
    if [[ -f ${pid_file} ]]; then
        pid=$(cat ${pid_file})
        ps ${pid} >/dev/null 2>&1
        return $?
    fi
    return 1
}

command=$1
case ${command} in
start)
    if [[ $# -lt 2 ]]; then
        usage
        exit 1
    fi
    check_process_is_running
    if [[ $? -eq 0 ]]; then
        echo "Service[pid="$(cat pid)"] Already Started, Stop It First"
        exit 11
    fi
    shift 1
    while [[ $# -gt 0 ]]; do
        case $1 in
        -P)
            JVM_PROPERTIES=$2
            shift 2
            ;;
        *)
            break
            ;;
        esac
    done
    if [[ -z "${JAVA_HOME}" ]]; then
        echo "Error: JAVA_HOME is not set."
        exit 111
    fi
    export CLASSPATH=${JAVA_HOME}/lib
    export PATH=${JAVA_HOME}/bin:$PATH
    #./boot-user-service/target/boot-user-service-0.0.1-SNAPSHOT.jar
    RUN_COMMAND="nohup java ${JVM_ARGS} -jar ./boot-user-service/target/boot-user-service-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &"
    echo "START SERVICE..."
    echo "[Command]" ${RUN_COMMAND}
    nohup java ${JVM_ARGS} -jar ./target/openapi-${VERSION}-SNAPSHOT.jar >/dev/null 2>&1 &
    # refresh heart-beat file if it is not empty
    FILE_HEART_BEAT=logs/stat.log
    [[ -s ${FILE_HEART_BEAT} ]] && touch ${FILE_HEART_BEAT}
    echo $! >${pid_file}

    ;;
stop)
    check_process_is_running
    if [[ $? -eq 0 ]]; then
        kill ${pid}
        echo "Stop Service[pid=$pid]..."
        sleep 1
    else
        echo "No Service Is Running"
    fi
    #DIR="$( cd "$( dirname "$0"  )" && pwd  )"
    #ps aux | grep $DIR | grep -v grep | awk '{print $2}' | xargs kill -9
    ;;
build)
    if [[ $# -lt 2 ]]; then
        usage
        exit 1
    fi
    shift 1
    while [[ $# -gt 0 ]]; do
        case $1 in
        -P)
            JVM_PROPERTIES=$2
            shift 2
            ;;
        *)
            break
            ;;
        esac
    done
    echo "mvn clean"
    mvn clean
    echo "mvn package -Ptest"
    mvn package -P ${JVM_PROPERTIES}

    ;;
*)
    echo Unsupported operation: ${command}
    usage
    exit 9
    ;;
esac
