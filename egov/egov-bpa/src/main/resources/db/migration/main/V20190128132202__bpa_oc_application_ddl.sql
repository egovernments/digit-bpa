alter table egbpa_occupancy_certificate add column edcrnumber character varying(30);

create sequence seq_egbpa_oc_building;
CREATE TABLE egbpa_oc_building
(
  id bigint NOT NULL,
  oc bigint NOT NULL,
  buildingNumber bigint,
  name character varying(50),
  floorcount bigint,
  totalplintharea double precision,
  heightfromgroundwithstairroom double precision,
  heightfromgroundwithoutstairroom double precision,
  fromstreetlevelwithstairroom double precision,
  fromstreetlevelwithoutstairroom double precision,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_oc_bldgdtl_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_bldg_oc FOREIGN KEY (oc)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_bldg_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_bldg_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_oc_floor;
CREATE TABLE egbpa_oc_floor
(
  id bigint NOT NULL,
  buildingdetail bigint NOT NULL,
  floornumber bigint,
  floordescription character varying(128),
  occupancy bigint,
  floorarea double precision,
  carpetarea double precision,
  plintharea double precision,
  orderoffloor bigint,
  version numeric NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  CONSTRAINT pk_egbpa_oc_floor_detail_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_floor_bldg FOREIGN KEY (buildingdetail)
      REFERENCES egbpa_oc_building (id),
  CONSTRAINT fk_egbpa_oc_floor_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_floor_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_floor_occupancy FOREIGN KEY (occupancy)
      REFERENCES eg_occupancy (id)
);

create sequence seq_egbpa_oc_existing_building;
CREATE TABLE egbpa_oc_existing_building
(
  id bigint NOT NULL,
  oc bigint NOT NULL,
  buildingNumber bigint,
  name character varying(50),
  totalplintharea double precision,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_oc_existing_building_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_building_oc FOREIGN KEY (oc)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_building_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_building_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_oc_existing_floor;

CREATE TABLE egbpa_oc_existing_floor
(
  id bigint NOT NULL,
  existingbuilding bigint NOT NULL,
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
  CONSTRAINT pk_egbpa_oc_existing_bldg_floor_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_exist_bldg_floor_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_exist_bldg_floor_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_exist_bldg_floor_oocupancy FOREIGN KEY (occupancy)
      REFERENCES eg_occupancy (id),
  CONSTRAINT fk_egbpa_oc_exist_bldg_floor_bldg FOREIGN KEY (existingbuilding)
      REFERENCES egbpa_oc_existing_building (id)
);


create sequence seq_egbpa_dcr_document_common;
CREATE TABLE egbpa_dcr_document_common
(
  id bigint NOT NULL,
  checklistdtl bigint NOT NULL,
  submissiondate date,
  issubmitted boolean DEFAULT false,
  remarks character varying(548),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifieddate timestamp without time zone,
  lastmodifiedby bigint,
  version numeric NOT NULL,
  CONSTRAINT pk_cmndcr_document_id PRIMARY KEY (id),
  CONSTRAINT fk_cmn_dcrdoc_chklistdtl FOREIGN KEY (checklistdtl)
      REFERENCES egbpa_mstr_chklistdetail (id),
  CONSTRAINT fk_cmn_dcrdoc_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_cmn_dcrdoc_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_dcr_files_common;
CREATE TABLE egbpa_dcr_files_common
(
  id bigint NOT NULL,
  filestore bigint NOT NULL,
  dcrdocument bigint NOT NULL,
  isautopopulated boolean DEFAULT false,
  CONSTRAINT pk_egbpa_dcr_files_cmn_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_dcr_files_cmn FOREIGN KEY (dcrdocument)
      REFERENCES egbpa_dcr_document_common (id),
  CONSTRAINT fk_egbpa_dcrfile_cmn_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id)
);

CREATE SEQUENCE seq_egbpa_oc_dcr_document;
CREATE TABLE egbpa_oc_dcr_document
(
  id bigint NOT NULL,
  dcrDocument bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_oc_dcr_document PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_dcr_document_cmn FOREIGN KEY (dcrDocument)
      REFERENCES egbpa_dcr_document_common (id),
  CONSTRAINT fk_egbpa_oc_dcr_document_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
   CONSTRAINT fk_egbpa_oc_dcr_document_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_dcr_document_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_oc_notice_conditions;
CREATE TABLE egbpa_oc_notice_conditions
(
  id bigint NOT NULL,
  occupancyCertificate bigint NOT NULL,
  noticecondition bigint NOT NULL,
  type character varying(30),
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
  CONSTRAINT pk_egbpa_oc_notice_conditions_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_oc_notice_conditions_oc FOREIGN KEY (occupancyCertificate)
      REFERENCES egbpa_occupancy_certificate (id),
  CONSTRAINT fk_egbpa_oc_notice_conditions_cndn FOREIGN KEY (noticecondition)
      REFERENCES egbpa_mstr_permit_conditions (id),
  CONSTRAINT fk_egbpa_oc_notice_conditions_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_oc_notice_conditions_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);