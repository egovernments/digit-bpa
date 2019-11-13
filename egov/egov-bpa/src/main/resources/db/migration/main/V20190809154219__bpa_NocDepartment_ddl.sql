
Create table EGBPA_MSTR_NOCDEPARTMENT( 
  id bigint,
  code varchar(20),
  name varchar(124),
  isActive boolean,
    	createdby bigint,
		createddate timestamp without time zone,
		lastmodifiedby bigint,
		lastmodifieddate timestamp without time zone,
		tenantId varchar(250),
		version bigint
);
alter table EGBPA_MSTR_NOCDEPARTMENT add constraint pk_EGBPA_MSTR_NOCDEPARTMENT primary key (id);
create sequence seq_EGBPA_MSTR_NOCDEPARTMENT;
