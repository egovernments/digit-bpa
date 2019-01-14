update eg_wf_matrix set nextstate = 'Registered' , nextstatus = 'Registered'  where objectType = 'BpaApplication' and currentstate = 'NEW' and additionalrule = 'CREATEBPAAPPLICATION-ONEDAYPERMIT';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Registered', NULL, 'Document Scrutiny Scheduling Pending', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Scheduled For Document Scrutiny', 'Document Verification Pending', 'Section Clerk', 'Scheduled For Document Scrutiny', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

delete from eg_wf_matrix where objectType = 'BpaApplication' and currentstate = 'Scheduled For Document Scrutiny' and additionalrule = 'CREATEBPAAPPLICATION-ONEDAYPERMIT';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Scheduled For Document Scrutiny', NULL, 'Document verification pending', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward,Initiate Rejection', NULL, NULL, '2018-02-01', '2099-04-01');
