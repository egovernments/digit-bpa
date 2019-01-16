alter table egbpa_sitedetail add column postaladdress bigint NOT NULL default 1;
alter table egbpa_sitedetail drop column district;
alter table egbpa_sitedetail drop column state;
alter table egbpa_sitedetail drop column sitepincode;


Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'get postal address details','/ajax/postaladdress',null,(select id from eg_module where name='BPA Transanctions'),21,'get postal address details','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='get postal address details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='get postal address details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BUSINESS'), (select id from eg_action where name='get postal address details'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='CITIZEN'), (select id from eg_action where name='get postal address details'));

create sequence SEQ_EGBPA_MSTR_POSTAL;

CREATE TABLE egbpa_mstr_postal
(
  id bigint NOT NULL,
  postoffice character varying(128),
  district character varying(128),
  pincode character varying(10),
  state character varying(50),
  taluk character varying(50),
  isactive boolean,
  CONSTRAINT pk_egbpa_mstr_postal_id PRIMARY KEY (id)
);
