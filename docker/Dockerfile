FROM adoptopenjdk:11-jre-hotspot
ARG JAR_FILE=*.jar
COPY ${JAR_FILE} api-movimientos.jar
ENTRYPOINT ["java", "-jar", "api-movimientos.jar"]