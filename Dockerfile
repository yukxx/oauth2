FROM java:8
ENV LANG=zh_CN.UTF-8
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms256m -Xmx256m -Dfile.encoding=UTF8  -Duser.timezone=GMT+08"
VOLUME /tmp
ONBUILD COPY target/yukx_*.jar app.jar
RUN ln -snf /usr/share/zoneinfo/${TZ} /etc/localtime \
  && echo ${TZ} > /etc/timezone
#ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar -c"]
#CMD ["--spring.profiles.active=test"]
ENTRYPOINT ["java","-Dspring.profiles.active=test","-jar","/app.jar"]

