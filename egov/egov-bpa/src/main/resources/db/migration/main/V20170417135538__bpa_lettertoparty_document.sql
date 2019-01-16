CREATE TABLE egbpa_lettertoparty_document
(
     id bigint NOT NULL,
	 checklistDetail  bigint NOT NULL,
	 lettertoParty bigint not null,
	 submissionDate date,
	 issubmitted boolean DEFAULT false,
	 createduser bigint,
	 Remarks character varying(256),
     createdby bigint NOT NULL,
     createddate timestamp without time zone NOT NULL,
 	 lastModifiedDate timestamp without time zone,
 	 lastModifiedBy bigint,
	 version numeric NOT NULL,
 	CONSTRAINT pk_egbpa_lettertoparty_document_ID PRIMARY KEY (id),
    CONSTRAINT fk_EGBPA_lpdoc_checklistDtl FOREIGN KEY (checklistDetail) REFERENCES EGBPA_MSTR_CHKLISTDETAIL (id),
	CONSTRAINT fk_EGBPA_lpdoc_lettertoParty FOREIGN KEY (lettertoParty) REFERENCES EGBPA_LETTERTOPARTY(id) ,
	CONSTRAINT fk_egbpa_lpdoc_createduser FOREIGN KEY (createduser) REFERENCES EG_user (id),
	CONSTRAINT FK_egbpa_lpdoc_MDFDBY FOREIGN KEY (lastModifiedBy) REFERENCES EG_USER (ID),
    CONSTRAINT FK_egbpa_lpdoc_CRTBY FOREIGN KEY (createdBy)REFERENCES EG_USER (ID)
   );
 CREATE INDEX IDX_egbpa_lettertoparty_document_ID  ON egbpa_lettertoparty_document USING btree (id); 
 CREATE SEQUENCE seq_egbpa_lettertoparty_document;
 
 
CREATE TABLE egbpa_lettertoparty_filestore
(
  filestoreid bigint NOT NULL,
  lettertoPartyDocumentid bigint NOT NULL,
  CONSTRAINT fk_egbpa_lettertoparty_document_lpdocument FOREIGN KEY(lettertoPartyDocumentid)
         REFERENCES egbpa_lettertoparty_document(ID)
);
CREATE INDEX INDX_egbpa_lettertoparty_filestore_filestoreid ON egbpa_lettertoparty_filestore (filestoreid);
CREATE INDEX INDX_egbpa_lettertoparty_filestore_lpdocumentid ON egbpa_lettertoparty_filestore (lettertoPartyDocumentid);

 