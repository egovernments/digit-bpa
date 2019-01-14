alter table egbpa_lettertoparty add column stateforownerposition character varying(200);

delete from eg_roleaction where actionid = (select id from eg_action where name='LetterToPartyChecklistView');
delete from eg_action where name='LetterToPartyChecklistView';

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'View Letter To Party Details','/lettertoparty/viewdetails',null,(select id from eg_module where name='BPA Transanctions'),19,
'View Letter To Party Details','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='View Letter To Party Details'));

INSERT INTO egbpa_mstr_lpreason (ID,CODE,DESCRIPTION,reason,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_LPREASON'),'Additional document required','Additional document required','Additional document required',true,0,1,now());

INSERT INTO egbpa_mstr_lpreason (ID,CODE,DESCRIPTION,reason,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_LPREASON'),'Additional details required','Additional details required','Additional details required',true,0,1,now());

INSERT INTO egbpa_mstr_lpreason (ID,CODE,DESCRIPTION,reason,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_LPREASON'),'Reason for the discrepancies','Reason for the discrepancies','Reason for the discrepancies',true,0,1,now());

INSERT INTO egbpa_mstr_lpreason (ID,CODE,DESCRIPTION,reason,ISACTIVE,VERSION,CREATEDBY,CREATEDDATE) VALUES (nextval('SEQ_EGBPA_MSTR_LPREASON'),'Others','Others','Others',true,0,1,now());