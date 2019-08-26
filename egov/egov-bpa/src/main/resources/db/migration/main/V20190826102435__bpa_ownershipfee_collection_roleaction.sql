

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Collect/Pay ownership transfer fee through online or counter','/application/ownership/transfer/generate-bill',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from EG_ACTION),'Collect/Pay ownership transfer fee through online or counter','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Collect/Pay ownership transfer fee through online or counter'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='Collect/Pay ownership transfer fee through online or counter'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Collect/Pay ownership transfer fee through online or counter'));



Insert into egcl_servicecategory (id,name,code,isactive,version,createdby,createddate,lastmodifiedby,lastmodifieddate) values (nextval('seq_egcl_servicecategory'),'Ownership Transfer','OT',true,0,1,now(),1,now());

Insert into egcl_servicedetails (id,name,serviceurl,isenabled,callbackurl,servicetype,code,fund,fundsource,functionary,vouchercreation,scheme,subscheme,servicecategory,isvoucherapproved,vouchercutoffdate,created_by,created_date,modified_by,modified_date,ordernumber) values (nextval('seq_egcl_servicedetails'), 'Ownership Transfer Charges', '/../bpa/application/ownership/transfer/generate-bill', true, '/receipts/receipt-create.action', 'B', 'OT', (select id from fund where code='01'), null, null, false, null, null, (select id from egcl_servicecategory where code='OT'), false, now(), 1, now(), 1, now(),null);
