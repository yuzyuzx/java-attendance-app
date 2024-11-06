create table attendance_records (
  record_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  date DATE NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  work_hours FLOAT NOT NULL,
  start_time_holiday TIME NOT NULL,
  end_time_holiday TIME NOT NULL,
  work_hours_holiday FLOAT NOT NULL,
  day_type CHAR(1) NOT NULL,
  holiday_name VARCHAR(255)
);

--

create table periods (
  period_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  period VARCHAR(255),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  work_hours_month FLOAT NOT NULL,
  work_hours_month_holiday FLOAT NOT NULL,
  created_at TIMESTAMP
)