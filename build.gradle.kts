// SFTeacher 独立 Slimefun 附属插件构建脚本
// 仅依赖 Paper API 与本地 Slimefun jar（libs/，compileOnly），不联网下载 Slimefun。
// 结构为单源集：功能极简（指南内嵌一个按钮分组页，点击按钮输出可点击链接），无需版本兼容层。
import org.gradle.api.attributes.java.TargetJvmVersion

plugins {
    java
}

group = "io.github.haiman233"
version = "1.3.0-standalone"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.compileJava {
    options.encoding = "UTF-8"
    // Java 17 字节码，与 WorldTaste 保持一致（1.20~1.21.x 服务端均可加载）
    options.release.set(17)
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Paper 1.21.11 API（非 Slimefun，可从 papermc 仓库获取）
    compileOnly("io.papermc.paper:paper-api:1.21.11-R0.1-SNAPSHOT")
    // 本地 Slimefun 依赖（compileOnly，运行期由服务器提供）：
    // 与 WorldTaste 相同的 2025.11 release jar，包结构一致，仅作编译参考
    compileOnly(files("libs/Slimefun-2025.11-release.jar"))
}

// paper-api 1.21.x 的模块元数据只发布 JVM 21 variant；本插件字节码目标为 Java 17，
// 显式指定依赖解析的 JVM 版本为 21 以正常选中依赖，编译产物（--release 17）不受影响
configurations.compileClasspath {
    attributes {
        attribute(TargetJvmVersion.TARGET_JVM_VERSION_ATTRIBUTE, 21)
    }
}

tasks.jar {
    archiveBaseName.set("SFTeacher")
    archiveVersion.set(project.version.toString())
    archiveClassifier.set("")
}

tasks.build {
    dependsOn(tasks.jar)
}
