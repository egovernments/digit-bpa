alter table egbpa_application_floordetail alter column floorarea type double precision USING floorarea::double precision;
alter table egbpa_application_floordetail add column orderoffloor bigint;

update egbpa_mstr_servicetype set isamenity =false where code='14';
update egbpa_mstr_servicetype set isamenity =false where code='15';

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATETOWER',15,0,'/bpa/application/citizen/towerconstruction-form',
'true','Tower Construction','true','true',
'/bpa/application/citizen/towerconstruction-form',
1,now(),now(),1);

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Tower Construction',
'/application/citizen/towerconstruction-form',null,(select id from eg_module where name='BPA Transanctions'),22,
'Tower Construction','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Tower Construction'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Tower Construction'));

Insert into EGP_PORTALSERVICE (id,module,code,sla,version,url,isactive,name,userservice,businessuserservice,helpdoclink,
createdby,createddate,lastmodifieddate,lastmodifiedby) values(nextval('seq_egp_portalservice'),(select id from eg_module where name='BPA'),
'CREATEPOLE',15,0,'/bpa/application/citizen/polestructures-form',
'true','Pole Structures','true','true',
'/bpa/application/citizen/polestructures-form',
1,now(),now(),1);

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Pole Structures',
'/application/citizen/polestructures-form',null,(select id from eg_module where name='BPA Transanctions'),23,
'Pole Structures','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='BUSINESS'),
 (select id from eg_action where name='Pole Structures'));

Insert into eg_roleaction (roleid,actionid) values
 ((select id from eg_role where name='CITIZEN'),
 (select id from eg_action where name='Pole Structures'));


------------------------tower construction checklist documents-------------------- 

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Tower Construction'),
0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1001','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1002','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1003',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1004','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1005','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1006','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1007','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1008','Declaration by the Applicant',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());



------------------------pole structures checklist documents--------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Pole Structures'),
0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2001','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2002','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2003',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2004','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2005','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2006','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2007','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2008','Declaration by the Applicant',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

