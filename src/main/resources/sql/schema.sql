DROP TABLE IF EXISTS `Customer`;

CREATE TABLE Customer 
(
  ID int NOT NULL auto_increment
, first_name VARCHAR2(256) 
, last_name VARCHAR2(256) 
, type VARCHAR2(256)
--, DATE_END timestamp 
--, DATE_CREATE timestamp DEFAULT SYSDATE NOT NULL 
, CONSTRAINT Customer PRIMARY KEY (ID)
);
