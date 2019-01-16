
delete from egbpa_docket_detail ;

delete from egbpa_docket ;

delete from egbpa_inspection;

delete from egbpa_mstr_chklistdetail where checklist in(select id from egbpa_mstr_checklist where checklisttype='INSPECTION') ;

delete from egbpa_mstr_checklist where checklisttype='INSPECTION';

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONLOCATION',NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONMEASUREMENT', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONACCESS', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONSURROUNDING', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONTYPEOFLAND', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONPROPOSEDSTAGEWORK', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONWORKCOMPLETEDPERPLAN', NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONHGTBUILDABUTROAD', NULL,
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'121',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLOCATION' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'122',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLOCATION' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'123',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLOCATION' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'124',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONMEASUREMENT'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'125',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONMEASUREMENT'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'126',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONMEASUREMENT'), 0,1,now());


--


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'127',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONACCESS' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'128',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONACCESS'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'129',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONACCESS'), 0,1,now());


---



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'130',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSURROUNDING'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'132',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSURROUNDING' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'133',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSURROUNDING' ), 0,1,now());

--





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'134',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONTYPEOFLAND' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'135',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONTYPEOFLAND' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'136',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONTYPEOFLAND'), 0,1,now());


--




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'137',
'Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONPROPOSEDSTAGEWORK' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'138',
'Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONPROPOSEDSTAGEWORK' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'149',
'Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONPROPOSEDSTAGEWORK'), 0,1,now());



--

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'139',
'Whether as per submitted plan (Subjected to Rule 10, 17(2)iii and 22(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONWORKCOMPLETEDPERPLAN'), 0,1,now());


--

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'140',
'Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'141',
'Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'142',
'Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD'), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'145',
'Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD' ), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'146',
'Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD' ), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'147',
'Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD'), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'148',
'Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONHGTBUILDABUTROAD'), 0,1,now());

