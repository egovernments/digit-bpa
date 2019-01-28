-------------Occupancy certificate dcr documents-----------------------------------

----New Construction------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCDCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-01','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-02','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-03','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-04','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-05','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-06','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

-------------Reconstruction---------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCDCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-21','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-22','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-23','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-24','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-25','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-26','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

-------------Alteration---------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCDCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Alteration'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-41','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-42','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-43','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-44','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-45','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-46','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


-------------Addition or Extension---------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCDCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Addition or Extension'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-61','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-62','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-63','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-64','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-65','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-66','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());


-------------Change in occupancy---------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCDCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='Change in occupancy'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-81','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-82','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-83','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-84','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-85','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-OC-86','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCDCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());


------------update oc general documents------------------------

-----New Construction-------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCDOC-03';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCDOC-04';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCDOC-05';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCDOC-06';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCDOC-07';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCDOC-08';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-09','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-10','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-11','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

-----Reconstruction---------------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCDOC-43';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCDOC-44';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCDOC-45';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCDOC-46';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCDOC-47';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCDOC-48';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-49','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-50','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-51','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());


-----Alteration---------------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCDOC-63';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCDOC-64';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCDOC-65';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCDOC-66';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCDOC-67';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCDOC-68';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-69','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-70','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-71','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


-----Addition or Extension---------------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCDOC-103';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCDOC-104';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCDOC-105';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCDOC-106';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCDOC-107';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCDOC-108';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-109','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-110','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-111','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());


-----Change in occupancy---------------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCDOC-123';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCDOC-124';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCDOC-125';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCDOC-126';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCDOC-127';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCDOC-128';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-129','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-130','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-131','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());