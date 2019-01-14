INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PDFHEIGHTSUBTRACTIONVALUE', 'value to be subtracted from pdf height',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PDFHEIGHTSUBTRACTIONVALUE'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '750',0);
 
 INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'PDFWIDTHSUBTRACTIONVALUE', 'value to be subtracted from pdf width',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) 
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PDFWIDTHSUBTRACTIONVALUE'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, '200',0);