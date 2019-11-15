insert into state.eg_role select nextval('state.seq_eg_role'),'Bill Creator','One who can create bills',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Bill Creator');
insert into state.eg_role select nextval('state.seq_eg_role'),'Bill Approver','One who can approve bills',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Bill Approver');

insert into state.eg_role select nextval('state.seq_eg_role'),'Voucher Creator','One who can create vouchers',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Voucher Creator');
insert into state.eg_role select nextval('state.seq_eg_role'),'Voucher Approver','One who can approve vouchers',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Voucher Approver');

insert into state.eg_role select nextval('state.seq_eg_role'),'Payment Creator','One who can create payments',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Payment Creator');
insert into state.eg_role select nextval('state.seq_eg_role'),'Payment Approver','One who can approve payments',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Payment Approver');

insert into state.eg_role select nextval('state.seq_eg_role'),'Financial Administrator','One who manages the masters and key Financials data',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Financial Administrator');
insert into state.eg_role select nextval('state.seq_eg_role'),'Financial Report Viewer','One who can view financial reports',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Financial Report Viewer');
insert into state.eg_role select nextval('state.seq_eg_role'),'Budget Creator','One who can upload budget',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Budget Creator');
insert into state.eg_role select nextval('state.seq_eg_role'),'Budget Approver','One who can approve budget',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Budget Approver');
insert into state.eg_role select nextval('state.seq_eg_role'),'Bank Reconciler','One who can perform Bank Reconciliation',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='Bank Reconciler');
insert into state.eg_role select nextval('state.seq_eg_role'),'FINANCIALS VIEW ACCESS','User has view access to Financials Masters and transactional data',now(),1,1,now(),0, false where not exists (select * from state.eg_role where name='FINANCIALS VIEW ACCESS');