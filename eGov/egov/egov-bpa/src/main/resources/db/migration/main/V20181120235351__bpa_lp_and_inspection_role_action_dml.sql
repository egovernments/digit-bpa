-------Inspection related role actions-------------------

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Create inspection details for occupancy certificate application','/application/occupancy-certificate/create-inspection',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Create inspection details for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Create inspection details for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Update inspection details for occupancy certificate application','/application/occupancy-certificate/update-inspection',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Update inspection details for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Update inspection details for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'View inspection details on success for occupancy certificate application','/application/occupancy-certificate/success/view-inspection-details',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View inspection details on success for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View inspection details on success for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Show inspection details for occupancy certificate application','/application/occupancy-certificate/show-inspection-details',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View inspection details for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));

---------------------------Occupancy certificate letter to party related role actions------------------

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Create letter to party for occupancy certificate application','/occupancy-certificate/letter-to-party/create',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Create letter to party for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Create letter to party for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Update letter to party details for occupancy certificate application','/occupancy-certificate/letter-to-party/update',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Update letter to party details for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Update letter to party details for occupancy certificate application'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Letter to party creation/updation success for occupancy certificate application','/occupancy-certificate/letter-to-party/result',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Letter to party creation/updation success for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Letter to party creation/updation success for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Letter to party creation/updation success for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Letter to party creation/updation success for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Letter to party creation/updation success for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Print generated letter to party notice for occupancy certificate application','/occupancy-certificate/letter-to-party/print',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Print generated letter to party notice for occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Print generated letter to party notice for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Print generated letter to party notice for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Print generated letter to party notice for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Print generated letter to party notice for occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Update sent date of letter to party when sending to citizen details of OC','/occupancy-certificate/letter-to-party/capture-sent-date',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Update sent date of letter to party when sending to citizen details of OC','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Update sent date of letter to party when sending to citizen details of OC'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'View letter to party details of occupancy certificate application','/occupancy-certificate/letter-to-party/view-details',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'View letter to party details of occupancy certificate application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='View letter to party details of occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='View letter to party details of occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='View letter to party details of occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='View letter to party details of occupancy certificate application'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Reply to letter to party with requested details of OC','/occupancy-certificate/letter-to-party/reply',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Reply to letter to party with requested details of OC','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Reply to letter to party with requested details of OC'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Reply to letter to party with requested details of OC'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Reply to letter to party with requested details of OC'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Reply to letter to party with requested details of OC'));


UPDATE EG_ACTION SET url ='/application/occupancy-certificate/appointment/view-details' where name='View scheduled appointment details of doc scrutiny for OC application';

update eg_action set url ='/application/details-view/by-permit-number' where name ='View Bpa application details by permit number';

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Bpa Administrator'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Show inspection details for occupancy certificate application'));