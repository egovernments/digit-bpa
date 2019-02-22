
----------------BPAFEEMASTERCOMMON------------------
CREATE TABLE egbpa_mstr_bpafee_common
(
  id bigint NOT NULL,
  glcode bigint,
  code character varying(128) NOT NULL,
  name character varying(128) NOT NULL,
  description character varying(256) NOT NULL,
  category character varying(128),
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_bpafee_common PRIMARY KEY (id),
  CONSTRAINT fk_bpafeecommon_glcode FOREIGN KEY (glcode)
      REFERENCES chartofaccounts (id),
  CONSTRAINT fk_egbpa_mstr_bpafee_common_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id) ,
  CONSTRAINT fk_egbpa_mstr_bpafee_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id) ,
  CONSTRAINT unq_bpafeecommon_code UNIQUE (code),
  CONSTRAINT unq_bpafeecommon_name UNIQUE (name)
);
        
CREATE SEQUENCE SEQ_EGBPA_MSTR_BPAFEE_COMMON;

-------------------BPAFEEMAPPING-----------------------------------

CREATE TABLE EGBPA_MSTR_BPAFEEMAPPING
   (	
     id bigint NOT NULL, 
	 applicationtype character varying(128) NOT NULL,
	 feesubtype character varying(128) NOT NULL,
 	 servicetype bigint,
	 calculationtype character varying(128),
     bpafeecommon bigint NOT NULL,
	 amount double precision NOT NULL,	 
	 version numeric DEFAULT 0,
	 createdBy bigint NOT NULL,
	 createdDate timestamp without time zone NOT NULL,
	 lastModifiedBy bigint,
     lastModifiedDate timestamp without time zone,
	 CONSTRAINT pk_EGBPA_MSTR_BPAFEEMAPPING PRIMARY KEY (id),
	 CONSTRAINT fk_bpafeemap_bpafeecommon FOREIGN KEY (bpafeecommon) REFERENCES EGBPA_MSTR_BPAFEE_COMMON (id),
	 CONSTRAINT fk_bpafeemap_servicetype FOREIGN KEY (servicetype) REFERENCES EGBPA_MSTR_SERVICETYPE (id),
	 CONSTRAINT FK_EGBPA_MSTR_BPAFEE_MDFDBY FOREIGN KEY (lastModifiedBy)
         REFERENCES EG_USER (ID),
     CONSTRAINT FK_EGBPA_MSTR_BPAFEE_CRTBY FOREIGN KEY (createdBy)
         REFERENCES EG_USER (ID)
   );


CREATE SEQUENCE SEQ_EGBPA_MSTR_BPAFEEMAPPING;