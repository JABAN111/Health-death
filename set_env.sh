#!/bin/bash
# Скрипт для установки переменных окружения, соответствующих проброшенным портам
# Используйте: source set_env.sh

# Gateway (проброшенный порт 23200, внутренний 8080)
export API_ADDRESS="0.0.0.0:23200"
export GATEWAY_PORT="23200"
export AUTH_SERVICE_JDBC_URL="jdbc:postgresql://localhost:5445/auth"
export AUTH_SERVICE_DB_USERNAME="user"
export AUTH_SERVICE_DB_PASSWORD="user"

# Остальные сервисы:
# authorization: 23210:8080
export AUTHORIZATION_ADDRESS="localhost"
export AUTHORIZATION_PORT="23210"

# diary: 23214:8080
export DIARY_ADDRESS="localhost"
export DIARY_PORT="23214"

# log: 23211:8080
export LOG_ADDRESS="localhost"
export LOG_PORT="23211"
export LOG_SERVICE_JDBC_URL="jdbc:postgresql://localhost:5444/log"
export LOG_SERVICE_DB_USERNAME="user"
export LOG_SERVICE_DB_PASSWORD="user"

# schedule: 23220:8080
export SCHEDULE_ADDRESS="localhost"
export SCHEDULE_PORT="23220"

# train: 23212:8080
export TRAIN_ADDRESS="localhost"
export TRAIN_PORT="23212"

# user: 23213:8080
export USER_ADDRESS="localhost"
export USER_PORT="23213"
export USER_SERVICE_JDBC_URL="jdbc:postgresql://localhost:5446/user"

echo "Local environment variables set:"
echo "API_ADDRESS=${API_ADDRESS}"
echo "AUTHORIZATION_ADDRESS=${AUTHORIZATION_ADDRESS}:${AUTHORIZATION_PORT}"
echo "DIARY_ADDRESS=${DIARY_ADDRESS}:${DIARY_PORT}"
echo "LOG_ADDRESS=${LOG_ADDRESS}:${LOG_PORT}"
echo "SCHEDULE_ADDRESS=${SCHEDULE_ADDRESS}:${SCHEDULE_PORT}"
echo "TRAIN_ADDRESS=${TRAIN_ADDRESS}:${TRAIN_PORT}"
echo "USER_ADDRESS=${USER_ADDRESS}:${USER_PORT}"
