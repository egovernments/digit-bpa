
CREATE SEQUENCE seq_egbpa_inspection_letter_to_party;
CREATE TABLE egbpa_inspection_letter_to_party
(
  id bigint NOT NULL,
  letterToParty bigint NOT NULL,
  inspectionapplication bigint NOT NULL,
  isLPRequestInitiated boolean default false,
  createdby bigint NOT NULL,
  createddate timestamp without time zone NOT NULL,
  lastmodifiedby bigint,
  lastmodifieddate timestamp without time zone,
  version numeric DEFAULT 0,
  CONSTRAINT pk_egbpa_inspection_letter_to_party PRIMARY KEY (id),
  CONSTRAINT fk_egbpa_inspection_letter_to_party_cmn FOREIGN KEY (letterToParty)
      REFERENCES egbpa_lettertoparty_common (id),
  CONSTRAINT fk_egbpa_inspection_letter_to_party_appln FOREIGN KEY (inspectionapplication)
      REFERENCES egbpa_inspection_application (id),
  CONSTRAINT fk_egbpa_inspection_letter_to_party_crtby FOREIGN KEY (createdby)
      REFERENCES state.eg_user (id),
  CONSTRAINT fk_egbpa_inspection_letter_to_party_mdfdby FOREIGN KEY (lastmodifiedby)
      REFERENCES state.eg_user (id)
);