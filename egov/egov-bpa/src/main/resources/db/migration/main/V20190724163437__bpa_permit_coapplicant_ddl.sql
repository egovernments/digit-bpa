create sequence seq_egbpa_coapplicant;
CREATE TABLE egbpa_coapplicant
(
  id bigint NOT NULL,
  application bigint NOT NULL,
  title character varying(24),
  name character varying(100),
  fatherorhusbandname character varying(128),
  dateofbirth timestamp without time zone,
  district character varying(128),
  taluk character varying(128),
  area character varying(128),
  city character varying(128),
  state character varying(128),
  pincode character varying(6),
  address character varying(1024),
  gender character varying(30),
  aadhaarnumber character varying(20),
  emailid character varying(128),
  mobileNumber character varying(15),
  version numeric NOT NULL DEFAULT 0,
  createdby bigint NOT NULL,
  createddate timestamp without time zone,
  lastmodifiedby bigint NOT NULL,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_coapplicant_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_coapplicant_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
  CONSTRAINT fk_egbpa_coapplicant_createdby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_coapplicant_lastmodifiedby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);