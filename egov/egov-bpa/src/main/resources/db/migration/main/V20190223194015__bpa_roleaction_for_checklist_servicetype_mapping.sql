INSERT INTO eg_module(id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
    VALUES (nextval('SEQ_EG_MODULE'), 'Checklist Servicetype Mapping Master', true, 'bpa', (select id from eg_module where name='BPA Master'),'Checklist ServiceType Mapping', 5);

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
'Create Checklist Servicetype Mapping','/checklistservicetypemapping/create',(select id from eg_module where name='Checklist Servicetype Mapping Master' and 
parentmodule=(select id from eg_module where name='BPA Master')),1,'Create Checklist Servicetype Mapping',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create Checklist Servicetype Mapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
'Search Checklist ServiceType Mapping','/checklistservicetypemapping/search/view',(select id from eg_module where name='Checklist Servicetype Mapping Master' and 
parentmodule=(select id from eg_module where name='BPA Master')),3,'View Checklist Servicetype Mapping',true,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search Checklist ServiceType Mapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
'Checklist ServiceType Mapping Result','/checklistservicetypemapping/result',(select id from eg_module where name='Checklist Servicetype Mapping Master' and 
parentmodule=(select id from eg_module where name='BPA Master')),3,'View Checklist Servicetype Mapping',false,'bpa',
(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Checklist ServiceType Mapping Result'));
