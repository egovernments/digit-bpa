
insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONLOCATION',NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONMEASUREMENT', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONACCESS', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONSURROUNDING', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONTYPEOFLAND', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONPROPOSEDSTAGEWORK', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONWORKCOMPLETEDPERPLAN', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONHGTBUILDABUTROAD', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONAREALOC', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONLENGTHOFCOMPOUNDWALL', NULL,0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate) values (nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCINSPECTIONNUMBEROFWELLS', NULL,0,1,now());

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-01', 'As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONLOCATION'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-02', 'As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONLOCATION'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-03', 'As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONMEASUREMENT'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-04', 'Access to Plot As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONACCESS'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-05', 'Access to Plot As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONACCESS'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-06', 'Surrounding of Plot As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONSURROUNDING'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-07', 'Surrounding of Plot As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONSURROUNDING'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-08', 'As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONTYPEOFLAND'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-09', 'Work not started:Y/N', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONPROPOSEDSTAGEWORK'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-10', 'Work started:Y/N', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONPROPOSEDSTAGEWORK'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-11', 'Work Completed:Y/N', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONPROPOSEDSTAGEWORK'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-12', 'Whether as per submitted plan (Subjected to Rule 10, 17(2) and 22(3))', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONWORKCOMPLETEDPERPLAN'), 0, 1, '2017-05-05 11:19:44.270074', NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-13', 'Violation of Rule 23(1)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-14', 'Violation of Rule 23(2)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-15', 'Violation of Rule 23(3)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-16', 'Violation of Rule 23(4)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-17', 'Violation of Rule 23(4a)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-18', 'Violation of Rule 23(5)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-19', 'Violation of Rule 11(A)', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONHGTBUILDABUTROAD'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-20', 'As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONAREALOC'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-21', 'As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONLENGTHOFCOMPOUNDWALL'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-22', 'As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONLENGTHOFCOMPOUNDWALL'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-23', 'As per site plan T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONNUMBEROFWELLS'), 0, 1, now(), NULL, NULL);

INSERT INTO egbpa_mstr_chklistdetail VALUES (nextval('SEQ_EGBPA_MSTR_CHECKLIST'), 'OCINSPN-24', 'As per Document:T/F', true, false, (select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCINSPECTIONNUMBEROFWELLS'), 0, 1, now(), NULL, NULL);


