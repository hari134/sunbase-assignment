# Use an official OpenJDK image as a parent image
FROM openjdk:17-alpine

# Create a directory for your application
RUN mkdir /app

# Set the working directory to /app
WORKDIR /app

# Copy your Spring Boot WAR file into the /app directory
COPY target/sunbase-0.0.1-SNAPSHOT.war /app/ROOT.war

# Expose the port that your Spring Boot application will run on (default is 8080)
EXPOSE 8080

# Run the Spring Boot application using java -jar
CMD ["java", "-jar", "ROOT.war"]
