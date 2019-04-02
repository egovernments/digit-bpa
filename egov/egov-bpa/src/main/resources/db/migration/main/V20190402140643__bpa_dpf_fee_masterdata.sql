
INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'DPF', 'Development Permit Fee', 'Development Permit Fee', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Development Permit Fee', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'DPF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='DPF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-19','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype,  feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION',  'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='New Construction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Development Permit Fee'), 10000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype,  feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION',  'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Reconstruction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Development Permit Fee'), 10000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype,  feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION',  'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Development Permit Fee'), 10000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype,  feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION',  'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Alteration'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Development Permit Fee'), 10000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype,  feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION',  'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Development Permit Fee'), 10000, 0, 1, now(), 1, now());