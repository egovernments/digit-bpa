

--------------------------------OWNERSHIP APPLICATION FEE--------------------------------------------------------

--------------------------------New Construction-----------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', null, 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 100, 0, 1, now(), 1, now());

--------------------------------Reconstruction-----------------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', null, 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 100, 0, 1, now(), 1, now());

--------------------------------Addition or Extension-----------------------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', null, 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 100, 0, 1, now(), 1, now());

-------------------------------------Alteration--------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', null, 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 100, 0, 1, now(), 1, now());

-----------------------------------Change in Occupancy--------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', null, 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 100, 0, 1, now(), 1, now());






---------------------------------------------------------Ownership transfer fees----------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'OTF', 'Ownership Transfer Fees', 'Ownership Transfer Fees', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Ownership Transfer Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'OTF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='OTF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Ownership Transfer Fees'), 100, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Ownership Transfer Fees'), 100, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Ownership Transfer Fees'), 100, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Ownership Transfer Fees'), 100, 0, 1, now(), 1, now());




INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'OWNERSHIP_TRANSFER', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Ownership Transfer Fees'), 100, 0, 1, now(), 1, now());

