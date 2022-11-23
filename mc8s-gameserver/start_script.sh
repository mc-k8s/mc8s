#!/bin/bash

if [ "$MC8S_DISABLE_NETHER" == "true"]
  echo "allow-nether=false\n" >> server.properties
fi

java -Dcom.mojang.eula.agree=true -jar spigot-1.19.2.jar -o false