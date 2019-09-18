INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'),'OC_ALLOW_DEVIATION', 'percentage of oc to allow deviation',0, (select id from eg_module where name='BPA')); 

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OC_ALLOW_DEVIATION' and module= (select id from eg_module where name='BPA')), current_date, 5,0);
