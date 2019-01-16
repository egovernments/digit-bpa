create sequence seq_egbpa_mstr_registrar_office;

CREATE TABLE egbpa_mstr_registrar_office
(
  id bigint NOT NULL,
  name character varying(128),
  isactive boolean DEFAULT true,
  ordernumber bigint,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_registrar_office_id PRIMARY KEY (id)
);


create sequence seq_egbpa_mstr_registrar_village;

CREATE TABLE egbpa_mstr_registrar_village
(
  id bigint NOT NULL,
  registrarOffice bigint NOT NULL,
  village bigint NOT NULL,
  isactive boolean DEFAULT true,
  version numeric DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_registrar_village_id PRIMARY KEY (id),
   CONSTRAINT fk_egbpa_mstr_village_registrar FOREIGN KEY (registrarOffice)
      REFERENCES egbpa_mstr_registrar_office (id),
   CONSTRAINT fk_egbpa_mstr_registrar_village FOREIGN KEY (village)
      REFERENCES eg_boundary (id)
);
