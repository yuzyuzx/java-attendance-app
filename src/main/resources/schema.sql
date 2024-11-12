create table attendance_records (
  record_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  `date` DATE NOT NULL,
  `period` VARCHAR(10),
  day_of_week CHAR(1) NOT NULL,
  start_time TIME NOT NULL,
  end_time TIME NOT NULL,
  work_hours DOUBLE NOT NULL,
  start_time_holiday TIME NOT NULL,
  end_time_holiday TIME NOT NULL,
  work_hours_holiday DOUBLE NOT NULL,
  day_type CHAR(1) NOT NULL,
  comment VARCHAR(255),
  holiday_name VARCHAR(255)
);

--

create table monthly_attendance (
  period_id BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
  period VARCHAR(10),
  start_date DATE NOT NULL,
  end_date DATE NOT NULL,
  work_hours_month FLOAT NOT NULL,
  work_hours_month_holiday FLOAT NOT NULL,
  created_at TIMESTAMP NOT NULL
);

--

create table approval (
  `period` VARCHAR(10) NOT NULL,
  `status` CHAR(1) NOT NULL,
  `requested_at` TIMESTAMP NOT NULL,
  `reviewed_at` TIMESTAMP NOT NULL
)