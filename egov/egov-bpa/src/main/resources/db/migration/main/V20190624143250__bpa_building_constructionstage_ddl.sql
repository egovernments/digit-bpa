CREATE TABLE EGBPA_BUILDING_CONSTRUCTIONSTAGE
   (	
     id bigint NOT NULL, 
	 name character varying(128) NOT NULL,
	 description character varying(256) NOT NULL,
	 active boolean DEFAULT true NOT NULL,
	 version numeric DEFAULT 0,
	 createdBy bigint NOT NULL,
	 createdDate timestamp without time zone NOT NULL,
	 lastModifiedBy bigint,
     lastModifiedDate timestamp without time zone,
	 CONSTRAINT pk_EGBPA_BUILDING_CONSTRUCTIONSTAGE PRIMARY KEY (id),
	 CONSTRAINT unq_EGBPA_BUILDING_CONSTRUCTIONSTAGE_name UNIQUE (name),
	 CONSTRAINT FK_EGBPA_BUILDING_CONSTRUCTIONSTAGE_lstmdfdby FOREIGN KEY (lastModifiedBy)
         REFERENCES state.EG_USER (ID),
    CONSTRAINT FK_EGBPA_BUILDING_CONSTRUCTIONSTAGE_CRTBY FOREIGN KEY (createdBy)
         REFERENCES state.EG_USER (ID)
   );

CREATE SEQUENCE SEQ_EGBPA_BUILDING_CONSTRUCTIONSTAGE;