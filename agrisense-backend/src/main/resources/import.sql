-- import.sql placeholder for quarkus.hibernate-orm.sql-load-script
-- Add initial SQL inserts here if needed for development

-- Insert test farmer
INSERT INTO farmers (id, username, email) VALUES (1, 'john_farmer', 'john@agrisense.io');

-- Insert test field
INSERT INTO fields (id, name, location, farmer_id) VALUES (1, 'North Field', 'GPS: 40.7128,-74.0060', 1);

-- Insert test sensors
INSERT INTO sensors (id, name, api_key, type, field_id) VALUES (1, 'Temperature Sensor 1', 'test-api-key-123', 'TEMPERATURE', 1);
INSERT INTO sensors (id, name, api_key, type, field_id) VALUES (2, 'Moisture Sensor 1', 'test-api-key-456', 'MOISTURE', 1);