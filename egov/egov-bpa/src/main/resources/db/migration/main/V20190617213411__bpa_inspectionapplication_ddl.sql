
-- Inspection Application

create sequence seq_egbpa_inspection_application;
CREATE TABLE egbpa_inspection_application
(
  id bigint NOT NULL,
  applicationnumber character varying(64),
  applicationdate timestamp without time zone NOT NULL,
  status bigint not null,
  state_id bigint,
  remarks character varying(1000),
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_inspection_application PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_inspection_application_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_inspection_application_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_inspection_application_status FOREIGN KEY (status)
      REFERENCES EGBPA_STATUS (ID),
  CONSTRAINT fk_egbpa_inspection_application_state FOREIGN KEY (state_id)
      REFERENCES EG_WF_STATES (ID) 
);

alter table egbpa_inspection_application  add column version numeric DEFAULT 0;
alter table EGBPA_INSPECTION_APPLICATION  add column  approvalDate timestamp without time zone ;
alter table EGBPA_INSPECTION_APPLICATION add column issenttopreviousowner boolean default false;
alter table EGBPA_INSPECTION_APPLICATION add column townsurveyorremarks character varying(5000);
alter table egbpa_inspection_application add column istownsurveyorinspectionrequire boolean default false;


--PermitInspectionApplication

CREATE SEQUENCE SEQ_EGBPA_PERMIT_INSPECTIONAPPLICATION;
CREATE TABLE EGBPA_PERMIT_INSPECTIONAPPLICATION
(
  id bigint NOT NULL,
  inspectionapplication bigint NOT NULL,
  application bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_permit_inspectionappln PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_permit_inspappln FOREIGN KEY (inspectionapplication)
      REFERENCES egbpa_inspection_application (id),
  CONSTRAINT fk_egbpa_permit_inspectionappln FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_permit_inspnappln_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_permit_inspnappln_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);



--InspectionAppointmentSchedule

CREATE SEQUENCE seq_egbpa_inspection_appointment_schedule;
CREATE TABLE egbpa_inspection_appointment_schedule
(
  id bigint NOT NULL,
  appointmentScheduleCommon bigint NOT NULL,
  inspectionapplication bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_ins_appointment_schedule PRIMARY KEY(id),
  CONSTRAINT fk_egbpa_ins_appmnt_schdle_cmn FOREIGN KEY (appointmentScheduleCommon)
      REFERENCES egbpa_appointment_schedule_common (id),
  CONSTRAINT fk_egbpa_ins_appmnt_schdle_ins FOREIGN KEY (inspectionapplication)
      REFERENCES egbpa_inspection_application (id),
  CONSTRAINT fk_egbpa_ins_appmnt_schdle_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_ins_appmnt_schdle_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);


CREATE TABLE egbpa_ts_inspnappln_documents
(
  inspectionapplication bigint,
  fileStoreId bigint,
  CONSTRAINT fk_egbpa_tsinspnapplndocs_id FOREIGN KEY (inspectionapplication)
      REFERENCES egbpa_inspection_application (id),
  CONSTRAINT fk_egbpa_tsinspnapplndocs_filemapper FOREIGN KEY (fileStoreId)
      REFERENCES eg_filestoremap (id)
);

CREATE SEQUENCE SEQ_EGBPA_INSPECTION_SCHEDULE;
CREATE TABLE EGBPA_INSPECTION_SCHEDULE
(
  id bigint NOT NULL,
  inspectioncommon bigint NOT NULL,
  inspectionapplication bigint NOT NULL,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_inspection_schedule PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_inspection_inspn_cmn FOREIGN KEY (inspectioncommon)
      REFERENCES egbpa_inspection_common (id),
  CONSTRAINT fk_egbpa_inspn_appln FOREIGN KEY (inspectionapplication)
      REFERENCES egbpa_inspection_application (id),
  CONSTRAINT fk_egbpa_inspnappln_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_inspnappln_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);

