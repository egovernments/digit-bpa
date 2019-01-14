INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '01'),'103','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '02'),'202','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '03'),'303','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '03'),'403','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '05'),'502','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '06'),'603','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '07'),'703','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '08'),'802','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '09'),'902','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '14'),'1007','Other Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  code=  '15'),'1008','Other Fees',true,true,false,1,now());

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='103'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='202'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='303'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='403'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='502'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='603'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='703'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='802'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='902'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='1007'),null,null,0,null,now(),1,now(),null );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate,additionaltype) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='1008'),null,null,0,null,now(),1,now(),null );


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '103', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='103' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '202', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='202' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '303', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='303' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '403', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='403' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '502', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='502' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '603', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='603' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '703', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='703' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '802', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='802' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '902', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='902' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '1007', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='1007' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '1008', 2, current_timestamp, current_timestamp,'t');

INSERT into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID) (select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='1008' and module=(select id from eg_module where name='BPA' and parentmodule is null)), inst.id, null, null, current_timestamp, current_timestamp, null from eg_installment_master inst where inst.id_module=(select id from eg_module where name='BPA' and parentmodule is null));

update EG_DEMAND_REASON set GLCODEID =(select ID from CHARTOFACCOUNTS where GLCODE = '1401202')  where id_demand_reason_master in ( select id from eg_demand_reason_master
where module=(select id from eg_module where name = 'BPA' and parentmodule is null));