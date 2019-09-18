
delete from eg_appconfig_values where CONFIG in (select id from eg_appconfig where KEYNAME='STATE_LOGO');
delete from eg_appconfig where KEYNAME='STATE_LOGO';