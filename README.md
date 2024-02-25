# type-systems-course

Сборка проекта

```bash
./gradlew build
```

Запуск

```bash
java -jar build/libs/typechecker.jar
```

Запуск тестов

```bash
./gradlew test
```

Программа при запуске ожидает программу на языке
Stella на stdin с EOF (Ctrl+D) в конце, после чего производит
проверку типов.