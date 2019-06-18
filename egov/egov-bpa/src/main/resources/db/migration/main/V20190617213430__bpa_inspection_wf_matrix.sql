INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate,version)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'NEW', NULL, 'Forward to section clerk is pending', null, 'INSPECTIONAPPLICATION', 'Initiated Inspection', 'Forwarded to section clerk', 'Section Clerk', 'Initiated Inspection', 'Forward', NULL, NULL, now(), '2099-04-01',0);
INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate,version)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'Initiated Inspection', 'Initiated Inspection', 'Forwarded to section clerk', null, 'INSPECTIONAPPLICATION', 'Section clerk approved', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Initiated Inspection', 'Forward', NULL, NULL, now(), '2099-04-01',0);
INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate,version)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'Section clerk approved', 'Initiated Inspection', 'Forwarded to Overseer for field inspection', null, 'INSPECTIONAPPLICATION', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01',0);
insert into eg_wf_matrix(id, department, objecttype, currentstate,  currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate, version) 
values (nextval('seq_eg_wf_matrix'), 'ANY', 'InspectionApplication', 'Field Inspection completed', null,  'Forwarded to Assistant Engineer For Approval', null, 'INSPECTIONAPPLICATION','END', 'END', 'Assistant engineer', 'Field Inspected', 'Approve,Revoke',NULL, NULL, now(), '2099-04-01',0);

--------------------------------------------------------------------------------------------

Insert into EGBPA_STATUS (ID,MODULETYPE,description,CODE,isactive,version,createdby,createddate,LASTMODIFIEDDATE,lastmodifiedby)
values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Initiated Inspection','Initiated Inspection',true,0,1,now(),now(),1);
Insert into EGBPA_STATUS (ID,MODULETYPE,description,CODE,isactive,version,createdby,createddate,LASTMODIFIEDDATE,lastmodifiedby)
values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Field Inspected','Field Inspected',true,0,1,now(),now(),1);
Insert into EGBPA_STATUS (ID,MODULETYPE,description,CODE,isactive,version,createdby,createddate,LASTMODIFIEDDATE,lastmodifiedby)
values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Approved','Approved',true,0,1,now(),now(),1);
Insert into EGBPA_STATUS (ID,MODULETYPE,description,CODE,isactive,version,createdby,createddate,LASTMODIFIEDDATE,lastmodifiedby)
values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Submitted','Submitted',true,0,1,now(),now(),1);
Insert into EGBPA_STATUS (ID,MODULETYPE,description,CODE,isactive,version,createdby,createddate,LASTMODIFIEDDATE,lastmodifiedby)
values (nextval('SEQ_EGBPA_STATUS'),'INSPECTION','Revocated','Revocated',true,0,1,now(),now(),1);

-------------------------------------------------------------------------------------------------

INSERT INTO eg_wf_types (id, module, type, link, createdby, createddate, lastmodifiedby, lastmodifieddate, enabled, grouped, typefqn, displayname, version)
 VALUES (nextval('seq_eg_wf_types'), (select id from eg_module where name='BPA'), 'Inspection', '/bpa/inspection/update/:ID', 1, now(), 1, now(), 'Y', 'N', 'org.egov.bpa.application.entity.InspectionApplication', 'Inspection Application', 0);


