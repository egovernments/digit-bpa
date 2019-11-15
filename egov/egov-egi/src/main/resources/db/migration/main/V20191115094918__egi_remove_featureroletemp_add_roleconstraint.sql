INSERT INTO eg_feature_role (feature, role)
SELECT feature, role FROM TEMP_FEATURE_ROLE;

DROP TABLE TEMP_FEATURE_ROLE;

ALTER TABLE  eg_feature_role ADD CONSTRAINT fk_feature_role FOREIGN KEY (role) REFERENCES state.eg_role (id);