ALTER TABLE egbpa_master_nocconfiguration ADD COLUMN isdeemedapproval boolean not null default false;

ALTER TABLE egbpa_master_nocconfiguration drop COLUMN isactive; 
