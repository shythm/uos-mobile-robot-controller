# Mobile Robot Controller & Simulator

- 2023-2학기 소프트웨어공학 팀프로젝트
- 구성원
  - 팀장 이성호
  - 팀원 김민교
  - 팀원 이정우

## Contribution

본 프로젝트는 `main` branch와 `dev/{contributor}/*` branch로 나누어 개발을 진행한다.

- `main` branch는 개발된 기능들을 통합하고 검수가 완료되어 정상적으로 동작하는 상태만을 보관한다.
  - 팀장에 의해 관리가 진행된다.
  - 팀원은 PR(Pull Request)를 통하여 해당 브랜치와의 통합을 진행한다.
- `dev/{contributor}/*` branch는 기능을 개발하고 테스팅하는 용도로 개인(contributor)이 직접 생성하고 관리해야 한다. 예를 들어, steve가 A기능을 개발을 한다고 가정했을 때 `dev/steve/A` branch를 생성하여 개발을 진행하여야 한다.
  - 특별한 사유가 없으면 `main`에서 branch를 생성하도록 한다.
  - `main` branch에서 `git checkout -b dev/{contributor}/*` 명령어를 통해 branch를 생성할 수 있다.

## Project Structure

본 프로젝트는 `app`, `sim-common`, `sim-model`, `sim-view`, `sim-addon` 컴포넌트로 구성된다. 이때 컴포넌트는 시스템의 구성 요소로 배포할 수 있는 가장 작은 단위를 의미한다. 따라서 해당 프로젝트를 빌드하면 `app.jar`, `sim-common.jar`, `sim-model.jar`, `sim-view.jar`, `sim-addon.jar` 산출물이 존재하게 된다.

```
Root project 'mobile-robot-sim'
+--- Project ':app'
+--- Project ':sim-addon'
+--- Project ':sim-common'
+--- Project ':sim-model'
\--- Project ':sim-view'
```

## How to run

```
./gradlew run
.\gradlew.bat run
```

## How to build

```
./gradlew build
.\gradlew.bat build
```

## Build Environment

### Compile From Scratch

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

### Gradle 프로젝트 초기화

모든 java 소스 코드를 일일이 컴파일하는 것에는 많은 문제가 존재한다. 따라서 우리는 [Gradle](https://github.com/gradle/gradle)이라는 빌드 도구를 이용하여 빌드를 자동화한다. [Building Java Application Sample](https://docs.gradle.org/8.4/samples/sample_building_java_applications.html)을 참고하여 빌드 환경을 구축해본다.

```bash
gradle init
# Type of project to generate: application
# Implementation language: Java
# Build script DSL: Groovy
# Test framework: JUnit Jupiter
```

### 기본 디렉터리 구조

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
        │       └── teamkernel/sim
        │           └── App.java
```

- `gradle`: wrapper 파일을 위한 디렉터리
- `gradlew`: Gradle wrapper 시작 스크립트 (Unix 계열)
- `gradlew.bat`: Gradle wrapper 시작 스크립트 (Windows)
- `settings.gradle`: 빌드 이름 및 **서브 프로젝트**를 정의하는 파일
- `build.gradle`: 프로젝트 빌드 스크립트
- `src/main/java`: 소스코드 디렉터리

### 최종 개발 환경

- 아래의 패키지를 설치하여야 한다.

  - Microsoft Visual Studio Code(vscode)
  - OpenJDK 17
  - Gradle

- vscode에서 사용하는 Extension은 다음과 같다.

  - [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)
  - [Gradle for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-gradle)
