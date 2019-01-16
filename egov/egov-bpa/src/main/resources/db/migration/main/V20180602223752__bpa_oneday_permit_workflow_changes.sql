update eg_wf_matrix set nextaction ='Forwarded to update permit conditions',nextstatus='Approved' where currentstate='Application Approval Initiated' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Permit Fee Collected', NULL, NULL, null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Record Approved', 'Forwarded to generate permit order', 'Assistant Engineer', 'Approved', 'Forward', NULL, NULL, '2018-06-01', '2099-04-01');

update eg_wf_matrix set currentstate ='Record Approved' where nextstate='END' and additionalrule='CREATEBPAAPPLICATION-ONEDAYPERMIT' and objecttype ='BpaApplication';