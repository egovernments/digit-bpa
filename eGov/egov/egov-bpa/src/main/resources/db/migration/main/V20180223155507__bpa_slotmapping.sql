alter table egbpa_mstr_slotmapping drop column day;
alter table egbpa_mstr_slotmapping add column day character varying(10);
alter table egbpa_mstr_slotmapping drop column ward;
alter table egbpa_mstr_slotmapping add column electionward bigint;
alter table egbpa_mstr_slotmapping add column revenueward bigint;


Insert into eg_roleaction values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='loadLocalityBoundaryByWard'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='loadLocalityBoundaryByWard'));
