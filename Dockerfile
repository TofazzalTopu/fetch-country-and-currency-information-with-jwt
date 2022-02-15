FROM openjdk:11
EXPOSE 8080
ADD target/country.jar country.jar
ENTRYPOINT ["java", "-jar", "/country.jar"]
