
drop table IF EXISTS egbpa_mstr_noc CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_MSTR_NOC;
drop table IF EXISTS egbpa_mstr_document CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_MSTR_DOCUMENT;

drop table IF EXISTS egbpa_document CASCADE;
drop table IF EXISTS egbpa_application_document CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_application_document;
drop table IF EXISTS egbpa_dcr_document CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_dcr_document;
drop table IF EXISTS egbpa_application_dcr_document CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_application_dcr_document;
drop table IF EXISTS egbpa_noc_document_store CASCADE;
drop table IF EXISTS egbpa_NOC_Document CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_NOC_Document;

DROP TABLE IF EXISTS egbpa_lettertoparty_filestore CASCADE;
DROP TABLE IF EXISTS egbpa_lettertoparty_document CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_lettertoparty_document;
DROP TABLE IF EXISTS egbpa_lp_and_reason CASCADE;
drop table IF EXISTS EGBPA_LETTERTOPARTY CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_LETTERTOPARTY;

drop table IF EXISTS egbpa_oc_plan_scrutiny_checklist CASCADE;
DROP SEQUENCE IF EXISTS seq_egbpa_oc_plan_scrutiny_checklist;
drop table IF EXISTS EGBPA_DOCKET_CONSTRNSTAGE CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_DOCKET_CONSTRNSTAGE;
drop table IF EXISTS EGBPA_DOCKET_DETAIL CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_DOCKET_DETAIL;
drop table IF EXISTS EGBPA_DOCKET CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_DOCKET CASCADE;
drop table IF EXISTS egbpa_plan_scrutiny_checklist;
drop table IF EXISTS egbpa_inspectiondocs;
DROP TABLE IF EXISTS EGBPA_INSPECTION CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_INSPECTION;

ALTER SEQUENCE seq_egbpa_documents_common RENAME TO seq_egbpa_general_document;
alter table egbpa_documents_common_files rename to egbpa_general_document_files;
alter table egbpa_documents_common rename to egbpa_general_document;
alter table egbpa_general_document rename column checklistdetail to servicechecklist;
ALTER TABLE egbpa_general_document DROP CONSTRAINT fk_docs_cmn_checklistdtl;
ALTER TABLE egbpa_general_document ADD CONSTRAINT fk_egbpa_general_document_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER SEQUENCE seq_egbpa_noc_document_common RENAME TO seq_egbpa_noc_document;
alter table egbpa_noc_files_common rename to egbpa_noc_document_files;
alter table egbpa_noc_document_common rename to egbpa_noc_document;
alter table egbpa_noc_document rename column checklist to servicechecklist;
ALTER TABLE egbpa_noc_document DROP CONSTRAINT fk_egbpa_noc_document_common_chklist;
ALTER TABLE egbpa_noc_document ADD CONSTRAINT fk_egbpa_noc_document_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER SEQUENCE seq_egbpa_dcr_document_common RENAME TO seq_egbpa_dcr_document;
ALTER SEQUENCE seq_egbpa_dcr_files_common RENAME TO seq_egbpa_dcr_document_files;
alter table egbpa_dcr_files_common rename to egbpa_dcr_document_files;
alter table egbpa_dcr_document_common rename to egbpa_dcr_document;
alter table egbpa_dcr_document rename column checklistdtl to servicechecklist;
ALTER TABLE egbpa_dcr_document_files DROP CONSTRAINT fk_egbpa_dcr_files_cmn;
ALTER TABLE egbpa_dcr_document_files ADD CONSTRAINT fk_egbpa_dcr_doc_files FOREIGN KEY (dcrdocument)
   REFERENCES egbpa_dcr_document (id);
