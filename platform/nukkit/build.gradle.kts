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
}