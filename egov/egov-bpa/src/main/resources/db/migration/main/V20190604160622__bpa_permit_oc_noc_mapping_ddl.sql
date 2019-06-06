
CREATE SEQUENCE SEQ_EGBPA_PERMIT_NOCAPPLICATION;
CREATE TABLE EGBPA_PERMIT_NOCAPPLICATION
(
  id bigint NOT NULL,
  nocapplication bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_noc PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_nocappln FOREIGN KEY (nocapplication)
      REFERENCES egbpa_nocapplication (id),
  CONSTRAINT fk_egbpa_permit_noc_appln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_noc_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_permit_noc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);


CREATE SEQUENCE SEQ_EGBPA_OC_NOCAPPLICATION;
CREATE TABLE EGBPA_OC_NOCAPPLICATION
(
  id bigint NOT NULL,
  nocapplication bigint NOT NULL,
  oc bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_noc PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_nocappln FOREIGN KEY (nocapplication)
      REFERENCES egbpa_nocapplication (id),
  CONSTRAINT fk_egbpa_oc_noc_appln FOREIGN KEY (oc)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_noc_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_oc_noc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);