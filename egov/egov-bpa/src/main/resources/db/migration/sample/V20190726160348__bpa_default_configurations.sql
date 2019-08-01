--Tenant list
insert into state.eg_tenant (id, name, code, createdby, createddate, lastmodifieddate, lastmodifiedby, url, version) select nextval('seq_eg_tenant'), 'Digit', 'generic', 1, now(), now(), 1, 'http://ulb.egovernments.org:8080/', 0 where not exists (select id from state.eg_tenant where code='generic');
update state.eg_tenant set url='http://ulb.egovernments.org:8080/' where code='generic';

--Boundary config
update eg_appconfig_values set value='{"validBoundary":{"REVENUE":[{"boundary":"Zone", "displayName":"Circle"}, {"boundary":"Ward", "displayName":"Revenue Ward"}]}, "crossBoundary":{},"uniformBoundary":{"REVENUE":[{"fromBoundary":"Zone:Circle","toBoundary":"Ward:Revenue Ward"}]}}' where key_id=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and key_name='BPA_GENERIC_BOUNDARY_CONFIGURATION');

--BPA Configurations
update eg_appconfig_values set value='REVENUE' where key_id=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and key_name='BPA_WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY');
update eg_appconfig_values set value='YES' where key_id=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and key_name='BPA_CITIZENACCEPTANCE_CHECK');
update eg_appconfig_values set value='NO' where key_id=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and key_name='DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED');

--EIS Config
update eg_appconfig_values set value='REVENUE' where key_id=(select id from eg_appconfig where module=(select id from eg_module where name='EIS') and key_name='EIS_EMPLOYEE_JURISDICTION_HIERARCHY');

--Collection config
INSERT into eg_appconfig_values (ID, KEY_ID, EFFECTIVE_FROM, VALUE) select nextval('seq_eg_appconfig_values'), (select id from eg_appconfig where KEY_NAME ='COLLECTIONDEPARTMENTCOLLMODES'), current_date, 'TP';
