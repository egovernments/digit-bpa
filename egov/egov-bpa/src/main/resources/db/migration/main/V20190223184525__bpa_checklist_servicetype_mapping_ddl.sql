create sequence seq_egbpa_checklist_servicetype_mapping;

CREATE TABLE egbpa_checklist_servicetype_mapping
(
  id bigint NOT NULL,
  checklist bigint NOT NULL,
  servicetype bigint NOT NULL,
  isrequired boolean NOT NULL DEFAULT true,
  ismandatory boolean NOT NULL DEFAULT false,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_checklist_servicetype PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_checklist_servicetype_checklist FOREIGN KEY (checklist)
      REFERENCES eg_checklist (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_checklist_servicetype_servicetype FOREIGN KEY (servicetype)
      REFERENCES egbpa_mstr_servicetype (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_checklist_servicetype_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_checklist_servicetype_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);

