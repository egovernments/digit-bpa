INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'DCRPDFQRCODEENABLED', 'value to enable/disable qr code for pdf format dcr documents',
0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='DCRPDFQRCODEENABLED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'true',0);