insert into attendance_records (
  date,
  day_of_week,
  start_time,
  end_time,
  work_hours,
  start_time_holiday,
  end_time_holiday,
  work_hours_holiday,
  day_type,
  holiday_name
)
values
  ( '2024-10-20', '6', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', ''),
  ( '2024-10-21', '6', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', ''),
  ( '2024-11-02', '6', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', ''),
  ( '2024-11-20', '6', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', ''),
  ( '2024-11-21', '6', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', '')
;

--

insert into periods (
  period,
  start_date,
  end_date,
  work_hours_month,
  work_hours_month_holiday,
  created_at
)
values
  ('10', '2024-09-21', '2024-10-20', 100, 10, CURRENT_TIMESTAMP(0)),
  ('11', '2024-10-21', '2024-11-20', 150, 0, CURRENT_TIMESTAMP(0))
;