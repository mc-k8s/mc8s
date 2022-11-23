#!/bin/bash

if [[ $MC8S_DISABLE_NETHER == "true" ]];
  then echo $'allow-nether=false\n' >> server.properties;
fi

if [[ $MC8S_DISABLE_END == "true" ]];
  then echo $'settings:\n  allow-end: false' >> bukkit.yml;
fi

echo $'settings:\n  bungeecord: true' >> spigot.yml;
echo $'online-mode=false\n' >> server.properties
cp -R /var/map-template/* .
java -Dcom.mojang.eula.agree=true -jar spigot-1.19.2.jar