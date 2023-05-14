INSERT INTO expense_category (name, description)
VALUES ('Обязательные', 'обязательные платежи: кредиты, комм.услуги, питание и т.д.'),
       ('Произвольные', 'разовые платежи'),
       ('Непредвиденные', 'непредвиденные расходы');

INSERT INTO expense (category_id, name, description)
VALUES (1, 'Ипотека', 'ежемесячно'),
       (1, 'Потребительский кредит', 'ежемесячно'),
       (1, 'Коммунальные услуги', 'ежемесячно'),
       (1, 'Продукты и хоз.товары', 'ежедневно'),
       (1, 'Оплата за садик, доп.занятия', 'ежемесячно'),
       (2, 'Оплата за кружки', 'анг.язык, баскетбол, рисование, гимнастика'),
       (2, 'Сбор средств на подарок', 'в садике, в школе, на работе'),
       (3, 'Ремонт', 'авто-запчасти, строй. и расх.материалы, услуги');

INSERT INTO income (name, description)
VALUES ('Зарплата и бонусы', 'основная работа'),
       ('Сдача аренда квартиры', 'средства от арендаторов'),
       ('Фриланс', 'дополнительная работа');

INSERT INTO actor (name, description)
VALUES ('Семья', 'общие семейные расходы и доходы'),
       ('Руслан (отец)', 'глава семьи'),
       ('Алия (мать)', 'заместитель главы семьи'),
       ('Тамирлан (сын)', 'старший сын'),
       ('Арлан (сын)', 'младший сын'),
       ('Рауана (дочь)', 'старшая дочь'),
       ('Диана (дочь)', 'младшая дочь'),
       ('Родственники', 'с обоих сторон');

INSERT INTO checks (val, date, note, income_id, expense_id, actor_id, object_id)
VALUES (500000, '2023-01-31', 'зарплата отца', 1, null, 2, null),
       (350000, '2023-01-31', 'зарплата матери', 1, null, 3, null),
       (200000, '2023-01-03', 'аренда квартиры', 2, null, 1, null),
       (250000, '2023-01-06', 'ипотека', null, 1, 1, null),
       (25000, '2023-01-25', 'ком.услуги', null, 3, 1, null),
       (50000, '2023-01-01', 'продукты', null, 4, 1, null),
       (50000, '2023-01-10', 'хоз.товары', null, 4, 1, null),
       (25000, '2023-01-01', 'дет.садик', null, 5, 7, null),
       (10000, '2023-01-15', 'замена смесителя', null, 8, 1, null);

INSERT INTO budget (sum, date, actor_id)
VALUES (200000, '2023-01-03', 1),
       (-250000, '2023-01-06', 1),
       (-25000, '2023-01-25', 1),
       (-50000, '2023-01-01', 1),
       (-50000, '2023-01-10', 1),
       (-10000, '2023-01-15', 1),
       (500000, '2023-01-31', 2),
       (350000, '2023-01-31', 3),
       (-25000, '2023-01-01', 7);