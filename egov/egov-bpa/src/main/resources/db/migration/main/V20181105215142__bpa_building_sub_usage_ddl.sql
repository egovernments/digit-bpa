CREATE SEQUENCE seq_egbpa_building_sub_usage;
CREATE TABLE egbpa_building_sub_usage
(
  id bigint NOT NULL,
  application bigint,
  blockNumber bigint,
  blockName character varying(50),
  mainusage character varying(128),
  subusage character varying(1024),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_egbpa_building_sub_usage PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_sub_usage_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_building_sub_usage_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_building_sub_usage_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);