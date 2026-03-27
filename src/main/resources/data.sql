-- ROOT tylko raz
INSERT INTO category (name)
SELECT 'Elektronika'
WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE name = 'Elektronika'
);

-- CHILD
INSERT INTO category (name, parent_id)
SELECT 'Komputery', c.id
FROM category c
WHERE c.name = 'Elektronika'
LIMIT 1;

INSERT INTO category (name, parent_id)
SELECT 'Telefony', c.id
FROM category c
WHERE c.name = 'Elektronika'
LIMIT 1;