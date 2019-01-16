update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-05';
update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-15';
update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-25';
update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-35';
update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-45';
update egbpa_mstr_chklistdetail set description ='Roof Plan',ismandatory=false where code='DCR-65';

update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-06';
update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-16';
update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-26';
update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-36';
update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-46';
update egbpa_mstr_chklistdetail set description ='Other Details',ismandatory=false where code='DCR-66';

update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-03';
update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-13';
update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-23';
update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-33';
update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-43';
update egbpa_mstr_chklistdetail set ismandatory=false where code='DCR-63';

update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-02';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-12';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-22';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-32';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-42';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-62';

update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-01';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-11';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-21';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-31';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-41';
update egbpa_mstr_chklistdetail set ismandatory=true where code='DCR-61';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-07','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-17','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-27','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-37','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-47','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-67','Floor Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());



insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-08','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-18','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-28','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-38','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-48','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-68','Details Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-09','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-19','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-29','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-39','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-49','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-69','Section Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-10','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-20','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-30','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-40','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-50','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-70','Elevation Plans',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());