
Create table EGBPA_MSTR_NOCCONFIG( 
  id bigint,
  nocDepartment bigint NOT NULL,
  name varchar(1024),
  description varchar(1024),
  applicationType varchar(22),
  IntrgrationType varchar(20),
  isDeemedApproved boolean,
  daysForDeemedApproval bigint,
      	createdby bigint,
		createddate timestamp without time zone,
		lastmodifiedby bigint,
		lastmodifieddate timestamp without time zone,
		tenantId varchar(250),
		version bigint
);
alter table EGBPA_MSTR_NOCCONFIG add constraint pk_EGBPA_MSTR_NOCCONFIG primary key (id);

alter table EGBPA_MSTR_NOCCONFIG add constraint fk_EGBPA_MSTR_NOCCONFIG_NOCDEPT foreign key (nocDepartment) references EGBPA_MSTR_NOCDEPARTMENT(id);
create sequence seq_EGBPA_MSTR_NOCCONF;
