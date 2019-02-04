INSERT INTO eg_wf_types (id, module, type, link, createdby, createddate, lastmodifiedby, lastmodifieddate, enabled, grouped, typefqn, displayname, version)
VALUES (nextval('seq_eg_wf_types'), (select id from eg_module where name='BPA'), 'StakeHolder', '/bpa/stakeholder/update/:ID', 1, now(), 1, now(), 'Y', 'N', 'org.egov.bpa.master.entity.StakeHolderState', 'Stake Holder', 0);


 INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'StakeHolderState', 'New', NULL, 'Building licensee application submission pending', NULL, 'CREATESTAKEHOLDER', 'Approved', 'END', '', 'Approved', 'Forward', NULL, NULL, '2017-01-01', '2099-04-01');