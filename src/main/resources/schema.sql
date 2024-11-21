create table attendance_records (
  `date` DATE NOT NULL,
  `month` VARCHAR(10) NOT NULL,
  `day` VARCHAR(10) NOT NULL,
  `day_of_week` CHAR(1) NOT NULL,
  `start_time` VARCHAR(10) NOT NULL,
  `end_time` VARCHAR(10) NOT NULL,
  `work_hours` DOUBLE NOT NULL,
  `start_time_holiday` VARCHAR(10) NOT NULL,
  `end_time_holiday` VARCHAR(10) NOT NULL,
  `work_hours_holiday` DOUBLE NOT NULL,
  `day_type` CHAR(1) NOT NULL,
  `comment` VARCHAR(255),
  `holiday_name` VARCHAR(255)
);

--

create table monthly_period (
  `period` VARCHAR(10),
  `start_date` DATE NOT NULL,
  `end_date` DATE NOT NULL,
  `work_hours_month` DOUBLE NOT NULL,
  `work_hours_month_holiday` DOUBLE NOT NULL,
  `created_at` TIMESTAMP NOT NULL
);

--

create table approval (
  `period` VARCHAR(10) NOT NULL,
  `status` CHAR(1) NOT NULL,
  `requested_at` TIMESTAMP NOT NULL,
  `reviewed_at` TIMESTAMP NOT NULL,
  `created_at` TIMESTAMP NOT NULL
);
