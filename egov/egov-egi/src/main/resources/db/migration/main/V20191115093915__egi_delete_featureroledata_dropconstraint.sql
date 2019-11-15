---- create feature role temp table

CREATE TABLE IF NOT EXISTS TEMP_FEATURE_ROLE(
feature bigint,
role bigint
);

INSERT INTO TEMP_FEATURE_ROLE (feature, role)
SELECT feature, role FROM eg_feature_role GROUP BY feature, role;

DELETE FROM eg_feature_role;

ALTER TABLE eg_feature_role DROP CONSTRAINT fk_feature_role ;