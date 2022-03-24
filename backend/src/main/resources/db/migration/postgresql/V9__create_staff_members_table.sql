CREATE TABLE IF NOT EXISTS migrations.staff_members(
    id SERIAL,
    account_id INTEGER,
    faculty_id INTEGER,

    PRIMARY KEY (id),
    CONSTRAINT FK_staff_members_account_id FOREIGN KEY (account_id) REFERENCES migrations.accounts (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT FK_staff_members_faculty_id FOREIGN KEY (faculty_id) REFERENCES migrations.faculties (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)