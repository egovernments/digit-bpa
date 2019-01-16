delete from  eg_appconfig_values where key_id = (SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='MERGEPERMITEDCR'
 AND MODULE =(select id from eg_module where name='BPA'));

delete from  eg_appconfig where KEY_NAME = 'MERGEPERMITEDCR' and module = (select id from eg_module where name='BPA');