INSERT INTO eg_module(id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
VALUES (nextval('SEQ_EG_MODULE'), 'BPA Noc Config', true, 'bpa', (SELECT id FROM eg_module WHERE name  ='BPA Master'), 'NOC Configuration',1);



Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'New-NocDepartment','/nocdepartment/new',
 (select id from eg_module where name='BPA Noc Config' ),
1,'New-NocDepartment',true,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='New-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Create-NocDepartment','/nocdepartment/create',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Create-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Update-NocDepartment','/nocdepartment/update',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Update-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Update-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'View-NocDepartment','/nocdepartment/view',
 (select id from eg_module where name='BPA Noc Config' ),
1,'View-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Edit-NocDepartment','/nocdepartment/edit',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Edit-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Edit-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Result-NocDepartment','/nocdepartment/result',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Result-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Result-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'View NocDepartment','/nocdepartment/search/view',
 (select id from eg_module where name='BPA Noc Config' ),
2,'View NocDepartment',true,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Edit NocDepartment','/nocdepartment/search/edit',
 (select id from eg_module where name='BPA Noc Config' ),
3,'Edit NocDepartment',true,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Edit NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Search and View Result-NocDepartment','/nocdepartment/ajaxsearch/view',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Search and View Result-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search and View Result-NocDepartment'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application)
 values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-NocDepartment','/nocdepartment/ajaxsearch/edit',
 (select id from eg_module where name='BPA Noc Config' ),
1,'Search and Edit Result-NocDepartment',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search and Edit Result-NocDepartment'));

