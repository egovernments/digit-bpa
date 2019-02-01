-----------------------------OC Rejection Notice--------------------------
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Generate oc rejection notice','/application/occupancy-certificate/rejectionnotice',null,(select id from eg_module where name='BPA Transanctions'),1,'Generate oc rejection notice','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BPA Approver'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name = 'Generate oc rejection notice' and contextroot = 'bpa'));

-----------------------------OC Demand Notice-------------------------------
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Generate oc demand notice','/application/occupancy-certificate/demandnotice',null,(select id from eg_module where name='BPA Transanctions'),1,'Generate oc demand notice','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BPA Approver'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Document Scrutiniser'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));
Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name = 'Generate oc demand notice' and contextroot = 'bpa'));

-----------------------------OC Letter to party/reply----------------------------
update eg_action set url='/occupancy-certificate/letter-to-party/print/lp' where name='Print generated letter to party notice for occupancy certificate application' and contextroot='bpa';


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Print generated letter to party reply notice for oc application','/occupancy-certificate/letter-to-party/print/lpreply',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Print generated letter to party reply notice for oc application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Print generated letter to party reply notice for oc application' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Print generated letter to party reply notice for oc application' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Print generated letter to party reply notice for oc application' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Print generated letter to party reply notice for oc application' and contextroot = 'bpa'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name = 'Print generated letter to party reply notice for oc application' and contextroot = 'bpa'));
