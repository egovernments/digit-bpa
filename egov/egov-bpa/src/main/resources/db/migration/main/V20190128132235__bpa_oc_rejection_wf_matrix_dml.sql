INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Rejection initiated by clerk', NULL, 'Generation of rejection notice pending', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Rejecetd', 'Generation of rejection notice pending', 'Superintendent', 'Rejected', 'Generate Rejection Notice', NULL, NULL, '2019-01-01', '2099-04-01');

update eg_wf_matrix set validactions='Forward,Initiate Rejection' where nextstate='Scheduled For Document Scrutiny' and nextaction='Document verification pending' and objecttype='OccupancyCertificate';

update eg_wf_matrix set validactions='Forward,Initiate Rejection' where nextstate='Rescheduled For Document Scrutiny' and nextaction='Document verification pending' and objecttype='OccupancyCertificate';

update eg_wf_matrix set validactions='Forward,Initiate Rejection' where nextstate='Document Verified' and nextaction='Forwarded to Overseer for field inspection' and objecttype='OccupancyCertificate';

update eg_wf_matrix set validactions='Forward,Initiate Rejection' where nextstate='Section clerk approved' and nextaction='Forwarded to Overseer for field inspection' and objecttype='OccupancyCertificate';

update eg_wf_matrix set pendingactions ='Application is rejected by approver' where currentstate='Rejected' and nextstate='Forwarded to generate rejection notice' and objecttype='OccupancyCertificate';