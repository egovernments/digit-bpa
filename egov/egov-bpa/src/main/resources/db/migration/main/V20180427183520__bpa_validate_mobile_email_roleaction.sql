Insert into eg_roleaction values((select id from eg_role where name='PUBLIC'),(select id from eg_action where name='Download BPA Documents'));

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application)
values (nextval('SEQ_EG_ACTION'),'Validate mobile number and email of business user','/validate/emailandmobile',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Validate mobile number and email of business user','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction values((select id from eg_role where name='PUBLIC'),(select id from eg_action where name='Validate mobile number and email of business user'));

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Validate mobile number and email of business user'));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Validate mobile number and email of business user'));

update eg_action set displayname ='Approve Building Licensee' where name='stakeHolderSearch';