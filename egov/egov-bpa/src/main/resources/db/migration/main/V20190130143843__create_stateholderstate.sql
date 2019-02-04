CREATE SEQUENCE seq_stakeholder_state;

create table EGBPA_STAKEHOLDER_STATE
( 
 id bigint NOT NULL,
 stakeholder bigint not null,
 STATE_ID   bigint,
 CREATEDBY bigint NOT NULL,
 createdDate timestamp without time zone,
 lastModifiedBy bigint,
 lastModifiedDate  timestamp without time zone,
 version numeric DEFAULT 0
);
