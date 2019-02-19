CREATE TABLE egbpa_inspectionnotice
(
  id bigint NOT NULL,
  applicationType character varying(128) NOT NULL,
  refNumber character varying(128) NOT NULL,
  inspectionNumber character varying(128) NOT NULL,
  noticefilestore bigint NOT NULL,
  noticegenerateddate date,
  CONSTRAINT pk_inspectionnotice_id PRIMARY KEY (id)
);

create sequence SEQ_EGBPA_INSPECTIONNOTICE;