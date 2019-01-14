INSERT INTO eg_module(
            id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
    VALUES (nextval('SEQ_EG_MODULE'), 'Holiday Master', true, 'bpa', (select id from eg_module where name='BPA Master'), 'Holiday', 1);

    -----------------------------role action mapping start----------
    -- for SYSTEM user
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create Holiday','/holiday/create',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'Create Holiday',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create Holiday'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'search and show update Holiday result','/holiday/search/update',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),2,'Update Holiday',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='search and show update Holiday result'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'edit and update Holiday','/holiday/update',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),2,'edit and update Holiday',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='edit and update Holiday'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'search and show view Holiday result','/holiday/search/view',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),3,'View Holiday',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='search and show view Holiday result'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'view Holiday details','/holiday/view',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),3,'view Holiday details',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='view Holiday details'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Holiday success details','/holiday/result',(select id from eg_module where name='Holiday Master' and parentmodule=(select id from eg_module where name='BPA Master')),3,'Holiday success details',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Holiday success details'));

-- for Bpa Administrator

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Create Holiday'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='search and show update Holiday result'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='edit and update Holiday'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='search and show view Holiday result'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='view Holiday details'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Holiday success details'));
