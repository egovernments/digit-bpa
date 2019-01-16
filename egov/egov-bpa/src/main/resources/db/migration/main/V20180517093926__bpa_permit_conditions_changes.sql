update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC11' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC12' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC13' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC14' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC15' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC16' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC17' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC18' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC19' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC20' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC21' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC22' and  conditiontype = 'STATIC_PERMITCONDITION';
update egbpa_mstr_permit_conditions set ordernumber = ordernumber + 1 where code = 'PC23' and  conditiontype = 'STATIC_PERMITCONDITION';

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype)
values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC24', 'Owner shall arrange all safety measures at site and inform this to office before starting work', 11 , 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

update egbpa_mstr_permit_conditions set description = 'Disabled person''s  entries shall be made as per KMBR' where code = 'PC16' and  conditiontype = 'STATIC_PERMITCONDITION';

update egbpa_mstr_permit_conditions set description = 'Sewage and solid waste disposal arrangements shall be made scientifically.' where code = 'PC20' and  conditiontype = 'STATIC_PERMITCONDITION';

update egbpa_mstr_permit_conditions set description = 'The Plan and Permit shall be exhibited infront of construction site itself for inspection purpose.' where code = 'PC21' and  conditiontype = 'STATIC_PERMITCONDITION';

update egbpa_mstr_permit_conditions set description = 'For the development, that happens and warrants tree to be cut, at least same number of trees shall be planted, maintained and brought up with in the plot in the immediate vicinity of the development.' where code = 'PC22' and  conditiontype = 'STATIC_PERMITCONDITION';


