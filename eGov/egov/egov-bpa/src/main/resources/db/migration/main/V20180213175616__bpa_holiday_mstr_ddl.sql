
Create table EGBPA_MSTR_HOLIDAY( 
  id bigint,
  holidayType varchar(14) NOT NULL,
  holidayDate date NOT NULL,
  description varchar(256),
  year varchar(30),
  createdby bigint,
  createddate timestamp without time zone,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version bigint

);
ALTER TABLE EGBPA_MSTR_HOLIDAY ADD CONSTRAINT pk_bpa_mstr_holiday PRIMARY KEY (id);
CREATE sequence SEQ_EGBPA_MSTR_HOLIDAY;
ALTER TABLE EGBPA_MSTR_HOLIDAY ADD CONSTRAINT fk_bpa_mstr_holiday_crtby FOREIGN KEY (createdby) REFERENCES eg_user (id);
ALTER TABLE EGBPA_MSTR_HOLIDAY ADD CONSTRAINT fk_bpa_mstr_holiday_mdfdby FOREIGN KEY (lastmodifiedby) REFERENCES eg_user (id);
ALTER TABLE EGBPA_MSTR_HOLIDAY ADD CONSTRAINT EGBPA_MSTR_HOLIDAY_UNIQ UNIQUE (year, holidayDate, holidayType);
