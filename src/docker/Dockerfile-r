#################################################################
#
# Microservice Template
# (C) MetaMagic Global Inc, NJ, USA, 2021
#
# Container Name: mock-service
#
# Microservices Containers - SpringBoot 2.5.3
#
# Version 0.1
#################################################################
# Base Version
FROM metamagic/java:8

LABEL author="Araf Karsh Hamid <araf.karsh@metamagic.in>"

RUN mkdir /Softwares/service
RUN mkdir /Softwares/service/libs
RUN mkdir /home/arivu/libs

ARG SPRINGBOOT_JAR=mock-service-spring-boot.jar
ARG MAVEN_JAR=mock-service-thin.jar

# Copy Service (Install)
# COPY --chown=arivu:arivu StartService /Softwares/service/
COPY --chown=arivu:arivu application.properties /home/arivu/

# SpringBoot Fat Jar
COPY --chown=arivu:arivu ${SPRINGBOOT_JAR} /Softwares/service/

# Maven Thin Jar
#COPY --chown=arivu:arivu ${MAVEN_JAR} /Softwares/service/
#COPY --chown=arivu:arivu /libs /Softwares/service/libs/
#COPY --chown=arivu:arivu /libs /home/arivu/libs/

# Change the Mode
RUN chmod -R 750 /Softwares/service/*

# Expose Microservice Port
EXPOSE 9090

# Start Microservice
#ENTRYPOINT ["java","-jar","/Softwares/service/mock-service-thin.jar"]
ENTRYPOINT ["java","-jar","/Softwares/service/mock-service-spring-boot.jar"]
