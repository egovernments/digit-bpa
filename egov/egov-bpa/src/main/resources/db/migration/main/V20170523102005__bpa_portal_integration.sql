
Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEBPA',15,0,'/bpa/application/newApplication-newform','true','Create Bpa Application','true','true','/bpa/application/newApplication-newform',
1,now(),now(),1);



insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/application/newApplication-newform'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/application/newApplication-newform'));
insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/boundary/ajaxBoundary-blockByWard.action'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/boundary/ajaxBoundary-blockByWard.action'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/application/getdocumentlistbyservicetype'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/application/getdocumentlistbyservicetype'));




insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/ajax/getAdmissionFees'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/ajax/getAdmissionFees'));


insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/application/newApplication-create'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/application/newApplication-create'));


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'update citizen bpa application','/application/citizen/update',null,
(select id from eg_module where name='BPA Transanctions'),17,'update citizen bpa application','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/application/citizen/update' and contextroot='bpa'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/application/citizen/update' and contextroot='bpa'));



insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Citizen'),
(select id from eg_action where url='/ajax/stakeholdersbytype'));

insert into eg_roleaction (roleid,actionid)values((select id from eg_role where name='Business User'),
(select id from eg_action where url='/ajax/stakeholdersbytype'));
