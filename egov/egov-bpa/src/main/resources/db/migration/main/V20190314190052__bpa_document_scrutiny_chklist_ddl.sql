alter table EGBPA_DOCUMENT_SCRUTINY drop column neighbourOwnerDtlSubmitted;
alter table EGBPA_DOCUMENT_SCRUTINY drop column whetherAllDocAttached;
alter table EGBPA_DOCUMENT_SCRUTINY drop column whetherAllPageOfDocAttached;
alter table EGBPA_DOCUMENT_SCRUTINY drop column whetherDocumentMatch;

CREATE SEQUENCE seq_egbpa_document_scrutiny_checklist;
CREATE TABLE egbpa_document_scrutiny_checklist
(
  id bigint NOT NULL,
  documentScrutiny bigint NOT NULL,
  serviceChecklist bigint NOT NULL,
  scrutinyvalue character varying(50),
  remarks character varying(1024),
  ordernumber bigint,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_document_scrutiny_checklist PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_servicechklst FOREIGN KEY (documentScrutiny)
      REFERENCES EGBPA_DOCUMENT_SCRUTINY (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_chklst_doc_scrutiny FOREIGN KEY (serviceChecklist)
      REFERENCES egbpa_checklist_servicetype_mapping (id),
  CONSTRAINT fk_egbpa_doc_scrutiny_servicechklst_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user(id),
  CONSTRAINT fk_egbpa_doc_scrutiny_servicechklst_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user(id)
);