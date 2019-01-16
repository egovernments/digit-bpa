ALTER TABLE egbpa_building_sub_usage DROP COLUMN subusage;

ALTER TABLE egbpa_building_sub_usage DROP COLUMN mainusage;

CREATE SEQUENCE seq_egbpa_building_sub_usage_details;

CREATE TABLE egbpa_building_sub_usage_details
(
  id bigint NOT NULL,
  mainusage bigint,
  buildingSubUsage bigint,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_egbpa_building_sub_usage_dtl PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_bldg_sub_usage_occup_dtl FOREIGN KEY (buildingSubUsage)
      REFERENCES egbpa_building_sub_usage (id),
  CONSTRAINT fk_egbpa_sub_usage_dtl_occup FOREIGN KEY (mainusage)
      REFERENCES eg_occupancy (id),
  CONSTRAINT fk_egbpa_building_sub_usage_dtl_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_building_sub_usage_dtl_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

CREATE TABLE egbpa_sub_usage
(
  subUsageDetails bigint NOT NULL,
  suboccupancy bigint NOT NULL,
  CONSTRAINT fk_egbpa_sub_usage_dtls_building FOREIGN KEY (subUsageDetails)
      REFERENCES egbpa_building_sub_usage_details (id),
  CONSTRAINT fk_egbpa_sub_usage FOREIGN KEY (suboccupancy)
      REFERENCES eg_sub_occupancy (id)
);