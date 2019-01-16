INSERT INTO eg_module(
            id, name, enabled, contextroot, parentmodule, displayname, ordernumber)
    VALUES (nextval('SEQ_EG_MODULE'), 'Slot Mapping Master', true, 'bpa', (select id from eg_module where name='BPA Master'), 'Slot Mapping', 1);
   

    -----------------------------role action mapping start----------
    -- for SYSTEM user
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create SlotMapping','/slotmapping/create',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'Create Slot Mapping',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Create SlotMapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search and Show SlotMapping','/slotmapping/search/update',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'Update Slot Mapping',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search and Show SlotMapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View And Edit SlotMapping','/slotmapping/update',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'View and edit Slot Mapping',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View And Edit SlotMapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Search SlotMapping','/slotmapping/search/view',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'View Slot Mapping',true,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Search SlotMapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'View SlotMapping','/slotmapping/view',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'Get Slot Mapping',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View SlotMapping'));

Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'SlotMapping Result','/slotmapping/result',(select id from eg_module where name='Slot Mapping Master' and parentmodule=(select id from eg_module where name='BPA Master')),1,'Slot Mapping Result',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));
Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='SlotMapping Result'));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='get active child boundaries of parent boundary'));


-- for Bpa Administrator

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Create SlotMapping'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Search and Show SlotMapping'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='View And Edit SlotMapping'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Search SlotMapping'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='View SlotMapping'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='get active child boundaries of parent boundary'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='SlotMapping Result'));


