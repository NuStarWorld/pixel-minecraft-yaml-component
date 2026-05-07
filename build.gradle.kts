import org.gradle.api.artifacts.component.ProjectComponentIdentifier

plugins {
    java
    idea
    `java-library`
    `maven-publish`
    id("org.jetbrains.kotlin.jvm") version "2.1.0"
    id("org.jetbrains.dokka") version "1.9.20"
}

/** 读取 Maven 凭据，优先使用 Gradle 属性，其次使用环境变量。 */
fun Project.mavenCredential(name: String): String? {
    return findProperty(name)?.toString()?.takeUnless { it.isBlank() || it == "null" }
        ?: System.getenv(name)?.takeUnless { it.isBlank() }
}

allprojects {
    repositories {
        mavenLocal()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://nexus.gtnewhorizons.com/repository/public/")
        maven("https://maven.nustar.top/repository/nustar-public/")
        maven{
            url = uri("https://maven.nustar.top/repository/pixel-repo/")
            val mavenUser = project.mavenCredential("NuStarMavenUser")
            val mavenPassword = project.mavenCredential("NuStarMavenPassword")
            if (mavenUser != null && mavenPassword != null) {
                credentials {
                    username = mavenUser
                    password = mavenPassword
                }
            }
        }
        mavenCentral()
    }
}

val platformProjectPaths = setOf(":platform:spigot", ":platform:nukkit")

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "maven-publish")

    dependencies {
        compileOnly("org.projectlombok:lombok:1.18.38")
        annotationProcessor("org.projectlombok:lombok:1.18.38")
        if (!project.name.contains("common")) {
            implementation(project(":common"))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.compilerArgs.addAll(listOf("-XDenableSunApiLintControl"))
    }

    java {
        withSourcesJar()
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    tasks.withType<Jar> {
        archiveBaseName.set(rootProject.name + "-" + project.name)
    }

    tasks.named<Jar>("jar") {
        from(project.sourceSets["main"].output)

        if (project.path in platformProjectPaths) {
            val internalRuntimeJars = configurations.named("runtimeClasspath").map { runtimeClasspath ->
                runtimeClasspath.incoming.artifactView {
                    componentFilter { componentId ->
                        componentId is ProjectComponentIdentifier
                    }
                }.files
            }

            dependsOn(internalRuntimeJars)
            from(internalRuntimeJars.map { files ->
                files.map { file ->
                    zipTree(file)
                }
            })
            duplicatesStrategy = DuplicatesStrategy.EXCLUDE
        }
    }

    publishing {
        repositories {
            maven {
                val mavenUser = project.mavenCredential("NuStarMavenUser")
                val mavenPassword = project.mavenCredential("NuStarMavenPassword")
                if (mavenUser != null && mavenPassword != null) {
                    credentials {
                        username = mavenUser
                        password = mavenPassword
                    }
                }
                authentication {
                    create<BasicAuthentication>("basic")
                }
                val releasesRepoUrl = uri("https://maven.nustar.top/repository/pixel-releases/")
                val snapshotsRepoUrl = uri("https://maven.nustar.top/repository/pixel-snapshot/")
                url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            }
            mavenLocal()
        }
        publications {

            create<MavenPublication>("maven") {
                artifactId = "${rootProject.name}-${project.name}".lowercase()
                groupId = project.group.toString()
                version = "${project.version}"
                from(components["java"])
                println("> ApplyMaven \"$groupId:$artifactId:$version\"")
            }
        }
    }
}
