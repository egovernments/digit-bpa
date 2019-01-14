alter table egbpa_documentscrutiny alter column subdivisionnumber type character varying(128);
alter table egbpa_documentscrutiny alter column resurveynumber type character varying(128);

--------------------Tower Construction----------------------
insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description='Tower Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1091','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1092','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1093','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1094','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1095','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1096','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1097','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1098','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1099','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

--------------------Pole Structures----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description='Pole Structures'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1101','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1102','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1103','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1104','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1105','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1106','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1107','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1108','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1109','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());
