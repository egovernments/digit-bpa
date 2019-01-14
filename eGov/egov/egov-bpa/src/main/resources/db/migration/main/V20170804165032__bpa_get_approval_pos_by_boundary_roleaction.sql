Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get positions by department and designation and boundary','/bpaajaxWorkFlow-positionsByDepartmentAndDesignationAndBoundary',
null,(select id from eg_module where name='BPA Transanctions'),25,'get positions by department and designation and boundary','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),
(select id from eg_action where name='get positions by department and designation and boundary'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='get positions by department and designation and boundary'));


INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'BPAPRIMARYDEPARTMENT', 'setting primary department to be selected in bpa',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='BPAPRIMARYDEPARTMENT' AND MODULE =(select id from eg_module where name='BPA')),current_date, 'Town Planning',0);

update eg_wf_matrix set nextdesignation='Superintendent' where objecttype='BpaApplication'  and nextdesignation in ( 'Superintendent of Approval' ,'Superintendent of NOC');
