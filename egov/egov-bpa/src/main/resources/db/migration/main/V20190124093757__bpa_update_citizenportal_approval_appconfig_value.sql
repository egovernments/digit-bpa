update eg_appconfig_values set value='YES' where CONFIG=(select id from eg_appconfig where KEYNAME='BPA_CITIZENACCEPTANCE_CHECK');