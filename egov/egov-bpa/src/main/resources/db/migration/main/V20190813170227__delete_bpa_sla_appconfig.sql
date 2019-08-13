delete from eg_appconfig_values where key_id in (select id from eg_appconfig where key_name = 'SLAFORBPAAPPLICATION');
delete from eg_appconfig where key_name = 'SLAFORBPAAPPLICATION';
