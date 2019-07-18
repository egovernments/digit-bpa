
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'CreateRenewalLetterToParty','/permitrenewal/lettertoparty/create/',null,(select id from eg_module where name='BPA Transanctions'),2,
'CreateRenewalLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='CreateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='CreateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='CreateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='CreateRenewalLetterToParty'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'RenewalLetterToPartySuccess','/permitrenewal/lettertoparty/result',null,(select id from eg_module where name='BPA Transanctions'),2,
'RenewalLetterToPartySuccess','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='RenewalLetterToPartySuccess'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='RenewalLetterToPartySuccess'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='RenewalLetterToPartySuccess'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='RenewalLetterToPartySuccess'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'UpdateRenewalLetterToParty','/permitrenewal/lettertoparty/update/',null,(select id from eg_module where name='BPA Transanctions'),2,
'UpdateRenewalLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='UpdateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='UpdateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='UpdateRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='UpdateRenewalLetterToParty'));



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'ViewRenewalLetterToParty','/permitrenewal/lettertoparty/viewdetails',null,(select id from eg_module where name='BPA Transanctions'),2,
'ViewRenewalLetterToParty','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='ViewRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='ViewRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='ViewRenewalLetterToParty'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='ViewRenewalLetterToParty'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'capturesentdateRenewalLTP','/permitrenewal/lettertoparty/capturesentdate',null,(select id from eg_module where name='BPA Transanctions'),2,
'capturesentdateRenewalLTP','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='capturesentdateRenewalLTP'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='capturesentdateRenewalLTP'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='capturesentdateRenewalLTP'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='capturesentdateRenewalLTP'));




Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'RenewalLetterToPartyReply','/permitrenewal/lettertoparty/lettertopartyreply',null,(select id from eg_module where name='BPA Transanctions'),2,
'RenewalLetterToPartyReply','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='RenewalLetterToPartyReply'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='RenewalLetterToPartyReply'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'),
(select id from eg_action where name='RenewalLetterToPartyReply'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'),
(select id from eg_action where name='RenewalLetterToPartyReply'));




Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'RENEWAL','Letter To Party Created',now(),'Letter To Party Created',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'RENEWAL','Letter To Party Reply Received',now(),'Letter To Party Reply Received',true,0,1,now());
 

