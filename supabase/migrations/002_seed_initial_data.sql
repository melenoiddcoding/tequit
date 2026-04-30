-- 002_seed_initial_data.sql

insert into public.crossings (name, status)
values
  ('Blvd Gobernadores', 'free'),
  ('Av Principal', 'free'),
  ('Colosio', 'free'),
  ('Quevedeño', 'free'),
  ('Av Guadalajara', 'free'),
  ('El Koa', 'free')
on conflict (name) do nothing;

insert into public.train_status (
  operational_status,
  direction,
  reference_point,
  note
)
values (
  'outside_city',
  null,
  null,
  'Estado inicial'
);

insert into public.alternate_routes (
  crossing_id,
  title,
  description,
  priority,
  is_active
)
select
  id,
  'Ruta alterna por 2 de Agosto / Colosio',
  'Si Blvd Gobernadores está bloqueado, considera tomar 2 de Agosto hacia Colosio como ruta alterna.',
  1,
  true
from public.crossings
where name = 'Blvd Gobernadores';
