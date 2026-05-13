tasks {
    processResources {
        filesMatching("plugin.yml") {
            expand(rootProject.properties)
        }
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}
dependencies {
    implementation(project(":api"))
    compileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    compileOnly("cn.hamster3:HamsterCurrency:2.2.0")
    compileOnly("me.clip:placeholderapi:2.10.4")
    compileOnly("org.serverct.ersha:AttributePlus:3.3.3.0")
    compileOnly("pers.neige.neigeitems:NeigeItems:1.21.62")
}