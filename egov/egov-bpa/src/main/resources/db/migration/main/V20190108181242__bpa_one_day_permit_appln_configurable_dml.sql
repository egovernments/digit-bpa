-----------------------Enable/Disable app config for one day permit application integration------------

INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED', 'To enable/disable one day permit application integration',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='ONE_DAY_PERMIT_APPLN_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);
