FROM gradle:8.0-jdk17 AS build

RUN apt update && apt install -y protobuf-compiler


WORKDIR /app

COPY . .

RUN ./gradlew generateProto
RUN ./gradlew clean shadowJar

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

EXPOSE 23214

ENTRYPOINT ["java", "-jar", "/app/app.jar"]