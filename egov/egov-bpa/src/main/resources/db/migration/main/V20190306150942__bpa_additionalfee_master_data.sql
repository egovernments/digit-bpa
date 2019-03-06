

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) 
VALUES(nextval('seq_eg_demand_reason_master'), 'Additional Fees', (select id from eg_reason_category where code='Fee'), 'N', 
(select id from eg_module where name='BPA' and parentmodule is null), 'ADF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='ADF'and module=(select id from eg_module where name='BPA')),
(select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp,
 (select id from chartofaccounts  where glcode='1401202'));


delete from egbpa_mstr_bpafeemapping where bpafeecommon in (select id from egbpa_mstr_bpafee_common where name='Permit Fees') and
servicetype in (select id from egbpa_mstr_servicetype where description='Huts and Sheds') and feesubtype='SANCTION_FEE';


delete from egbpa_mstr_bpafeemapping where bpafeecommon in (select id from egbpa_mstr_bpafee_common where name='Additional Fees') and
servicetype in (select id from egbpa_mstr_servicetype where description='Change in occupancy') and feesubtype='SANCTION_FEE';




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());







