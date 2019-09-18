delete from eg_appconfig_values where CONFIG in (select id from eg_appconfig where KEYNAME = 'SLAFORBPAAPPLICATION');
delete from eg_appconfig where KEYNAME = 'SLAFORBPAAPPLICATION';
