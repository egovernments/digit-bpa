------------permit inspection images service checklist-------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PERMITINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-05' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-06' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PERMITINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PERMITINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-05' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-06' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PERMITINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PERMITINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-01' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-02' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-03' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-04' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now());

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where code='PERMITINSPNIMAGES-11' and checklisttypeid =(select id from eg_checklist_type where code='PERMITINSPNIMAGES') ),(select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now());

------------occupancy certificate inspection images service checklist-------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='OCINSPNIMAGES'));
