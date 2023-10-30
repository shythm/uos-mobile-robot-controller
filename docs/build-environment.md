# 개발 환경 구축

이 문서는 Mobile Robot Simulator 프로젝트의 개발 및 [빌드](https://en.wikipedia.org/wiki/Software_build) 환경에 대해 설명한다.

## Compile From Scratch

1. `javac` 컴파일러를 이용하여 프로젝트 모든 디렉터리에 있는 자바 소스 코드 컴파일한다.
   ```bash
   # Example
   javac Main.java core/SimController.java
   ```
1. `jar` 명령어를 이용해 중간 언어로 컴파일 된 class 파일들을 모두 하나의 파일로 묶어준다.
   ```bash
   # example
   jar --create --file mobile-robot-sim.jar  --main-class Main -c Main.class core/SimController.java
   ```
1. `java` 명령어를 이용하여 실행 가능하다.
   ```bash
   # example
   java -jar mobile-robot-sim.jar
   ```

- 문제점

  - 의존성 관리가 힘들기에 재컴파일이 필요하지 않은 java 파일 또한 컴파일하게 된다.
  - 컴파일 할 모든 java 파일을 매번 입력해 주어야 한다. 귀찮다.

- 해결 방안

  - [Maven](https://maven.apache.org/)이나 [Gradle](https://github.com/gradle/gradle)과 같은 빌드 자동화 도구를 이용한다.
  - 여기서는 Gradle을 사용한다. 그 이유는 Maven보다 유연하고, 성능이 좋으며, 사용하기 쉽기 때문이다([Gradle vs Maven Comparison](https://gradle.org/maven-vs-gradle/)).

## Gradle 프로젝트 초기화

모든 java 소스 코드를 일일이 컴파일하는 것에는 많은 문제가 존재한다. 따라서 우리는 [Gradle](https://github.com/gradle/gradle)이라는 빌드 도구를 이용하여 빌드를 자동화한다. [Building Java Application Sample](https://docs.gradle.org/8.4/samples/sample_building_java_applications.html)을 참고하여 빌드 환경을 구축해본다.

```bash
gradle init
# Type of project to generate: application
# Implementation language: Java
# Build script DSL: Groovy
# Test framework: JUnit Jupiter
```

## 디렉터리 구조

```
├── gradle
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── gradlew
├── gradlew.bat
├── settings.gradle
└── app
    ├── build.gradle
    └── src
        ├── main
        │   └── java
        │       └── uos/teamkernel/sim
        │           └── App.java
        └── test
            └── java
                └── uos/teamkernel/sim
                    └── AppTest.java
```

- `gradle`: wrapper 파일을 위한 디렉터리
- `gradlew`: Gradle wrapper 시작 스크립트 (Unix 계열, 윈도우 계열은 `gradlew.bat` 사용)
- `settings.gradle`: 빌드 이름 및 서브프로젝트를 정의하는 파일
- `build.gradle`: `app` 프로젝트 빌드 스크립트
- `app/src/main/java`: 소스코드 디렉터리
- `app/src/test/java`: 테스트 소스코드 디렉터리

## 실행 또는 빌드하기

- 애플리케이션 실행: `./gradlew run`
- 애플리케이션 묶기: `./gradlew build`
