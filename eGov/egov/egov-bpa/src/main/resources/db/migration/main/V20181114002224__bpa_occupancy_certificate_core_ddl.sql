CREATE SEQUENCE seq_egbpa_document_scrutiny_common;
CREATE TABLE egbpa_document_scrutiny_common
(
  id bigint NOT NULL,
  plotsurveynumber character varying(24),
  subdivisionnumber character varying(128),
  extentinsqmts double precision,
  natureofownership character varying(128),
  registraroffice character varying(128),
  village bigint,
  taluk character varying(128),
  district character varying(128),
  neighbourOwnerDtlSubmitted character varying(30),
  deednumber character varying(64),
  deeddate date,
  whetheralldocattached character varying(30),
  whetherallpageofdocattached character varying(30),
  whetherdocumentmatch character varying(30),
  verifiedby bigint NOT NULL,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  resurveynumber character varying(128),
  CONSTRAINT pk_doc_scrutiny_cmn PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_cmn_verifiedby FOREIGN KEY (verifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_documents_common;
CREATE TABLE egbpa_documents_common
(
  id bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  submissiondate date,
  issubmitted boolean DEFAULT false,
  createduser bigint,
  remarks character varying(256),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_documents_common PRIMARY KEY (id),
  CONSTRAINT fk_docs_cmn_user FOREIGN KEY (createduser)
      REFERENCES eg_user (id),
  CONSTRAINT fk_docs_cmn_checklistdtl FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_docs_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_docs_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_documents_common_files
(
  filestoreid bigint NOT NULL,
  documentid bigint NOT NULL,
  CONSTRAINT fk_docs_cmn_files_filestore FOREIGN KEY (filestoreid)
      REFERENCES eg_filestoremap (id),
  CONSTRAINT fk_egbpa_document_cmn_files FOREIGN KEY (documentid)
      REFERENCES egbpa_documents_common (id)
);

CREATE SEQUENCE seq_egbpa_appointment_schedule_common;
CREATE TABLE egbpa_appointment_schedule_common
(
  id bigint NOT NULL,
  purpose character varying(50),
  appointmentdate date,
  appointmenttime character varying(50),
  appointmentlocation bigint,
  ispostponed boolean DEFAULT false,
  remarks character varying(256),
  postponementreason character varying(256),
  parent bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_appointment_scdl_cmn PRIMARY KEY (id),
  CONSTRAINT fk_appointment_scdl_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_appointment_scdl_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_appointment_scdl_cmn_parent FOREIGN KEY (parent)
      REFERENCES egbpa_appointment_schedule (id),
  CONSTRAINT fk_appointment_scdl_cmn_location FOREIGN KEY (appointmentlocation)
      REFERENCES egbpa_mstr_appointment_location (id)
);

create sequence seq_egbpa_inspection_common;

CREATE TABLE egbpa_inspection_common
(
  id bigint NOT NULL,
  inspectionnumber character varying(64),
  inspectiondate timestamp without time zone NOT NULL,
  parent bigint,
  inspectedby bigint NOT NULL,
  isinspected boolean,
  inspectionremarks character varying(1000),
  ispostponed boolean,
  postponementreason character varying(256),
  postponeddate date,
  boundarydrawingsubmitted boolean DEFAULT false,
  righttomakeconstruction boolean DEFAULT false,
  typeofland character varying(128),
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_inspection_cmn PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_inspection_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_inspection_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_inspection_cmn_inspectedby FOREIGN KEY (inspectedby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_inspection_common_files
(
  inspectionid bigint,
  filestoreid bigint,
CONSTRAINT fk_inspn_cmn_files_filestore FOREIGN KEY (filestoreid)
      REFERENCES eg_filestoremap (id),
CONSTRAINT fk_inspn_cmn_files_inspection FOREIGN KEY (inspectionid)
      REFERENCES egbpa_inspection_common (id)
);

CREATE SEQUENCE seq_egbpa_plan_scrutiny_checklist_common;
CREATE TABLE egbpa_plan_scrutiny_checklist_common
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  scrutinyvalue character varying(50),
  remarks character varying(1024),
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_plan_scrutiny_checklist_cmn PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_plan_scrutiny_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_plan_scrutiny_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_plan_scrutinychecklist_cmn FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_egbpa_plan_scrutiny_cmn_inspection FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection_common (id)
);


CREATE SEQUENCE seq_egbpa_docket_common;
CREATE TABLE egbpa_docket_common
(
  id bigint NOT NULL,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  inspection bigint,
  CONSTRAINT pk_egbpa_docket_cmn PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_docket_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_docket_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_insp_docket_cmn FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection_common (id)
);

CREATE SEQUENCE seq_egbpa_docket_detail_common;
CREATE TABLE egbpa_docket_detail_common
(
  id bigint NOT NULL,
  value character varying(32),
  remarks character varying(256),
  required character varying(32),
  provided character varying(32),
  docket bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_docket_detail_cmn PRIMARY KEY (id),
  CONSTRAINT fk_docketdetails_cmn_chklistdtl FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_docketdtls_docket_cmn FOREIGN KEY (docket)
      REFERENCES egbpa_docket_common (id),
  CONSTRAINT fk_egbpa_docketdtl_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_docketdtl_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_docket_constrnstage_common;
CREATE TABLE egbpa_docket_constrnstage_common
(
  id bigint NOT NULL,
  value character varying(32),
  remarks character varying(256),
  docket bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_docket_constrnstage_cmn PRIMARY KEY (id),
  CONSTRAINT fk_docketconstrnstage_cmn_checklistdetails FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_docketconstrnstage_docket FOREIGN KEY (docket)
      REFERENCES egbpa_docket_common (id),
  CONSTRAINT fk_egbpa_docket_constrnstage_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_docket_constrstage_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_lettertoparty_common;
CREATE TABLE egbpa_lettertoparty_common
(
  id bigint NOT NULL,
  inspection bigint,
  acknowledgementnumber character varying(32),
  lpnumber character varying(128),
  letterdate timestamp without time zone NOT NULL,
  scheduledby bigint,
  scheduledplace character varying(128),
  scheduledtime date,
  sentdate date,
  replydate date,
  lpremarks character varying(1024),
  lpreplyremarks character varying(1024),
  lpdesc character varying(1024),
  lpreplydesc character varying(1024),
  ishistory boolean,
  documentid character varying(512),
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  lastreplydate date,
  currentstatevalueoflp character varying(200),
  currentapplnstatus bigint,
  stateforownerposition character varying(200),
  pendingaction character varying(200),
  CONSTRAINT pk_egbpa_lettertoparty_cmn PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_lp_cmn_appln_status FOREIGN KEY (currentapplnstatus)
      REFERENCES egbpa_status (id),
  CONSTRAINT fk_egbpa_lp_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_lp_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_lp_cmn_inspection FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection_common (id),
  CONSTRAINT fk_lp_cmn_scheduledby FOREIGN KEY (scheduledby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_lp_reason_common
(
  lettertoparty bigint NOT NULL,
  lpreason bigint NOT NULL,
  CONSTRAINT fk_egbpa_lp_reason_cmn_lpid FOREIGN KEY (lettertoparty)
      REFERENCES egbpa_lettertoparty_common (id),
  CONSTRAINT fk_egbpa_lp_reason_cmn_lpreasonid FOREIGN KEY (lpreason)
      REFERENCES egbpa_mstr_lpreason (id)
);


CREATE SEQUENCE seq_egbpa_lp_document_common;
CREATE TABLE egbpa_lp_document_common
(
  id bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  lettertoparty bigint NOT NULL,
  submissiondate date,
  issubmitted boolean DEFAULT false,
  createduser bigint,
  remarks character varying(256),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  isrequested boolean DEFAULT false,
  CONSTRAINT pk_egbpa_lp_document_common_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_lpdoc_cmn_checklistdtl FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_egbpa_lpdoc_cmn_createduser FOREIGN KEY (createduser)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_lpdoc_cmn_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_lpdoc_cmn_lettertoparty FOREIGN KEY (lettertoparty)
      REFERENCES egbpa_lettertoparty_common (id),
  CONSTRAINT fk_egbpa_lpdoc_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_lp_files_common
(
  filestore bigint NOT NULL,
  lpDocument bigint NOT NULL,
CONSTRAINT fk_egbpa_lp_files_common_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id),
  CONSTRAINT fk_egbpa_lettertoparty_document_lpdocument FOREIGN KEY (lpDocument)
      REFERENCES egbpa_lettertoparty_document (id)
);

CREATE SEQUENCE seq_egbpa_noc_document_common;
CREATE TABLE egbpa_noc_document_common
(
  id bigint NOT NULL,
  checklist bigint NOT NULL,
  natureofrequest character varying(1000),
  lettersenton timestamp without time zone,
  replyreceivedon timestamp without time zone,
  rejection boolean DEFAULT false,
  notapplicable boolean DEFAULT false,
  nocstatus character varying(40),
  submissiondate date,
  issubmitted boolean DEFAULT false,
  createduser bigint,
  remarks character varying(1000),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_egbpa_noc_document_common_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_noc_document_common_chklist FOREIGN KEY (checklist)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_egbpa_noc_document_common_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_noc_document_common_crtdusr FOREIGN KEY (createduser)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_noc_document_common_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_noc_files_common
(
  filestore bigint NOT NULL,
  nocdocument bigint NOT NULL,
  CONSTRAINT fk_egbpa_noc_files_common_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id),
  CONSTRAINT fk_egbpa_noc_files_common_doc FOREIGN KEY (nocdocument)
      REFERENCES egbpa_noc_document_common (id)
);

CREATE SEQUENCE seq_egbpa_occupancy_certificate;
CREATE TABLE egbpa_occupancy_certificate
(
  id bigint NOT NULL,
  parent bigint NOT NULL,
  applicationNumber character varying(128),
  occupancyCertificateNumber character varying(128),
  applicationDate date,
  source character varying(50),
  applicationType character varying(128),
  status bigint NOT NULL,
  state_id bigint,
  demand bigint NOT NULL,
  commencedDate date,
  completionDate date,
  workCompletionDueDate date,
  citizenAccepted boolean default false,
  architectAccepted boolean default false,
  isSentToPreviousOwner boolean default false,
  isRescheduledByCitizen boolean default false,
  isRescheduledByEmployee boolean default false,
  townsurveyorremarks character varying(5000),
  islprequestinitiated boolean DEFAULT false,
  failureinscheduler boolean DEFAULT false,
  schedulerfailedremarks character varying(5000),
  authorizedtosubmitplan boolean DEFAULT false,
  istownsurveyorinspectionrequire boolean DEFAULT false,
  createdby bigint NOT NULL,
  version numeric DEFAULT 0,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint NOT NULL,
  CONSTRAINT pk_egbpa_occupancy_certificate PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_appln FOREIGN KEY (parent)
      REFERENCES egbpa_application(id),
  CONSTRAINT fk_egbpa_oc_states FOREIGN KEY (state_id)
      REFERENCES eg_wf_states (id),
  CONSTRAINT fk_egbpa_oc_status FOREIGN KEY (status)
      REFERENCES egbpa_status (id),
  CONSTRAINT fk_egbpa_oc_demand FOREIGN KEY (demand)
      REFERENCES eg_demand,
CONSTRAINT fk_egbpa_oc_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_appointment_schedule;
CREATE TABLE egbpa_oc_appointment_schedule
(
  id bigint NOT NULL,
  appointmentScheduleCommon bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_appointment_schedule PRIMARY KEY(id),
  CONSTRAINT fk_egbpa_oc_appmnt_schdle_cmn FOREIGN KEY (appointmentScheduleCommon)
      REFERENCES egbpa_appointment_schedule_common (id),
  CONSTRAINT fk_egbpa_oc_appmnt_schdle_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_appmnt_schdle_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_appmnt_schdle_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_documents;
CREATE TABLE egbpa_oc_documents
(
  id bigint NOT NULL,
  document bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_documents PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_documents_cmn FOREIGN KEY (document)
      REFERENCES egbpa_documents_common (id),
  CONSTRAINT fk_egbpa_oc_documents_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_doc_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_doc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_document_scrutiny;
CREATE TABLE egbpa_oc_document_scrutiny
(
  id bigint NOT NULL,
  documentScrutiny bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_document_scrutiny PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_document_scrutiny_cmn FOREIGN KEY (documentScrutiny)
      REFERENCES egbpa_document_scrutiny_common (id),
  CONSTRAINT fk_egbpa_oc_document_scrutiny_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_doc_scrutny_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_doc_scrutny_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_inspection;
CREATE TABLE egbpa_oc_inspection
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_inspection PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_document_scrutiny_cmn FOREIGN KEY (inspection)
      REFERENCES egbpa_inspection_common (id),
  CONSTRAINT fk_egbpa_oc_inspection_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_inspection_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_inspection_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_noc_document;
CREATE TABLE egbpa_oc_noc_document
(
  id bigint NOT NULL,
  nocDocument bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_noc_document PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_noc_document_cmn FOREIGN KEY (nocDocument)
      REFERENCES egbpa_noc_document_common (id),
  CONSTRAINT fk_egbpa_oc_noc_document_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
   CONSTRAINT fk_egbpa_oc_noc_document_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_noc_document_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE SEQUENCE seq_egbpa_oc_slot;
CREATE TABLE egbpa_oc_slot
(
  id bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  scheduleappointmenttype character varying(50) NOT NULL,
  slotdetailid bigint NOT NULL,
  isactive boolean DEFAULT false,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_slot PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_slot_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_slot_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_slot_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_slot_slotdetail FOREIGN KEY (slotdetailid)
      REFERENCES egbpa_slotdetail (id)
);

CREATE SEQUENCE seq_egbpa_oc_letter_to_party;
CREATE TABLE egbpa_oc_letter_to_party
(
  id bigint NOT NULL,
  letterToParty bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_letter_to_party PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_letter_to_party_cmn FOREIGN KEY (letterToParty)
      REFERENCES egbpa_lettertoparty_common (id),
  CONSTRAINT fk_egbpa_oc_letter_to_party_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_letter_to_party_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_letter_to_party_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);