update eg_wf_matrix set nextaction  ='Forwarded to town surveyor inspection initiator approval' where pendingactions='Town Surveyor Approval Pending' and objecttype ='BpaApplication';

update eg_wf_matrix set nextaction  ='Application is rejected by approver',nextdesignation='Superintendent' where currentstate='Rejected' and objecttype ='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Rejection initiated by clerk', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Rejecetd', 'Generation of rejection notice pending', 'Superintendent', 'Rejected', 'Generate Rejection Notice', NULL, NULL, '2018-02-01', '2099-04-01');

update eg_wf_matrix set nextaction='Document Scrutiny Scheduling Pending' where currentstate ='NEW' and objecttype ='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Registered', NULL, 'Document Scrutiny Scheduling Pending', null, 'CREATEBPAAPPLICATION', 'Scheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Scheduled For Document Scrutiny', 'Forward,Initiate Rejection', NULL, NULL, '2018-02-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Scheduled For Document Scrutiny', NULL, 'Document Scrutiny ReScheduling Pending', null, 'CREATEBPAAPPLICATION', 'Rescheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Rescheduled For Document Scrutiny', 'Forward,Initiate Rejection', NULL, NULL, '2018-02-01', '2099-04-01');

update eg_wf_matrix set currentstate='Scheduled For Document Scrutiny',pendingactions='Document verification pending',validactions='Forward,Initiate Rejection' where nextstate ='Document Verified' and objecttype ='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Rescheduled For Document Scrutiny', NULL, 'Document verification pending', null, 'CREATEBPAAPPLICATION', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward,Initiate Rejection', NULL, NULL, '2018-02-01', '2099-04-01');

update eg_wf_matrix set pendingactions='Forwarded to Overseer' where nextstate ='Field Inspection completed' and currentstate='Town Surveyor Inspection Completed' and objecttype ='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Town Surveyor Inspection Completed', NULL, 'Forwarded to Assistant Engineer', null, 'CREATEBPAAPPLICATION', 'NOC updation in progress', 'Forwarded to Superintendent for Noc Updation', 'Superintendent', 'Field Inspected', 'Forward', NULL, NULL, '2018-02-01', '2099-04-01');