create table EGBPA_FEEBOUNDARY_MAPPING(
	  id bigint NOT NULL,
	  boundary bigint NOT NULL,
	  bpafeemapping bigint NOT NULL,
      amount bigint,
	  createdby bigint NOT NULL,
      createddate timestamp without time zone NOT NULL,
 	  lastModifiedDate timestamp without time zone,
 	  lastModifiedBy bigint,
	  version numeric NOT NULL,
	  CONSTRAINT PK_FEEBOUNDARY_ID PRIMARY KEY (ID),
	  CONSTRAINT FK_FEEBOUNDARY_BOUNDARY FOREIGN KEY (boundary) REFERENCES eg_boundary(ID),
	  CONSTRAINT FK_FEEBOUNDARY_FEE FOREIGN KEY (bpafeemapping) REFERENCES egbpa_mstr_bpafeemapping(ID),
	  CONSTRAINT FK_EGBPA_FEEBOUNDARY_MDFDBY FOREIGN KEY (lastModifiedBy) REFERENCES state.EG_USER (ID),
      CONSTRAINT FK_EGBPA_FEEBOUNDARY_CRTBY FOREIGN KEY (createdBy)REFERENCES state.EG_USER (ID)
 );
 create sequence SEQ_EGBPA_FEEBOUNDARY_MAPPING;
