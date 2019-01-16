update eg_wf_matrix set currentstate ='Town Surveyor Inspection Initiated' where nextaction ='Forwarded to Town Surveyor Additional Inspection' and nextstate ='Town Surveyor Inspection Initiated' and objecttype ='OccupancyCertificate';

update eg_wf_matrix set nextaction ='Forwarded to town surveyor inspection initiator approval' where currentstate ='Town Surveyor Inspection Initiated' and nextstate ='Town Surveyor Inspection Completed' and pendingactions='Forwarded to Town Surveyor Additional Inspection' and objecttype ='OccupancyCertificate';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rejected', 'NOC Updated', '', '', 'OCCUPANCYCERTIFICATE', 'Forwarded to generate rejection notice', 'Application is rejected by approver', 'Superintendent', 'Rejected', 'Generate Rejection Notice', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'LP Initiated', NULL, NULL, null, 'OCCUPANCYCERTIFICATE', 'LP Created', 'Letter To Party Reply Pending', 'Section Clerk', 'Letter To Party Created', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'LP Created', NULL, NULL, null, 'OCCUPANCYCERTIFICATE', 'LP Reply Received', 'Forward to LP Initiator pending', 'Section Clerk', 'Letter To Party Reply Received', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Town Surveyor Inspection Completed', 'Town Surveyor Inspected', 'Forwarded to Assistant Engineer', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'NOC updation in progress', 'Forwarded to Superintendent for Noc Updation', 'Superintendent', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');
