insert into eg_action values(nextval('seq_eg_action'),'getAmenityServiceType','/bpa/rest/servicetype/amenities', null,(select id from eg_module where name='BPA Transanctions'),31,'Get Amenity Service Type',false,'bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));
insert into eg_roleaction values((select id from eg_role where name = 'PUBLIC'),(select id from eg_action where name = 'getAmenityServiceType'));


insert into eg_action values(nextval('seq_eg_action'),'getMainServiceType','/bpa/rest/servicetype/main', null,(select id from eg_module where name='BPA Transanctions'),32,'Get Main Service Type',false,'bpa',0,1,now(),1,now(),(select id from eg_module where name='BPA'));
insert into eg_roleaction values((select id from eg_role where name = 'PUBLIC'),(select id from eg_action where name = 'getMainServiceType'));
