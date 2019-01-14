    
Insert into eg_action(id,name,url,parentmodule,ordernumber,displayname,enabled,contextroot,application) values(nextval('SEQ_EG_ACTION'),'Create StakeHolder By Citizen','/stakeholder/createbycitizen',(select id from eg_module where name='Stake Holder Master' and parentmodule=(select id from eg_module where name='BPA Master')),7,'Create StakeHolder By Citizen',false,'bpa',(select id from eg_module where name='BPA' and parentmodule is null));

Insert into eg_roleaction values((select id from eg_role where name='PUBLIC'),(select id from eg_action where name='Create StakeHolder By Citizen'));

Insert into eg_roleaction values((select id from eg_role where name='PUBLIC'),(select id from eg_action where name='StakeHolder success details'));

