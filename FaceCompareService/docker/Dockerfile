#指定镜像
FROM registry.cn-hangzhou.aliyuncs.com/hzgc/java-all:8

#操作文件
COPY facecompareservice /facecompare

#执行命令
RUN chmod +x /facecompare/bin/* &&  /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

#初始化参数并启动应用
ENTRYPOINT ["/bin/sh","-c","/facecompare/bin/start-service.sh"]
