
INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'SF', 'Shelter Fund', 'Shelter Fund', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Shelter Fund', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'SF', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='SF'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Demolition'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Tower Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Pole Structures'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Shelter Fund'), 50, 0, 1, now(), 1, now());



--Labour cess

INSERT INTO egbpa_mstr_bpafee_common(id, glcode, code, name, description, category, version, createdby, 
            createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafee_common'), (select id from chartofaccounts  where glcode='1401202'), 'LC', 'Labour cess', 'Labour cess', (select id from eg_reason_category where code='Fee'), 0, 1, 
            now(), 1, now());

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Labour cess', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), 'LC', 2, current_timestamp, current_timestamp,'t');


Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='LC'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Demolition'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Huts and Sheds'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Tower Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());



INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE',
 (select id from egbpa_mstr_servicetype where description='Pole Structures'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Labour cess'), 50, 0, 1, now(), 1, now());
 
 
 --------------------------------------
 
 INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Other Fees'), 0, 0, 1, now(), 1, now());



update egbpa_mstr_applicationtype SET enabled = false where name in ('Low Risk','High Risk','Medium Risk');

update egbpa_mstr_applicationtype SET enabled = true  where name in ('Regular');



INSERT INTO eg_roleaction (roleid, actionid)
SELECT (select id from eg_role where name='SYSTEM'), (select id from eg_action where name='Servicetype details')
WHERE
    NOT EXISTS (select roleid,actionid from eg_roleaction where roleid in (select id from eg_role where name='SYSTEM') and actionid in 
 (select id from eg_action where name='Servicetype details'));
