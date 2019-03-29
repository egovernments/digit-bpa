Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'stakeholderpayregfee','/stakeholder/payregfee',null,(select id from eg_module where name='BPA Transanctions'),1,'stakeholderpayregfee','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='PUBLIC'),(select id from eg_action where name='stakeholderpayregfee'));