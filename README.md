# T1_Bootcamp_Weyland

Предисловие: Kafka запускал локально, в application.yml указан
bootstrap-server localhost:9092 и топик для аудита command-audit

Запуск:

## 1- Запустите локально kafka и при необходимости исправьте параметры application.yml:
- audit.servers="<your servers>"
- audit.topic="<your topic>"
Для выбора режима аудита:
- audit.mode=KAFKA для аудита в топик кафки
- audit.mode=CONSOLE для аудита в консоль

## 2-  Запустите bishop-prototype
## 3- Для проверки отправьте POST api запрос на /api/v1/command. Шаблон:
{
    "description": "task",
    "author": "autor",
    "priority": "CRITICAL",
    "time": "12:12:12"
}

- priority может быть CRITICAL или COMMON

## 4- Реализованный аудит можно увидеть в консоли или в топике kafka
## 5- Метрики можно посмотреть по путям:
-/actuator/metrics/robot.queue.size - для просмотра размера очереди
-/actuator/metrics/robot.commands.executed?tag=author:<Author> - для просмотра выполненных задач по автору
