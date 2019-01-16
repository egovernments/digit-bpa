update eg_appconfig_values set value  ='YES' where key_id=(select id from eg_appconfig  where key_name  ='BPA_AUTOCALCULATE_FEE');

alter table egbpa_sitedetail alter column roofconversion type double precision USING roofconversion::double precision;
alter table egbpa_sitedetail alter column shutter type bigint USING shutter::bigint;
alter table egbpa_sitedetail alter column erectionoftower type bigint USING erectionoftower::bigint;
alter table egbpa_sitedetail add column noofpoles bigint;
alter table egbpa_sitedetail add column noofhutorsheds bigint;
alter table egbpa_sitedetail add column extentofland bigint;