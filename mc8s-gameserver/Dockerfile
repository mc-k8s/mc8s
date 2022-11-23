FROM openjdk:19-buster

WORKDIR /src

RUN wget https://download.getbukkit.org/spigot/spigot-1.19.2.jar
RUN mkdir plugins
RUN echo "eula=true" >> eula.txt
RUN echo "online-mode=false" >> server.properties
#COPY build/libs/mc8s-gameserver-1.0-SNAPSHOT.jar ./plugins

ENTRYPOINT ["java", "-jar", "spigot-1.19.2.jar"]