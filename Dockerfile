FROM openjdk:8
EXPOSE 8089
ADD target/khaddem.jar khaddem.jar
ENTRYPOINT ["java","-jar","/khaddem.jar"]
