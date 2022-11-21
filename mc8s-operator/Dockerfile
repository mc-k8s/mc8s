FROM openjdk:19

WORKDIR /src

COPY build/libs/mc8s-operator-1.0-SNAPSHOT-all.jar .

ENTRYPOINT ['java', '-jar', 'mc8s-operator-1.0-SNAPSHOT-all.jar']