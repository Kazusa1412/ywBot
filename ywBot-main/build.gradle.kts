import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

buildscript {
    dependencies {
        classpath("com.github.jengelman.gradle.plugins:shadow:6.1.0")
    }
}

plugins {
    kotlin("multiplatform") version Versions.kotlin
    id("com.github.johnrengelman.shadow") version Versions.shadowJar
}

apply(plugin = "com.github.johnrengelman.shadow")

group = "com.elouyi"
version = Versions.ywBot

repositories {
    mavenCentral()
}

kotlin {
    /* Targets configuration omitted. 
    *  To find out how to configure the targets, please follow the link:
    *  https://kotlinlang.org/docs/reference/building-mpp-with-gradle.html#setting-up-targets */

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = Versions.jvmTarget
        }
        testRuns["test"].executionTask.configure {
            useJUnit()
        }

        /**
         *  不知道怎么生成 多平台项目 的jar包，用这个将就下 ( 老人 手机 地铁.jpg)
         *  这个 task 会生成两个 jar，一个大一个小
         *  把大的 jar 包里面的 META-INF 删掉
         *  然后把小的文件夹的 META-INF 和 com 文件夹复制到大 jar 包内即可
         */
        tasks.register("ywJar",ShadowJar::class){
            dependsOn("jvmJar")
            configurations = mutableListOf(project.configurations.findByName("jvmRuntimeClasspath"))
//            manifest {
//                attributes (
//                    "Main-Class" to "com.elouyi.YwBotApplicationKt"
//                )
//            }
        }

        tasks.withType<org.gradle.jvm.tasks.Jar> {
            doFirst {
                manifest {
                    attributes (
                        "Main-Class" to "com.elouyi.YwBotApplicationKt"
                    )
                }
            }
        }
    }

    mingwX64 {
        binaries {
            executable {
                entryPoint = "com.elouyi.main"
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlin("stdlib-common"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("net.mamoe:mirai-core:${Versions.mirai}")
                implementation("org.yaml:snakeyaml:${Versions.snakeyaml}")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
        val mingwX64Main by getting
        val mingwX64Test by getting
    }
}