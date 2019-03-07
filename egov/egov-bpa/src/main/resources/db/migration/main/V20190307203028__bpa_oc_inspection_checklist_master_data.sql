-----------Occupancy certificate inspection checklist------------------

----------------------------------New construction-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPECTION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYRULE'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYDRAWING'));

----------------------------------Reconstruction-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPECTION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYRULE'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYDRAWING'));


----------------------------------Alteration-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPECTION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYRULE'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYDRAWING'));


----------------------------------Addition or Extension-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPECTION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYRULE'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYDRAWING'));

----------------------------------Change in occupancy-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPECTION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYRULE'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCPLANSCRUTINYDRAWING'));
