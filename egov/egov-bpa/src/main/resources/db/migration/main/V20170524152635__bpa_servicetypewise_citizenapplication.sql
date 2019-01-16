delete from EGP_PORTALSERVICE where code='CREATEDEMOLITIONBPA';

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATENEWCONSTR',15,0,'/bpa/application/citizen/newconstruction-form',
'true','New Construction','true','true',
'/bpa/application/citizen/newconstruction-form',
1,now(),now(),1);


Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEDEMOLITIONBPA',15,0,'/bpa/application/citizen/demolition-form',
'true','Demolition','true','true',
'/bpa/application/citizen/demolition-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'BPACHANGEOFOCCU',15,0,'/bpa/application/citizen/changeofoccupancy-form',
'true','Change in Occupancy','true','true',
'/bpa/application/citizen/changeofoccupancy-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEREC',15,0,'/bpa/application/citizen/reconstruction-form',
'true','Reconstruction','true','true',
'/bpa/application/citizen/reconstruction-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEAOE',15,0,'/bpa/application/citizen/addextnew-form',
'true','Adding of Extension','true','true',
'/bpa/application/citizen/addextnew-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATALT',15,0,'/bpa/application/citizen/alteration-form',
'true','Alteration','true','true',
'/bpa/application/citizen/alteration-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATESOLAND',15,0,'/bpa/application/citizen/subdevland-form',
'true','Sub-Division of plot/Land Development','true','true',
'/bpa/application/citizen/subdevland-form',
1,now(),now(),1);

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEPOHOS',15,0,'/bpa/application/citizen/permissionhutorshud-form',
'true','Permission for Temporary hut or shed','true','true',
'/bpa/application/citizen/permissionhutorshud-form',
1,now(),now(),1);