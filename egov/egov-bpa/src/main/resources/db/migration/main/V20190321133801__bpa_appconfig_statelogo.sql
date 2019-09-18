
INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE )
VALUES (nextval('SEQ_EG_APPCONFIG'), 'STATE_LOGO', 'State logo',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEYNAME='STATE_LOGO' and module= (select id from eg_module where name='BPA')), current_date, '/egi/resources/global/images/logo@2x.png',0);

