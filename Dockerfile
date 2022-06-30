FROM openjdk:11
VOLUME /tmp
EXPOSE 8081
COPY ./target/*.jar /Documents/images/rocketmq/consumer.jar
WORKDIR /Documents/images
RUN sh -c 'touch  consumer.jar'
ENTRYPOINT ["java","-jar","consumer.jar"]