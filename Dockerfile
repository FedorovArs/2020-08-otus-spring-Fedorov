FROM openjdk:11-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]

#docker build -t my-book-app:latest . - Собрать контейнер
#docker image ls - Посмотреть доступные контейнеры
#sudo docker run -p 8080:8080 my-book-app - запуск контейнера my-book-app + проброс порта 8080
#контейнер не стартует т.к. в нём нет postgres'a -> надо запустить docker-compose
