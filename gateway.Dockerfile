FROM gradle:8.4-jdk17 AS build

WORKDIR /app

COPY logger-lib /app/logger-lib
# TODO Очень оооочоень долго идет билд этой либы почему-то. Возможно хочется ее вынести отсюда каким-то образом,
#    ибо она в каждом контейнере лежит
RUN cd /app/logger-lib && gradle build -x test

COPY ./gateway /app/service
COPY /proto  /app/proto

RUN cd service && gradle shadowJar

FROM openjdk:17-jdk-slim AS runner

WORKDIR /run
# NOTE: только у gateway отличается имя джарника(gateway-all.jar instead of app.jar)
COPY --from=build /app/service/build/libs/gateway-all.jar /run/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/run/app.jar"]