ALTER TABLE egbpa_dcr_document DROP CONSTRAINT fk_cmn_dcrdoc_chklistdtl;
ALTER TABLE egbpa_dcr_document ADD CONSTRAINT fk_egbpa_dcr_document_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER TABLE egbpa_inspection_common_files DROP COLUMN filestoreid;
ALTER TABLE egbpa_inspection_common_files DROP COLUMN direction;
ALTER TABLE egbpa_inspection_common_files ADD COLUMN servicechecklist bigint;
ALTER TABLE egbpa_inspection_common_files ADD CONSTRAINT fk_inspn_images_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER TABLE egbpa_docket_detail_common DROP CONSTRAINT fk_docketdetails_cmn_chklistdtl;
alter table egbpa_docket_detail_common rename column checklistdetail to servicechecklist;
ALTER TABLE egbpa_docket_detail_common ADD CONSTRAINT fk_inspn_docketdetails_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER TABLE EGBPA_DOCKET_CONSTRNSTAGE_COMMON DROP CONSTRAINT fk_docketconstrnstage_cmn_checklistdetails;
alter table EGBPA_DOCKET_CONSTRNSTAGE_COMMON rename column checklistdetail to servicechecklist;
ALTER TABLE EGBPA_DOCKET_CONSTRNSTAGE_COMMON ADD CONSTRAINT fk_inspn_docketconstrnstage_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

ALTER TABLE egbpa_plan_scrutiny_checklist_common DROP CONSTRAINT fk_egbpa_plan_scrutinychecklist_cmn;
alter table egbpa_plan_scrutiny_checklist_common rename column checklistdetail to servicechecklist;
ALTER TABLE egbpa_plan_scrutiny_checklist_common ADD CONSTRAINT fk_egbpa_inspn_plan_scrtny_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);
alter table egbpa_plan_scrutiny_checklist_common add column scrutinyChecklistType character varying(128);

ALTER TABLE egbpa_lp_document_common DROP CONSTRAINT fk_egbpa_lpdoc_cmn_checklistdtl;
alter table egbpa_lp_document_common rename column checklistdetail to servicechecklist;
ALTER TABLE egbpa_lp_document_common ADD CONSTRAINT fk_egbpa_lpdoc_servichklst FOREIGN KEY (servicechecklist)
   REFERENCES egbpa_checklist_servicetype_mapping (id);

CREATE SEQUENCE seq_egbpa_permit_documents;
CREATE TABLE egbpa_permit_documents
(
  id bigint NOT NULL,
  document bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_documents PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_gen_documents FOREIGN KEY (document)
      REFERENCES egbpa_general_document (id),
  CONSTRAINT fk_egbpa_permit_documents_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_doc_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_doc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);


CREATE SEQUENCE seq_egbpa_permit_noc_document;
CREATE TABLE egbpa_permit_noc_document
(
  id bigint NOT NULL,
  nocDocument bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_noc_document PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_noc_document_cmn FOREIGN KEY (nocDocument)
      REFERENCES egbpa_noc_document (id),
  CONSTRAINT fk_egbpa_permit_noc_document_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
   CONSTRAINT fk_egbpa_permit_noc_document_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_noc_document_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_permit_dcr_document;
CREATE TABLE egbpa_permit_dcr_document
(
  id bigint NOT NULL,
  dcrDocument bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_dcr_document PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_dcr_document_cmn FOREIGN KEY (dcrDocument)
      REFERENCES egbpa_dcr_document (id),
  CONSTRAINT fk_egbpa_permit_dcr_document_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
   CONSTRAINT fk_egbpa_permit_dcr_document_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_dcr_document_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_permit_letter_to_party;
CREATE TABLE egbpa_permit_letter_to_party
(
  id bigint NOT NULL,
  letterToParty bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_letter_to_party PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_letter_to_party_cmn FOREIGN KEY (letterToParty)
      REFERENCES egbpa_lettertoparty_common (id),
  CONSTRAINT fk_egbpa_permit_letter_to_party_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_letter_to_party_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_letter_to_party_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE SEQ_EGBPA_PERMIT_INSPECTION;
CREATE TABLE EGBPA_PERMIT_INSPECTION
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_inspection PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_appln_inspn_cmn FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection_common (id),
  CONSTRAINT fk_egbpa_permit_inspn_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_inspn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_inspn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);


CREATE TABLE egbpa_site_inspection_images
(
  inspectiondocument bigint,
  filestoreid bigint,
CONSTRAINT fk_inspn_images_servicechklist_id FOREIGN KEY (inspectiondocument)
      REFERENCES egbpa_inspection_common_files (id),
CONSTRAINT fk_egbpa_inspnimages_filemapper FOREIGN KEY (filestoreid)
      REFERENCES eg_filestoremap (id)
);