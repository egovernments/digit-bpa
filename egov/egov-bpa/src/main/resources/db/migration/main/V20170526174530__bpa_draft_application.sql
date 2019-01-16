
 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, 
pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'), 
 'ANY', 'BpaApplication', 'Created', NULL, NULL, null, 
 'CREATEBPAAPPLICATION', 'NEW', 'Collection pending', 'Senior Assistant,Junior Assistant', 
 'Created', 'Submiit', NULL, NULL, now(), '2099-04-01');

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Created',now(),'Created',true,0,1,now());