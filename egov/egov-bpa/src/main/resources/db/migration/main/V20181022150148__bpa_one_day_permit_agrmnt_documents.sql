insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'190','One day permit agreement',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION'
and servicetype= (select id from egbpa_mstr_servicetype where code='01')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'452','One day permit agreement',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION'
and servicetype= (select id from egbpa_mstr_servicetype where code='04')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'652','One day permit agreement',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION'
and servicetype= (select id from egbpa_mstr_servicetype where code='06')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'852','One day permit agreement',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION'
and servicetype= (select id from egbpa_mstr_servicetype where code='08')), 0,1,now());
