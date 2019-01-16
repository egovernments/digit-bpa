Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEAMENITY',15,0,'/bpa/application/citizen/amenity-form',
'true','Amenities (Well,Compound Wall,Roof cov..etc)','true','true',
'/bpa/application/citizen/amenity-form',
1,now(),now(),1);



Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Bpa Amenity',
'/application/citizen/amenity-form',null,(select id from eg_module where name='BPA Transanctions'),17,
'Bpa Amenity','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Bpa Amenity'));



Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Bpa Amenity'));