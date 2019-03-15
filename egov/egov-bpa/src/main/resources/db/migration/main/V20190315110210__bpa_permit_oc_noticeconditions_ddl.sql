create sequence SEQ_EGBPA_NOTICE_CONDITION;

CREATE TABLE egbpa_notice_conditions
(
  id bigint NOT NULL,
  type character varying(30),
  checklistservicetype bigint NOT NULL,
  conditionddate timestamp without time zone,
  conditionnumber character varying(30),
  ordernumber bigint,
  isrequired boolean DEFAULT true,
  additionalcondition character varying(600),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_notice_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_notice_conditions_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_notice_conditions_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_notice_conditions_checklist_servicetype FOREIGN KEY (checklistservicetype)
      REFERENCES egbpa_checklist_servicetype_mapping (id)    
);

delete from egbpa_application_permit_conditions;
alter table egbpa_application_permit_conditions drop constraint fk_egbpa_appln_permit_conditions;
alter table egbpa_application_permit_conditions rename permitcondition to noticecondition;
alter table egbpa_application_permit_conditions add constraint fk_egbpa_appln_notice_conditions FOREIGN KEY (noticecondition) REFERENCES egbpa_notice_conditions (id);
alter table egbpa_application_permit_conditions drop column permitconditionddate;
alter table egbpa_application_permit_conditions drop column permitconditionnumber;
alter table egbpa_application_permit_conditions drop column ordernumber;
alter table egbpa_application_permit_conditions drop column permitconditiontype;
alter table egbpa_application_permit_conditions drop column isrequired;
alter table egbpa_application_permit_conditions drop column additionalpermitcondition;


delete from egbpa_oc_notice_conditions;
alter table egbpa_oc_notice_conditions drop constraint fk_egbpa_oc_notice_conditions_cndn;
alter table egbpa_oc_notice_conditions add constraint fk_egbpa_oc_notice_conditions FOREIGN KEY (noticecondition) REFERENCES egbpa_notice_conditions (id);
alter table egbpa_oc_notice_conditions drop column type;
alter table egbpa_oc_notice_conditions drop column conditionddate;
alter table egbpa_oc_notice_conditions drop column conditionnumber;
alter table egbpa_oc_notice_conditions drop column ordernumber;
alter table egbpa_oc_notice_conditions drop column isrequired;
alter table egbpa_oc_notice_conditions drop column additionalcondition;