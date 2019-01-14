create sequence seq_egbpa_slot;

create table egbpa_slot
(
id bigint NOT NULL,
zone bigint NOT NULL,
appointmentdate date NOT NULL,
createdby bigint NOT NULL,
createddate timestamp without time zone NOT NULL,
lastmodifiedby bigint,
lastmodifieddate timestamp without time zone,
version numeric DEFAULT 0,
constraint pk_egbpa_slot PRIMARY KEY(id),
  CONSTRAINT fk_egbpa_slot_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_slot_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_slotdetail;

create table egbpa_slotdetail
(
id bigint NOT NULL,
appointmenttime character varying(50) NOT NULL,
maxscheduledslots integer NOT NULL,
maxrescheduledslots integer NOT NULL,
utilizedscheduledslots integer NOT NULL,
utilizedRescheduledSlots integer NOT NULL,
slotid bigint NOT NULL,
createdby bigint NOT NULL,
createddate timestamp without time zone NOT NULL,
lastmodifiedby bigint,
lastmodifieddate timestamp without time zone,
version numeric DEFAULT 0,
constraint PK_egbpa_slotdetail PRIMARY KEY(id),
constraint fk_egbpa_slotdetail_slot FOREIGN KEY (slotid) REFERENCES egbpa_slot(id),
  CONSTRAINT fk_egbpa_slotdetail_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_slotdetail_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);

create sequence seq_egbpa_slotapplication;

create table egbpa_slotapplication
(
id bigint NOT NULL,
application bigint NOT NULL,
scheduleappointmenttype character varying(50) NOT NULL,
isRescheduledByCitizen boolean,
isRescheduledByEmployee boolean,
slotdetailid bigint NOT NULL,
createdby bigint NOT NULL,
createddate timestamp without time zone NOT NULL,
lastmodifiedby bigint,
lastmodifieddate timestamp without time zone,
version numeric DEFAULT 0,
constraint pk_egbpa_slotapplication PRIMARY KEY(id),
constraint fk_egbpa_slotapplication_slotdetail FOREIGN KEY (slotdetailid) REFERENCES egbpa_slotdetail(id),
constraint fk_egbpa_slotapplication_bpaapplication FOREIGN KEY (application) REFERENCES egbpa_application(id),
  CONSTRAINT fk_egbpa_slot_crtby FOREIGN KEY (createdby)
      REFERENCES eg_user (id),
  CONSTRAINT fk_egbpa_slot_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES eg_user (id)
);



create sequence seq_egbpa_mstr_slotmapping;

create table egbpa_mstr_slotmapping
(
id bigint NOT NULL,
zone bigint NOT NULL,
ward bigint,
day character varying(10),
applicationtype character varying(128) NOT NULL,
maxslotsallowed bigint NOT NULL,
maxrescheduledslotsallowed bigint NOT NULL,
createdby bigint NOT NULL,
createddate timestamp without time zone NOT NULL,
lastmodifiedby bigint,
lastmodifieddate timestamp without time zone,
version numeric DEFAULT 0,
constraint PK_egbpa_mstr_slotmpng PRIMARY KEY(id),
CONSTRAINT fk_egbpa_mstr_slotmpng_crtby FOREIGN KEY (createdby)
REFERENCES eg_user (id),
CONSTRAINT fk_egbpa_mstr_slotmpng_mdfdby FOREIGN KEY (lastmodifiedby)
REFERENCES eg_user (id),
CONSTRAINT fk_egbpa_mstr_slotmpng_zone FOREIGN KEY (zone)
REFERENCES eg_boundary (id),
CONSTRAINT fk_egbpa_mstr_slotmpng_ward FOREIGN KEY (ward)
REFERENCES eg_boundary (id)
);

 