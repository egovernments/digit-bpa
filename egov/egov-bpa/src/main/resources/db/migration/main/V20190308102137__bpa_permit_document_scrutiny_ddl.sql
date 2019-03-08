DROP TABLE IF EXISTS EGBPA_DOCUMENTSCRUTINY CASCADE;
DROP SEQUENCE IF EXISTS SEQ_EGBPA_DOCUMENTSCRUTINY;
DROP SEQUENCE IF EXISTS seq_egbpa_plan_scrutiny_checklist;

ALTER TABLE EGBPA_DOCUMENT_SCRUTINY_COMMON RENAME TO EGBPA_DOCUMENT_SCRUTINY;
ALTER SEQUENCE SEQ_EGBPA_DOCUMENT_SCRUTINY_COMMON RENAME TO SEQ_EGBPA_DOCUMENT_SCRUTINY;

CREATE SEQUENCE SEQ_EGBPA_PERMIT_DOCUMENT_SCRUTINY;
CREATE TABLE EGBPA_PERMIT_DOCUMENT_SCRUTINY
(
  id bigint NOT NULL,
  documentScrutiny bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_doc_scrutiny PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_doc_scrutiny FOREIGN KEY (documentScrutiny)
      REFERENCES EGBPA_DOCUMENT_SCRUTINY (id),
  CONSTRAINT fk_egbpa_permit_doc_scrutiny_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_doc_scrutiny_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_permit_doc_scrutiny_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);