Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Search for Occupancy Certificate Application','/application/occupancy-certificate/search',null,(select id from eg_module where name='BPA Occupancy Certificate'),1,'Search Occupancy Certificate','true','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name = 'Search for Occupancy Certificate Application' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Search for Occupancy Certificate Application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Search for Occupancy Certificate Application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Bpa Administrator'), (select id from eg_action where name='Search for Occupancy Certificate Application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View Occupancy Certificate Details','/application/occupancycertificate/viewdetails',null,(select id from eg_module where name='BPA Transanctions'),10,'View Occupancy Certificate Details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BPA Approver'),(select id from eg_action where name = 'View Occupancy Certificate Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'View Occupancy Certificate Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name = 'View Occupancy Certificate Details' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name = 'View Occupancy Certificate Details' and contextroot = 'bpa'));
