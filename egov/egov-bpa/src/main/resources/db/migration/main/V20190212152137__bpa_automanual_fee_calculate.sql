INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE )
VALUES (nextval('SEQ_EG_APPCONFIG'), 'BPA_FEE_CALCULATION', 'Auto and manual calculate permit fee',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEYNAME='BPA_FEE_CALCULATION' and module= (select id from eg_module where name='BPA')), current_date, 'AUTOFEECAL',0);
