# 基于哪个镜像
FROM java:8
# 将本地文件夹挂载到当前容器
VOLUME /tmp
# 拷贝文件到容器
ADD target/yukx_eureka-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/app.jar"]