delete from egbpa_application_feedetails;
delete from egbpa_mstr_bpafeedetail ;
delete from egbpa_mstr_bpafee ;
delete from egbpa_document ;
delete from egbpa_documenthistory ;
delete from egbpa_documentscrutiny ;
delete from egbpa_applicationamenity ;
delete from egbpa_application_feedetails ;
delete from egbpa_application_floordetail ;
delete from  egbpa_applicationstakeholder ;
delete from egbpa_appointment_schedule ;
delete from egbpa_buildingdetail ;
delete from egbpa_dd_detail ;
delete from egbpa_docket_constrnstage ;
delete from egbpa_dochist_detail ;
delete from egbpa_docket_detail;
delete from egbpa_docket ;
delete from egbpa_inspection ;
delete from egbpa_lettertoparty_filestore ;
delete from egbpa_lettertoparty_document ;
delete from egbpa_lettertoparty ;
delete from egbpa_noc_document ;
delete from egbpa_permitted_floordetail ;
delete from egbpa_sitedetail ;
delete from egbpa_buildingdetail ;
delete from egbpa_stakeholder_document ;
delete from egbpa_unconsider_checklist ;
delete from egbpa_application_fee ;
delete from egbpa_application_document;

create table temp_workflow_states(
stateId bigint 
);
insert into temp_workflow_states (stateId)(select state_id from egbpa_application where state_id is not null);
delete from egbpa_application ;
delete from egbpa_applicant ;
delete from eg_wf_state_history  where state_id in(select stateId from temp_workflow_states);
delete from eg_wf_states where id in(select stateId from temp_workflow_states );
drop table temp_workflow_states;
delete from egbpa_mstr_chklistdetail where checklist in(select id from EGBPA_MSTR_CHECKLIST where
 checklisttype='DOCUMENTATION');
 
delete from egbpa_mstr_chklistdetail where checklist in(select id from EGBPA_MSTR_CHECKLIST where
 checklisttype='NOC');

delete from EGBPA_MSTR_CHECKLIST where checklisttype in('DOCUMENTATION','NOC');
delete from egbpa_mstr_servicetype ;

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'01','New Construction',true,0,1,now(),null,now(),true,true,true,false,false,false,'PPA',null,null,false);

--2

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'02','Demolition',false,0,1,now(),null,now(),false,false,true,false,false,false,'DA',null,null,false); 

--3

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'03','Reconstruction',true,0,1,now(),null,now(),true,true,true,false,false,false,'REC',null,null,false);  

--4

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'04','Alteration',true,0,1,now(),null,now(),true,true,true,false,false,false,'ALT',null,null,false); 

--5
insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'05','Sub-Division of plot/Land Development',false,0,1,now(),null,now(),false,false,true,false,false,false,'DP',null,null,false); 

--6
insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'06','Adding of Extension',false,0,1,now(),null,now(),false,false,true,false,false,false,'AE',null,null,false); 

--7
insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'07','Change in occupancy',false,0,1,now(),null,now(),false,false,true,false,false,false,'CO',null,null,false); 

--8

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'08','Amenities',false,0,1,now(),null,now(),false,false,true,false,false,false,'CO',null,null,false); 

--9

insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'09','Permission for Temporary hut or shed',true,0,1,now(),null,now(),true,true,true,false,false,false,'PTH',null,null,false);


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'10','Well',true,
0,1,now(),null,now(),false,false,false,false,false,false,'WEL',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'11','Compound Wall',true,
0,1,now(),null,now(),false,false,false,false,false,false,'CMPWELL',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'12','Shutter or Door Conversion/Erection under rule 100 or 101',true,
0,1,now(),null,now(),false,false,false,false,false,false,'STR',null,null,true);


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'13','Roof Conversion under rule 100 or 101',true,
0,1,now(),null,now(),false,false,false,false,false,false,'RFC',null,null,true);


insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'14','Tower Construction',true,
0,1,now(),null,now(),false,false,false,false,false,false,'TC',null,null,true);



insert into egbpa_mstr_servicetype ( id,code,description,isactive, version,createdby,
createddate,lastmodifiedby,lastmodifieddate,buildingplanapproval,siteapproval, 
isapplicationfeerequired,isptisnumberrequired,isautodcrnumberrequired, isdocuploadforcitizen,
servicenumberprefix,descriptionlocal,sla,isAmenity )
values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'15','Pole Structures',true,
0,1,now(),null,now(),false,false,false,false,false,false,'PLS',null,null,true);


----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Demolition'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Alteration'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Adding of Extension'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Change in occupancy'),
0,1,now());



insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Amenities'),
0,1,now());



insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'DOCUMENTATION', (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed'),
0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'101','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'102','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'103',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'104','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'105','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'106','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());



--servicetype 2 deocument list
insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'201','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'202','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'203',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'204','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'205','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'206','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());


---serv 3

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'301','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'302','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'303',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'304','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'305','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'306','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());


--serv 4

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'401','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'402','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'403',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'404','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'405','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'406','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());



--serv 5

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'501','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'502','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'503',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'504','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'505','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'506','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());


--serv 6

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'601','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'602','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'603',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'604','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'605','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'606','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());


--serv 7

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'701','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'702','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'703',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'704','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'705','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'706','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());


--serv 8

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'801','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'802','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'803',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'804','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'805','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'806','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());




--serv 12

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'901','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'902','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'903',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'904','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'905','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'906','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());


---bpa fee for each servicetype 
--admission fees


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'New Construction'),'001','Admission Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Demolition'),'002','Admission Fees',true,true,false,1,now());



INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Reconstruction'),'003','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Alteration'),'004','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Sub-Division of plot/Land Development'),'005','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Adding of Extension'),'006','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Change in occupancy'),'007','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Amenities'),'008','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 
 'AdmissionFee',(select id from egbpa_mstr_servicetype where 
  description=  'Permission for Temporary hut or shed'),'009','Admission Fees',true,true,false,1,now());



--bpafee admission fee for amenities

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Compound Wall'),'010','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Well'),'011','Admission Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Shutter or Door Conversion/Erection under rule 100 or 101'),'012','Admission Fees',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Roof Conversion under rule 100 or 101'),'013','Admission Fees',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Tower Construction'),'014','Admission Fees',true,true,false,1,now());



INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'AdmissionFee',(select id from egbpa_mstr_servicetype where  description=  'Pole Structures'),'015','Admission Fees',true,true,false,1,now());

--bpafee for sanctionfee for main servicetype

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'101','Permit Fees',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'102','Charges for Well',true,true,false,1,now());
 INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'New Construction'),'103','Charges for Compound Wall',true,true,false,1,now());
  
 INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Demolition'),'201','Demolition Fees',true,true,false,1,now());
								

 
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'401','Permit Fees',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Alteration'),'402','Other Charges',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Reconstruction'),'501','Permit Fees',true,true,false,1,now());

INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Sub-Division of plot/Land Development'),'502','Land Development Charges',true,true,false,1,now());
INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Sub-Division of plot/Land Development'),'503','Other Charges',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Adding of Extension'),'601','Permit Fees',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Change in occupancy'),'701','Other Charges',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Amenities'),'801','Other Charges',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Permission for Temporary hut or shed'),'901','Other Charges',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Well'),'1001','Other Charges',true,true,false,1,now());





INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Compound Wall'),'1002','Other Charges',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Shutter or Door Conversion/Erection under rule 100 or 101'),'1003','Other Charges',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Roof Conversion under rule 100 or 101'),'1004','Other Charges',true,true,false,1,now());




INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Tower Construction'),'1005','Other Charges',true,true,false,1,now());


INSERT INTO egbpa_mstr_bpafee (ID, feetype, SERVICETYPE, code, description, isfixedamount,isactive,ismandatory,CREATEDBY, createddate)
 VALUES ((nextval('SEQ_EGBPA_MSTR_BPAFEE'))  , 'SanctionFee',(select id from egbpa_mstr_servicetype where  description=  'Pole Structures'),'1006','Other Charges',true,true,false,1,now());




--bpafeedetail for servicetype admission fees

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='001'),null,null,50,null,now(),1,now()  );



insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='002'),null,null,50,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='003'),null,null,50,null,now(),1,now()  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='004'),null,null,50,null,now(),1,now()  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='005'),null,null,50,null,now(),1,now()  );
insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='006'),null,null,50,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='007'),null,null,50,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='008'),null,null,50,null,now(),1,now()  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='009'),null,null,50,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='010'),null,null,15,null,now(),1,now()  );

insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='011'),null,null,15,null,now(),1,now()  );



insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='012'),null,null,15,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='013'),null,null,15,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='014'),null,null,15,null,now(),1,now()  );


insert into egbpa_mstr_bpafeedetail (id,bpafee,fromareasqmt,toareasqmt,amount,subtype,startdate,CREATEDBY, createddate) values (
nextval('SEQ_EGBPA_MSTR_BPAFEEDETAIL'), (select id from egbpa_mstr_bpafee where code='015'),null,null,15,null,now(),1,now()  );




--------------------------------------------NOC Master Data For building approval----------------------------------------------

--------------------New Construction----------------------
insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'111','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'112','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'113','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'114','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'115','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'116','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'117','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'118','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'119','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

--------------------Demolition----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Demolition'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'211','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'212','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'213','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'214','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'215','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'216','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'217','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'218','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'219','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

--------------------Reconstruction----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Reconstruction'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'311','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'312','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'313','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'314','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'315','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'316','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'317','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'318','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'319','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());



--------------------Alteration----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Alteration'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'411','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'412','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'413','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'414','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'415','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'416','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'417','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'418','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'419','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


--------------------Division Of Plot----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description=  'Sub-Division of plot/Land Development'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'511','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'512','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'513','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'514','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'515','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'516','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'517','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'518','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'519','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

--------------------'Adding of Extension'----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Adding of Extension'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'611','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'612','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'613','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'614','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'615','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'616','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'617','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'618','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'619','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());



--------------------Change in occupancy----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Change in occupancy'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'711','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'712','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'713','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'714','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'715','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'716','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'717','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'718','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'719','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

--------------------'Amenities'----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Amenities'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'811','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'812','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'813','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'814','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'815','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'816','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'817','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'818','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'819','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

---Permission for Temporary hut or shed



insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Permission for Temporary hut or shed'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'911','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'912','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'913','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'914','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'915','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'916','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'917','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'918','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'919','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());


