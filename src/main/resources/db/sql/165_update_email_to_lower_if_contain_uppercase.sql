update cms_users
set email = LOWER(email)
WHERE CAST(LOWER(email) AS BINARY) <> CAST(email AS BINARY);