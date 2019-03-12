delete from egbpa_checklist_servicetype_mapping where servicetype not in (select id from egbpa_mstr_servicetype where code in ('01','07','03','04','06')) and 
checklist in (select id from eg_checklist where checklisttypeid in (select id from eg_checklist_type where code in ('OCPLANSCRUTINYDRAWING','OCGENERALDOCUMENTS',
'OCLTPDOCUMENTS','OCPLANSCRUTINYRULE','OCDCRDOCUMENTS','OCNOC','OCINSPECTION')));
