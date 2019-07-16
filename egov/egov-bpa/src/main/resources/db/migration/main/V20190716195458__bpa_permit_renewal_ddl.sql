CREATE SEQUENCE SEQ_EGBPA_PERMIT_RENEWAL;
CREATE TABLE EGBPA_PERMIT_RENEWAL
(
  id bigint NOT NULL,
  parent bigint NOT NULL,
  source character varying(50) NOT NULL,
  applicationNumber character varying(64) NOT NULL,
  renewalNumber character varying(64),
  applicationDate date NOT NULL,
  renewalApprovalDate date,
  status bigint NOT NULL,
  state_id bigint,
  constructionStage bigint,
  constructionStatus character varying(512),
  permitRenewalExpiryDate date,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT PK_SEQ_EGBPA_PERMIT_RENEWAL_ID PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_renewal_parent FOREIGN KEY (parent)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_permit_renewal_status FOREIGN KEY (status)
      REFERENCES egbpa_status (id),
  CONSTRAINT fk_permit_renewal_conststage FOREIGN KEY (constructionStage)
      REFERENCES EGBPA_MSTR_CONST_STAGES (id),
  CONSTRAINT fk_permit_renewal_states FOREIGN KEY (state_id)
      REFERENCES eg_wf_states (id),
  CONSTRAINT fk_permit_renewal_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_permit_renewal_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);

create table egbpa_permit_renewal_documents
(
  permitrenewal bigint,
  filestore bigint,
  CONSTRAINT fk_permit_renewal_doc_appln FOREIGN KEY (permitrenewal)
      REFERENCES EGBPA_PERMIT_RENEWAL (id),
  CONSTRAINT fk_permit_renewal_doc_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id)
);