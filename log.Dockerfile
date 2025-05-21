FROM gradle:8.4-jdk17 AS build-libs
WORKDIR /libs

COPY logger-lib logger-lib
# test requeire connection to db -> skip them
RUN cd logger-lib \
    && gradle build -x test \
    && cp build/libs/loglib-1.jar /libs/loglib-1.jar

FROM gradle:8.4-jdk17 AS build

WORKDIR /app

COPY --from=build-libs /libs /app/libs

COPY ./log /app/service
COPY /proto  /app/proto

RUN cd service && gradle shadowJar

FROM openjdk:17-jdk-slim AS runner

WORKDIR /run

COPY --from=build /app/service/build/libs/app.jar /run/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/run/app.jar"]
