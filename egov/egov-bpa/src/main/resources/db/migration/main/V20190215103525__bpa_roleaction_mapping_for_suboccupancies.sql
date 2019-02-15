INSERT INTO egbpa_occupancy(id, code, name, isactive, version, createdby, createddate, lastmodifiedby,lastmodifieddate, maxcoverage, minfar, maxfar, ordernumber, description) VALUES (nextval('SEQ_EGBPA_USAGE'), 'MIXED', 'Mixed', 't', 0, 1, now(), 1, now(), 1, 0, 1, 25, 'Mixed');


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,
lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'get bpa occupancy And suboccupancy details','/application/getOccupancyAndSuboccupancyMap',null,(select id from eg_module where name='BPA Transanctions'),18,'get bpa occupancy and suboccupancy details','false','bpa',0,1,
now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get bpa occupancy And suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='get bpa occupancy And suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get bpa occupancy And suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get bpa occupancy And suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='get bpa occupancy And suboccupancy details'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,
lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'get bpa suboccupancy details','/application/getsuboccupancydetails',null,(select id from eg_module where name='BPA Transanctions'),18,'get bpa suboccupancy details','false','bpa',0,1,
now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where 
name='get bpa suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where 
name='get bpa suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where 
name='get bpa suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where 
name='get bpa suboccupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where 
name='get bpa suboccupancy details'));



