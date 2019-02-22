
------------------------Create Fee------------------------------
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'Create Fees','/bpafee/create',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'Create Fees',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create Fees'));


Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
	'Fees success','/bpafee/result',(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where
		name='BPA Master')),3,'Fees success',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Fees success'));


---------------------------Service Type-------------------------------------------------------
Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Servicetype details','/application/getServiceTypeDetails',null,(select id from eg_module where name='BPA Transanctions'),18,'Servicetype details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Servicetype details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Servicetype details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Servicetype details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Servicetype details'));


-----------------------------------Update Fee------------------------------------------------------------------
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'Modify Fees','/bpafee/update',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'Modify Fees',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Modify Fees'));


------------------------------------Search Fee----------------------------------------------
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'Search Fees','/bpafee/view',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'Search Fees',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search Fees'));


---------------------------------------Search Fee By Code-------------------------------------

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'FeesByCode','/bpafee/fee-by-code',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'FeesByCode',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='FeesByCode'));


update eg_action set enabled=false where name='View Update Fees';



