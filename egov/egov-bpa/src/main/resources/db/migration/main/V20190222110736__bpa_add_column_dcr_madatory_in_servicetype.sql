alter table egbpa_mstr_servicetype add column isbuildingrequired boolean default false;

alter table egbpa_mstr_servicetype rename column isAutoDcrNumberRequired to isedcrmandatory;

update egbpa_mstr_servicetype set isedcrmandatory = true, isbuildingrequired=true where code in('01','03','04','06','07');