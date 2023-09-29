-- Initially need to create database or schema using below :
CREATE SCHEMA `bookstore` ;


-- After Run application we need to run the below all Scripts into our mySql bookstore Database;
DROP TABLE if exists bookstore.book_audit;
CREATE TABLE bookstore.book_audit (
    book_id BIGINT NOT NULL,
    old_row_data JSON,
    new_row_data JSON,
    dml_type ENUM('INSERT', 'UPDATE', 'DELETE') NOT NULL,
    dml_timestamp DATETIME NOT NULL,
    dml_created_by VARCHAR(255) NOT NULL,
    PRIMARY KEY (book_id, dml_type, dml_timestamp)
);



-- This is insert Trigger Related
DROP TRIGGER if exists bookstore.book_insert_audit_trigger;
DELIMITER $$
CREATE TRIGGER bookstore.book_insert_audit_trigger
AFTER INSERT ON bookstore.book_store FOR EACH ROW
BEGIN
    INSERT INTO book_audit (
        book_id,
        old_row_data,
        new_row_data,
        dml_type,
        dml_timestamp,
        dml_created_by
    )
    VALUES(
        NEW.id,
        null,
        JSON_OBJECT(
            "title", NEW.title,
            "author", NEW.author,
            "price", NEW.price,
            "publisher", NEW.publisher,
            "createDateTime", NEW.create_date_time,
            "updateDateTime", NEW.update_date_time
        ),
        'INSERT',
        CURRENT_TIMESTAMP,
        system_user()
	);
END$$
DELIMITER ;


-- This is for Update Trigger related
DROP TRIGGER if exists bookstore.book_update_audit_trigger;
DELIMITER $$
CREATE TRIGGER bookstore.book_update_audit_trigger
AFTER UPDATE ON bookstore.book_store FOR EACH ROW
BEGIN
    INSERT INTO book_audit (
        book_id,
        old_row_data,
        new_row_data,
        dml_type,
        dml_timestamp,
        dml_created_by
    )
    VALUES(
        NEW.id,
        JSON_OBJECT(
            "title", OLD.title,
            "author", OLD.author,
            "price", OLD.price,
            "publisher", OLD.publisher,
            "createDateTime", OLD.create_date_time,
            "updateDateTime", OLD.update_date_time
        ),
        JSON_OBJECT(
            "title", NEW.title,
            "author", NEW.author,
            "price", NEW.price,
            "publisher", NEW.publisher,
            "createDateTime", NEW.create_date_time,
            "updateDateTime", NEW.update_date_time
        ),
        'UPDATE',
        CURRENT_TIMESTAMP,
        system_user()
	);
END$$
DELIMITER ;


-- This is for DELETE Trigger
DROP TRIGGER if exists bookstore.book_delete_audit_trigger;
DELIMITER $$
CREATE TRIGGER bookstore.book_delete_audit_trigger
AFTER DELETE ON bookstore.book_store FOR EACH ROW
BEGIN
    INSERT INTO book_audit (
        book_id,
        old_row_data,
        new_row_data,
        dml_type,
        dml_timestamp,
        dml_created_by
    )
    VALUES(
        OLD.id,
        JSON_OBJECT(
            "title", OLD.title,
            "author", OLD.author,
            "price", OLD.price,
            "publisher", OLD.publisher,
            "createDateTime", OLD.create_date_time,
            "updateDateTime", OLD.update_date_time
        ),
        null,
        'DELETE',
        CURRENT_TIMESTAMP,
        system_user()
	);
END$$
DELIMITER ;


