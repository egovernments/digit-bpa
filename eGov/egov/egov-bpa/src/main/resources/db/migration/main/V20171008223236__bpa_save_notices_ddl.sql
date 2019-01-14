CREATE TABLE egbpa_notice
(
  id bigint NOT NULL,
  application bigint NOT NULL,
  noticefilestore bigint NOT NULL,
  noticegenerateddate date,
  noticetype character varying(128),
  CONSTRAINT pk_notice_id PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_notice_application FOREIGN KEY (application)
      REFERENCES egbpa_application (id)
);

create sequence SEQ_EGBPA_NOTICE;