
INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '50', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='50'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));

INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '150', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='150'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '250', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='250'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '350', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='350'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Occupancy Certificate Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '450', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='450'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));


INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '51', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='51'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '151', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='151'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '251', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='251'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '351', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='351'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));



INSERT INTO EG_DEMAND_REASON_MASTER ( ID, REASONMASTER, "category", ISDEBIT, module, CODE, "order", create_date, modified_date,isdemand) VALUES(nextval('seq_eg_demand_reason_master'), 'Other Fees', (select id from eg_reason_category where code='Fee'), 'N', (select id from eg_module where name='BPA' and parentmodule is null), '451', 2, current_timestamp, current_timestamp,'t');
Insert into EG_DEMAND_REASON (ID,ID_DEMAND_REASON_MASTER,ID_INSTALLMENT,PERCENTAGE_BASIS,ID_BASE_REASON,create_date,modified_date,GLCODEID)
(select (nextval('seq_eg_demand_reason')), (select id from eg_demand_reason_master where code='451'and module=(select id from eg_module where name='BPA')), (select id from EG_INSTALLMENT_MASTER where ID_MODULE = (select id from EG_MODULE where name = 'BPA') and start_date = to_date('01-04-18','DD-MM-YY')), null, null, current_timestamp, current_timestamp, (select id from chartofaccounts  where glcode='1401202'));
