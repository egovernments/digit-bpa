INSERT INTO egbpa_mstr_changeofusage (ID,CODE,DESCRIPTION,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_CHANGEOFUSAGE'),'Residential','Residential',true,0,1,now());
INSERT INTO egbpa_mstr_changeofusage (ID,CODE,DESCRIPTION,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_CHANGEOFUSAGE'),'Mixed Use','Mixed Use',true,0,1,now());


alter table egbpa_sitedetail add column scheme bigint;
alter table egbpa_sitedetail add column landUsage bigint;

alter table egbpa_sitedetail add CONSTRAINT FK_EGBPA_SITEDETAIL_scheme FOREIGN KEY (scheme) REFERENCES EGBPA_MSTR_SCHEME (ID);
alter table egbpa_sitedetail add CONSTRAINT FK_EGBPA_SITEDETAIL_landUsage FOREIGN KEY (landUsage) REFERENCES EGBPA_MSTR_CHANGEOFUSAGE (ID);



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'landusagebyscheme','/ajax/getlandusagebyscheme',null,(select id from eg_module where name='BPA Transanctions'),1,'landusagebyscheme','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'),
(select id from eg_action where name='landusagebyscheme'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='landusagebyscheme'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='landusagebyscheme'));
