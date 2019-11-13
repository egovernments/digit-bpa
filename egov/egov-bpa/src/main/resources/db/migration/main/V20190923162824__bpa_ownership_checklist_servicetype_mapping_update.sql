Update egbpa_checklist_servicetype_mapping set ismandatory = 'true'  where checklist in 
(select id from eg_checklist where  checklisttypeid = (select id from eg_checklist_type  where code='OWNERSHIPDOCUMENTS') and 
description not in ('Other'));