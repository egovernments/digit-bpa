CREATE TABLE egbpa_nocapplication
   (    
    ID bigint NOT NULL,
    application   bigint,
    nocType   character varying(128),
    status bigint not null,
    remarks character varying(128),
    CREATEDDATE  timestamp without time zone,
    CREATEDBY bigint NOT NULL,
    lastmodifiedby bigint,
    lastmodifieddate  timestamp without time zone,
    version numeric DEFAULT 0,
	
    CONSTRAINT PK_egbpa_nocapplication_ID PRIMARY KEY (ID),
    CONSTRAINT FK_egbpa_nocapplication_MDFDBY FOREIGN KEY (lastmodifiedby)
      REFERENCES state.EG_USER (ID),
    CONSTRAINT FK_egbpa_nocapplication_bpa FOREIGN KEY (application)
      REFERENCES EGBPA_APPLICATION (ID),
    CONSTRAINT FK_egbpa_nocapplication_CRTDBY FOREIGN KEY (CREATEDBY)
      REFERENCES state.EG_USER (ID),
    CONSTRAINT FK_egbpa_nocapplication_STATUS FOREIGN KEY (STATUS)
      REFERENCES EGBPA_STATUS (ID)   
   ) ;

CREATE SEQUENCE SEQ_EGBPA_NOCAPPLICATION;


----------------------------------------------------------------------------------------------------------------------------
CREATE TABLE egbpa_noc_certificate
(
  nocapplication bigint,
  fileStore bigint,
CONSTRAINT fk_noc_appln_id FOREIGN KEY (nocapplication)
      REFERENCES EGBPA_NOCAPPLICATION (id),
CONSTRAINT fk_noc_appln_filemapper FOREIGN KEY (fileStore)
      REFERENCES eg_filestoremap (id)
);