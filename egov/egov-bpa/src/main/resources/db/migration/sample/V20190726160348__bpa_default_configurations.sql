--Boundary config
update eg_appconfig_values set value='{"validBoundary":{"REVENUE":[{"boundary":"Zone", "displayName":"Circle"}, {"boundary":"Ward", "displayName":"Revenue Ward"}]}, "crossBoundary":{},"uniformBoundary":{"REVENUE":[{"fromBoundary":"Zone:Circle","toBoundary":"Ward:Revenue Ward"}]}}' where CONFIG=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and keyname='BPA_GENERIC_BOUNDARY_CONFIGURATION');

--BPA Configurations
update eg_appconfig_values set value='REVENUE' where CONFIG=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and keyname='BPA_WORKFLOW_EMPLOYEE_BOUNDARY_HIERARCHY');
update eg_appconfig_values set value='YES' where CONFIG=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and keyname='BPA_CITIZENACCEPTANCE_CHECK');
update eg_appconfig_values set value='NO' where CONFIG=(select id from eg_appconfig where module=(select id from eg_module where name='BPA') and keyname='DOCUMENT_SCRUTINY_INTEGRATION_REQUIRED');

--EIS Config
update eg_appconfig_values set value='REVENUE' where CONFIG=(select id from eg_appconfig where module=(select id from eg_module where name='EIS') and keyname='EIS_EMPLOYEE_JURISDICTION_HIERARCHY');

--Collection config
INSERT into eg_appconfig_values (ID, CONFIG, EFFECTIVEFROM, VALUE) select nextval('seq_eg_appconfig_values'), (select id from eg_appconfig where keyname ='COLLECTIONDEPARTMENTCOLLMODES'), current_date, 'TP';
