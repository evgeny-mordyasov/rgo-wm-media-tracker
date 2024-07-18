CREATE ROLE owner_role_${userOwner};
GRANT CREATE ON DATABASE ${database} TO owner_role_${userOwner};
GRANT CREATE ON TABLESPACE ${tbs} TO owner_role_${userOwner};

CREATE USER ${userOwner} WITH PASSWORD '${userOwnerPass}';
GRANT owner_role_${userOwner} TO ${userOwner};

ALTER USER ${userOwner} SET default_tablespace = ${tbs};
