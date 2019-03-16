Insert into eg_roleaction values((select id from eg_role where name='CITIZEN'),(select id from eg_action where name='Get Boundary configuration'));
