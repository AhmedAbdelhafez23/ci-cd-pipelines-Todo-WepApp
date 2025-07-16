# Dockerfile for Spring Boot ToDo-App with Logs Monitoring
FROM openjdk:17-jdk-slim

# Proxy settings
ARG http_proxy
ARG https_proxy
ARG no_proxy
ENV HTTP_PROXY=$http_proxy
ENV HTTPS_PROXY=$https_proxy
ENV NO_PROXY=$no_proxy

# Set working directory
WORKDIR /app

# Install dependencies with minimal space usage
RUN echo 'Acquire::http::Proxy "http://proxy.th-wildau.de:8080";' > /etc/apt/apt.conf.d/01proxy && \
    apt-get update --allow-insecure-repositories --allow-releaseinfo-change && \
    DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends \
    curl \
    maven \
    openjdk-11-jre-headless && \
    apt-get clean && \
    rm -rf /var/cache/apt/* /var/lib/apt/lists/* /tmp/* /var/tmp/*

# Copy project files
COPY pom.xml /app/
COPY src /app/src/
COPY logging.properties /app/logging.properties

# Maven proxy settings
RUN mkdir -p ~/.m2 && \
    echo '<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 https://maven.apache.org/xsd/settings-1.0.0.xsd"> \
    <proxies> \
    <proxy> \
    <id>th-wildau-proxy</id> \
    <active>true</active> \
    <protocol>http</protocol> \
    <host>proxy.th-wildau.de</host> \
    <port>8080</port> \
    <nonProxyHosts>www.google.com|*.th-wildau.de|*.tfh-wildau.de</nonProxyHosts> \
    </proxy> \
    </proxies> \
    </settings>' > ~/.m2/settings.xml

# Resolve Maven dependencies
RUN mvn dependency:resolve

# Build the project
RUN mvn package -DskipTests

# Expose the application port
EXPOSE 8080

# Run the application with logging configuration
ENTRYPOINT ["java", "-Djava.util.logging.config.file=/app/logging.properties", "-jar", "target/MyFirstTodoApp-0.0.1-SNAPSHOT.jar"]
