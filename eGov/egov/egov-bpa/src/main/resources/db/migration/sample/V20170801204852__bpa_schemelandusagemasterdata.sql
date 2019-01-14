
insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,description,isactive,CREATEDBY, createddate) values (nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='1'),'Residential',true,1,now()  );

insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,description,isactive,CREATEDBY, createddate) values (nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='1'),'Commercial',true,1,now()  ); 

insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,description,isactive,CREATEDBY, createddate) values (nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='2'),'Commercial',true,1,now()  ); 