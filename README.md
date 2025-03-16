# Короткая инструкция по работе с проектом


У проекта предполагается два вида запуска, локальный и через контейнеры
## Требования к системе для запуска проекта локально

- Gradle 8.12.1
- Java 17
- [protoc libprotoc 29.3](https://grpc.io/docs/protoc-installation/) (для генерации proto файлов для работы с grpc)

## Требования к системе для запуска проекта через контейнеры
- Docker 27.4.0

# Запуск проекта локально
Нужно объединить вызовы с makefile, но пока нужно запустить по одному
```shell
make run-user
make run-auth
make run-diary
make run-log
make run-schedule
make run-train
```

# Запуск проекта через контейнеры
```shell
docker compose up -d
```

# Протыкивание ручек без gateway
Для протыкивания ручек без gateway можно использовать [grpcurl](https://github.com/fullstorydev/grpcurl) или [grpcui](https://github.com/fullstorydev/grpcui)
grpcurl
```shell
grpcurl -plaintext localhost:23210 list 
grpcurl -plaintext -d {} localhost:23210 search.Authorization/Ping
```
grpcui
```shell
grpcui -plaintext localhost:23210
```

NB: на текущий момент докеры и docker compose запущен минимально для демонстрации работы
необходимо перенастроить и добавить возможность парсить конфиг через ENV у докера


## Отметка директории, как модуля(для IntelliJ IDEA)
Если idea не отмечает директорию как микросервис, то есть нет синей пометочки возле директории(как на фото)
<img width="540" alt="Снимок экрана 2025-03-16 в 13 56 10" src="https://github.com/user-attachments/assets/60b57768-45d0-4b8f-987a-ffe0dd7f7b6a" />

То необходимо зайти в директорию и прилинковать gradle к idea
<img width="1142" alt="Снимок экрана 2025-03-16 в 13 57 13" src="https://github.com/user-attachments/assets/e701c8e3-dcea-4d1c-a9f6-52eec063497e" />
