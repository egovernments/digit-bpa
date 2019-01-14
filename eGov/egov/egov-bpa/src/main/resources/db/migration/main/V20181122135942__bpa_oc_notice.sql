create sequence seq_egbpa_notice_common;
create table egbpa_notice_common(
 id bigint NOT NULL,
 noticegenerateddate date,
 noticefilestore bigint,
 noticetype character varying(128),
 createdby bigint NOT NULL,
 createddate timestamp without time zone NOT NULL,
 lastmodifieddate timestamp without time zone,
 lastmodifiedby bigint,
 version numeric NOT NULL,
 CONSTRAINT pk_notice_common PRIMARY KEY (id),
 CONSTRAINT fk_notice_cmn_noticefilestore FOREIGN KEY (noticefilestore)
 REFERENCES eg_filestoremap (id),
 CONSTRAINT fk_notice_cmn_crtby FOREIGN KEY (createdby)
 REFERENCES eg_user (id),
 CONSTRAINT fk_notice_cmn_mdfdby FOREIGN KEY (lastmodifiedby)
 REFERENCES eg_user (id)
);

create sequence seq_egbpa_oc_notice;
create table egbpa_oc_notice(
id bigint NOT NULL,
occupancycertificate bigint NOT NULL,
notice bigint NOT NULL,
 createdby bigint NOT NULL,
 createddate timestamp without time zone NOT NULL,
 lastmodifieddate timestamp without time zone,
 lastmodifiedby bigint,
 version numeric NOT NULL,
 CONSTRAINT pk_notice_oc PRIMARY KEY (id),
 CONSTRAINT fk_notice_oc FOREIGN KEY (occupancycertificate)
 REFERENCES egbpa_occupancy_certificate (id),
 CONSTRAINT fk_notice_cmn FOREIGN KEY (notice)
 REFERENCES egbpa_notice_common (id),
 CONSTRAINT fk_notice_oc_crtby FOREIGN KEY (createdby)
 REFERENCES eg_user (id),
 CONSTRAINT fk_notice_oc_mdfdby FOREIGN KEY (lastmodifiedby)
 REFERENCES eg_user (id)
);