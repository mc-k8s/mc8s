FROM openjdk:19-buster

WORKDIR /src

RUN wget https://ci.md-5.net/job/BungeeCord/lastSuccessfulBuild/artifact/bootstrap/target/BungeeCord.jar
RUN mkdir plugins
COPY build/libs/mc8s-bungeecord-1.0-SNAPSHOT-all.jar ./plugins
RUN echo "ip_forward: false\nlisteners: \n  - query_port: 25577  \n    host: 0.0.0.0:25565" >> config.yml
RUN echo "version: 2 \nmodules: []" >> modules.yml

ENTRYPOINT ["java", "-jar", "BungeeCord.jar"]