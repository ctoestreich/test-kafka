FROM openjdk:8-jdk-alpine 
RUN apk --no-cache add curl
COPY target/test-kafka*.jar test-kafka.jar
CMD java ${JAVA_OPTS} -jar test-kafka.jar