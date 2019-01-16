delete from egbpa_stakeholder_support_documents where stakeholderdocumentid in (select id from egbpa_stakeholder_document where checklistdetail =(select id from EGBPA_MSTR_CHKLISTDETAIL where code='LICCERT' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT')));

delete from egbpa_stakeholder_document where checklistdetail in (select id from EGBPA_MSTR_CHKLISTDETAIL where code='LICCERT' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT'));

update EGBPA_MSTR_CHKLISTDETAIL set description='Aadhaar card of the licensee - copy attested by a gazetted officer, attested within one month prior to the date of application, to be scanned and uploaded' where code ='ADHRCRD' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

update EGBPA_MSTR_CHKLISTDETAIL set description='Copy attested by a gazetted officer attested within one month prior to the date of application, to be scanned and uploaded(License Copy)' where code ='CPYATTSTD' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

delete from EGBPA_MSTR_CHKLISTDETAIL where code='LICCERT' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');