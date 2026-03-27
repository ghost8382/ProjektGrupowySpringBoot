INSERT INTO category (name)
VALUES ('Elektronika');

INSERT INTO category (name, parent_id)
VALUES (
           'Komputery',
           (SELECT id FROM category WHERE name = 'Elektronika')
       );
INSERT INTO category(name, parent_id)
VALUES (
        'Stacjonarne',
        (SELECT id from category WHERE name = 'Komputery')
       )
INSERT INTO category (name)
VALUES ('Dom i Ogród');

