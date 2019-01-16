update eg_wf_matrix set nextaction = 'Document Scrutiny Scheduling Pending' where additionalrule ='CREATEBPAAPPLICATION-ONEDAYPERMIT'
and objectType = 'BpaApplication' and currentstate = 'NEW';
update eg_wf_matrix set pendingactions = 'Document Scrutiny Scheduling Pending', nextstate = 'Scheduled For Document Scrutiny',nextaction = 'Document Verification Pending',nextdesignation = 'Section Clerk', nextstatus = 'Scheduled For Document Scrutiny', validactions = 'Forward'  where additionalrule ='CREATEBPAAPPLICATION-ONEDAYPERMIT'
and objectType = 'BpaApplication' and currentstate = 'Registered';
INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Scheduled For Document Scrutiny', NULL, 'Document verification pending', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward,Initiate Rejection', NULL, NULL, '2018-02-01', '2099-04-01');

