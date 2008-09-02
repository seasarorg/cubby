-- TODO
create table USER (ID VARCHAR, PASSWORD VARCHAR, NAME VARCHAR, PRIMARY KEY (ID));
create table TODO (ID IDENTITY, TEXT VARCHAR, MEMO VARCHAR, TYPEID INT, LIMITDATE DATE, PRIMARY KEY (ID));
create table TODOTYPE (ID INT, NAME VARCHAR, PRIMARY KEY (ID));
insert into USER values ('test', 'test', 'Cubby');
insert into TODOTYPE values (1, 'type1');
insert into TODOTYPE values (2, 'type2');
insert into TODOTYPE values (3, 'type3');

-- Converter example
CREATE TABLE BOOK (ID INTEGER, ISBN13 CHAR(14), TITLE VARCHAR, PRIMARY KEY (ID));
INSERT INTO BOOK VALUES (1, '978-4894714366', 'Effective Java プログラミング言語ガイド');
INSERT INTO BOOK VALUES (2, '978-4894711877', 'Javaの格言—より良いオブジェクト設計のためのパターンと定石');
INSERT INTO BOOK VALUES (3, '978-4894712584', 'Javaの鉄則—エキスパートのプログラミングテクニック');
