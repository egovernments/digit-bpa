
delete from eg_appconfig_values where key_id in (select id from eg_appconfig where key_name='STATE_LOGO');
delete from eg_appconfig where key_name='STATE_LOGO';