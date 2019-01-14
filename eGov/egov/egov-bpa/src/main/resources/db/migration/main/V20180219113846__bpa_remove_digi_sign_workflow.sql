update eg_wf_matrix set  nextaction ='Forwarded to generate permit order',nextstate='Permit Fee paid' where currentstate ='Final Approval Process initiated' and pendingactions ='Permit Fee Collection Pending' and objecttype ='BpaApplication';

update eg_wf_matrix set  currentstate ='Permit Fee paid' where nextstate ='END' and nextaction ='Generated permit orders' and objecttype ='BpaApplication';

ALTER TABLE egbpa_application_permit_conditions ALTER COLUMN additionalPermitCondition TYPE character varying(600);