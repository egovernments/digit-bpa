-----------------OC LP Checklist documents------------------------------------------

---New Construction---

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCLTPDOCUMENTS', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-01','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-02','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-03','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-04','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-05','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-06','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-07','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-08','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

---Reconstruction---

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCLTPDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-21','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-22','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-23','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-24','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-25','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-26','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-27','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-28','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

-------Alteration-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCLTPDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Alteration'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-41','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-42','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-43','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-44','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-45','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-46','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-47','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-48','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

-------Addition or Extension-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCLTPDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Addition or Extension'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-61','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-62','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-63','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-64','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-65','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-66','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-67','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-68','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

-------Change in occupancy-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCLTPDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Change in occupancy'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-81','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-82','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-83','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-84','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-85','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-86','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-87','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCLTP-88','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCLTPDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());