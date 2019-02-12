---------------------------OC LTP DOCUMENTS----------------------------------
----------------NEW CONSTRUCTION-------------------

update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents' where code='OCLTP-03';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCLTP-04';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document' where code='OCLTP-05';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property' where code='OCLTP-06';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report' where code='OCLTP-07';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt' where code='OCLTP-08';


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-09','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-10','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-11','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());


------------------------Addition or Extension--------------------

update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-101' where code='OCLTP-61';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-102' where code='OCLTP-62';
update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents',code='OCLTP-103' where code='OCLTP-63';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated',code='OCLTP-104' where code='OCLTP-64';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document',code='OCLTP-105' where code='OCLTP-65';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property',code='OCLTP-106' where code='OCLTP-66';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report',code='OCLTP-107' where code='OCLTP-67';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt',code='OCLTP-108' where code='OCLTP-68';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-109','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-110','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-111','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());


----------------------Alteration----------------------

update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-61' where code='OCLTP-41';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-62' where code='OCLTP-42';
update EGBPA_MSTR_CHKLISTDETAIL set description='Other documents',code='OCLTP-63' where code='OCLTP-43';
update EGBPA_MSTR_CHKLISTDETAIL set description='Completion certificate in prescribed format duly signed/ authenticated',code='OCLTP-64' where code='OCLTP-44';
update EGBPA_MSTR_CHKLISTDETAIL set description='Copy of land deed document',code='OCLTP-65' where code='OCLTP-45';
update EGBPA_MSTR_CHKLISTDETAIL set description='Title deed of the property',code='OCLTP-66' where code='OCLTP-46';
update EGBPA_MSTR_CHKLISTDETAIL set description='Specification report',code='OCLTP-67' where code='OCLTP-47';
update EGBPA_MSTR_CHKLISTDETAIL set description='Building tax receipt',code='OCLTP-68' where code='OCLTP-48';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-69','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-70','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-71','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


----------------------Reconstruction--------------------------


update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-41' where code='OCLTP-21';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-42' where code='OCLTP-22';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-43', description='Other documents' where code='OCLTP-23';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-44',description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCLTP-24';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-45',description='Copy of land deed document' where code='OCLTP-25';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-46',description='Title deed of the property' where code='OCLTP-26';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-47',description='Specification report' where code='OCLTP-27';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-48',description='Building tax receipt' where code='OCLTP-28';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-49','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-50','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-51','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

-----------------------Change in occupancy----------------------------------

update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-121' where code='OCLTP-81';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-122' where code='OCLTP-82';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-123', description='Other documents' where code='OCLTP-83';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-124',description='Completion certificate in prescribed format duly signed/ authenticated' where code='OCLTP-84';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-125',description='Copy of land deed document' where code='OCLTP-85';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-126',description='Title deed of the property' where code='OCLTP-86';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-127',description='Specification report' where code='OCLTP-87';
update EGBPA_MSTR_CHKLISTDETAIL set code='OCLTP-128',description='Building tax receipt' where code='OCLTP-88';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-129','Layout approval copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-130','Location sketch / Village sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-131','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());
