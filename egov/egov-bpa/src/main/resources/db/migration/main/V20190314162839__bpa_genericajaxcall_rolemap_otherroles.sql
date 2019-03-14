Insert into eg_roleaction values((select id from eg_role where name='Employee Admin'), (select id from eg_action where name='Get Boundary configuration'));
Insert into eg_roleaction values((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Get Boundary configuration'));
Insert into eg_roleaction values((select id from eg_role where name='Document Scrutiniser'), (select id from eg_action where name='Get Boundary configuration'));
