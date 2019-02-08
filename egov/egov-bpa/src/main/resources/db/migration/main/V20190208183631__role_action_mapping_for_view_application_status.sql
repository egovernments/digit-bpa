Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) values (nextval('SEQ_EG_ACTION'),'View BPA Application Status','/application/view/status',null,(select id from eg_module where name='BPA Transanctions'),(select max(ordernumber)+1 from eg_action where parentmodule=2576),'View BPA Application Status','false','bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='View BPA Application Status'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='View BPA Application Status'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='PUBLIC'), (select id from eg_action where name='View BPA Application Status'));

