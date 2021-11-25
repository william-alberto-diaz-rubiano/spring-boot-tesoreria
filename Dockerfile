FROM openjdk:11
COPY build/libs/vuce-*.jar /zee-api.jar
CMD ["-jar", "/zee-api.jar"]
ENTRYPOINT ["java"]
EXPOSE 8081

