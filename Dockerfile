FROM openjdk:11
VOLUME /tmp
COPY ./target/*.jar /consumer.jar
RUN sh -c 'touch  consumer.jar'
ENTRYPOINT ["java","-jar","consumer.jar"]