Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Service type wise status report'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='Regular application slot details report'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='One day permit application slot details report'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'), (select id from eg_action where name='applicationsfailedduringschedulingreport'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='SYSTEM'),(select id from eg_action where name='View Service type wise application details'));

Insert into eg_roleaction (roleid,actionid) values((select id from eg_role where name='BPA Report Viewer'),(select id from eg_action where name='get active child boundaries of parent boundary'));