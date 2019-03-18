/*
 * Copyright 2018 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

allprojects {
    intellij {
        setPlugins("yaml")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    dependencies {
//        compile("org.jetbrains.kotlin:kotlin-runtime:1.3")

//        compile("io.grpc:grpc-netty:1.19.0") {
//            exclude(group = "com.google.guava", module = "guava")
//            exclude(group = "com.google.code.gson", module = "gson")
//            exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
//            exclude(group = "com.google.protobuf", module = "protobuf-java")
//        }
//        compile("com.google.protobuf:protobuf-java:3.7.0")
//    compile("com.google.protobuf:protobuf-java:3.4.0")

        compile("io.grpc:grpc-protobuf:1.19.0") {
            exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
        exclude(group = "com.google.protobuf", module = "protobuf-java")
//            exclude(group = "io.grpc", module = "grpc-protobuf-lite")
        }
    compile("io.grpc:grpc-stub:1.19.0") {
            exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")

        exclude(group = "com.google.protobuf", module = "protobuf-java")
//        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
//        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }

        compile("io.grpc:grpc-okhttp:1.19.0") {
            exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")
            exclude(group = "com.squareup.okio", module = "okio")

            exclude(group = "com.google.protobuf", module = "protobuf-java")
//        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
//        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }

        compile("io.grpc:grpc-netty:1.19.0") {
            exclude(group = "com.google.guava", module = "guava")
        exclude(group = "com.google.code.gson", module = "gson")

            exclude(group = "com.google.protobuf", module = "protobuf-java")
//        exclude(group = "com.google.api.grpc", module = "proto-google-common-protos")
//        exclude(group = "com.google.protobuf", module = "protobuf-java")
    }

        testCompile("io.mockk:mockk:+") {
            // this ensures kotlin plugin/version takes precedence, mockk updates less often
            exclude(group = "org.jetbrains.kotlin", module = "kotlin-reflect")
        }
    }
}

dependencies {
    compile(project(":kubernetes:core"))
    compile(project(":kubernetes:skaffold"))

//    compile("com.google.protobuf:protobuf-java:2.5.0")


    testCompile(project(":kubernetes:common-test-lib"))
}

inline operator fun <T : Task> T.invoke(a: T.() -> Unit): T = apply(a)
