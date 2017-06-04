1. 部署前请做如下准备工作（也可以修改脚本）
创建目录
/data/logs
/data/log
/data/webapp
/data/repos

2. 打包
mvn package -P prod
将打好的包拷贝到/data/repos目录下

3. 拷贝deploy_ceeses.sh脚本到/data/webapp目录下，执行该脚本即可完成部署
可以通过修改脚本调整JVM内存参数及GC配置
