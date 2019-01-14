insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Demolition'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-11','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-12','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-13','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-14','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-15','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-16','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-21','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-22','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-23','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-24','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-25','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-26','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Alteration'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-31','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-32','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-33','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-34','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-35','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-36','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Change in occupancy'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-41','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-42','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-43','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-44','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-45','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-46','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());
