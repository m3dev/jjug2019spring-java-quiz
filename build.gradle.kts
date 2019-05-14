plugins {
  java
}

repositories {
  mavenCentral()
}

java {
  sourceCompatibility = JavaVersion.VERSION_11
  targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<Test> {
  useJUnitPlatform()
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}
