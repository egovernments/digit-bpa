create sequence seq_egbpa_existing_buildingdetail; 
CREATE TABLE egbpa_existing_buildingdetail
(
  id bigint NOT NULL,
  application bigint NOT NULL,
  totalplintarea double precision,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_existing_buildingdetail_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_buildingdetail_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_buildingdetail_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_buildingdetail_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_existing_building_floordetail;
CREATE TABLE egbpa_existing_building_floordetail
(
  id bigint NOT NULL,
  existingbuildingdetail bigint NOT NULL,
  orderoffloor bigint,
  floordescription character varying(128),
  floornumber bigint,
  occupancy bigint,
  plintharea double precision,
  floorarea double precision,
  carpetarea double precision,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_egbpa_existing_building_floordetail_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_existing_building_and_floordetails FOREIGN KEY (existingbuildingdetail)
      REFERENCES egbpa_existing_buildingdetail (id),
  CONSTRAINT fk_egbpa_appfloordetail_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_appfloordetail_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_application_floordetail_oocupancy FOREIGN KEY (occupancy)
      REFERENCES egbpa_mstr_occupancy (id)
);
