DROP TABLE egbpa_inspection_common_files;
CREATE TABLE egbpa_inspection_common_files
(
  id bigint NOT NULL,
  inspectionid bigint,
  filestoreid bigint,
  direction character varying(30),
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_inspection_common_files PRIMARY KEY (id),
  CONSTRAINT fk_inspn_cmn_files_filestore FOREIGN KEY (filestoreid)
      REFERENCES eg_filestoremap (id),
  CONSTRAINT fk_inspn_cmn_files_inspection FOREIGN KEY (inspectionid)
      REFERENCES egbpa_inspection_common (id),
  CONSTRAINT fk_inspn_cmn_files_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_inspn_cmn_files_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_inspection_common_files;

DROP TABLE egbpa_lp_files_common;

CREATE TABLE egbpa_lp_files_common
(
  filestore bigint NOT NULL,
  lpDocument bigint NOT NULL,
CONSTRAINT fk_egbpa_lp_files_common_filestore FOREIGN KEY (filestore)
      REFERENCES eg_filestoremap (id),
  CONSTRAINT fk_egbpa_lp_files_lpdocument FOREIGN KEY (lpDocument)
      REFERENCES egbpa_lp_document_common (id)
);

ALTER TABLE egbpa_appointment_schedule_common DROP CONSTRAINT fk_appointment_scdl_cmn_parent;

ALTER TABLE egbpa_appointment_schedule_common ADD CONSTRAINT fk_appointment_scdl_cmn_parent FOREIGN KEY (parent)
      REFERENCES egbpa_appointment_schedule_common (id);