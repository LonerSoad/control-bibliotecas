FROM bitnami/mariadb:10.4
USER root
COPY Mariadb/my.cnf /opt/bitnami/mariadb/conf/my.cnf
RUN apt-get update
RUN apt-get install -y locales locales-all
RUN echo "America/Mexico_City" >> /etc/timezone
ENV LC_ALL es_MX.UTF-8
ENV LANG es_MX.UTF-8
ENV LANGUAGE es_MX.UTF-8
