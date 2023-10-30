FROM openjdk:8
EXPOSE 8089
ADD target/5SAE2-G6-Khaddem.jar 5SAE2-G6-Khaddem.jar
ENTRYPOINT ["java","-jar","/5SAE2-G6-Khaddem.jar"]
