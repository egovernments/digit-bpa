
update egbpa_mstr_applicationsubtype set enabled = true where name in ('Low Risk','Medium Risk','High Risk');
update egbpa_mstr_applicationsubtype set enabled = false where name in ('One day permit','Regular');