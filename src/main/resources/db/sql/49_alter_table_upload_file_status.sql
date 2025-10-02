ALTER TABLE upload_file_status
  ADD COLUMN `is_enough`    TINYINT NULL,
  DROP COLUMN record_status;