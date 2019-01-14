delete from eg_wf_matrix where currentstate ='Superintendent Approved' and nextstate='Document Verified' and nextaction='Forwarded to Superintendent-Noc';

update eg_wf_matrix set currentstate='Superintendent Approved' , nextstate='Document Verified'
where nextaction='Forwarded to Assistant Engineer for field ispection' and objecttype='BpaApplication';

update eg_wf_matrix set currentstate='Document Verified'
where nextstate='Field Inspection Initiated' and nextaction='Forwarded to Overseer for field inspection' and objecttype='BpaApplication';

update eg_wf_matrix set nextaction='Forwarded to Assistant Engineer For Approval',nextdesignation='Assistant Engineer',validactions='Forward,Reject'
where nextstate='Noc Details Updated' and nextstatus='NOC Updated' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Reject' where nextaction='Forwarded to Superintendent for Noc Updation' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,Reject' where nextaction='Forwarded to Assistant Engineer Approval' and nextstatus='NOC Updated' and objecttype='BpaApplication';

update eg_wf_matrix set validactions='Forward,CANCEL APPLICATION' where nextaction='Document verification pending' and nextstate='Superintendent Approved' and objecttype='BpaApplication';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Rejected', NULL, NULL, null, 'CREATEBPAAPPLICATION', 'Document Verified','Application is Rejected by Superior Officer', 'Assistant Engineer', 'Document Verified', 'Forward,CANCEL APPLICATION', NULL, NULL, '2017-01-01', '2099-04-01');

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'REGISTRATION','Rejected',now(),'Rejected',true,0,1,now());