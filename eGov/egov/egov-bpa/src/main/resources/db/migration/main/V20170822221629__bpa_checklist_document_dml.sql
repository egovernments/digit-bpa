---------------------------------delete Building Tax Receipt for new construction,land development and hut and shed------------------------------

delete from egbpa_document where applicationdocumentid  in (select id from egbpa_application_document  where checklistdetail in (select id from egbpa_mstr_chklistdetail where code  in ('105','505','905','185','525','925')));

delete from egbpa_application_document where checklistdetail in (select id from egbpa_mstr_chklistdetail where code  in ('105','505','905','185','525','925'));

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='DOCUMENTATION' and servicetype=(select id from egbpa_mstr_servicetype  where code ='01')) and code ='105';

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='DOCUMENTATION' and servicetype=(select id from egbpa_mstr_servicetype  where code ='05')) and code ='505';

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='DOCUMENTATION' and servicetype=(select id from egbpa_mstr_servicetype  where code ='09')) and code ='905';

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='LTP' and servicetype=(select id from egbpa_mstr_servicetype  where code ='01')) and code ='185';

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='LTP' and servicetype=(select id from egbpa_mstr_servicetype  where code ='05')) and code ='525';

delete from egbpa_mstr_chklistdetail where checklist=(select id from egbpa_mstr_checklist where  checklisttype ='LTP' and servicetype=(select id from egbpa_mstr_servicetype  where code ='09')) and code ='925';

--------------add new columns---------------------

alter table egbpa_sitedetail add column workCommencementDate date;

alter table egbpa_sitedetail add column workCompletionDate date;

---------------------------Structural Stability certificate checklist document-------------------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'108','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'208','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'308','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'408','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'608','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'708','Structural Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());
