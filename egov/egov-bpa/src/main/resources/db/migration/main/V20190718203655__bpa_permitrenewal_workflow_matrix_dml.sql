INSERT INTO eg_wf_types (id, module, type, link, createdby, createddate, lastmodifiedby, lastmodifieddate, enabled, grouped, typefqn, displayname, version)
 VALUES (nextval('seq_eg_wf_types'), (select id from eg_module where name='BPA'), 'PermitRenewal', '/bpa/application/permit/renewal/update/:ID', 1, now(), 1, now(), 'Y', 'N', 'org.egov.bpa.transaction.entity.PermitRenewal', 'Permit Renewal Application', 0);


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'new', NULL, 'Permit renewal application creation pending', NULL, 'Low Risk', 'Initiated for permit renewal', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Initiated for permit renewal', 'Registered', 'Forwarded to section clerk', NULL, 'Low Risk', 'Section clerk approved', 'Approver approval pending', 'Assistant Engineer', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Section clerk approved', 'Approved', 'Approver approval pending', NULL, 'Low Risk', 'Record approved', 'Permit renewal fee payment pending', 'Assistant Engineer', 'Approved', 'Approve,Reject', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Record approved', 'Approved', 'Permit renewal fee payment pending', NULL, 'Low Risk', 'Permit renewal fee payment done', 'Forwarded to generate permit renewal order', 'Assistant Engineer', 'Approved', '', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Permit renewal fee payment done', 'Approved', 'Forwarded to generate permit renewal order', NULL, 'Low Risk', 'END', 'END', 'Assistant Engineer', 'Order Issued to Applicant', 'Generate Permit Renewal Order', 0, 300, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'new', NULL, 'Permit renewal application creation pending', NULL, 'Medium Risk', 'Initiated for permit renewal', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Initiated for permit renewal', 'Registered', 'Forwarded to section clerk', NULL, 'Medium Risk', 'Section clerk approved', 'Approver approval pending', 'Assistant Engineer', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Section clerk approved', 'Approved', 'Approver approval pending', NULL, 'Medium Risk', 'Record approved', 'Permit renewal fee payment pending', 'Assistant Engineer', 'Approved', 'Approve,Reject', 300, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Record approved', 'Approved', 'Permit renewal fee payment pending', NULL, 'Medium Risk', 'Permit renewal fee payment done', 'Forwarded to generate permit renewal order', 'Assistant Engineer', 'Approved', '', 300, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Permit renewal fee payment done', 'Approved', 'Forwarded to generate permit renewal order', NULL, 'Medium Risk', 'END', 'END', 'Assistant Engineer', 'Order Issued to Applicant', 'Generate Permit Renewal Order', 300, 2500, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'new', NULL, 'Permit renewal application creation pending', NULL, 'High Risk', 'Initiated for permit renewal', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Initiated for permit renewal', 'Registered', 'Forwarded to section clerk', NULL, 'High Risk', 'Section clerk approved', 'Approver approval pending', 'Assistant Engineer', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Section clerk approved', 'Approved', 'Approver approval pending', NULL, 'High Risk', 'Record approved', 'Permit renewal fee payment pending', 'Assistant Engineer', 'Approved', 'Approve,Reject', 2500, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Record approved', 'Approved', 'Permit renewal fee payment pending', NULL, 'High Risk', 'Permit renewal fee payment done', 'Forwarded to generate permit renewal order', 'Assistant Engineer', 'Approved', '', 2500, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'PermitRenewal', 'Permit renewal fee payment done', 'Approved', 'Forwarded to generate permit renewal order', NULL, 'High Risk', 'END', 'END', 'Assistant Engineer', 'Order Issued to Applicant', 'Generate Permit Renewal Order', 2500, 1000000, now(), '2099-04-01');