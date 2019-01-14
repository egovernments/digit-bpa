CREATE TABLE EGBPA_MSTR_SCHEME
   (	 id bigint NOT NULL, 
	 code character varying(52) NOT NULL,
	 description character varying(256) NOT NULL,
	 isactive  boolean  DEFAULT true,
	 version numeric DEFAULT 0,
	 createdBy bigint NOT NULL,
	 createdDate timestamp without time zone NOT NULL,
	 lastModifiedBy bigint,
         lastModifiedDate timestamp without time zone,
	 CONSTRAINT pk_EGBPA_MSTR_SCHEME PRIMARY KEY (id),
	 CONSTRAINT unq_bpascheme_code UNIQUE (code),
	 CONSTRAINT FK_EGBPA_MSTR_SCHEME_MDFDBY FOREIGN KEY (lastModifiedBy) REFERENCES EG_USER (ID),
         CONSTRAINT FK_EGBPA_MSTR_SCHEME_CRTBY FOREIGN KEY (createdBy) REFERENCES EG_USER (ID)
   );

CREATE INDEX IDX_EGBPA_MSTR_SCHEME_CODE ON EGBPA_MSTR_SCHEME USING btree (code);        
CREATE SEQUENCE SEQ_EGBPA_MSTR_SCHEME;



CREATE TABLE EGBPA_MSTR_SCHEMELANDUSAGE
   (	
     id bigint NOT NULL, 
	 bpaScheme bigint NOT NULL,
         usageType bigint NOT NULL,
	 version numeric DEFAULT 0,
	 createdBy bigint NOT NULL,
	 createdDate timestamp without time zone NOT NULL,
	 lastModifiedBy bigint,
         lastModifiedDate timestamp without time zone,
	 CONSTRAINT pk_EGBPA_MSTR_SCHEMELANDUSAGE PRIMARY KEY (id),
	 CONSTRAINT fk_SCHEMELANDUSAGE_scheme FOREIGN KEY (bpaScheme) REFERENCES EGBPA_MSTR_SCHEME (id),
	 CONSTRAINT fk_SCHEMELndUSAGE_ugeType FOREIGN KEY (usageType) REFERENCES  EGBPA_MSTR_CHANGEOFUSAGE (id),
	 CONSTRAINT FK_EGBPA_MSTR_SMELANDUSAGE_MDFDBY FOREIGN KEY (lastModifiedBy)
         REFERENCES EG_USER (ID),
    CONSTRAINT FK_EGBPA_MSTR_SMELANDUSAGE_CRTBY FOREIGN KEY (createdBy)
         REFERENCES EG_USER (ID)
   );
   
CREATE INDEX IDX_EGBPA_MSTR_SMELANDUSAGE_SME ON EGBPA_MSTR_SCHEMELANDUSAGE USING btree (bpaScheme);     
   
CREATE SEQUENCE SEQ_EGBPA_MSTR_SCHEMELANDUSAGE;