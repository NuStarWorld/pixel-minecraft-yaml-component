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
    compileOnly("net.milkbowl.vault:Vault:1.7.3")
    compileOnly("github.saukiya.sxitem:SXItem:4.5.10:all")
    compileOnly(fileTree(File(project.projectDir, "libs")))

    testCompileOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    testCompileOnly("me.clip:placeholderapi:2.10.4")
    testRuntimeOnly("org.spigotmc:spigot-api:1.12.2-R0.1-SNAPSHOT")
    testRuntimeOnly("me.clip:placeholderapi:2.10.4")
    testImplementation(fileTree(File(project.projectDir, "libs")))
}