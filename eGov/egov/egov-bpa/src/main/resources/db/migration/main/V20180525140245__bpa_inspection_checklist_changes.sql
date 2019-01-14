update egbpa_docket_detail set value ='YES' where value='true';
update egbpa_docket_detail set value ='NO' where value='false';

update EGBPA_MSTR_CHKLISTDETAIL set description='Whether as per submitted plan (Subjected to Rule 10, 17(2) and 22(3))' where code ='139' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONWORKCOMPLETEDPERPLAN');

delete from egbpa_docket_detail where checklistdetail =(select id from EGBPA_MSTR_CHKLISTDETAIL where code ='125' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONMEASUREMENT'));

delete from egbpa_docket_detail where checklistdetail =(select id from EGBPA_MSTR_CHKLISTDETAIL where code ='134' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONTYPEOFLAND'));

delete from egbpa_docket_detail where checklistdetail =(select id from EGBPA_MSTR_CHKLISTDETAIL where code ='152' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONAREALOC'));

delete from egbpa_docket_detail where checklistdetail =(select id from EGBPA_MSTR_CHKLISTDETAIL where code ='168' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONROOFCONVERSION'));

delete from EGBPA_MSTR_CHKLISTDETAIL where code ='125' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONMEASUREMENT');

delete from EGBPA_MSTR_CHKLISTDETAIL where code ='134' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONTYPEOFLAND');

delete from EGBPA_MSTR_CHKLISTDETAIL where code ='152' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONAREALOC');

delete from EGBPA_MSTR_CHKLISTDETAIL where code ='168' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='INSPECTIONROOFCONVERSION');