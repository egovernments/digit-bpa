update eg_wf_matrix set nextstate  ='Record Approved' where validactions='Approve,Revert,Reject' and nextstatus='Approved' and nextaction='Forwarded to generate occupancy certificate' and objecttype ='OccupancyCertificate';

update eg_wf_matrix set nextdesignation  ='Superintendent' where currentstate='Field Inspection completed' and nextaction='Forwarded to Superintendent for Noc Updation' and objecttype ='OccupancyCertificate';

update eg_wf_matrix set currentstate  ='Record Approved' where currentstate='Final Approval Process initiated' and pendingactions='Forwarded to generate occupancy certificate' and nextstatus='Order Issued to Applicant' and nextaction='END' and objecttype ='OccupancyCertificate';


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'NOC Updated', 'Approved with fee collection pending', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Approval Fee Payment Pending', 'Assistant Engineer', 'Approved', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Approval Fee Payment Pending', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'Fee Collected', 'Forwarded to generate occupancy certificate', 'Assistant Engineer', 'Approved', '', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Fee Collected', 'Approved', 'Forwarded to generate occupancy certificate', 'Assistant Engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Assistant Engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 0, 300, now(), '2099-04-01');


INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'NOC Updated', 'Approved with fee collection pending', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Approval Fee Payment Pending', 'Assistant executive engineer', 'Approved', 'Forward', 301, 750, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Approval Fee Payment Pending', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'Fee Collected', 'Forwarded to generate occupancy certificate', 'Assistant executive engineer', 'Approved', 'Forward', 301, 750, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Fee Collected', 'Approved', 'Forwarded to generate occupancy certificate', 'Assistant executive engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Assistant executive engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 301, 750, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'NOC Updated', 'Approved with fee collection pending', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Approval Fee Payment Pending', 'Executive engineer', 'Approved', 'Forward', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Approval Fee Payment Pending', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'Fee Collected', 'Forwarded to generate occupancy certificate', 'Executive engineer', 'Approved', 'Forward', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Fee Collected', 'Approved', 'Forwarded to generate occupancy certificate', 'Executive engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Executive engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 751, 1500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'NOC Updated', 'Approved with fee collection pending', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Approval Fee Payment Pending', 'Corporation Engineer', 'Approved', 'Forward', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Approval Fee Payment Pending', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'Fee Collected', 'Forwarded to generate occupancy certificate', 'Corporation Engineer', 'Approved', 'Forward', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Fee Collected', 'Approved', 'Forwarded to generate occupancy certificate', 'Corporation Engineer', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Corporation Engineer', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 1501, 2500, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'NOC Updated', 'Approved with fee collection pending', 'Secretary', 'OCCUPANCYCERTIFICATE', 'Final Approval Process initiated', 'Approval Fee Payment Pending', 'Secretary', 'Approved', 'Forward', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Final Approval Process initiated', 'Approved', 'Approval Fee Payment Pending', 'Secretary', 'OCCUPANCYCERTIFICATE', 'Fee Collected', 'Forwarded to generate occupancy certificate', 'Secretary', 'Approved', 'Forward', 2501, 1000000, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Fee Collected', 'Approved', 'Forwarded to generate occupancy certificate', 'Secretary', 'OCCUPANCYCERTIFICATE', 'END', 'END', 'Secretary', 'Order Issued to Applicant', 'Generate Occupancy Certificate', 2501, 1000000, now(), '2099-04-01');
