alter table eg_wf_matrix alter column currentstatus type varchar(120);

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'New', '', 'Occupancy certificate application creation pending', '', 'OCCUPANCYCERTIFICATE', 'Registered', 'Document Scrutiny Scheduling Pending', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Registered', 'Registered', 'Document Scrutiny Scheduling Pending', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Scheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Scheduled For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Scheduled For Document Scrutiny', 'Scheduled For Document Scrutiny', 'Document Scrutiny ReScheduling Pending', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Rescheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Rescheduled For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Scheduled For Document Scrutiny', 'Scheduled For Document Scrutiny', 'Pending For Auto Rescheduling Document Scrutiny', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Scheduled For Document Scrutiny', 'Initiated For Auto Rescheduling Appointment', 'Section Clerk', 'Pending For Rescheduling For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rescheduled For Document Scrutiny', 'Rescheduled For Document Scrutiny', 'Pending For Auto Rescheduling Document Scrutiny', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Rescheduled For Document Scrutiny', 'Initiated For Auto Rescheduling Appointment', 'Section Clerk', 'Pending For Rescheduling For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Scheduled For Document Scrutiny', 'Pending For Rescheduling For Document Scrutiny', 'Initiated For Auto Rescheduling Appointment', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Rescheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Rescheduled For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rescheduled For Document Scrutiny', 'Pending For Rescheduling For Document Scrutiny', 'Initiated For Auto Rescheduling Appointment', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Rescheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'Rescheduled For Document Scrutiny', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Scheduled For Document Scrutiny', 'Scheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rescheduled For Document Scrutiny', 'Rescheduled For Document Scrutiny', 'Document verification pending', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Document Verified', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Document Verified', 'Document Verified', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'OCCUPANCYCERTIFICATE', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Field Inspection completed', 'Field Inspected', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'NOC updation in progress', 'Forwarded to Superintendent for Noc Updation', 'Superintendent,Town Surveyor', 'Field Inspected', 'Forward,Revert', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Town Surveyor Inspection Initiated', 'Field Inspected', 'Forwarded to Assistant Engineer Approval', 'Town Surveyor', 'OCCUPANCYCERTIFICATE', 'Town Surveyor Inspection Initiated', 'Forwarded to Town Surveyor Additional Inspection', 'Town Surveyor', 'Town Surveyor Inspection Initiated', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Town Surveyor Inspection Initiated', 'Town Surveyor Inspection Initiated', 'Forwarded to Town Surveyor Additional Inspection', 'Town Surveyor', 'OCCUPANCYCERTIFICATE', 'Town Surveyor Inspection Completed', 'Forwarded to Overseer', 'Town Planning Building Overseer', 'Town Surveyor Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Town Surveyor Inspection Completed', 'Town Surveyor Inspected', 'Forwarded to Overseer', 'Town Planning Building Overseer', 'OCCUPANCYCERTIFICATE', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'NOC updation in progress', 'Field Inspected', 'Forwarded to Superintendent for Noc Updation', 'Superintendent', 'OCCUPANCYCERTIFICATE', 'Assistant Engineer Approval Pending', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'NOC Updated', 'Forward', NULL, NULL, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Assistant Engineer Approval Pending', 'NOC Updated', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Forwarded to generate occupancy certificate', 'Assistant Engineer', 'Approved', 'Approve,Revert,Reject', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Forwarded to generate occupancy certificate', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Assistant Engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 0, 300, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Assistant Engineer Approval Pending', 'NOC Updated', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'AEE Application Approval Pending', 'Forwarded to AEE', 'Assistant executive engineer', '', 'Forward,Revert', 301, 750, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'AEE Application Approval Pending', 'NOC Updated', 'Forwarded to AEE', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Forwarded to generate occupancy certificate', 'Assistant executive engineer', 'Approved', 'Approve,Revert,Reject', 301, 750, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Forwarded to generate occupancy certificate', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Assistant executive engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 301, 750, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Assistant Engineer Approval Pending', 'NOC Updated', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'AEE Application Approval Pending', 'Forwarded to AEE', 'Assistant executive engineer', '', 'Forward,Revert', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'AEE Application Approval Pending', 'NOC Updated', 'Forwarded to AEE', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'EE Application Approval Pending', 'Forwarded to EE', 'Executive engineer', '', 'Forward,Revert', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'EE Application Approval Pending', 'NOC Updated', 'Forwarded to EE', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Forwarded to generate occupancy certificate', 'Executive engineer', 'Approved', 'Approve,Revert,Reject', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Forwarded to generate occupancy certificate', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Executive engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 751, 1500, now(), '2099-04-01');




INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Assistant Engineer Approval Pending', 'NOC Updated', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'AEE Application Approval Pending', 'Forwarded to AEE', 'Assistant executive engineer', '', 'Forward,Revert', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'AEE Application Approval Pending', 'NOC Updated', 'Forwarded to AEE', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'EE Application Approval Pending', 'Forwarded to EE', 'Executive engineer', '', 'Forward,Revert', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'EE Application Approval Pending', 'NOC Updated', 'Forwarded to EE', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'Corporation Engineer Application Approval Pending', 'Forwarded to Corporation Engineer', 'Corporation Engineer', '', 'Forward,Revert', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Corporation Engineer Application Approval Pending', 'NOC Updated', 'Forwarded to Corporation Engineer', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Forwarded to generate occupancy certificate', 'Corporation Engineer', 'Approved', 'Approve,Revert,Reject', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Forwarded to generate occupancy certificate', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Corporation Engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 1501, 2500, now(), '2099-04-01');



INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Assistant Engineer Approval Pending', 'NOC Updated', 'Forwarded to Assistant Engineer For Approval', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'AEE Application Approval Pending', 'Forwarded to AEE', 'Assistant executive engineer', '', 'Forward,Revert', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'AEE Application Approval Pending', 'NOC Updated', 'Forwarded to AEE', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'EE Application Approval Pending', 'Forwarded to EE', 'Executive engineer', '', 'Forward,Revert', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'EE Application Approval Pending', 'NOC Updated', 'Forwarded to EE', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'Corporation Engineer Application Approval Pending', 'Forwarded to Corporation Engineer', 'Corporation Engineer', '', 'Forward,Revert', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Corporation Engineer Application Approval Pending', 'NOC Updated', 'Forwarded to Corporation Engineer', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'Secretary Application Approval Pending', 'Forwarded to Secretary', 'Secretary', '', 'Forward,Revert', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Secretary Application Approval Pending', 'NOC Updated', 'Forwarded to Secretary', 'Secretary', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Forwarded to generate occupancy certificate', 'Secretary', 'Approved', 'Approve,Revert,Reject', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Forwarded to generate occupancy certificate', 'Secretary', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Secretary', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 2501, 1000000, now(), '2099-04-01');
