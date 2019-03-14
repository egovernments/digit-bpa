Insert into eg_roleaction values((select id from eg_role where name='EMPLOYEE'), (select id from eg_action where name='Get Boundary configuration'));
