FROM nexus-docker-msb.df.msb.com.vn/msb-openjdk:8-alpine-3.18

ARG JAR_FILE=target/*.jar

ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005

COPY ${JAR_FILE} homeloan-be-2.0.0.jar

ENTRYPOINT ["java","-jar","/homeloan-be-2.0.0.jar"]

# End file, test jenkins webhook.
