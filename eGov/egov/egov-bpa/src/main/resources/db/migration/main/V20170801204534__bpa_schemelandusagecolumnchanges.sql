update egbpa_sitedetail set scheme=null,landUsage=null;
delete from EGBPA_MSTR_SCHEMELANDUSAGE;

alter table egbpa_sitedetail drop CONSTRAINT FK_EGBPA_SITEDETAIL_landUsage;
alter table egbpa_sitedetail add CONSTRAINT FK_EGBPA_SITEDTL_schmelndUsage FOREIGN KEY (landUsage) REFERENCES egbpa_mstr_schemelandusage (ID);


alter table EGBPA_MSTR_SCHEMELANDUSAGE drop column usageType;
alter table EGBPA_MSTR_SCHEMELANDUSAGE add column  description character varying(256) NOT NULL; 
alter table EGBPA_MSTR_SCHEMELANDUSAGE add column   isactive  boolean  DEFAULT true;

