#!/bin/bash

if [[ $MC8S_DISABLE_NETHER == "true" ]];
  then echo "allow-nether=false" >> server.properties;
fi

if [[ $MC8S_DISABLE_END == "true" ]];
  then echo $'settings:\n  allow-end: false' >> bukkit.yml;
fi

cp -R /var/map-template/* .
java -Dcom.mojang.eula.agree=true -jar spigot-1.19.2.jar -o false