Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get bpa occupancy details','/application/getoccupancydetails',null,(select id from eg_module where name='BPA Transanctions'),18,'get bpa occupancy details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get bpa occupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='get bpa occupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get bpa occupancy details'));
Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get bpa occupancy details'));

alter table egbpa_buildingdetail drop column fromgroundwithwostairroom;
alter table egbpa_buildingdetail drop column fromstreetwithwostairroom;

alter table egbpa_buildingdetail add column heightfromgroundwithstairroom double precision;
alter table egbpa_buildingdetail add column heightfromgroundwithoutstairroom double precision;
alter table egbpa_buildingdetail add column fromstreetlevelwithstairroom double precision;
alter table egbpa_buildingdetail add column fromstreetlevelwithoutstairroom double precision; 
alter table egbpa_sitedetail add column townsurveynumber character varying(24);
alter table egbpa_sitedetail add column resurveynumber character varying(24); 