plugins {
    kotlin("multiplatform") version Versions.kotlin
}

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