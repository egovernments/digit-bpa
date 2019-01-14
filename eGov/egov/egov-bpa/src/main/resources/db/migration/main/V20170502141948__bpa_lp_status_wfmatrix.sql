Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Letter To Party Created',now(),'Letter To Party Created',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Letter To Party Reply Received',now(),'Letter To Party Reply Received',true,0,1,now());
 
 
INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'LP Initiated', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'LP Initiated', 'LP Sent to Applicant', 'LP Raised', 'Letter To Party Created', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');
