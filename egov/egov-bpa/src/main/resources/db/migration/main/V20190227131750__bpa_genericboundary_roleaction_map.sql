Insert into EG_ACTION (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) values (nextval('SEQ_EG_ACTION'), 'Get Boundary configuration', '/boundary/ajax-boundary-configuration', null, (select id from eg_module where name='BPA Transanctions'), (select max(ordernumber)+1 from EG_ACTION),'Get Boundary configuration', 'false', 'bpa', 0, 1, now(), 1, now(), (select id from eg_module where name='BPA'));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Get Boundary configuration'));
Insert into eg_roleaction values((select id from eg_role where name='BUSINESS'),(select id from eg_action where name='Get Boundary configuration'));

Insert into EG_ACTION (id, name, url, queryparams, parentmodule, ordernumber, displayname, enabled, contextroot, version, createdby, createddate, lastmodifiedby, lastmodifieddate, application) values (nextval('SEQ_EG_ACTION'), 'Get Cross Boundary', '/boundary/ajax-cross-boundary', null, (select id from eg_module where name='BPA Transanctions'), (select max(ordernumber)+1 from EG_ACTION),'Get Cross Boundary', 'false', 'bpa', 0, 1, now(), 1, now(), (select id from eg_module where name='BPA'));

Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Get Cross Boundary'));

Insert into eg_roleaction values((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='Get Cross Boundary'));
