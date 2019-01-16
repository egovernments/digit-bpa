update egbpa_stakeholder_document set checklistdetail = (select id from EGBPA_MSTR_CHKLISTDETAIL where code ='RGSTRTNCER' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT')) where checklistdetail = (select id from EGBPA_MSTR_CHKLISTDETAIL where code ='CPYATTSTD' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT'));

delete from EGBPA_MSTR_CHKLISTDETAIL where code ='CPYATTSTD' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

update EGBPA_MSTR_CHKLISTDETAIL set description='Aadhaar card of the licensee - copy attested by a gazetted officer, including office seal
attested within one month prior to the date of application, to be scanned and uploaded' where code ='ADHRCRD' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

update EGBPA_MSTR_CHKLISTDETAIL set description='Registration certificate of the licensee – copy attested by a gazette officer, including office
seal,attested within one month prior to the date of application, to be scanned and uploaded.
( including renewal certificate if any)' where code ='RGSTRTNCER' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

update EGBPA_MSTR_CHKLISTDETAIL set description='Recent passport size photo - Taken within six months prior to the date of application shall
be uploaded – please ensure that the photo uploaded is of passport size and gives a front
and clear view of the face.' where code ='RCNTPHTO' and checklist=(select id from EGBPA_MSTR_CHECKLIST where checklisttype='STAKEHOLDERDOCUMENT');

alter table egbpa_mstr_stakeholder add column status character varying(30);

update egbpa_mstr_stakeholder set status ='SUBMITTED' where isactive = false and source is not null  and comments is null;

update egbpa_mstr_stakeholder set status ='APPROVED' where isactive = true;

update egbpa_mstr_stakeholder set status ='REJECTED' where isactive = false and source is not null and comments is not null;