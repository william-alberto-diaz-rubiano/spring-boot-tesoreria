FROM openjdk:11
RUN mkdir /opt/app
COPY build/libs/*.jar /opt/app/japp.jar
VOLUME /tmp
EXPOSE 8081
CMD ["-jar", "/opt/app/japp.jar"]
ENTRYPOINT ["java"]