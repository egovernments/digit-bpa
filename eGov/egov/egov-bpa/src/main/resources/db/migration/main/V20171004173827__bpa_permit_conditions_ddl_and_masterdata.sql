create sequence seq_egbpa_mstr_permit_conditions;

CREATE TABLE egbpa_mstr_permit_conditions
(
  id bigint NOT NULL,
  code character varying(32),
  description character varying(1560) NOT NULL,
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_permit_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_mstr_permit_conditions_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_mstr_permit_conditions_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC01', 'Permit issued as per the order PCB NOC PCB/40/KKD/ICE/7525/06/2010 Dtd.7.11.2016', 1, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC02', 'Permit issued as per the order Revised Fire NOC No..F2/141422/2016 Dtd.15.3.17 (Fire and Rescue Dept.)', 2, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC03', 'Permit issued Ministry of Environment & Forests Dept. NOC 21-15/2010-I A-III Dtd.7.10.2010', 3, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC04', 'Arrangement should be there to dispose the solid ans liquid waste from the propoed building inside the owners site itself and it should not be diverted to any public place drain or public place.A drawing showing the treatment plant proposed shall be submitted in advance.', 4, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC05', 'Adequate safety precaution shall be provided at all stage of constructicn for safe guarding thelife of workers public any hazards.', 5, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC06', 'The work shall be carried out strictly following the KMBR provision under the supervision of a qualified Engineer as per the
plans.The Name and Address of Engineer having supervision over the constuction shall be informed in advance.', 6, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC07', 'The owner shall be responsible for the structural stability and other safety of the building.', 7, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC08', 'On the basis of concurrence for CTP vide No.D1/1062/2016/D-Dis Dtd.2.06.16', 8, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC09', 'Rain water harvesting tank,solar heating and lighting shall be provided as per KMBR.', 9, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC10', 'Disabled persons entry to the two entries shall be made as per KMBR.', 10, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC11', 'Recreation space shall be ear marked.', 11, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC12', 'No over hanging in open space shall be provided.', 12, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC13', 'No construction shall be made in road widening area.', 13, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC14', 'Sewage and solid waste disposal shall be made scientifically.', 14, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC15', 'The Plan and Permit shall be exhibited infront of premised itself for inspection purpose.', 15, 0, 1, now(), 1, now());

insert into egbpa_mstr_permit_conditions (id, code, description, ordernumber, version, createdBy, createdDate, lastmodifiedby, lastmodifieddate) values ((nextval('seq_egbpa_mstr_permit_conditions')), 'PC16', 'Issued for the Permit 19.8.2016 is cancelled.(Bar 2009 Vol.IV Page No. 5)', 16, 0, 1, now(), 1, now());


CREATE TABLE egbpa_application_permit_conditions
(
  application bigint NOT NULL,
  permitcondition bigint NOT NULL,
  CONSTRAINT fk_egbpa_application_permit_conditions FOREIGN KEY(application)
         REFERENCES egbpa_application(ID),
  CONSTRAINT fk_egbpa_appln_permit_conditions FOREIGN KEY(permitcondition)
         REFERENCES egbpa_mstr_permit_conditions(id)
);

alter table EGBPA_APPLICATION add column additionalPermitConditions character varying(2048);