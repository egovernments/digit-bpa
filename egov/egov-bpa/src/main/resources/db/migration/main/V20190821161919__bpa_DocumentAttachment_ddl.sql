
Create table EGBPA_DOC_FILES( 
  id bigint,
  type varchar(1024),
  description varchar(1024),
    createdby bigint,
		createddate timestamp without time zone,
		lastmodifiedby bigint,
		lastmodifieddate timestamp without time zone,
		tenantId varchar(250),
		version bigint
);
alter table EGBPA_DOC_FILES add constraint pk_EGBPA_DOC_FILES primary key (id);
create sequence seq_EGBPA_DOC_FILES;
