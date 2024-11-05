create table attendance_records (
  id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  request_date DATE NOT NULL,
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  status CHAR(1) NOT NULL,
  create_time TIMESTAMP,
  update_time TIMESTAMP
);
