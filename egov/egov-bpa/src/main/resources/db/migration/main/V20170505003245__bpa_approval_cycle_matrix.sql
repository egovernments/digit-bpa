update eg_wf_matrix set validactions='Forward' where nextstate='Record Approved' and nextaction='Forwarded to Digital Signature' and objecttype='BpaApplication';

update eg_wf_matrix set nextaction='Forwarded to Assistant Engineer For Approval',nextdesignation='Assistant Engineer',validactions='Forward,Reject' where nextstate='Application Approval Pending' and nextstatus='NOC Updated' and objecttype='BpaApplication';

delete from eg_wf_matrix where currentstate='Application Approval Pending' and objecttype='BpaApplication';

update eg_wf_matrix set nextdesignation='Superintendent of Approval' where nextaction='Forwarded to generate permit order' and nextstate='Digitally signed' and objecttype='BpaApplication';

update eg_wf_matrix set nextdesignation='Superintendent of Approval' where nextaction='Generated permit orders' and nextstate='END' and objecttype='BpaApplication';

delete from eg_wf_matrix where currentstate  ='Noc Details Updated' and nextstatus ='Field Inspected' and objecttype='BpaApplication';


 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Application Approval Pending', NULL, 'Forwarded to Assistant Engineer For Approval', NULL,  'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending',  'Superintendent of Approval',  NULL, 'Approve', 0, 299, now(), '2099-04-01');


 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Application Approval Pending', NULL, 'Forwarded to Assistant Engineer For Approval', NULL,  'CREATEBPAAPPLICATION', 'AEE Application Approval Pending', 'Forwarded to AEE',  'Assistant executive engineer',  NULL, 'Forward', 300,749, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'AEE Application Approval Pending', NULL, 'Forwarded to AEE', NULL,  'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending',  'Superintendent of Approval',  NULL, 'Approve', 300, 749, now(), '2099-04-01');



 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Application Approval Pending', NULL, 'Forwarded to Assistant Engineer For Approval', NULL,  'CREATEBPAAPPLICATION', 'AEE Application Approval Pending', 'Forwarded to AEE',  'Assistant executive engineer',  NULL, 'Forward', 750,1499, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'AEE Application Approval Pending', NULL, 'Forwarded to AEE', NULL,  'CREATEBPAAPPLICATION', 'EE Application Approval Pending', 'Forwarded to EE',  'Executive engineer',  NULL, 'Forward', 750,1499, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'EE Application Approval Pending', NULL, 'Forwarded to EE', NULL,  'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending',  'Superintendent of Approval',  NULL, 'Approve', 750, 1499, now(), '2099-04-01');




 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Application Approval Pending', NULL, 'Forwarded to Assistant Engineer For Approval', NULL,  'CREATEBPAAPPLICATION', 'AEE Application Approval Pending', 'Forwarded to AEE',  'Assistant executive engineer',  NULL, 'Forward', 1500,9999, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'AEE Application Approval Pending', NULL, 'Forwarded to AEE', NULL,  'CREATEBPAAPPLICATION', 'EE Application Approval Pending', 'Forwarded to EE',  'Executive engineer',  NULL, 'Forward', 1500,9999, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'EE Application Approval Pending', NULL, 'Forwarded to EE', NULL,  'CREATEBPAAPPLICATION', 'Corporation Engineer Application Approval Pending', 'Forwarded to Corporation Engineer',  'Corporation Engineer',  NULL, 'Forward', 1500,9999, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Corporation Engineer Application Approval Pending', NULL, 'Forwarded to Corporation Engineer', NULL,  'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending',  'Superintendent of Approval',  NULL, 'Approve',1500, 9999, now(), '2099-04-01');




 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Application Approval Pending', NULL, 'Forwarded to Assistant Engineer For Approval', NULL,  'CREATEBPAAPPLICATION', 'AEE Application Approval Pending', 'Forwarded to AEE',  'Assistant executive engineer',  NULL, 'Forward',10000,1000000, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'AEE Application Approval Pending', NULL, 'Forwarded to AEE', NULL,  'CREATEBPAAPPLICATION', 'EE Application Approval Pending', 'Forwarded to EE',  'Executive engineer',  NULL, 'Forward', 10000,1000000, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'EE Application Approval Pending', NULL, 'Forwarded to EE', NULL,  'CREATEBPAAPPLICATION', 'Corporation Engineer Application Approval Pending', 'Forwarded to Corporation Engineer',  'Corporation Engineer',  NULL, 'Forward', 10000,1000000, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Corporation Engineer Application Approval Pending', NULL, 'Forwarded to Corporation Engineer', NULL,  'CREATEBPAAPPLICATION', 'Secretary Application Approval Pending', 'Forwarded to Secretary',  'Secretary',  NULL, 'Forward', 10000,1000000, now(), '2099-04-01');

 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation,
 nextstatus, validactions, fromqty, toqty, fromdate, todate) VALUES (nextval('SEQ_EG_WF_MATRIX'),  'ANY', 'BpaApplication',
  'Secretary Application Approval Pending', NULL, 'Forwarded to Secretary', NULL,  'CREATEBPAAPPLICATION', 'Final Approval Process initiated', 'Digital Sign Pending',  'Superintendent of Approval',  NULL, 'Approve',10000, 1000000, now(), '2099-04-01');
