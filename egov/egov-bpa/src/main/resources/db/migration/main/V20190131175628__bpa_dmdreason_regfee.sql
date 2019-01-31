
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'registrationfee',(select id from egbpa_mstr_servicetype where  code=  '01'),'30','Building License Registration Fees',true,true,false,1,now());

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='30'),null,null,50,null,now(),1,now()  );

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Registration Fee', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '30' , 1, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='30' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, (select ID from CHARTOFACCOUNTS where GLCODE = '1401202') from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, MODULE ) VALUES ( nextval('SEQ_EG_APPCONFIG'), 'BUILDING_LICENSEE_REG_FEE_REQUIRED','Building licensee registration fee is required or not',(select id from eg_module where name='BPA'));

Insert into eg_appconfig_values (ID,KEY_ID,EFFECTIVE_FROM,VALUE) values (nextval('seq_eg_appconfig_values'),(select id from eg_appconfig where KEY_NAME ='BUILDING_LICENSEE_REG_FEE_REQUIRED'),now(),'NO');
