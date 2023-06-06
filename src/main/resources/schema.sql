create sequence HIBERNATE_SEQ START WITH 1000;
CREATE TABLE postcodelatlng (
  id BIGINT NOT NULL primary key,
  postcode varchar(8) NOT NULL,
  latitude decimal(10,7) NULL,
  longitude decimal(10,7) NULL
);
CREATE INDEX postcode_idx ON postcodelatlng(postcode);