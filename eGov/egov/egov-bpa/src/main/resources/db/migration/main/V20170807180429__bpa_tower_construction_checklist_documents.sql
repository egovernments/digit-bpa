------------------------tower construction checklist documents-------------------- 

update EGBPA_MSTR_CHKLISTDETAIL set description = 'EMF certification for radiation compliance',ismandatory = true where code ='1001';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'SACFA – Application form for full sitting category –full site (BWA)',ismandatory = true where code ='1002';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Stability certificate',ismandatory = true where code ='1003';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Deed of lease',ismandatory = true where code ='1004';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Document',ismandatory = true where code ='1005';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Registration certificate for infrastructure provider category -1 (IP-1)(Govt. of India)',ismandatory = true where code ='1006';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'ARAI certificate – The automotive research association of India',ismandatory = true where code ='1007';

update EGBPA_MSTR_CHKLISTDETAIL set description = 'Consent letter',ismandatory = true where code ='1008';

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1009','Building tax Receipt (if the tower is located on top of a building)',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1010','Land tax Receipt',true,true ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'1011','Possession Certificate',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='DOCUMENTATION' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Tower Construction')), 0,1,now());