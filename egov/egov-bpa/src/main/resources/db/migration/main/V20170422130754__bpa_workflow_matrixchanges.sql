Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','NOC Updated',now(),'NOC Updated',true,0,1,now());

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'NOC updation in progress', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Noc Details Updated', 'Forwarded to Superintendent approval', 'Superintendent of Approval', 'NOC Updated', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');


update eg_wf_matrix set nextstate='NOC updation in progress' 
where currentstate='Field Inspection completed' and nextaction='Forwarded to Superintendent for Noc Updation' and objecttype='BpaApplication'; 

update eg_wf_matrix set currentstate='Noc Details Updated', nextstate='Final Approval Process initiated'
where currentstate='Field Inspected' and nextaction='Forwarded to Approval' and objecttype='BpaApplication'; 

update eg_wf_matrix set currentstate='Final Approval Process initiated'
where currentstate='Noc Details Updated' and nextstate='Record Approved' and objecttype='BpaApplication'; 