
---------------------------------------------------APPLICATION FEE------------------------------------------------------
INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AF', 'Application Fees', 'Application Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE',
 (select id from egbpa_mstr_servicetype where description='New Construction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Demolition'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Reconstruction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', (select id from egbpa_mstr_servicetype where description='Alteration'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Tower Construction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Pole Structures'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


--------------------------------------------------PERMIT FEE------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'PF', 'Permit Fees', 'Permit Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Permit Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'PF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='PF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'ADF', 'Additional Fees', 'Additional Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Additional Fees'), 5000, 0, 1, now(), 1, now());



--------------------------------------------------OTHER FEE--------------------------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'OF', 'Other Fees', 'Other Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'OF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='OF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Demolition'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Amenities'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Tower Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Pole Structures'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());



----------------------------------------------------DEMOLITION FEE----------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'DF', 'Demolition Fees', 'Demolition Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Demolition Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'DF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='DF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Demolition'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Demolition Fees'), 0, 0, 1, now(), 1, now());



-------------------------------------------------LAND DEVELOPMENT FEE-------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'LDC', 'Land Development Charges', 'Land Development Charges', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Land Development Charges', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'LDC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='LDC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Land Development Charges'), 10, 0, 1, now(), 1, now());



------------------------------------------------------CHARGES FOR WELL-------------------------------------------------------


INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CW', 'Charges for Well', 'Charges for Well', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Well', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CW', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CW'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Well'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Well'), 25, 0, 1, now(), 1, now());



------------------------------------------------------CHARGES FOR COMPOUND WALL--------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CCW', 'Charges for Compound Wall', 'Charges for Compound Wall', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Compound Wall', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CCW', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CCW'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Compound Wall'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Compound Wall'), 4, 0, 1, now(), 1, now());



------------------------------------------------------------------------------------------------------------------------


INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CSDC', 'Charges for Shutter or Door Conversion', 'Charges for Shutter or Door Conversion', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Shutter or Door Conversion', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CSDC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CCW'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Shutter or Door Conversion/Erection under rule 100 or 101'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Shutter or Door Conversion'), 500, 0, 1, now(), 1, now());



--------------------------------------------------------------------------------------------------------------------------


INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CRC', 'Charges for Roof Conversion', 'Charges for Roof Conversion', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Roof Conversion', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CRC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CRC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Roof Conversion under rule 100 or 101'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Roof Conversion'), 4, 0, 1, now(), 1, now());


--------------------------------------------------------------------------------------------------------------------------------


INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CTC', 'Charges for Tower Construction', 'Charges for Tower Construction', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Tower Construction', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CTC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CTC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Tower Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Tower Construction'), 10000, 0, 1, now(), 1, now());



-----------------------------------------------------------------------------------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'CPS', 'Charges for Pole Structures', 'Charges for Pole Structures', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());




INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Pole Structures', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'CPS', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='CPS'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Pole Structures'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Charges for Pole Structures'), 2500, 0, 1, now(), 1, now());



-------------------------------------------------------OC FEE----------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'OCF', 'Occupancy Certificate Fees', 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'OCF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='OCF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Occupancy Certificate Fees'), 15, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Occupancy Certificate Fees'), 15, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Occupancy Certificate Fees'), 15, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Occupancy Certificate Fees'), 15, 0, 1, now(), 1, now());




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OCCUPANCY_CERTIFICATE', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Occupancy Certificate Fees'), 15, 0, 1, now(), 1, now());





--------------------------------------------------------------------------------------------------------------------



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AFA', 'Application Fees for Amenities', 'Application Fees for Amenities', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees for Amenities', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AFA', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AFA'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Amenities'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees for Amenities'), 0, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AFWC', 'Application Fees for Well consturction', 'Application Fees for Well consturction', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees for Well consturction', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AFWC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AFWC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Well'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees for Well consturction'), 15, 0, 1, now(), 1, now());




INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AFCW', 'Application Fees for compound wall', 'Application Fees for compound wall', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees for compound wall', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AFCW', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AFCW'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Compound Wall'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees for compound wall'), 15, 0, 1, now(), 1, now());




INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AFSC', 'Application Fees for Shutter or Door conversion', 'Application Fees for Shutter or Door conversion', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees for Shutter or Door conversion', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AFSC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AFSC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Shutter or Door Conversion/Erection under rule 100 or 101'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees for Shutter or Door conversion'), 20, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'AFRC', 'Application Fees for Roof conversion', 'Application Fees for Roof conversion', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Application Fees for Roof conversion', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'AFRC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='AFRC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Roof Conversion under rule 100 or 101'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Application Fees for Roof conversion'), 20, 0, 1, now(), 1, now());





-------------------------------------------REGISTRATION FEE------------------------------------------------




INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'RF', 'Registration Fees', 'Registration Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());




INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Registration Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'RF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='RF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'FIXED',(select id from egbpa_mstr_bpafee_common where name='Registration Fees'), 50, 0, 1, now(), 1, now());



