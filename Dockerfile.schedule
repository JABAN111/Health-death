FROM gradle:8.4-jdk17 AS build

RUN apt update && apt install -y protobuf-compiler

WORKDIR /app

COPY logger-lib /app/logger-lib
RUN cd /app/logger-lib && gradle build -x test

COPY ./schedule /app/service
COPY /proto  /app/proto

RUN cd service && gradle shadowJar

FROM openjdk:17-jdk-slim AS runner

WORKDIR /run

COPY --from=build /app/service/build/libs/app.jar /run/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/run/app.jar"]
