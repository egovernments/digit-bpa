
CREATE SEQUENCE SEQ_EGBPA_OWNERSHIP_TRANSFER;
CREATE TABLE EGBPA_OWNERSHIP_TRANSFER
(
  id bigint NOT NULL,
  parent bigint NOT NULL,
  applicationNumber character varying(64) NOT NULL,
  ownershipNumber character varying(64),
  source character varying(50) NOT NULL,
  applicationDate date NOT NULL,
  ownershipApprovalDate date,
  status bigint NOT NULL,
  state_id bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT PK_SEQ_EGBPA_OWNERSHIP_TRANSFER_ID PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_ownership_transfer_parent FOREIGN KEY (parent)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_ownership_transfer_status FOREIGN KEY (status)
      REFERENCES egbpa_status (id),
  CONSTRAINT fk_ownership_transfer_states FOREIGN KEY (state_id)
      REFERENCES eg_wf_states (id),
  CONSTRAINT fk_ownership_transfer_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_ownership_transfer_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);

create table egbpa_ownership_transfer_documents
(
  ownershiptransfer bigint,
  filestore bigint,
  CONSTRAINT fk_ownership_transfer_doc_appln FOREIGN KEY (ownershiptransfer)
      REFERENCES EGBPA_OWNERSHIP_TRANSFER (id),
  CONSTRAINT fk_ownership_transfer_doc_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id)
);


create sequence seq_egbpa_ownershiptransfer_conditions;
CREATE TABLE egbpa_ownershiptransfer_conditions
(
  id bigint NOT NULL,
  ownershiptransfer bigint NOT NULL,
  noticecondition bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_onwership_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_onwershiptransfer_conditions FOREIGN KEY (ownershiptransfer)
      REFERENCES egbpa_ownership_transfer (id),
  CONSTRAINT fk_egbpa_onwership_conditions_cndn FOREIGN KEY (noticecondition)
      REFERENCES egbpa_notice_conditions (id),
  CONSTRAINT fk_egbpa_onwership_conditions_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_onwership_conditions_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);



create sequence seq_egbpa_ownershiptransfer_notice;
create table egbpa_ownershiptransfer_notice(
id bigint NOT NULL,
ownershiptransfer bigint NOT NULL,
notice bigint NOT NULL,
 createdby bigint NOT NULL,
 createddate timestamp without time zone NOT NULL,
 lastmodifieddate timestamp without time zone,
 lastmodifiedby bigint,
 version numeric NOT NULL,
 CONSTRAINT pk_notice_ownership PRIMARY KEY (id),
 CONSTRAINT fk_notice_ownership FOREIGN KEY (ownershiptransfer)
   REFERENCES egbpa_ownership_transfer (id),
 CONSTRAINT fk_notice_ownership_cmn FOREIGN KEY (notice)
   REFERENCES egbpa_notice_common (id),
 CONSTRAINT fk_notice_ownership_crtby FOREIGN KEY (createdby)
   REFERENCES state.eg_user (id),
 CONSTRAINT fk_notice_ownership_mdfdby FOREIGN KEY (lastmodifiedby)
   REFERENCES state.eg_user (id)
);