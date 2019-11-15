

CREATE TABLE IF NOT EXISTS TEMP_EG_ROLEACTION(
actionid bigint,
roleid bigint
);

INSERT INTO TEMP_EG_ROLEACTION (actionid, roleid)
SELECT actionid, roleid FROM eg_roleaction GROUP BY actionid, roleid;

DELETE FROM eg_roleaction;

ALTER TABLE eg_roleaction DROP CONSTRAINT fk_role_id ;