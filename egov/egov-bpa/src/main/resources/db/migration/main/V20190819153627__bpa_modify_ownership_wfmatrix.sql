
Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'OWNERSHIP','Checked',now(),'Checked',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'OWNERSHIP','Validated',now(),'Validated',true,0,1,now());

delete from eg_wf_matrix where objecttype='OwnershipTransfer';

insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version) values (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Created', 'NEW', '', '', '', 'Low Risk', 'Collection pending', 'Senior Assistant,Junior Assistant', 'Created', 'Submit', '2019-01-01', '2099-04-01',0);

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'NEW', NULL, 'Ownership Transfer application creation pending', NULL, 'Low Risk', 'Initiated for ownership transfer', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Initiated for ownership transfer', 'Registered', 'Forwarded to section clerk', NULL, 'Low Risk', 'Section clerk approved', 'Town plan overseer approval pending', 'Town Planning Building Overseer', 'Checked', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Section clerk approved', 'Checked', 'Town plan overseer approval pending', NULL, 'Low Risk', 'Town plan overseer approved', 'Assistant Engineer approval pending', 'Assistant Engineer', 'Validated', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Town plan overseer approved', 'Validated', 'Assistant Engineer approval pending', NULL, 'Low Risk', 'Assistant Engineer approved', 'Superintendent Approval pending', 'Superintendent', 'Approved', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Assistant Engineer approved', 'Approved', 'Superintendent Approval pending', NULL, 'Low Risk', 'Record approved', 'Ownership transfer fee payment pending', 'Superintendent', 'Approved', 'Approve,Reject', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Record approved', 'Approved', 'Ownership transfer fee payment pending', NULL, 'Low Risk', 'Ownership transfer fee payment done', 'Forwarded to generate ownership transfer order', 'Superintendent', 'Approved', '', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Ownership transfer fee payment done', 'Approved', 'Forwarded to generate ownership transfer order', NULL, 'Low Risk', 'END', 'END', 'Superintendent', 'Order Issued to Applicant', 'Generate Ownership transfer Order', 0, 300, now(), '2099-04-01');






insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version) values (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Created', 'NEW', '', '', '', 'Medium Risk', 'Collection pending', 'Senior Assistant,Junior Assistant', 'Created', 'Submit', '2019-01-01', '2099-04-01',0);

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'NEW', NULL, 'Ownership Transfer application creation pending', NULL, 'Medium Risk', 'Initiated for ownership transfer', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Initiated for ownership transfer', 'Registered', 'Forwarded to section clerk', NULL, 'Medium Risk', 'Section clerk approved', 'Town plan overseer approval pending', 'Town Planning Building Overseer', 'Checked', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Section clerk approved', 'Checked', 'Town plan overseer approval pending', NULL, 'Medium Risk', 'Town plan overseer approved', 'Assistant Engineer approval pending', 'Assistant Engineer', 'Validated', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Town plan overseer approved', 'Validated', 'Assistant Engineer approval pending', NULL, 'Medium Risk', 'Assistant Engineer approved', 'Superintendent Approval pending', 'Superintendent', 'Approved', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Assistant Engineer approved', 'Approved', 'Superintendent Approval pending', NULL, 'Medium Risk', 'Record approved', 'Ownership transfer fee payment pending', 'Superintendent', 'Approved', 'Approve,Reject', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Record approved', 'Approved', 'Ownership transfer fee payment pending', NULL, 'Medium Risk', 'Ownership transfer fee payment done', 'Forwarded to generate ownership transfer order', 'Superintendent', 'Approved', '', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Ownership transfer fee payment done', 'Approved', 'Forwarded to generate ownership transfer order', NULL, 'Medium Risk', 'END', 'END', 'Superintendent', 'Order Issued to Applicant', 'Generate Ownership transfer Order', 0, 300, now(), '2099-04-01');






insert into eg_wf_matrix(id, department, objecttype, currentstate, nextstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextaction, nextdesignation, nextstatus, validactions, fromdate, todate, version) values (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Created', 'NEW', '', '', '', 'High Risk', 'Collection pending', 'Senior Assistant,Junior Assistant', 'Created', 'Submit', '2019-01-01', '2099-04-01',0);

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'NEW', NULL, 'Ownership Transfer application creation pending', NULL, 'High Risk', 'Initiated for ownership transfer', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Initiated for ownership transfer', 'Registered', 'Forwarded to section clerk', NULL, 'High Risk', 'Section clerk approved', 'Town plan overseer approval pending', 'Town Planning Building Overseer', 'Checked', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Section clerk approved', 'Checked', 'Town plan overseer approval pending', NULL, 'High Risk', 'Town plan overseer approved', 'Assistant Engineer approval pending', 'Assistant Engineer', 'Validated', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Town plan overseer approved', 'Validated', 'Assistant Engineer approval pending', NULL, 'High Risk', 'Assistant Engineer approved', 'Superintendent Approval pending', 'Superintendent', 'Approved', 'Forward', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Assistant Engineer approved', 'Approved', 'Superintendent Approval pending', NULL, 'High Risk', 'Record approved', 'Ownership transfer fee payment pending', 'Superintendent', 'Approved', 'Approve,Reject', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Record approved', 'Approved', 'Ownership transfer fee payment pending', NULL, 'High Risk', 'Ownership transfer fee payment done', 'Forwarded to generate ownership transfer order', 'Superintendent', 'Approved', '', 0, 300, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OwnershipTransfer', 'Ownership transfer fee payment done', 'Approved', 'Forwarded to generate ownership transfer order', NULL, 'High Risk', 'END', 'END', 'Superintendent', 'Order Issued to Applicant', 'Generate Ownership transfer Order', 0, 300, now(), '2099-04-01');
