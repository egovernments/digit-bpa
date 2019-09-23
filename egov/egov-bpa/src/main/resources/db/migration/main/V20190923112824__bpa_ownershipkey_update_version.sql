update eg_appconfig_values  set version=0 where key_id = (select id from eg_appconfig  where key_name ='AUTOGENERATE_OWNERSHIP_NUMBER');
