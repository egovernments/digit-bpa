Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Validate is plan scrutiny number used with any occupancy certificate application','/validate/occupancy-certificate/edcrno-used',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Validate is plan scrutiny number used with any occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='Validate is plan scrutiny number used with any occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='Validate is plan scrutiny number used with any occupancy certificate application'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='check is edcr require at application by servicetype and occupancy'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='validate any approved edcr plan is used in bpa application'));