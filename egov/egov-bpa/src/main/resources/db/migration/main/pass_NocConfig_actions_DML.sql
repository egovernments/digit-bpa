Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'New-NocConfig','/nocconfig/new',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'New-NocConfig',true,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='New-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create-NocConfig','/nocconfig/create',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Create-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Update-NocConfig','/nocconfig/update',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Update-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Update-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View-NocConfig','/nocconfig/view',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'View-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit-NocConfig','/nocconfig/edit',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Edit-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Edit-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Result-NocConfig','/nocconfig/result',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Result-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Result-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View NocConfig','/nocconfig/search/view',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),2,'View NocConfig',true,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Edit NocConfig','/nocconfig/search/edit',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),3,'Edit NocConfig',true,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Edit NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and View Result-NocConfig','/nocconfig/ajaxsearch/view',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Search and View Result-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search and View Result-NocConfig'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Edit Result-NocConfig','/nocconfig/ajaxsearch/edit',(select id from eg_module where name='Master' and parentmodule=(select id from eg_module where name='pass' and parentmodule is null)),1,'Search and Edit Result-NocConfig',false,'pass',(select id from eg_module where name='pass' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search and Edit Result-NocConfig'));

