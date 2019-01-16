update egbpa_mstr_postal set taluk='Kozhikode';
alter table egbpa_application add column approvedfeeamount double precision;
alter table egbpa_sitedetail drop column taluk;