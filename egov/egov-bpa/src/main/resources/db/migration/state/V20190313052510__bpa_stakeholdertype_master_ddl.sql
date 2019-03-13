
create table EGBPA_MSTR_STAKEHOLDERTYPE
	(
	  id bigint NOT NULL,
      name character varying(128) NOT NULL,
      code character varying(50),
      isactive boolean,
	  createdby bigint NOT NULL,
      createddate timestamp without time zone NOT NULL,
 	  lastModifiedDate timestamp without time zone,
 	  lastModifiedBy bigint,
	  version numeric NOT NULL,
	  CONSTRAINT PK_STAKEHOLDERTYPE_ID PRIMARY KEY (ID),
	  CONSTRAINT FK_EGBPA_MSTR_STAKEHOLDERTYPE_MDFDBY FOREIGN KEY (lastModifiedBy) REFERENCES EG_USER (ID),
      CONSTRAINT FK_EGBPA_MSTR_STAKEHOLDERTYPE_CRTBY FOREIGN KEY (createdBy)REFERENCES EG_USER (ID)
   );

CREATE SEQUENCE seq_egbpa_mstr_stakeholderType
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
  
 
insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Architect','Architect',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Building Designer - A','Building Designer - A',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Building Designer - B','Building Designer - B',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Engineer - A','Engineer - A',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Engineer - B','Engineer - B',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Town Planner - A','Town Planner - A',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Town Planner - B','Town Planner - B',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Supervisor - A','Supervisor - A',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Supervisor - B','Supervisor - B',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Supervisor Senior','Supervisor Senior',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Chartered Architect','Chartered Architect',true,1,now(),now(),1,0);

insert into egbpa_mstr_stakeholderType(id,name,code, isactive,createdby,createddate,lastmodifieddate,lastmodifiedby,version) values (nextval('seq_egbpa_mstr_stakeholderType'),'Chartered Engineer','Chartered Engineer',true,1,now(),now(),1,0);
  
