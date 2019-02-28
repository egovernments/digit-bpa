alter table egbpa_mstr_servicetype add column isocrequired boolean default false;

update egbpa_mstr_servicetype set isocrequired = true where code in ('01','03','04','06','07');