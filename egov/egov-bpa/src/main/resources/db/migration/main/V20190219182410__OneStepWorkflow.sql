
update eg_wf_types set type='StakeHolderState' where type='StakeHolder';

delete from eg_wf_matrix where objecttype='StakeHolderState';

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule,
 nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'StakeHolderState', 'New', NULL, 'Building licensee application submission pending', 
NULL, 'CREATESTAKEHOLDER', 'Registered', 'Approver Approval Pending', 'Superintendent', 'Submitted', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation,
 additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'StakeHolderState', 'Registered', 'Submitted', 'Approver Approval Pending', 
'Superintendent', 'CREATESTAKEHOLDER', 'END', 'END', 'Town Planner', 'Submitted', 'Approve,Reject', NULL, NULL, '2017-01-01',
 '2099-04-01');




