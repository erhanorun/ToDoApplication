FROM openjdk:17-oracle
COPY ./target/todo-0.0.1-SNAPSHOT.jar spring-boot.jar
CMD java -jar spring-boot.jar
