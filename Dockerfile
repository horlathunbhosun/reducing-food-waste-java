FROM --platform=linux/amd64 openjdk:17-jdk-alpine
LABEL authors="olulodeolatunbosun"
WORKDIR /app
COPY . .
RUN ./mvnw package
COPY target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080

## Use a smaller base image
#FROM adoptopenjdk:17-jdk-hotspot-bionic as build
#WORKDIR /app
#COPY . /app
#RUN ./mvnw package
#
#FROM adoptopenjdk:17-jre-hotspot-bionic
#LABEL authors="olulodeolatunbosun" \
#      description="This is rest api for wastemanagement application."
#WORKDIR /app
## Copy only the built JAR from the build stage
#COPY --from=build /app/target/*.jar app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]
#EXPOSE 8080