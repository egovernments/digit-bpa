Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),
'City Selection','/common/city/selection-form',(select id from eg_module where name='BPA Master'),3,'City Selection',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='City Selection'));
Insert into eg_roleaction values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='City Selection'));
Insert into eg_roleaction values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='City Selection'));