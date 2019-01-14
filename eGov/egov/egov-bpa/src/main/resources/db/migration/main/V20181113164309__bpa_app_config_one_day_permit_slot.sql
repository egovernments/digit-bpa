insert into eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) values(nextval('seq_eg_appconfig'),'NOOFDAYSFORASSIGNINGSLOTSFORONEDAYPERMIT',
'Number of days for assigning slots for one day permit application',0,(select id from eg_module where name='BPA'));

insert into eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) values (nextval('seq_eg_appconfig_values'),(select id from eg_appconfig
 where key_name = 'NOOFDAYSFORASSIGNINGSLOTSFORONEDAYPERMIT' and module = (select id from eg_module where name='BPA')),current_date, '1',0);