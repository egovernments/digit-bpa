insert into eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) values(nextval('seq_eg_appconfig'),'GAPFORSCHEDULINGOC',
'Number of days gap for scheduling occupancy certificate',0,(select id from eg_module where name='BPA'));

insert into eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) values (nextval('seq_eg_appconfig_values'),(select id from eg_appconfig
 where key_name = 'GAPFORSCHEDULINGOC' and module = (select id from eg_module where name='BPA')),current_date, '0',0);