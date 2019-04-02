ALTER TABLE egbpa_mstr_applicationtype RENAME TO egbpa_mstr_applicationsubtype;

alter table egbpa_application rename applicationtype to applicationsubtype;

alter table egbpa_mstr_bpafeemapping ADD  column applicationsubtype bigint;

alter table egbpa_mstr_bpafeemapping add constraint fk_egbpa_bpafeemap_appsubtype 
FOREIGN KEY (applicationsubtype)  REFERENCES egbpa_mstr_applicationsubtype (id);

ALTER SEQUENCE SEQ_EGBPA_MSTR_APPLICATIONTYPE RENAME TO SEQ_EGBPA_MSTR_APPLICATIONSUBTYPE;