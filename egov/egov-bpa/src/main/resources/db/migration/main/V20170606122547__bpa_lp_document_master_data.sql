ALTER TABLE egbpa_lettertoparty DROP CONSTRAINT fk_lettertoparty_lpreason;

ALTER TABLE egbpa_lettertoparty DROP COLUMN lpreason; 

CREATE TABLE egbpa_lp_and_reason
(
  lettertoparty bigint NOT NULL,
  lpreason bigint NOT NULL,
  CONSTRAINT fk_egbpa_lp_reason_lpid FOREIGN KEY (lettertoparty)
      REFERENCES egbpa_lettertoparty (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_lp_reason_lpreasonid FOREIGN KEY (lpreason)
      REFERENCES egbpa_mstr_lpreason (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

-------------------Letter To Party checklist documents--------------------

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Demolition'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Reconstruction'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Alteration'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Adding of Extension'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Change in occupancy'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Amenities'),
0,1,now());

insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'LTP', (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed'),
0,1,now());


-----------------------service type 1 LTP document list-----------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'181','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'182','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'183',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'184','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'185','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'186','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'187','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

-----------------------service type 2 LTP document list-----------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'221','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'222','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'223',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'224','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'225','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'226','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'227','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Demolition')), 0,1,now());


--------------------service type 3 LTP document list--------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'321','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'322','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'323',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'324','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'325','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'326','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'327','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Reconstruction')), 0,1,now());

-----------------------service type 4 LTP document list-----------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'421','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'422','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'423',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'424','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'425','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'426','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'427','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Alteration')), 0,1,now());

--------------------service type 5 LTP document list--------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'521','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'522','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'523',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'524','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'525','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'526','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'527','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Sub-Division of plot/Land Development')), 0,1,now());


----------------------service type 6 LTP document list------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'621','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'622','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'623',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'624','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'625','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'626','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'627','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Adding of Extension')), 0,1,now());


---------------------service type 7 LTP document list-------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'721','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'722','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'723',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'724','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'725','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'726','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'727','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Change in occupancy')), 0,1,now());


---------------------service type 8 LTP document list-------------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'821','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'822','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'823',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'824','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'825','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'826','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'827','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Amenities')), 0,1,now());


------------------------service type 9 LTP document list----------------------

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'921','Title Deed of the Property',true,false,
(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,
checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'922','Possession Certificate',true,
false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,
version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'923',
'Land Tax Receipt',true,false,(select id from EGBPA_MSTR_CHECKLIST where 
checklisttype='LTP' and servicetype= (select id from egbpa_mstr_servicetype 
where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'924','Location Sketch',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and servicetype= 
(select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'925','Building Tax Receipt',true,false ,(
select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' and 
servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'926','Layout Approval Copy',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'927','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='LTP' 
and servicetype= (select id from egbpa_mstr_servicetype where description='Permission for Temporary hut or shed')), 0,1,now());

