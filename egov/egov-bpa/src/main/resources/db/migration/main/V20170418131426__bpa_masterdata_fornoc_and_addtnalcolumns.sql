--------------------------------------------NOC Master Data For building approval----------------------------------------------

--------------------New Construction----------------------
insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1001','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1002','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1003','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1004','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1005','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1006','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1007','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1008','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1009','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

--------------------Demolition----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Demolition'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1010','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1011','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1012','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1013','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1014','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1015','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1016','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1017','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1018','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

--------------------Reconstruction----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Reconstruction'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1019','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1020','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1021','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1022','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1023','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1024','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1025','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1026','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1027','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

--------------------Division Of Plot----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description=  'Division Of Plot'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1028','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1029','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1030','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1031','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1032','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1033','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1034','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1035','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1036','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Division Of Plot')), 0,1,now());

--------------------Alteration----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Alteration'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1037','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1038','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1039','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1040','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1041','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1042','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1043','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1044','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1045','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

--------------------Additional or Extension----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Additional or Extension'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1046','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1047','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1048','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1049','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1050','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1051','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1052','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1053','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1054','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Additional or Extension')), 0,1,now());

--------------------Digging of Well----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Digging of Well'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1055','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1056','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1057','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1058','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1059','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1060','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1061','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1062','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1063','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Digging of Well')), 0,1,now());

--------------------Change in occupancy----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Change in occupancy'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1064','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1065','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1066','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1067','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1068','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1069','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1070','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1071','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1072','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

--------------------Erection of Telecommunication tower or other structure----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'NOC', (select id from egbpa_mstr_servicetype where description= 'Erection of Telecommunication tower or other structure'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1073','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1074','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1075','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1076','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1077','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1078','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1079','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1080','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1081','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='NOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Erection of Telecommunication tower or other structure')), 0,1,now());

---------------roleaction for download bpa documents---------------------

Insert into EG_ACTION (id,name,url,queryparams,parentmodule,ordernumber,displayname,enabled,contextroot,version,createdby,createddate,lastmodifiedby,lastmodifieddate,application) 
values (nextval('SEQ_EG_ACTION'),'Download BPA Documents','/application/downloadfile',null,(select id from eg_module where name='BPA Transanctions'),13,'Download BPA Documents','false','bpa',0,1,now(),1,now(),
(select id from eg_module where name='BPA'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='ULB Operator'),(select id from eg_action where name='Download BPA Documents'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Collection Operator'), (select id from eg_action where name='Download BPA Documents'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='BPA Approver'), (select id from eg_action where name='Download BPA Documents'));

Insert into eg_roleaction (roleid,actionid) values ((select id from eg_role where name='Super User'), (select id from eg_action where name='Download BPA Documents'));


alter  table EGBPA_APPOINTMENT_SCHEDULE alter column purpose type bigint USING purpose::bigint;

--------------------added additional columns for noc------------
ALTER TABLE egbpa_NOC_Document ALTER COLUMN remarks TYPE character varying(512);
alter table egbpa_NOC_Document add column nocfilestore bigint;
alter table egbpa_NOC_Document add column natureOfRequest character varying(512);
alter table egbpa_NOC_Document add column letterSentOn timestamp without time zone;
alter table egbpa_NOC_Document add column replyReceivedOn timestamp without time zone;
alter table egbpa_NOC_Document add column rejection boolean default false;
alter table egbpa_NOC_Document add column notApplicable boolean default false;