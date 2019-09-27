
Create table EGBPA_NOC( 
  id bigint,
  initiationDate date,
  endDate date,
  nocConfigId bigint ,
  status varchar(23),
  supportDocsId bigint ,
  nocId bigint ,
  referenceNumber varchar(256),
  isDeemedApproved boolean,
    createdby bigint,
		createddate timestamp without time zone,
		lastmodifiedby bigint,
		lastmodifieddate timestamp without time zone,
		tenantId varchar(250),
		version bigint
);
alter table EGBPA_NOC add constraint pk_EGBPA_NOC primary key (id);
alter table EGBPA_NOC add constraint fk_EGBPA_NOC_nocConfigId  FOREIGN KEY (nocConfigId) REFERENCES EGBPA_MSTR_NOCCONFIG(id);
alter table EGBPA_NOC add constraint fk_EGBPA_NOC_supportDocsId  FOREIGN KEY (supportDocsId) REFERENCES EGBPA_DOC_FILES(id);
alter table EGBPA_NOC add constraint fk_EGBPA_NOC_nocId  FOREIGN KEY (nocId) REFERENCES EGBPA_DOC_FILES(id);
create sequence seq_EGBPA_NOC;
