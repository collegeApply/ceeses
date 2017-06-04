#!/bin/bash

artifactFileName="ceeses.jar"
artifactFilePath="/data/repos/${artifactFileName}"
gclogFilePathPath="/data/log/ceeses/gc.log"
jvmDumpFilePath="/data/log/ceeses/jvmdump"
logFilePath="/data/logs/ceeses/ceeses.log"


function close_old_program(){
    echo "closing ceeses..."
    for pid in `ps aux | grep java | grep ceeses | awk  '{print $2}'`
    do
        echo "Process id is $pid"
        kill -9 $pid
        sleep 5
		echo "ceeses was closed"
    done
}

function deploy(){
    currentTimeString="`date +%m%d%H%M`"
    test -e ${artifactFileName} && cp ${artifactFileName} ${artifactFileName}.${currentTimeString}
	cp ${artifactFilePath} ./
    JAVA_OPTS=" -Djava.net.preferIPv4Stack=true "
    JAVA_MEM_OPTS=" -server -Xmx1g -Xms1g -XX:PermSize=128m -XX:MaxPermSize=256m -Xss256k -XX:+DisableExplicitGC -XX:+UseConcMarkSweepGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:LargePageSizeInBytes=128m -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 "
    JAVA_OPTS="$JAVA_OPTS $JAVA_MEM_OPTS -XX:+PrintGCDetails -Xloggc:${gclogFilePathPath}/gc.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=${jvmDumpFilePath}"

    nohup java $JAVA_OPTS -jar -Dspring.profiles.active=prod ${artifactFileName} &
    sleep 5
    tailf ${logFilePath}
}

function prepare(){
    if [ -d ${jvmDumpFilePath} ] ; then
		echo "${jvmDumpFilePath} is exist ."
	else
		mkdir -p ${jvmDumpFilePath}
	fi
	if [ -d ${gclogFilePathPath} ] ; then
		echo "${gclogFilePathPath} is exist ."
	else
		mkdir -p ${gclogFilePathPath}
	fi
}

if [ -f ${artifactFilePath} ] ; then
    prepare;
    close_old_program;
    deploy;
else
    echo "${artifactFileName} NOT EXIST ."
fi