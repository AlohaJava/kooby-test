FROM gradle:7.6.1-jdk17 as build
WORKDIR /myapp
COPY build.gradle build.gradle
COPY settings.gradle settings.gradle
COPY src src
COPY conf conf
RUN gradle shadowJar

FROM openjdk:17-jdk-slim
WORKDIR /myapp
COPY --from=build /myapp/build\libs\myapp-1.0.0-all.jar app.jar
COPY conf conf
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]
