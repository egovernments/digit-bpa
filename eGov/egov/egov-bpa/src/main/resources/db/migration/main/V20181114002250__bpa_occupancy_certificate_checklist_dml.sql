--------------------- Occupancy certificate General Documents -------------------------------

--- document list - by service --

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-01','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-02','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-03','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-04','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-05','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-06','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-07','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-08','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());


--Demolition service

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description= 'Demolition'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-21','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-22','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-23','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-24','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-25','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-26','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-27','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-28','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

---Reconstruction---

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-41','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-42','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-43','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-44','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-45','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-46','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-47','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-48','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

-------Alteration-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Alteration'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-61','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-62','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-63','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-64','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-65','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-66','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-67','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-68','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


-------Sub-Division of plot/Land Development-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Sub-Division of plot/Land Development'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-81','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-82','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-83','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-84','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-85','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-86','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-87','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-88','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

-------Addition or Extension-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Addition or Extension'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-101','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-102','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-103','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-104','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-105','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-106','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-107','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-108','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

-------Change in occupancy-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Change in occupancy'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-121','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-122','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-123','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-124','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-125','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-126','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-127','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-128','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

-------Amenities-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Amenities'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-151','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-152','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-153','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-154','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-155','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-156','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-157','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-158','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

-------Huts and Sheds-------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCGENERALDOCUMENTS', (select id from egbpa_mstr_servicetype where description=  'Huts and Sheds'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-171','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-172','Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-173','Floor plans',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-174','Elevations',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-175','Site plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-176','Service plan',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-177','Sections',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL(id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCDOC-178','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCGENERALDOCUMENTS' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

--------------------------------------------Occupancy certificate NOC Master Data----------------------------------------------

--------------------New Construction----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-01','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-02','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-03','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-04','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-05','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-06','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-07','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-08','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-09','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

--------------------Demolition----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Demolition'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-21','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-22','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-23','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-24','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-25','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-26','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-27','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-28','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-29','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

--------------------Reconstruction----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Reconstruction'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-41','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-42','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-43','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-44','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-45','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-46','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-47','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-48','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-49','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

--------------------Alteration----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Alteration'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-61','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-62','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-63','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-64','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-65','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-66','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-67','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-68','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-69','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());


--------------------Sub-Division of plot/Land Development----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description=  'Sub-Division of plot/Land Development'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-81','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-82','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-83','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-84','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-85','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-86','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-87','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-88','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-89','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

--------------------Addition or Extension----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Addition or Extension'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-101','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-102','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-103','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-104','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-105','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-106','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-107','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-108','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-109','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

--------------------Change in occupancy----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Change in occupancy'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-121','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-122','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-123','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-124','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-125','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-126','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-127','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-128','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-129','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

--------------------Amenities----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Amenities'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-141','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-142','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-143','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-144','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-145','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-146','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-147','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-148','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-149','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

--------------------Huts and Sheds----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Huts and Sheds'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-161','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-162','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-163','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-164','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-165','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-166','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-167','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-168','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-169','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

--------------------Tower Construction----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Tower Construction'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-181','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-182','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-183','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-184','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-185','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-186','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-187','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-188','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-189','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

--------------------Pole Structures----------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'OCNOC', (select id from egbpa_mstr_servicetype where description= 'Pole Structures'),0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-201','Port Trust',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-202','Railways',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-203','RTP/CTP – Layout Concurrence',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-204','Pollution Control Board',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-205','Fire Services',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-206','CRZ Clearance',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-207','Art & Heritage Commission',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-208','Forest Department',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'OCNOC-209','Others if any',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='OCNOC' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());