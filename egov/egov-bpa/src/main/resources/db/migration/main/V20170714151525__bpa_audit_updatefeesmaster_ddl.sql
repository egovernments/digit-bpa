CREATE TABLE egbpa_mstr_bpafeedetail_aud
(
  id bigint NOT NULL,
  rev bigint NOT NULL,
  bpafee bigint NOT NULL,
  fromareasqmt double precision,
  toareasqmt double precision,
  amount double precision NOT NULL,
  subtype character varying(128),
  landusezone character varying(128),
  floornumber bigint,
  usagetype bigint,
  startdate date NOT NULL,
  enddate date,
  additionaltype character varying(128),
  revtype numeric,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  CONSTRAINT pk_egbpa_mstr_bpafeedetail_aud PRIMARY KEY (id,rev)
);
