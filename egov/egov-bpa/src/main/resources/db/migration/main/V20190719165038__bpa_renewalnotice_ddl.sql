create sequence seq_egbpa_permitrenewal_conditions;
CREATE TABLE egbpa_permitrenewal_conditions
(
  id bigint NOT NULL,
  permitrenewal bigint NOT NULL,
  noticecondition bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_renewal_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_renewal_conditions_renewal FOREIGN KEY (permitrenewal)
      REFERENCES egbpa_permit_renewal (id),
  CONSTRAINT fk_egbpa_renewal_conditions_cndn FOREIGN KEY (noticecondition)
      REFERENCES egbpa_notice_conditions (id),
  CONSTRAINT fk_egbpa_renewal_conditions_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_renewal_conditions_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);



create sequence seq_egbpa_permitrenewal_notice;
create table egbpa_permitrenewal_notice(
id bigint NOT NULL,
permitrenewal bigint NOT NULL,
notice bigint NOT NULL,
 createdby bigint NOT NULL,
 createddate timestamp without time zone NOT NULL,
 lastmodifieddate timestamp without time zone,
 lastmodifiedby bigint,
 version numeric NOT NULL,
 CONSTRAINT pk_notice_renewal PRIMARY KEY (id),
 CONSTRAINT fk_notice_renewal FOREIGN KEY (permitrenewal)
   REFERENCES egbpa_permit_renewal (id),
 CONSTRAINT fk_notice_renewal_cmn FOREIGN KEY (notice)
   REFERENCES egbpa_notice_common (id),
 CONSTRAINT fk_notice_renewal_crtby FOREIGN KEY (createdby)
   REFERENCES state.eg_user (id),
 CONSTRAINT fk_notice_renewal_mdfdby FOREIGN KEY (lastmodifiedby)
   REFERENCES state.eg_user (id)
);