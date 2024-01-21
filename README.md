# java-filmorate
Template repository for Filmorate project.
Спринт - 9
Представьте, что после изучения сложной темы и успешного выполнения всех заданий вы решили отдохнуть и провести вечер за просмотром фильма. Вкусная еда уже готовится, любимый плед уютно свернулся на кресле — а вы всё ещё не выбрали, что же посмотреть!


Фильмов много — и с каждым годом становится всё больше. Чем их больше, тем больше разных оценок. Чем больше оценок, тем сложнее сделать выбор. Однако не время сдаваться! Вы напишете бэкенд для сервиса, который будет работать с фильмами и оценками пользователей, а также возвращать топ-5 фильмов, рекомендованных к просмотру. Теперь ни вам, ни вашим друзьям не придётся долго размышлять, что посмотреть вечером.
В этом спринте вы начнёте с малого, но очень важного: создадите каркас Spring Boot приложения Filmorate (от англ. film — «фильм» и rate — «оценивать»). В дальнейшем сервис будет обогащаться новым функционалом и с каждым спринтом становиться лучше благодаря вашим знаниям о Java. Скорее вперёд!
Предварительная настройка проекта
В репозитории создайте ветку controllers-films-users. Разработку решения для первого спринта нужно вести в ней. Репозиторий при этом должен быть публичным.
В качестве основы для проекта используйте этот шаблон. В нём уже настроен maven-проект в файле pom.xml, но вы можете модифицировать некоторые параметры, указанные ниже.
Параметр	Значение
Group (организация)	ru.yandex.practicum
Artifact (артефакт)	filmorate
Name (название проекта)	filmorate
Dependencies (зависимости)	Spring Web
Ура! Проект настроен. Теперь можно шаг за шагом реализовать приложение.
Модели данных
Создайте пакет model. Добавьте в него два класса — Film и User. Это классы — модели данных приложения.
У model.Film должны быть следующие свойства:
целочисленный идентификатор — id;
название — name;
описание — description;
дата релиза — releaseDate;
продолжительность фильма — duration.
Свойства model.User:
целочисленный идентификатор — id;
электронная почта — email;
логин пользователя — login;
имя для отображения — name;
дата рождения — birthday.
Подсказка: про аннотацию @Data
Используйте аннотацию @Data библиотеки Lombok — с ней будет меньше работы по созданию сущностей.
Хранение данных
Сейчас данные можно хранить в памяти приложения — так же, как вы поступили в случае с менеджером задач. Для этого используйте контроллер.
В следующих спринтах мы расскажем, как правильно хранить данные в долговременном хранилище, чтобы они не зависели от перезапуска приложения.
REST-контроллеры
Создайте два класса-контроллера. FilmController будет обслуживать фильмы, а UserController — пользователей. Убедитесь, что созданные контроллеры соответствуют правилам REST.
Добавьте в классы-контроллеры эндпоинты с подходящим типом запроса для каждого из случаев.
Для FilmController:
добавление фильма;
обновление фильма;
получение всех фильмов.
Для UserController:
создание пользователя;
обновление пользователя;
получение списка всех пользователей.
Эндпоинты для создания и обновления данных должны также вернуть созданную или изменённую сущность.
Подсказка: про аннотацию @RequestBody
Используйте аннотацию @RequestBody, чтобы создать объект из тела запроса на добавление или обновление сущности.
Валидация
Проверьте данные, которые приходят в запросе на добавление нового фильма или пользователя. Эти данные должны соответствовать определённым критериям.
Для Film:
название не может быть пустым;
максимальная длина описания — 200 символов;
дата релиза — не раньше 28 декабря 1895 года;
продолжительность фильма должна быть положительной.
Для User:
электронная почта не может быть пустой и должна содержать символ @;
логин не может быть пустым и содержать пробелы;
имя для отображения может быть пустым — в таком случае будет использован логин;
дата рождения не может быть в будущем.
Подсказка: как обработать ошибки
Для обработки ошибок валидации напишите новое исключение — например, ValidationException.
Логирование
Добавьте логирование для операций, которые изменяют сущности — добавляют и обновляют их. Также логируйте причины ошибок — например, если валидация не пройдена. Это считается хорошей практикой.
Подсказка: про логирование сообщений
Воспользуйтесь библиотекой slf4j для логирования и объявляйте логер для каждого класса — так будет сразу видно, где в коде выводится та или иная строка.
private final static Logger log = LoggerFactory.getLogger(Example.class);
Вы также можете применить аннотацию @Slf4j библиотеки Lombok, чтобы не создавать логер вручную.
Тестирование
Добавьте тесты для валидации. Убедитесь, что она работает на граничных условиях.
Подсказка: на что обратить внимание при тестировании
Проверьте, что валидация не пропускает пустые или неверно заполненные поля. Посмотрите, как контроллер реагирует на пустой запрос.
Проверьте себя
Так как у вашего API пока нет интерфейса, вы будете взаимодействовать с ним через веб-клиент. Мы подготовили набор тестовых данных — Postman коллекцию. С её помощью вы сможете протестировать ваше API: postman.json
Дополнительное задание*
А теперь необязательное задание для самых смелых! Валидация, которую мы предлагаем реализовать в основном задании, — базовая. Она не покрывает всех возможных ошибок. Например, всё ещё можно создать пользователя с такой электронной почтой: это-неправильный?эмейл@.
В Java есть инструменты для проверки корректности различных данных. С помощью аннотаций можно задать ограничения, которые будут проверяться автоматически. Для этого добавьте в описание сборки проекта следующую зависимость.
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
Теперь вы можете применить аннотацию @NotNull к полю класса-модели для проверки на null, @NotBlank — для проверки на пустую строку, @Email — для проверки на соответствие формату электронного адреса. Полный список доступных аннотаций можно найти в документации.
💡 Чтобы Spring не только преобразовал тело запроса в соответствующий класс, но и проверил корректность переданных данных, вместе с аннотацией @RequestBody нужно использовать аннотацию @Valid.  
public createUser(@Valid @RequestBody User user)
Поздравляем: первый шаг навстречу уютным киновечерам сделан. 🎞️
Интересного вам программирования!
Спринт - 10
Настало время улучшить Filmorate. Чтобы составлять рейтинг фильмов, нужны отзывы пользователей. А для улучшения рекомендаций по просмотру хорошо бы объединить пользователей в комьюнити.
По итогам прошлого спринта у вас получилась заготовка приложения. Программа может принимать, обновлять и возвращать пользователей и фильмы. В этот раз улучшим API приложения до соответствия REST, а также изменим архитектуру приложения с помощью внедрения зависимостей.
Наводим порядок в репозитории
Для начала убедитесь в том, что ваша работа за предыдущий спринт слита с главной веткой main. Создайте новую ветку, которая будет называться add-friends-likes. Название ветки важно сохранить, потому что оно влияет на запуск тестов в GitHub.
Подсказка: про работу в Git
Архитектура
Начнём с переработки архитектуры. Сейчас вся логика приложения спрятана в контроллерах — изменим это. Вынесите хранение данных о фильмах и пользователях в отдельные классы. Назовём их «хранилищами» (англ. storage) — так будет сразу понятно, что они делают.  
Создайте интерфейсы FilmStorage и UserStorage, в которых будут определены методы добавления, удаления и модификации объектов.
Создайте классы InMemoryFilmStorage и InMemoryUserStorage, имплементирующие новые интерфейсы, и перенесите туда всю логику хранения, обновления и поиска объектов.
Добавьте к InMemoryFilmStorage и InMemoryUserStorage аннотацию @Component, чтобы впоследствии пользоваться внедрением зависимостей и передавать хранилища сервисам.
Подсказка: про структуру проекта
Новая логика
Пока у приложения нет никакой бизнес-логики, кроме валидации сущностей. Обеспечим возможность пользователям добавлять друг друга в друзья и ставить фильмам лайки.
Создайте UserService, который будет отвечать за такие операции с пользователями, как добавление в друзья, удаление из друзей, вывод списка общих друзей. Пока пользователям не надо одобрять заявки в друзья — добавляем сразу. То есть если Лена стала другом Саши, то это значит, что Саша теперь друг Лены.
Создайте FilmService, который будет отвечать за операции с фильмами, — добавление и удаление лайка, вывод 10 наиболее популярных фильмов по количеству лайков. Пусть пока каждый пользователь может поставить лайк фильму только один раз.
Добавьте к ним аннотацию @Service — тогда к ним можно будет получить доступ из контроллера.
Подсказка: ещё про структуру
Подсказка: про список друзей и лайки
Зависимости
Переделайте код в контроллерах, сервисах и хранилищах под использование внедрения зависимостей.
Используйте аннотации @Service, @Component, @Autowired. Внедряйте зависимости через конструкторы классов.
Классы-сервисы должны иметь доступ к классам-хранилищам. Убедитесь, что сервисы зависят от интерфейсов классов-хранилищ, а не их реализаций. Таким образом в будущем будет проще добавлять и использовать новые реализации с другим типом хранения данных.
Сервисы должны быть внедрены в соответствующие контроллеры.
Подсказка: @Service vs @Component
@Component — аннотация, которая определяет класс как управляемый Spring. Такой класс будет добавлен в контекст приложения при сканировании. @Service не отличается по поведению, но обозначает более узкий спектр классов — такие, которые содержат в себе бизнес-логику и, как правило, не хранят состояние.
Полный REST
Дальше стоит заняться контроллерами и довести API до соответствия REST.
С помощью аннотации @PathVariable добавьте возможность получать каждый фильм и данные о пользователях по их уникальному идентификатору: GET .../users/{id}.
Добавьте методы, позволяющие пользователям добавлять друг друга в друзья, получать список общих друзей и лайкать фильмы. Проверьте, что все они работают корректно.
PUT /users/{id}/friends/{friendId} — добавление в друзья.
DELETE /users/{id}/friends/{friendId} — удаление из друзей.
GET /users/{id}/friends — возвращаем список пользователей, являющихся его друзьями.
GET /users/{id}/friends/common/{otherId} — список друзей, общих с другим пользователем.
PUT /films/{id}/like/{userId} — пользователь ставит лайк фильму.
DELETE /films/{id}/like/{userId} — пользователь удаляет лайк.
GET /films/popular?count={count} — возвращает список из первых count фильмов по количеству лайков. Если значение параметра count не задано, верните первые 10.
Убедитесь, что ваше приложение возвращает корректные HTTP-коды.
400 — если ошибка валидации: ValidationException;
404 — для всех ситуаций, если искомый объект не найден;
500 — если возникло исключение.
Подсказка
НастройтеExceptionHandler для централизованной обработки ошибок.
Тестирование
Убедитесь, что приложение работает, — протестируйте его с помощью Postman: postman.json.
Ого! Оцените, как Filmorate быстро растёт, — все компоненты занимают свои места, проявляется настоящая бизнес-логика. Любители кино потирают руки. Удачной разработки!