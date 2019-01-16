Insert into eg_action values(nextval('seq_eg_action'),'applicationsfailedduringschedulingreport','/reports/failureinscheduler', null,(select id from eg_module where name='BPA Reports'),6,'Applications failed during scheduling report ',true,'bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));


Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Report Viewer'), (select id from eg_action where name='applicationsfailedduringschedulingreport'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='applicationsfailedduringschedulingreport'));