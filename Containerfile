FROM registry.redhat.io/ubi8/openjdk-11

MAINTAINER Christopher Tate <computate@computate.org>

USER root

RUN install -d /usr/local/src/opendatapolicing
COPY . /usr/local/src/opendatapolicing
WORKDIR /usr/local/src/opendatapolicing
RUN mvn clean install -DskipTests
RUN cp /usr/local/src/opendatapolicing/target/*.jar /usr/local/src/opendatapolicing/app.jar
CMD java $JAVA_OPTS -cp .:* com.opendatapolicing.enus.vertx.MainVerticle
