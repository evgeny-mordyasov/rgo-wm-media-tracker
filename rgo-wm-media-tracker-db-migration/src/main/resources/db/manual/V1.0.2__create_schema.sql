CREATE SCHEMA ${userOwner}
AUTHORIZATION owner_role_${userOwner};

ALTER DATABASE ${database}
           SET search_path TO ${userOwner}, public;
