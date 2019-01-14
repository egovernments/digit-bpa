update egbpa_mstr_servicetype set description  ='Addition or Extension' where code ='06';

update EGP_PORTALSERVICE set name ='Addition or Extension' where code ='CREATEAOE';

update EGBPA_MSTR_CHKLISTDETAIL set description ='Location Sketch and Village Sketch' where description='Location Sketch';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'105','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'109','Certificates and Undertaking by LBE/LBS
',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'110','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'209','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'210','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'251','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'309','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'310','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'351','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'409','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'410','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'451','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'509','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'510','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'551','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'609','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'610','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'651','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'709','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'710','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'751','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'809','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'810','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'851','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'909','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'910','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'951','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1012','Specification Report',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1013','Certificates and Undertaking by LBE/LBS',true,true ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1014','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2009','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2010','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2011','Others',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());



------------------------tower construction LTP checklist documents-------------------- 

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Tower Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1031','EMF certification for radiation compliance',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1032','SACFA – Application form for full sitting category –full site (BWA)',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1033',
'Stability certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1034','Deed of lease',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1035','Document',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1036','Registration certificate for infrastructure provider category -1 (IP-1)(Govt. of India)',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1037','ARAI certificate – The automotive research association of India',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1038','Consent letter',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1039','Building tax Receipt (if the tower is located on top of a building)',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1040','Land tax Receipt',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1041','Possession Certificate',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1042','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1043','Declaration by the Applicant',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1044','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1045','Certificates and Undertaking by LBE/LBS',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

------------------------pole structures LTP checklist documents--------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Pole Structures'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2031','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2032','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2033',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2034','Location Sketch and Village Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2035','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2036','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2037','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2038','Declaration by the Applicant',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2039','Specification Report',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2040','Certificates and Undertaking by LBE/LBS',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

-------------other new LTP documents------------------------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'188','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'189','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'228','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'229','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'328','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'329','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'428','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'429','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'528','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'529','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'628','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'629','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Addition or Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'728','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'729','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'828','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'829','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'928','Specification Report',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'929','Certificates and Undertaking by LBE/LBS',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Huts and Sheds')), 0,1,now());

