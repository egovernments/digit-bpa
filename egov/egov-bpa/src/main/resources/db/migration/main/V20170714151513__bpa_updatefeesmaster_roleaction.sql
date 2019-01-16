INSERT INTO eg_module(
id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
VALUES (nextval('SEQ_EG_MODULE'), 'Fees Master', true, 'bpa', (select id from eg_module where name='BPA Master'), 'Fees', 1);

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'View Update Fees','/fees/view',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'Update Fees',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View Update Fees'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) 
values(nextval('SEQ_EG_ACTION'),'Update Fees Detail','/fees/update',
(select id from eg_module where name='Fees Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,
'Update Fees Detail',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Update Fees Detail'));
