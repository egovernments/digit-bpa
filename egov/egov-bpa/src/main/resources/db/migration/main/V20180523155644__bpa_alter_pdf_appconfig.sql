update eg_appconfig_values set value = '50' where key_id = (SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PDFHEIGHTSUBTRACTIONVALUE'
AND MODULE =(select id from eg_module where name='BPA'));
 
update eg_appconfig_values set value = '50' where key_id = (SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='PDFWIDTHSUBTRACTIONVALUE'
 AND MODULE =(select id from eg_module where name='BPA'));
 
 