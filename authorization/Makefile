.PHONY: proto build

proto:
	./gradlew generateProto
build:
	./gradlew generateProto
	./gradlew clean shadowJar

run: build
	java -jar build/libs/*.jar