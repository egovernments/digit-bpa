
INSERT INTO eg_checklist_type(id, code, description, version, createdby, createddate,lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_eg_checklist_type'), 'INCNSTRNINSPNIMAGES', 'In Construction inspection images', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-01', 'North Side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-02', 'South Side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-03', 'East Side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-04', 'West Side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-05', 'Front side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-06', 'Back side', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-07', 'Existing building', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-08', 'Proposed building', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-09', 'Setback', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-10', 'Roof image', 0, 1, now(), 1, now());

INSERT INTO eg_checklist(id, checklisttypeid, code, description, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES 
(nextval('seq_eg_checklist'), (select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'), 'INCNSTRNINSPNIMAGES-11', 'Others', 0, 1, now(), 1, now());



INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INCNSTRNINSPNIMAGES'));
