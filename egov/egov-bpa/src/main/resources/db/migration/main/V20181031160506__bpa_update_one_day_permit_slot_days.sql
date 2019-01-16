update eg_appconfig_values set value = '1' where key_id = (select id from eg_appconfig where key_name = 'NOOFDAYSFORASSIGNINGSLOTS');
