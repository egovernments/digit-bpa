INSERT INTO EGBPA_MSTR_SCHEME (ID, code, description, isactive,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_SCHEME')), '1','DTP Scheme for Ward No. 1',true,1,now());

insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,usageType,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='1'),(select id from EGBPA_MSTR_CHANGEOFUSAGE where code='Residential')
,1,now()  );
insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,usageType,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='1'),(select id from EGBPA_MSTR_CHANGEOFUSAGE where code='Commercial')
,1,now()  ); 

INSERT INTO EGBPA_MSTR_SCHEME (ID, code, description, isactive,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_SCHEME')), '2','DTP Scheme for Ward No. 7',true,1,now());

insert into EGBPA_MSTR_SCHEMELANDUSAGE (id,bpaScheme,usageType,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_SCHEMELANDUSAGE'), (select id from EGBPA_MSTR_SCHEME where code='2'),(select id from EGBPA_MSTR_CHANGEOFUSAGE where code='Commercial')
,1,now()  ); 