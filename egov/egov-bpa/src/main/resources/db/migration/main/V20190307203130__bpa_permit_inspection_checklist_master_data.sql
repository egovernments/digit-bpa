delete from egbpa_checklist_servicetype_mapping where checklist in (select id from eg_checklist where checklisttypeid = (select id from eg_checklist_type where code='LTP')) and servicetype = (select id from EGBPA_MSTR_SERVICETYPE where description='New Construction');

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Title Deed of the Property' and checklisttypeid = (select id from eg_checklist_type where code='LTP')), (select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby, lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Possession Certificate' and checklisttypeid = (select id from eg_checklist_type where code='LTP')),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Land Tax Receipt' and checklisttypeid = (select id from eg_checklist_type where code='LTP')), (select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Layout Approval Copy' and checklisttypeid = (select id from eg_checklist_type where code='LTP')),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Others' and checklisttypeid = (select id from eg_checklist_type where code='LTP')),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Location Sketch and Village Sketch' and checklisttypeid = (select id from eg_checklist_type where code='LTP')), (select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Specification Report' and checklisttypeid = (select id from eg_checklist_type where code='LTP')),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());
INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate) VALUES (nextval('seq_egbpa_checklist_servicetype_mapping'), (select id from eg_checklist where description='Certificates and Undertaking by LBE/LBS - Declaration as per Appendix A, should be signed scanned and uploaded' and checklisttypeid = (select id from eg_checklist_type where code='LTP')),(select id from EGBPA_MSTR_SERVICETYPE where description='New Construction'), 'true', 'false', 0, 1, now(), 1, now());


-----------Permit Application Inspection checklist---------------------------
----------------------------------New construction-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINY'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='01'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINYDRAWING'));


----------------------------------Demolition-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='02'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));


----------------------------------Reconstruction-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINY'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='03'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINYDRAWING'));


----------------------------------Alteration-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINY'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='04'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINYDRAWING'));

----------------------------------Sub-Division of plot/Land Development-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='05'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

----------------------------------Addition or Extension-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINY'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='06'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINYDRAWING'));

----------------------------------Change in occupancy-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINY'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='07'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='PLANSCRUTINYDRAWING'));

----------------------------------Amenities-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='08'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));



----------------------------------Hut And Shed-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='09'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));


----------------------------------Tower Construction-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='14'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONERECTIONOFTOWER'));


----------------------------------Pole Structures-----------------------

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONAREALOC'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLOCATION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONACCESS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONMEASUREMENT'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONWORKCOMPLETEDPERPLAN'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSURROUNDING'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONHGTBUILDABUTROAD'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONTYPEOFLAND'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONPROPOSEDSTAGEWORK'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONLENGTHOFCOMPOUNDWALL'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONNUMBEROFWELLS'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONROOFCONVERSION'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONSHUTTER'));

INSERT INTO egbpa_checklist_servicetype_mapping(id, checklist, servicetype, isrequired, ismandatory, version, createdby, createddate, lastmodifiedby,lastmodifieddate)  
(select nextval('seq_egbpa_checklist_servicetype_mapping'), id, (select id from EGBPA_MSTR_SERVICETYPE where code='15'), 'true', 'false', 0, 1, now(), 1, now()
 from eg_checklist where checklisttypeid  in ( select id from eg_checklist_type where code='INSPECTIONERECTIONOFTOWER'));
