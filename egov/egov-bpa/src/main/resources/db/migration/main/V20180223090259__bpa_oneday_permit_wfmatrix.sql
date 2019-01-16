----------------------------- WF MATRIX FOR ONE DAY PERMIT -------------------------
INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'NEW', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Registered', 'Scheduled For Document Scrutiny', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Registered', NULL, 'Scheduled For Document Scrutiny', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward,Reject', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Document Verified', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Field Inspection completed', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Field Inspection completed', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Application Approval Initiated', 'Permit Fee Collection Pending', 'Superintendent', 'Approved', 'Approve,Revert,Reject', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Application Approval Initiated', NULL, 'Permit Fee Collection Pending', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Permit Fee Collected', 'Forwarded to generate permit order', 'Superintendent', 'Generated permit orders', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Rejected', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'generate rejection notice', 'Application is Rejected by Superior Officer', 'Assistant Engineer', 'Rejected', 'Generate Rejection Notice', NULL, NULL, '2017-01-01', '2099-04-01');


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Permit Fee Collected', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'END', 'Generated permit orders', 'Superintendent', 'Order Issued to Applicant', 'Generate Permit Order', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Rejection initiated by clerk', NULL, null, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Rejected', 'Generation of rejection notice', 'Superintendent', 'Rejected', 'Generate Rejection Notice', NULL, NULL, '2017-01-01', '2099-04-01');



