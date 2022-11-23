FROM openjdk:19-buster

WORKDIR /src

RUN wget https://download.getbukkit.org/spigot/spigot-1.19.2.jar
RUN mkdir plugins
COPY ./start_script.sh .
#COPY build/libs/mc8s-gameserver-1.0-SNAPSHOT.jar ./plugins

ENTRYPOINT ["/bin/bash", "start_script.sh"]