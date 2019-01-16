insert into eg_roleaction values((select id from eg_role where name = 'SYSTEM'),(select id from eg_action where name = 'stakeHolderSearch'));

insert into eg_roleaction values((select id from eg_role where name = 'SYSTEM'),(select id from eg_action where name = 'stakeHolderUpdateForApproval'));
