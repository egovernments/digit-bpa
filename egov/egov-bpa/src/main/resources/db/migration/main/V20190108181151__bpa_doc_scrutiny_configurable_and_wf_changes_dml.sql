-----------------------Enable/Disable app config for document scrutiny of permit application------------

INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED', 'To enable and disable document scrutiny workflow integration for the permit application',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);

----------Regular application type workflow matrix changes when document scrutiny not required----------

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'NEW', NULL, 'Forward to section clerk is pending', null, 'CREATEBPAAPPLICATION', 'Registered', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Registered', 'Registered', 'Forwarded to section clerk', null, 'CREATEBPAAPPLICATION', 'Section clerk approved', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Registered', 'Forward,Initiate Rejection', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Section clerk approved', 'Registered', 'Forwarded to Overseer for field inspection', null, 'CREATEBPAAPPLICATION', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');

----------One day permit application type workflow matrix changes if document scrutiny not required----------

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'NEW', NULL, 'Forward to section clerk is pending', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Registered', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Registered', 'Registered', 'Forwarded to section clerk', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Section clerk approved', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Registered', 'Forward,Initiate Rejection', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'BpaApplication', 'Section clerk approved', 'Registered', 'Forwarded to Overseer for field inspection', null, 'CREATEBPAAPPLICATION-ONEDAYPERMIT', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');


------------Occupancy certificate changes------------------------------------------

-----------------------Enable/Disable app config for document scrutiny of occupancy certificate application------------

INSERT INTO eg_appconfig ( ID, KEYNAME, DESCRIPTION, VERSION, MODULE ) VALUES
(nextval('SEQ_EG_APPCONFIG'), 'OC_DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED', 'To enable and disable document scrutiny workflow integration for the OC application',0, (select id from eg_module where name='BPA'));

INSERT INTO eg_appconfig_values ( ID, CONFIG, EFFECTIVEFROM, VALUE, VERSION )
VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'),(SELECT id FROM EG_APPCONFIG WHERE KEYNAME='OC_DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED'
 AND MODULE =(select id from eg_module where name='BPA')),current_date, 'YES',0);


----------Occupancy certificate application type workflow matrix changes if document scrutiny not required----------

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'NEW', NULL, 'Forward to section clerk is pending', null, 'OCCUPANCYCERTIFICATE', 'Registered', 'Forwarded to section clerk', 'Section Clerk', 'Registered', 'Forward', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Registered', 'Registered', 'Forwarded to section clerk', 'Section Clerk', 'OCCUPANCYCERTIFICATE', 'Section clerk approved', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'Registered', 'Forward,Initiate Rejection', NULL, NULL, now(), '2099-04-01');

INSERT INTO eg_wf_matrix (id, department, objecttype, currentstate, currentstatus, pendingactions, currentdesignation, additionalrule, nextstate, nextaction, nextdesignation, nextstatus, validactions, fromqty, toqty, fromdate, todate)
 VALUES (nextval('seq_eg_wf_matrix'), 'ANY', 'OccupancyCertificate', 'Section clerk approved', 'Registered', 'Forwarded to Overseer for field inspection', 'Town Planning Building Overseer', 'OCCUPANCYCERTIFICATE', 'Field Inspection completed', 'Forwarded to Assistant Engineer Approval', 'Assistant Engineer', 'Field Inspected', 'Forward', NULL, NULL, now(), '2099-04-01');
