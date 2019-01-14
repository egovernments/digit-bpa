--------------workflow matrix----------------

update eg_wf_matrix set nextdesignation='Superintendent of NOC' where nextaction='Forwarded to Superintendent-Noc' and objecttype='BpaApplication';

update eg_wf_matrix set nextdesignation='Superintendent of NOC' where nextaction='Forwarded to Superintendent for Noc Updation' and objecttype='BpaApplication';

update eg_wf_matrix set nextstate='Field Inspection Initiated' , nextaction='Forwarded to Overseer for field inspection', nextdesignation='Town Planning Building Overseer', nextstatus='Field Inspection initiated' 
where currentstate='Initiate Noc' and nextaction='Forwarded to Superintendent for Noc Updation' and objecttype='BpaApplication'; 

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Field Inspection Initiated', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Field Inspection completed', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Field Inspected', 'Forwarded to Superintendent for Noc Updation', 'Superintendent of NOC', 'Field Inspected', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

update eg_wf_matrix set nextdesignation='Superintendent of Approval' where nextaction='Forwarded to Approval' and objecttype='BpaApplication' and fromqty=0 and toqty=299;

update eg_wf_matrix set nextdesignation='Corporation Engineer' where nextaction='Forwarded to Approval' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

update eg_wf_matrix set nextdesignation='Corporation Engineer' where nextaction='Forwarded to Digital Signature' and objecttype='BpaApplication' and fromqty=1500 and toqty=9999;

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Field Inspection initiated',now(),'Field Inspection initiated',true,0,1,now());