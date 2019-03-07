INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'APPLICATION_FEE',
 (select id from egbpa_mstr_servicetype where description='New Construction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Reconstruction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Alteration'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


update egbpa_mstr_bpafeemapping set bpafeecommon = (Select id from egbpa_mstr_bpafee_common where name='Application Fees')
where feesubtype='APPLICATION_FEE' 
and applicationtype='PERMIT_APPLICATION' and servicetype=(select id from egbpa_mstr_servicetype where description='Amenities');
