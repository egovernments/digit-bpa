CREATE TABLE EGBPA_MSTR_OCCUPANCY 
   (	
	 id bigint NOT NULL, 
	 code character varying(128) NOT NULL,
	 description character varying(256) not null,
	 isactive boolean  DEFAULT true,
	 version numeric DEFAULT 0,
	 createdBy bigint NOT NULL,
	 createdDate timestamp without time zone NOT NULL,
	 lastModifiedBy bigint,
     lastModifiedDate timestamp without time zone,
	occupantDoors numeric,
     noofOccupancy numeric, 
     occupantLoad numeric,
      CONSTRAINT pk_EGBPA_MSTR_OCCUPANCY PRIMARY KEY (id),
	 CONSTRAINT unq_OCCUPANCY_code UNIQUE (code),
	 CONSTRAINT FK_EGBPA_MSTR_OCCUPANCY_MDFDBY FOREIGN KEY (lastModifiedBy)
         REFERENCES EG_USER (ID),
    CONSTRAINT FK_EGBPA_MSTR_OCCUPANCY_CRTBY FOREIGN KEY (createdBy)
         REFERENCES EG_USER (ID)
   );
CREATE INDEX IDX_EGBPA_MSTR_OCCUPANCY_CODE ON EGBPA_MSTR_OCCUPANCY USING btree (code);     
   
CREATE SEQUENCE SEQ_EGBPA_MSTR_OCCUPANCY;
