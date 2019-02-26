CREATE TABLE egbpa_permit_occupancies
(
  application bigint NOT NULL,
  occupancy bigint NOT NULL,
  CONSTRAINT fk_egbpa_permitappln_occupancy FOREIGN KEY (application)
      REFERENCES egbpa_application (id),
CONSTRAINT fk_egbpa_permit_occupancy FOREIGN KEY (occupancy)
      REFERENCES egbpa_occupancy (id)
);