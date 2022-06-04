FROM maven:3.5.4-jdk-8-alpine as builder

COPY ./webshop/pom.xml ./pom.xml
COPY ./webshop/src ./src
RUN mvn clean package

FROM tomcat:9.0-alpine
COPY --from=builder /target/EShop-2.0.0.war /usr/local/tomcat/webapps/
COPY ./webshop/conf/tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml

EXPOSE 8080