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

RUN mkdir /Softwares/apps/
RUN mkdir /Softwares/apps/payments/
RUN mkdir /Softwares/apps/payments/libs

# Copy Service (Install)
COPY --chown=arivu:arivu startService /Softwares/apps/payments/
# SpringBoot Fat Jar
COPY --chown=arivu:arivu payments-service-0.1.0-spring-boot.jar /Softwares/apps/payments/
# Maven Thin Jar
#COPY --chown=arivu:arivu payments-service-0.1.0.jar /Softwares/apps/payments/
#COPY --chown=arivu:arivu /libs /Softwares/apps/payments/libs/

# Change the Mode
RUN chmod -R 750 /Softwares/apps/payments/*

# Expose Micro Service Port
EXPOSE 9090

# Start Micro Service
#ENTRYPOINT ["java","-jar","/Softwares/apps/payments/payments-service-0.1.0.jar"]
ENTRYPOINT ["java","-jar","/Softwares/apps/payments/payments-service-0.1.0-spring-boot.jar"]
