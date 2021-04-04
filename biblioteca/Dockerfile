FROM openjdk:11-jre
COPY /biblioteca/target/control-bibliotecas.jar /aplicaciones/control-bibliotecas/
COPY /configuration-files/application.yaml /aplicaciones/control-bibliotecas/
COPY /configuration-files/log4j2.xml /aplicaciones/control-bibliotecas/
ARG INIT_RAM=258m
ARG AMBIENTE=null
ENV INIT_RAM=${INIT_RAM}
ARG RAM_LIMIT=384m
ENV RAM_LIMIT=${RAM_LIMIT}
WORKDIR /aplicaciones/control-bibliotecas/
ENTRYPOINT ["sh","-c","java -jar -XX:MaxRAM=${RAM_LIMIT} -Xmx${RAM_LIMIT} -Dlog4j.configurationFile=log4j2.xml -jar  control-bibliotecas.jar"]
