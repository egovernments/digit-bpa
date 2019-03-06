INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'EIS_EMPLOYEE_JURISDICTION_HIERARCHY', 'The employee jurisdiction hierarchy',0, (select id from eg_module where name='EIS'));

Insert into eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='EIS_EMPLOYEE_JURISDICTION_HIERARCHY' and module=(select id from eg_module where name='EIS')), '01-Apr-2019', 'ADMINISTRATION',0);
