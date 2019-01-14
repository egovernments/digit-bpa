insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where code='06'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-61','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-62','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-63','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-64','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-65','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-66','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());
