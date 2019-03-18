Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
'Modify Checklist Servicetype Mapping','/checklistservicetypemapping/update',(select id from eg_module where name='Checklist Servicetype Mapping Master' and 
parentmodule=(select id from eg_module where name='BPA Master')),3,'Modify Checklist Servicetype Mapping',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Modify Checklist Servicetype Mapping'));