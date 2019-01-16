


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Create Bpa Demolition Application',
'/application/citizen/demolition-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Create Bpa Demolition Application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
  (select id from eg_action where name='Create Bpa Demolition Application'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
  (select id from eg_action where name='Create Bpa Demolition Application'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Create Bpa New Application',
'/application/citizen/newconstruction-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Create Bpa New Construction','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Create Bpa New Application'));


Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Create Bpa New Application'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'bpaReconstruction',
'/application/citizen/reconstruction-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Reconstruction','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='bpaReconstruction'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='bpaReconstruction'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'BPAAlteration',
'/application/citizen/alteration-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'BPAAlteration','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='BPAAlteration'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='BPAAlteration'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Sub-Division of plot/Land Development',
'/application/citizen/subdevland-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Sub-Division of plot/Land Development','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Sub-Division of plot/Land Development'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Sub-Division of plot/Land Development'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Adding of Extension',
'/application/citizen/addextnew-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Adding of Extension','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Adding of Extension'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Adding of Extension'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Change in occupancy',
'/application/citizen/changeofoccupancy-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Change in occupancy','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Change in occupancy'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Change in occupancy'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Permission for Temporary hut or shed',
'/application/citizen/permissionhutorshud-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Permission for Temporary hut or shed','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Permission for Temporary hut or shed'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Permission for Temporary hut or shed'));


--POST


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Save BpaApplication',
'/application/citizen/application-create',null,(select id from eg_module where name='BPA Transanctions'),17,
'Save BpaApplication','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Save BpaApplication'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Save BpaApplication'));