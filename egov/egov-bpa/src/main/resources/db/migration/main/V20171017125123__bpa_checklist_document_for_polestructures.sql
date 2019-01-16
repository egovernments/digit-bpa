------------------------tower structure------------------------------------------------------

update EGBPA_MSTR_CHKLISTDETAIL set ismandatory = false where code ='1008';

update EGBPA_MSTR_CHKLISTDETAIL set ismandatory = false where code ='1009';

update EGBPA_MSTR_CHKLISTDETAIL set ismandatory = false where code ='1010';

------------------------pole strctures checklist documents-------------------- 

update EGBPA_MSTR_CHKLISTDETAIL set description = 'EMF certification for radiation compliance',ismandatory = true where code ='2001';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'SACFA – Application form for full sitting category –full site (BWA)',ismandatory = true where code ='2002';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Stability certificate',ismandatory = true where code ='2003';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Deed of lease',ismandatory = true where code ='2004';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Document',ismandatory = true where code ='2005';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Registration certificate for infrastructure provider category -1 (IP-1)(Govt. of India)',ismandatory = true where code ='2006';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'ARAI certificate – The automotive research association of India',ismandatory = true where code ='2007';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Consent letter',ismandatory = false where code ='2008';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Building tax Receipt (if the pole is located on top of a building)',ismandatory = false where code ='2009';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Land tax Receipt',ismandatory = false where code ='2010';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Possession Certificate',ismandatory = true where code ='2011';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2012','Declaration by the Applicant',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2013','Specification Report',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2014','Certificates and Undertaking by LBE/LBS',true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2015','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

------------------------pole structures LTP checklist documents--------------------

update EGBPA_MSTR_CHKLISTDETAIL set description = 'EMF certification for radiation compliance',ismandatory = false where code ='2031';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'SACFA – Application form for full sitting category –full site (BWA)',ismandatory = true where code ='2032';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Stability certificate',ismandatory = false where code ='2033';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Deed of lease',ismandatory = false where code ='2034';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Document',ismandatory = false where code ='2035';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Registration certificate for infrastructure provider category -1 (IP-1)(Govt. of India)',ismandatory = false where code ='2036';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'ARAI certificate – The automotive research association of India',ismandatory = false where code ='2037';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Consent letter',ismandatory = false where code ='2038';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Building tax Receipt (if the pole is located on top of a building)',ismandatory = false where code ='2039';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Land tax Receipt',ismandatory = false where code ='2040';


insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2041','Possession Certificate',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2042','Declaration by the Applicant',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2043','Specification Report',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2044','Certificates and Undertaking by LBE/LBS',true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'2045','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Pole Structures')), 0,1,now());


-----------------modify permit fee ddl----------------

ALTER TABLE EGBPA_APPLICATION_FEE ADD COLUMN modifyFeeReason character varying(512);
