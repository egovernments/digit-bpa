insert into eg_role values(nextval('seq_eg_role'),'Bpa Administrator','One who manages the masters and key Bpa data',current_date,1,1,current_date,0);

Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='Create StakeHolder'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='search and show update StakeHolder result'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='edit and update StakeHolder'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='search and show view StakeHolder result'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='view StakeHolder details'));
Insert into eg_roleaction values((select id from eg_role where name='Bpa Administrator'),(select id from eg_action where name='StakeHolder success details'));

