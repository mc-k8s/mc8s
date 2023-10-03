rootProject.name = "mc8s"

include("mc8s-operator")
include("mc8s-classfilter")
include("mc8s-bungeecord")
include("mc8s-kubernetes-bridge")
include("mc8s-gameserver")
include("service:mc8s-player-service")
include("service:mc8s-player-service-api")
findProject(":service:mc8s-player-service-api")?.name = "mc8s-player-service-api"
include("service:mc8s-player-service-api")
findProject(":service:mc8s-player-service-api")?.name = "mc8s-player-service-api"
