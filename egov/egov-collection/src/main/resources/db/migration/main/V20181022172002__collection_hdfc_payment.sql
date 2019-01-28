Insert into egcl_servicecategory (id,name,code,isactive,version,createdby,createddate,lastmodifiedby,lastmodifieddate) values
 (nextval('seq_egcl_servicecategory'),'HDFC Bank Payment Gateway','HDFC',false,0,1,now(),1,now());

Insert into egcl_servicedetails (id,name,serviceurl,isenabled,callbackurl,servicetype,code,fund,fundsource,functionary,vouchercreation,
scheme,subscheme,servicecategory,isvoucherapproved,vouchercutoffdate,created_by,created_date,modified_by,modified_date,ordernumber) values
(nextval('seq_egcl_servicedetails'), 'HDFC Bank Payment Gateway', 'https://test.payu.in/_payment', true,
'https://kozhikode-uat-phase2.egovernments.org/collection/citizen/onlineReceipt-acceptMessageFromPaymentGateway.action', 'P', 'HDFC',
(select id from fund where code='01'), null, null, false, null, null, (select id from egcl_servicecategory where code='HDFC'), false, now(), 1, now(),
 1, now(),null);