ALTER TABLE operations
ADD COLUMN operation_timestamp TIMESTAMPTZ NOT NULL DEFAULT current_timestamp;

COMMENT ON COLUMN operations.operation_timestamp IS 'Временная метка исполнения операции';