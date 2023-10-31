# Mobile Robot Controller & Simulator

- 2023-2학기 소프트웨어공학 팀프로젝트
- 구성원
  - 팀장 이성호
  - 팀원 김민교
  - 팀원 이정우

## Contribution

본 프로젝트는 `main` branch와 `dev-{contributor}-*` branch로 나누어 개발을 진행한다.

- `main` branch는 개발된 기능들을 통합하고 검수가 완료되어 정상적으로 동작하는 상태만을 보관한다.
  - 팀장에 의해 관리가 진행된다.
  - 팀원은 PR(Pull Request)를 통하여 해당 브랜치와의 통합을 진행한다.
- `dev-{contributor}-*` branch는 기능을 개발하고 테스팅하는 용도로 개인(contributor)이 직접 생성하고 관리해야 한다. 예를 들어, steve가 A기능을 개발을 한다고 가정했을 때 `dev-steve-A` branch를 생성하여 개발을 진행하여야 한다.
  - 특별한 사유가 없으면 `main`에서 branch를 생성하도록 한다.
  - `main` branch에서 `git checkout -b dev-{contributor}-*` 명령어를 통해 branch를 생성할 수 있다.

## Documentation

- [프로젝트 계획서](./docs/project-plan.pdf)
- [개발 환경 구축](./docs/build-environment.md)
