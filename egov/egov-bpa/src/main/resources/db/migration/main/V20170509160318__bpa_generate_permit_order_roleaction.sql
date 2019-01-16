create sequence SEQ_BPA_PLANPERMNO_;

alter table EGBPA_APPLICATION_FLOORDETAIL add column floordescription character varying(128);

update eg_wf_matrix set validactions ='Generate Permit Order' where nextstatus='Order Issued to Applicant' and currentstate='Digitally signed' and objecttype ='BpaApplication';

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'generate building permit order','/application/generatepermitorder',null,(select id from eg_module where name='BPA Transanctions'),17,'generate building permit order','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='generate building permit order'));