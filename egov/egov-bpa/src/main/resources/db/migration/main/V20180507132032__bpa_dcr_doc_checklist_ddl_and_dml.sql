insert into EGBPA_MSTR_CHECKLIST(id,checklisttype,servicetype,version,createdBy,createdDate)
values(nextval('SEQ_EGBPA_MSTR_CHECKLIST'),'BPADCRDOCUMENTS', (select id from egbpa_mstr_servicetype where description='New Construction'),
0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-01','Site Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-02','Service Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-03','Building Plan',
true,true,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-04','Parking Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-05','Terrace Plan',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());

insert into EGBPA_MSTR_CHKLISTDETAIL (id ,code,description,isactive,ismandatory,checklist,version,createdBy,createdDate)
values((nextval('SEQ_EGBPA_MSTR_CHKLISTDETAIL')),'DCR-06','Others',
true,false,(select id from EGBPA_MSTR_CHECKLIST where checklisttype='BPADCRDOCUMENTS'
and servicetype= (select id from egbpa_mstr_servicetype where description='New Construction')), 0,1,now());


CREATE TABLE egbpa_application_dcr_document
(
  id bigint NOT NULL,
  checklistDtl bigint NOT NULL,
  application bigint NOT NULL,
  submissiondate date,
  issubmitted boolean DEFAULT false,
  remarks character varying(512),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_applndcr_document_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_dcrsup_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_dcrdoc_checklistdtl FOREIGN KEY (checklistDtl)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_egbpa_dcrdoc_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_dcrdoc_mdfdby FOREIGN KEY (lastmodifiedby)
  REFERENCES eg_user (id)
);

create sequence seq_egbpa_application_dcr_document;

CREATE TABLE egbpa_dcr_document
(
  id bigint NOT NULL,
  filestore bigint NOT NULL,
  dcrdocument bigint NOT NULL,
  CONSTRAINT pk_egbpa_dcr_document_id PRIMARY KEY (id),
CONSTRAINT fk_egbpa_applndcr_document FOREIGN KEY (dcrdocument)
      REFERENCES egbpa_application_dcr_document (id),
CONSTRAINT fk_egbpa_applndcr_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id)
);

create sequence seq_egbpa_dcr_document;