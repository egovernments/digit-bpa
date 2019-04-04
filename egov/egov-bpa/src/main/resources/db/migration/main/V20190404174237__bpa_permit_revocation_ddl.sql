CREATE SEQUENCE SEQ_BPA_REVOKENO_;

CREATE SEQUENCE SEQ_EGBPA_PERMIT_REVOCATION;
CREATE TABLE EGBPA_PERMIT_REVOCATION
(
  id bigint NOT NULL,
  application bigint NOT NULL,
  applicationnumber character varying(64) NOT NULL,
  applicationdate date NOT NULL,
  revocationnumber character varying(64),
  revocationdate date,
  initiateRemarks character varying(1048),
  approveCancelRemarks character varying(1048),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_revocation PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_revocation_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
   CONSTRAINT fk_egbpa_permit_revocation_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_permit_revocation_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);

CREATE SEQUENCE SEQ_EGBPA_PERMIT_REVOCATION_DETAIL;
CREATE TABLE EGBPA_PERMIT_REVOCATION_DETAIL
(
  id bigint NOT NULL,
  revocation bigint NOT NULL,
  requestDate date,
  replyDate date,
  natureOfRequest character varying(520),
  issuedBy character varying(136),
  remarks character varying(520),
  orderNumber bigint,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_revocation_detail PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_revocation_detail FOREIGN KEY (revocation)
      REFERENCES EGBPA_PERMIT_REVOCATION (id),
   CONSTRAINT fk_egbpa_permit_revocation_detail_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_permit_revocation_detail_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);


CREATE TABLE egbpa_permit_revocation_document
(
  revocationDetail bigint,
  fileStore bigint,
CONSTRAINT fk_permit_revokeDetail_id FOREIGN KEY (revocationDetail)
      REFERENCES EGBPA_PERMIT_REVOCATION_DETAIL (id),
CONSTRAINT fk_permit_revokeDetail_filemapper FOREIGN KEY (fileStore)
      REFERENCES eg_filestoremap (id)
);