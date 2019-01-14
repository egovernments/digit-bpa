create sequence seq_egbpa_mstr_appointment_location;

CREATE TABLE egbpa_mstr_appointment_location
(
  id bigint NOT NULL,
  code character varying(32),
  description character varying(160) NOT NULL,
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_appointment_location_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_mstr_appointment_location_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_mstr_appointment_location_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);


INSERT INTO egbpa_mstr_appointment_location(
            id, code, description, ordernumber, version, createdby, createddate, lastmodifiedby, 
            lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_appointment_location'), 'KOKDEC', 'Kozhikode​ Corporation',1, 0, 1, now(), 1, 
            now());

INSERT INTO egbpa_mstr_appointment_location(
            id, code, description, ordernumber, version, createdby, createddate, lastmodifiedby, 
            lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_appointment_location'), 'BEYPRZO', 'Beypore Zonal Office',2, 0, 1, now(), 1, 
            now());

INSERT INTO egbpa_mstr_appointment_location(
            id, code, description, ordernumber, version, createdby, createddate, lastmodifiedby, 
            lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_appointment_location'), 'ELATRZO', 'Elathoor Zonal Office',3, 0, 1, now(), 1, 
            now());

INSERT INTO egbpa_mstr_appointment_location(
            id, code, description, ordernumber, version, createdby, createddate, lastmodifiedby, 
            lastmodifieddate)
    VALUES (nextval('seq_egbpa_mstr_appointment_location'), 'CHRUNRZO', 'Cheruvannur Zonal Office​',4, 0, 1, now(), 1, 
            now());

alter table egbpa_appointment_schedule alter column appointmentdate type date USING appointmentdate::date;

alter table egbpa_appointment_schedule alter column appointmentlocation type BIGINT USING appointmentlocation::BIGINT;

ALTER TABLE egbpa_appointment_schedule ADD CONSTRAINT fk_egbpa_appointment_schedule FOREIGN KEY (appointmentlocation) REFERENCES egbpa_mstr_appointment_location (id);