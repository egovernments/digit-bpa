create table EGBPA_MSTR_APPLICATIONTYPE
	(
	  id bigint NOT NULL,
          name character varying(100) NOT NULL,
          description character varying(256),
          enabled boolean,
	  createdby bigint NOT NULL,
          createddate timestamp without time zone NOT NULL,
 	  lastModifiedDate timestamp without time zone,
 	  lastModifiedBy bigint,
	  version numeric NOT NULL,
	  CONSTRAINT PK_APPLICATIONTYPE_ID PRIMARY KEY (ID),
	  CONSTRAINT FK_EGBPA_MSTR_APPLICATIONTYPE_MDFDBY FOREIGN KEY (lastModifiedBy) REFERENCES EG_USER (ID),
          CONSTRAINT FK_EGBPA_MSTR_APPLICATIONTYPE_CRTBY FOREIGN KEY (createdBy)REFERENCES EG_USER (ID)
   );
create sequence SEQ_EGBPA_MSTR_APPLICATIONTYPE;