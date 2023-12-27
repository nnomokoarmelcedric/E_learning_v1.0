FROM eclipse-temurin:17-jre-jammy
#RUN addgroup appadmin; adduser  --ingroup appadmin --disabled-password appadmin  ####creates a group and user appadmin to run the application
#USER appadmin 
WORKDIR /opt/app
EXPOSE 8085
COPY target/*.jar /opt/app/apigw-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/opt/app/apigw-0.0.1-SNAPSHOT.jar" ]

