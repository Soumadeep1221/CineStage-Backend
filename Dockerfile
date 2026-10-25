FROM eclipse-temurin:25-jre
COPY target/BMS-System.jar BMS-System.jar
CMD ["java", "-jar", "BMS-System.jar"]