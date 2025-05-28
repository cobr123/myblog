# Cache maven dependencies as an intermediate docker image
# (This only happens when pom.xml changes or you clear your docker image cache)
FROM maven:3-amazoncorretto-21-alpine AS dependencies
COPY pom.xml /build/
WORKDIR /build/
RUN mvn --batch-mode dependency:go-offline dependency:resolve-plugins

# Build the app using Maven and the cached dependencies
# (This only happens when your source code changes or you clear your docker image cache)
# Should work offline, but https://issues.apache.org/jira/browse/MDEP-82
FROM maven:3-amazoncorretto-21-alpine AS build
COPY --from=dependencies /root/.m2 /root/.m2
COPY pom.xml /build/
COPY src /build/src
WORKDIR /build/
RUN mvn package -Dmaven.test.skip

# Run the application (using the JRE, not the JDK)
FROM tomcat:11-jdk21 AS runtime
COPY --from=build /build/target/myblog.war /usr/local/tomcat/webapps/ROOT.war
CMD ["catalina.sh", "run"]