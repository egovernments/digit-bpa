Insert into egcl_servicedetails (id,name,serviceurl,isenabled,callbackurl,servicetype,code,fund,fundsource,functionary,vouchercreation,
scheme,subscheme,servicecategory,isvoucherapproved,vouchercutoffdate,created_by,created_date,modified_by,modified_date,ordernumber) values 
(nextval('seq_egcl_servicedetails'), 'Punjab Bank Payment Gateway', 'https://cgt.in.worldline.com/ipg/doMEPayRequest', true, 
'http://kozhikode.egovernments.org/collection/citizen/onlineReceipt-acceptMessageFromPaymentGateway.action', 'P', 'PNB', 
(select id from fund where code='01'), null, null, false, null, null, (select id from egcl_servicecategory where code='PNB'), false, now(), 1, now(),
 1, now(),null);

Insert into EGCL_SERVICE_INSTRUMENTACCOUNTS(id, instrumenttype, servicedetails, chartofaccounts, createdby, createddate, lastmodifiedby, lastmodifieddate)
values(nextval('seq_egcl_service_instrumentaccounts'),(select id from egf_instrumenttype  where type ='online'),(select id from egcl_servicedetails where name='Punjab Bank Payment Gateway' and code='PNB'),
(select id from chartofaccounts where glcode='4318001'),1,now(),1,now());
