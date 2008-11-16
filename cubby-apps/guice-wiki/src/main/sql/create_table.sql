CREATE SEQUENCE PAGE_ID_SEQ START 10000;

CREATE TABLE PAGE (
       ID INTEGER PRIMARY KEY,
     , NAME VARCHAR(200) NOT NULL UNIQUE,
     , CONTENT TEXT
     , VARSION_NO INTEGER NOT NULL,
     , CREATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
     , UPDATED TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);