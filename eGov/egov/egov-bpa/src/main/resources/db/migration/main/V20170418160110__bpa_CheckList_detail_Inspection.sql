insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='01'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='02'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='03'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='04'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='05'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='06'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='07'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='08'),
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTION', 
(select id from egbpa_mstr_servicetype where   code='09'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'121',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'122',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'123',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'124',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'125',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'126',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'127',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'128',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'129',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'130',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'132',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'133',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'134',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'135',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'136',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='01')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'137',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'138',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'139',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'140',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'141',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'142',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'145',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'146',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'147',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'148',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='01')), 0,1,now());


-----serviceTYpe 2



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'221',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'222',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'223',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'224',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'225',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'226',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'227',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'228',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'229',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'230',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'232',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'233',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'234',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'235',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'236',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='02')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'237',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'238',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'239',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'240',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'241',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'242',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'245',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'246',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'247',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'248',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='02')), 0,1,now());


--serviceType 3


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'321',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'322',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'323',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'324',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'325',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'326',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'327',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'328',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'329',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'330',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'332',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'333',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'334',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'335',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'336',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where    code='03')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'337',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'338',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'339',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'340',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'341',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'342',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'345',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'346',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'347',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'348',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where   code='03')), 0,1,now());


--servcieType 4



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'421',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'422',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'423',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'424',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'425',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'426',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'427',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'428',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'429',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'430',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'432',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'433',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'434',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'435',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'436',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where  code='04')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'437',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'438',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'439',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'440',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'441',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'442',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'445',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'446',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'447',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'448',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());



--serviceType 5



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'521',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'522',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'523',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'524',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'525',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'526',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'527',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'528',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'529',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'530',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'532',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'533',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'534',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'535',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'536',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'537',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'538',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'539',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'540',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'541',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'542',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'545',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'546',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'547',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'548',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='05')), 0,1,now());



--serviceType 6


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'621',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'622',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'623',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'624',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'625',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'626',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'627',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'628',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'629',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'630',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'632',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'633',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'634',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'635',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'636',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'637',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'638',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'639',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'640',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'641',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'642',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'645',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'646',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'647',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'648',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='06')), 0,1,now());


--servicetype 7


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'721',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'722',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'723',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'724',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'725',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'726',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'727',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'728',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'729',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'730',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'732',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'733',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'734',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'735',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'736',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'737',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'738',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'739',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'740',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'741',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'742',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'745',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'746',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'747',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'748',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='07')), 0,1,now());


--servicetype 8


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'821',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'822',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'823',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'824',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'825',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'826',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'827',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'828',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'829',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'830',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'832',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'833',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'834',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'835',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'836',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'837',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'838',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'839',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'840',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'841',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'842',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'845',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'846',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'847',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'848',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='08')), 0,1,now());


--servicetype 9



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'921',
'Location Of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'922',
'Location Of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'923',
'Location Of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='04')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'924',
'Measurement of the Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'925',
'Measurement of the Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'926',
'Measurement of the Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'927',
'Access to Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'928',
'Access to Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'929',
'Access to Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'930',
'Surrounding of Plot As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'932',
'Surrounding of Plot As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'933',
'Surrounding of Plot As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'934',
'Type of Land As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'935',
'Type of Land As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'936',
'Type of Land As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());





insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'937',
'Stages of  Proposed Work not started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'938',
'Stages of  Proposed Work started:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'939',
'Stages of  Proposed Work Completed:Y/N',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());




insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'940',
'Height of building from the abutting road Violation of Rule 23(1)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'941',
'Height of building from the abutting road Violation of Rule 23(2)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'942',
'Height of building from the abutting road Violation of Rule 23(3)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'945',
'Height of building from the abutting road Violation of Rule 23(4)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'946',
'Height of building from the abutting road Violation of Rule 23(4a)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'947',
'Height of building from the abutting road Violation of Rule 23(5)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'948',
'Height of building from the abutting road Violation of Rule 11(A)',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTION' and servicetype= 
(select id from egbpa_mstr_servicetype where code='09')), 0,1,now());


