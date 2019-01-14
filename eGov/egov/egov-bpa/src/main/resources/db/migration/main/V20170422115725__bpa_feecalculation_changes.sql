alter table egbpa_application_fee add column isrevised boolean DEFAULT false;
alter table EGBPA_APPLICATION_FEE add column STATE_ID bigint;

alter table egbpa_status drop CONSTRAINT unq_bpastatus;
alter table egbpa_status add CONSTRAINT unq_bpastatus UNIQUE (code,MODULETYPE);

CREATE SEQUENCE SEQ_BPA_APPLICATIONFEE_CODE;

-----application fee status 

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'APPLICATIONFEE','Approved',now(),'Approved',true,0,1,now());

Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'APPLICATIONFEE','Cancelled',now(),'Cancelled',true,0,1,now());

 insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'APPLICATIONFEE','Created',now(),'Created',true,0,1,now());

 Insert into EGBPA_STATUS (ID,MODULETYPE,description,LASTMODIFIEDDATE,CODE,isactive,version,createdby,createddate)
 values (nextval('SEQ_EGBPA_STATUS'),'APPLICATIONFEE','Rejected',now(),'Rejected',true,0,1,now());
 
 Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'captureApplicationFee','/application/calculateFee/',null,(select id from eg_module where name='BPA Transanctions'),1,'Capture Application Fee','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'),
(select id from eg_action where name='captureApplicationFee'));

--- Appconfig to autocalculate fee or autogenerate

INSERT INTO eg_appconfig ( ID, KEY_NAME, DESCRIPTION, VERSION, MODULE ) VALUES (nextval('SEQ_EG_APPCONFIG'), 'BPA_AUTOCALCULATE_FEE', 'Auto calculate permit fee',0, (select id from eg_module where name='BPA')); 
INSERT INTO eg_appconfig_values ( ID, KEY_ID, EFFECTIVE_FROM, VALUE, VERSION ) VALUES (nextval('SEQ_EG_APPCONFIG_VALUES'), (SELECT id FROM EG_APPCONFIG WHERE KEY_NAME='BPA_AUTOCALCULATE_FEE' and module= (select id from eg_module where name='BPA')), current_date, 'NO',0);

---- fee type for each service type

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'101','Permit Fees',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'102','Charges for Well',true,true,false,1,now());
 INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'103','Charges for Compound Wall',true,true,false,1,now());
  
 INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Demolition'),'201','Demolition Fees',true,true,false,1,now());
								

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Reconstruction'),'301','Permit Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Division Of Plot'),'401','Land Development Charges',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Division Of Plot'),'402','Other Charges',true,true,false,1,now());

 
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'501','Permit Fees',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'502','Other Charges',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Additional or Extension'),'601','Permit Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Digging of Well'),'701','Other Charges',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Change in occupancy'),'801','Other Charges',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Erection of Telecommunication tower or other structure'),'901','Other Charges',true,true,false,1,now());


 --- Add demand reason and reason master
 
INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Permit Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '101', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Permit Fees' and code='101' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Well', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '102', 3, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Charges for Well' and code='102' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));
 
INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Charges for Compound Wall', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '103', 4, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Charges for Compound Wall' and code='103' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Demolition Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '201', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Demolition Fees' and code='201' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Permit Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '301', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Permit Fees' and code='301' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Permit Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '501', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Permit Fees' and code='501' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Charges', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '502', 3, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Other Charges' and code='502' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Permit Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '601', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Permit Fees' and code='601' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Charges', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '701', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Other Charges' and code='701' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Charges', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '801', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Other Charges' and code='801' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Charges', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '901', 2, current_timestamp, current_timestamp,'t');
INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where reasonmaster='Other Charges' and code='901' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));


