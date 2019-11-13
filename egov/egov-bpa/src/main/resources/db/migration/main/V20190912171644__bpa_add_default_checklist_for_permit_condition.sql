INSERT INTO eg_checklist_type(id, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist_type'), 'PERMITDEFAULTCONDITIONS', 'Permit Default Conditions', 0, 1, now(), 1, now() where not exists(select * from eg_checklist_type where code='PERMITDEFAULTCONDITIONS');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-01', 'The development shall be undertaken strictly according to plans enclosed with necessary permission endorsement.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-01');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-02', 'The land in question must be in lawful ownership and peaceful possession of the applicant.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-02');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-03', 'The permission is valid for period of five years with effect from the date of issue.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-03');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-04', 'Permission accorded under the provision cannot be construed as evidence in respect of right title interest of the plot over which the plan is approved.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-04');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-05', 'Any dispute arising out of land record or in respect of right/ title/ interest after this approval the plan shall be treated automatically cancelled during the period of dispute.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-05');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-06', 'Adequate safety precaution shall be provided at all stages of construction for safe guarding the life of workers and any public hazard.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-06');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-07', 'The land/ Building shall be used exclusively for the above occupancy for which you applied and the uses shall not be changed to any other use without prior approval of this Authorit.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-07');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-08', 'Adequate space mentioned in the approved plan shall be kept open for parking and no part of it will be built upon.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-08');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-09', 'The land over which construction is proposed is accessible by an approved means of access with sufficient road width.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-09');

INSERT INTO eg_checklist(id,checklisttypeid, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
select nextval('seq_eg_checklist'),(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS'), 'PERMITDEFAULTCONDITION-10', 'The applicant shall free gift strip of land of the road in the name of the authority for the purposes as mentioned in the Byelaws.', 0, 1, now(), 1, now() where not exists(select * from eg_checklist where code='PERMITDEFAULTCONDITION-10');



------------------------------------------------------------SERVICE TYPE MAPPING-----------------------------------------------------------


----------------------------------New Construction--------------------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='New Construction')));


-------------------------------------Change in occupancy---------------------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Change in occupancy')));

-----------------------------------------Reconstruction-------------------------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Reconstruction')));

-------------------------------------Alteration-------------------------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Alteration'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Alteration')));


-------------------------------------Addition or Extension---------------------------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-01' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-02' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-03' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-04' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-05' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-06' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-07' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-08' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-09' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)
select nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and
checklisttypeid = (select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS') ),(select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension'),
'true', 'false', 0, 1, now(), 1, now()
where not exists(select * from egbpa_checklist_servicetype_mapping where checklist=
(select id from eg_checklist where code='PERMITDEFAULTCONDITION-10' and checklisttypeid =
(select id from eg_checklist_type where code='PERMITDEFAULTCONDITIONS')) and servicetype=((select id from EGBPA_MSTR_SERVICETYPE where description='Addition or Extension')));




