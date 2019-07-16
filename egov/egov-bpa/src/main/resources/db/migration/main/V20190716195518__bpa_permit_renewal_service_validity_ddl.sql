alter table EGBPA_MSTR_SERVICETYPE add column renewalValidity double precision;

update EGBPA_MSTR_SERVICETYPE set renewalValidity=3;