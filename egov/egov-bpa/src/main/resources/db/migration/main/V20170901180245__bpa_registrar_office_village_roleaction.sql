
delete from eg_roleaction where actionid in (select id from eg_action where name='get registrar offices for village');

delete from eg_action where name = 'get registrar offices for village';

update egbpa_sitedetail set registraroffice = null;

alter table egbpa_sitedetail alter column registraroffice type bigint USING registraroffice::bigint;

ALTER TABLE egbpa_sitedetail ADD CONSTRAINT fk_egbpa_sitedetail_registrar_village FOREIGN KEY (registraroffice) REFERENCES egbpa_mstr_registrar_village (id);



----------------------------------to get registrar office for village role action mapping---------------------------

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get registrar offices for village','/ajax/registraroffice',
null,(select id from eg_module where name='BPA Transanctions'),27,'get registrar offices for village','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='get registrar offices for village'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='get registrar offices for village'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get registrar offices for village'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get registrar offices for village'));