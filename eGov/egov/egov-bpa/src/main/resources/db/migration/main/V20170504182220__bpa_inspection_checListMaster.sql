insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONAREALOC',NULL,
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONLENGTHOFCOMPOUNDWALL',NULL,
0,1,now());




insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONERECTIONOFTOWER',NULL,
0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONNUMBEROFWELLS',NULL,
0,1,now());




insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONSHUTTER',NULL,
0,1,now());




insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'INSPECTIONROOFCONVERSION',NULL,
0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'151',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONAREALOC' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'152',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONAREALOC' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'153',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONAREALOC' ), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'154',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLENGTHOFCOMPOUNDWALL' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'155',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLENGTHOFCOMPOUNDWALL' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'156',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONLENGTHOFCOMPOUNDWALL' ), 0,1,now());






insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'157',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONNUMBEROFWELLS' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'158',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONNUMBEROFWELLS' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'159',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONNUMBEROFWELLS' ), 0,1,now());






insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'161',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONERECTIONOFTOWER' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'162',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONERECTIONOFTOWER' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'163',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONERECTIONOFTOWER' ), 0,1,now());









insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'164',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSHUTTER' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'165',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSHUTTER' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'166',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONSHUTTER' ), 0,1,now());







insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'167',
'As per site plan T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONROOFCONVERSION' ), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'168',
'As per Document:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONROOFCONVERSION' ), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'169',
'As per Site Inspection:T/F',true,false,(select id from EGBPA_MSTR_CHECKLIST 
where checklisttype='INSPECTIONROOFCONVERSION' ), 0,1,now());


