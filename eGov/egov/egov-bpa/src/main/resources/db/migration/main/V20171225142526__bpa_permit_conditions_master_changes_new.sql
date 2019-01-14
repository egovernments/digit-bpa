update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of  PCB as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC01';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of Fire & Rescue Service as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC02';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of Ministry of Environment & Forests as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC03';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the concurrence of CTP vide No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC04';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of Railway Department as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC05';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of  Defence Department as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC06';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the concurrence of Art & Heritage Commission vide No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC07';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the concurrence of KCZMA vide No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC08';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of DMRC/Light Metro Project as per the Order No. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC09';
update egbpa_mstr_permit_conditions set description='Permit issued basesd on the Approval Certificate of National Highway Authority as per the Order. ',conditiontype='DYNAMIC_PERMITCONDITION' where code='PC10';

update egbpa_mstr_permit_conditions set description='Adequate safety precaution shall be provided at all stage of construction for safe guarding the life of workers public any hazards.',conditiontype='STATIC_PERMITCONDITION' where code='PC11';
update egbpa_mstr_permit_conditions set description='The work shall be carried out strictly following the KMBR provision under the supervision of a qualified engineer as per the plans.The name and address of engineer having supervision over the constuction shall be informed in advance.', conditiontype='STATIC_PERMITCONDITION' where code='PC12';
update egbpa_mstr_permit_conditions set description='The owner shall be responsible for the structural stability and other safety of the building.' ,conditiontype='STATIC_PERMITCONDITION'where code='PC13';
update egbpa_mstr_permit_conditions set description='Arrangement should be there to  dispose the solid ans liquid waste from the propoed building inside the owners site itself and it should not be diverted to any public place drain or public place.A drawing showing the treatment plant proposed shall be submitted in advance.',conditiontype='STATIC_PERMITCONDITION' where code='PC14';
update egbpa_mstr_permit_conditions set description='Rain water harvesting tank,solar heating and lighting shall be provided as per KMBR.',conditiontype='STATIC_PERMITCONDITION' where code='PC15';
update egbpa_mstr_permit_conditions set description='Disabled persons entry to the two entries shall be made as per KMBR.',conditiontype='STATIC_PERMITCONDITION' where code='PC16';

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC17', 'Recreation space shall be ear marked.', 17, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC18', 'No over hanging in open space shall be provided.', 18, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC19', 'No construction shall be made in road widening area.', 19, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC20', 'Sewage and solid waste disposal shall be made scientifically.', 20, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC21', 'The Plan and Permit shall be exhibited infront of premised itself for inspection purpose.', 21, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC22', 'For the development, that happens and warrants true to be cut, at least same number of trees shall be planted, maintained and brought up with in the plot in the immediate vicinity of the development.', 22, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC23', 'Pipe composting /biogas plants/vermin composting etc.. anyone of these should be provided with appropriate technique at the time of completion of building, for processing organic waste at source itself.', 23, 0, 1, now(), 1, now(),'STATIC_PERMITCONDITION');

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate,conditiontype) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'ADNLPC23', '', 24, 0, 1, now(), 1, now(),'ADDITIONAL_PERMITCONDITION');


alter table egbpa_application_permit_conditions add column id bigint, add column permitConditiondDate  timestamp without time zone, add column permitConditionNumber character varying(30), add column orderNumber bigint, add column permitConditionType character varying(30), add column isrequired boolean default true,add column additionalPermitCondition character varying(500);

create sequence seq_egbpa_application_permit_conditions;

update egbpa_application_permit_conditions set id = nextval('seq_egbpa_application_permit_conditions');

update egbpa_application_permit_conditions set permitConditionType = 'STATIC_PERMITCONDITION' where permitConditionType is  null;

ALTER TABLE egbpa_application_permit_conditions ADD CONSTRAINT pk_egbpa_application_permit_conditions PRIMARY KEY (id);