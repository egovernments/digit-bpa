alter table egbpa_application add column issenttopreviousowner boolean default false;

alter table egbpa_application add column townsurveyorremarks character varying(5000);

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Town Surveyor Inspection Initiated',now(),'Town Surveyor Inspection Initiated',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Town Surveyor Inspected',now(),'Town Surveyor Inspected',true,0,1,now());


delete from eg_wf_matrix where nextstate ='Superintendent Approved' and objecttype ='BpaApplication';

delete from eg_wf_matrix where  nextstate ='Field Inspection Initiated' and nextstatus='Field Inspection initiated' and objecttype ='BpaApplication';

update eg_wf_matrix set validactions ='Generate Rejection Notice',nextstate='generate rejection notice',nextstatus='Rejected' where currentstate='Rejected' and nextaction='Application is Rejected by Superior Officer' and objecttype ='BpaApplication';

update eg_wf_matrix set validactions='Forward',nextaction  ='Document verification pending', nextdesignation = 'Section Clerk' where currentstate='NEW' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Cancel Application', currentstate='Registered', nextaction  ='Forwarded to Overseer for field inspection', nextdesignation = 'Town Planning Building Overseer' where nextstate='Document Verified' and objecttype='BpaApplication';

update eg_wf_matrix set currentstate='Document Verified' where nextstate='Field Inspection completed' and currentstate='Field Inspection Initiated' and objecttype='BpaApplication';

update eg_wf_matrix set nextdesignation='Superintendent,Town Surveyor', validactions='Forward,Revert' where currentstate='Field Inspection completed' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Approve,Revert,Reject', nextaction ='Permit Fee Collection Pending' where  nextstate ='Final Approval Process initiated' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Revert' where  nextstate ='AEE Application Approval Pending' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Revert' where  nextstate ='EE Application Approval Pending' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Revert' where  nextstate ='Corporation Engineer Application Approval Pending' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Revert' where  nextstate ='Secretary Application Approval Pending' and objecttype='BpaApplication';


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Final Approval Process initiated', NULL, 'Permit Fee Collection Pending', null, 'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending', 'Superintendent', 'Approved', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Town Surveyor Inspection Initiated', NULL, 'AE Approval Pending', null, 'CREATEBPAAPPLICATION', 'Town Surveyor Inspection Initiated', 'Forwarded to Town Surveyor For Additional Inspection', 'Town Surveyor', 'Town Surveyor Inspection Initiated', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Town Surveyor Inspection Initiated', NULL, 'Town Surveyor Approval Pending', null, 'CREATEBPAAPPLICATION', 'Town Surveyor Inspection Completed', 'Forwarded to Overseer', 'Town Planning Building Overseer', 'Town Surveyor Inspected', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Town Surveyor Inspection Completed', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');
