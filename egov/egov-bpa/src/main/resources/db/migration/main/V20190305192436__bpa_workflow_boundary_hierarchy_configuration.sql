INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'BPA_WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY', 'The boundary hierarchy for work to be used to get employee',0, (select id from eg_module where name='BPA'));

Insert into eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEYNAME='BPA_WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY' and module=(select id from eg_module where name='BPA')), '01-Apr-2019', 'ADMINISTRATION',0);
