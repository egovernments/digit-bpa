
CREATE SEQUENCE seq_egbpa_permit_coapplicant;
CREATE TABLE egbpa_permit_coapplicant
(
  id bigint NOT NULL,
  application bigint,
  coapplicant bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  lastreplydate date,
  CONSTRAINT pk_egbpa_permit_coappid PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_coapp_ownership FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_coapp_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_permit_coapp_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_permit_coapp_coapplicant FOREIGN KEY (coapplicant)
      REFERENCES egbpa_coapplicant (id)
);


CREATE SEQUENCE seq_egbpa_ownershiptransfer_coapplicant;
CREATE TABLE egbpa_ownershiptransfer_coapplicant
(
  id bigint NOT NULL,
  ownershiptransfer bigint,
  coapplicant bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  lastreplydate date,
  CONSTRAINT pk_egbpa_ownershiptransfer_coappid PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_ownershiptransfer_coapp_ownership FOREIGN KEY (ownershiptransfer)
      REFERENCES egbpa_ownership_transfer (id),
  CONSTRAINT fk_egbpa_ownershiptransfer_coapp_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_ownershiptransfer_coapp_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_ownershiptransfer_coapplicant FOREIGN KEY (coapplicant)
      REFERENCES egbpa_coapplicant (id)
);


alter table egbpa_coapplicant drop column application ;



CREATE SEQUENCE seq_egbpa_ownershiptransfer_document;
CREATE TABLE egbpa_ownershiptransfer_document
(
  id bigint NOT NULL,
  document bigint NOT NULL,
  ownershiptransfer bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_ownership_documents PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_ownership_documents FOREIGN KEY (document)
      REFERENCES egbpa_general_document (id),
  CONSTRAINT fk_egbpa_ownership_documents_appln FOREIGN KEY (ownershiptransfer)
      REFERENCES egbpa_ownership_transfer (id),
  CONSTRAINT fk_egbpa_ownership_doc_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_ownership_doc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);

