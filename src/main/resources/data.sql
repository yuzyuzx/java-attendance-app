insert into attendance_records (
  `date`,
  `month`,
  `day`,
  day_of_week,
  start_time,
  end_time,
  work_hours,
  start_time_holiday,
  end_time_holiday,
  work_hours_holiday,
  day_type,
  `comment`,
  holiday_name
)
values
  ( '2024-10-20', '10', '20', '月', '9:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', 'comment', ''),
  ( '2024-10-21', '10', '21', '火', '10:30:00', '19:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', '', ''),
  ( '2024-11-02', '11', '02', '土', '08:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '1', '', ''),
  ( '2024-11-20', '11', '20', '日', '00:00:00', '00:00:00', 7.5, '10:00:00', '16:00:00', 8.5, '2', 'comment', ''),
  ( '2024-11-21', '11', '21', '水', '09:30:00', '18:00:00', 7.5, '00:00:00', '00:00:00', 0, '0', '', '')
;

--

insert into monthly_period (
  period,
  start_date,
  end_date,
  work_hours_month,
  work_hours_month_holiday,
  created_at
)
values
  ('202410', '2024-09-21', '2024-10-20', 100, 10, CURRENT_TIMESTAMP(0)),
  ('202411', '2024-10-21', '2024-11-20', 150, 0, CURRENT_TIMESTAMP(0))
;

--

insert into approval (
  `period`,
  `status`,
  `requested_at`,
  `reviewed_at`
)
values
  ('202410', '1', '2024-10-22 00:00:00', '2024-10-22 12:00:00'),
  ('202411', '1', '1900-01-01 00:00:00', '1900-01-01 00:00:00')
;