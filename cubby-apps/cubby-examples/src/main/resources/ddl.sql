-- TODO
create table USER (ID VARCHAR, PASSWORD VARCHAR, NAME VARCHAR, PRIMARY KEY (ID));
create table TODO (ID IDENTITY, TEXT VARCHAR, MEMO VARCHAR, TYPEID INT, LIMITDATE DATE, PRIMARY KEY (ID));
create table TODOTYPE (ID INT, NAME VARCHAR, PRIMARY KEY (ID));
insert into USER values ('test', 'test', 'Cubby');
insert into TODOTYPE values (1, 'type1');
insert into TODOTYPE values (2, 'type2');
insert into TODOTYPE values (3, 'type3');
