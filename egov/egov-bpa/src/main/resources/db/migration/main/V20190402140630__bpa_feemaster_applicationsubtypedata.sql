--------------------------------New Construction, Permit Fee-----------------------------------------------------------------
INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 10, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 20, 0, 1, now(), 1, now());


--------------------------------Reconstruction Permit Fee--------------------------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 10, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 20, 0, 1, now(), 1, now());

-----------------------------Addition Permit Fee-----------------------------------------------------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 10, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 20, 0, 1, now(), 1, now());

-------------------------------------Alteration Permit Fee--------------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 10, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 20, 0, 1, now(), 1, now());

-----------------------------------CHange in Occupancy Permit Fee--------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 10, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 15, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'SANCTION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Permit Fees'), 20, 0, 1, now(), 1, now());










--------------------------------New Construction, Application Fee-----------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='New Construction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 10000, 0, 1, now(), 1, now());


--------------------------------Reconstruction Application Fee-----------------------------------------------------------------------

INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Reconstruction'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 10000, 0, 1, now(), 1, now());

-----------------------------Addition Application Fee-----------------------------------------------------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Addition or Extension'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 10000, 0, 1, now(), 1, now());

-------------------------------------Alteration Application Fee--------------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Alteration'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 10000, 0, 1, now(), 1, now());

-----------------------------------Change in Occupancy Application Fee--------------------------------


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Low Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 1000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='Medium Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 5000, 0, 1, now(), 1, now());


INSERT INTO egbpa_mstr_bpafeemapping(id, applicationtype, applicationsubtype, feesubtype, servicetype, calculationtype, 
            bpafeecommon, amount, version, createdby, createddate, lastmodifiedby, lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_bpafeemapping'), 'PERMIT_APPLICATION', (Select id from egbpa_mstr_applicationsubtype where name='High Risk'), 'APPLICATION_FEE', 
(select id from egbpa_mstr_servicetype where description='Change in occupancy'), 'AUTO',(select id from egbpa_mstr_bpafee_common where name='Application Fees'), 10000, 0, 1, now(), 1, now());