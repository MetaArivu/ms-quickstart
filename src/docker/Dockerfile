#################################################################
#
# Micro Service - Developer Tool box to test Pods in Kubernetes
#
# (C) MetaMagic Global Inc, NJ, USA, 2017
#
# Desire3D Micro Services Containers - Tomcat
#
# Version 0.1
#################################################################
# Base Version
FROM metamagic/java:8

LABEL author="Araf Karsh Hamid <araf.karsh@metamagic.in>"

RUN mkdir /Softwares/service
RUN mkdir /Softwares/service/libs

ARG SPRINGBOOT_JAR=*?.?.?-spring-boot.jar
ARG MAVEN_JAR=*-?.?.?.jar

# Copy Service (Install)
# COPY --chown=arivu:arivu startService /Softwares/service/

# SpringBoot Fat Jar
COPY --chown=arivu:arivu ${SPRINGBOOT_JAR} /Softwares/service/

# Maven Thin Jar
#COPY --chown=arivu:arivu ${MAVEN_JAR} /Softwares/service/
#COPY --chown=arivu:arivu /libs /Softwares/service/libs/

# Change the Mode
RUN chmod -R 750 /Softwares/service/*

# Expose Micro Service Port
EXPOSE 9090

# Start Micro Service
#ENTRYPOINT ["java","-jar","/Softwares/service/mock-service-0.1.0.jar"]
ENTRYPOINT ["java","-jar","/Softwares/service/mock-service-0.1.0-spring-boot.jar"]
