INSERT INTO users (id, code, email, is_moderator, name, password, photo, reg_time)
VALUES ( 1,
         'userUser',
         '9824104901@mail.ru',
         0,
         'User',
         '$2y$12$bTfQ.I1qLk0Z4dLdc3nylOzfwchQPkqKdCWXk6WFhYLDppCsYfS7S',
         'u1s',
         '2021-04-10'),

        (2,
        'modeMode',
        'anmiha0672@mail.ru',
        1,
        'Moderator',
        '$2y$12$bTfQ.I1qLk0Z4dLdc3nylOzfwchQPkqKdCWXk6WFhYLDppCsYfS7S',
        'us',
        '2021-04-10');

INSERT INTO global_settings (id, code, name, value)
    VALUES(1,'MULTIUSER_MODE','Многопользовательский режим', 'YES'),
        (2,'POST_PREMODERATION','Премодерация постов', 'YES'),
        (3,'STATISTICS_IS_PBLIC','Показывать всем статистику блога', 'YES');
