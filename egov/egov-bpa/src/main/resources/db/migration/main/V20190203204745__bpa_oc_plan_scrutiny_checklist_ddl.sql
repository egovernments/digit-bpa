CREATE TABLE egbpa_oc_plan_scrutiny_checklist
(
  id bigint NOT NULL,
  inspection bigint NOT NULL,
  checklistdetail bigint NOT NULL,
  scrutinyvalue bigint,
  remarks character varying(1024),
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  scrutinychecklisttype character varying(50),
  CONSTRAINT pk_egbpa_oc_plan_scrutiny_checklist PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_plan_scrutiny_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_oc_plan_scrutiny_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_oc_plan_scrutinychecklist FOREIGN KEY (checklistdetail)
      REFERENCES egbpa_mstr_chklistdetail (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_egbpa_oc_plan_scrutinyinspection FOREIGN KEY (inspection)
      REFERENCES egbpa_oc_inspection (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
create sequence seq_egbpa_oc_plan_scrutiny_checklist;
