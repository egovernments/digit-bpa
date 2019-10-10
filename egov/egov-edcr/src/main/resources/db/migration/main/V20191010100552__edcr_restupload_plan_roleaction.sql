
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Upload dxf','/rest/dcr/uploadplan',null,(select id from eg_module where name='BPA Transanctions'),13,'Upload dxf','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='PUBLIC'), (select id from eg_action where name='Upload dxf'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Get dcr details','/rest/dcr/getdetails',null,(select id from eg_module where name='BPA Transanctions'),13,'Get dcr details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='PUBLIC'), (select id from eg_action where name='Get dcr details'));

alter table edcr_application add column transactionnumber character varying(128);