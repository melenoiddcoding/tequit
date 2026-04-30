-- 001_initial_schema.sql

create extension if not exists "pgcrypto";

-- Roles permitidos:
-- user, notifier, admin

create table if not exists public.profiles (
  id uuid primary key references auth.users(id) on delete cascade,
  email text not null,
  role text not null check (role in ('user', 'notifier', 'admin')),
  created_at timestamptz not null default now()
);

create table if not exists public.train_status (
  id uuid primary key default gen_random_uuid(),
  direction text check (direction in ('entering', 'leaving')),
  operational_status text not null check (
    operational_status in ('passing', 'blocked', 'maneuvering', 'stopped', 'outside_city')
  ),
  reference_point text check (reference_point in ('la_cantera', 'pantanal')),
  note text,
  updated_by uuid references public.profiles(id),
  updated_at timestamptz not null default now()
);

create table if not exists public.crossings (
  id uuid primary key default gen_random_uuid(),
  name text not null unique,
  status text not null check (status in ('free', 'passing', 'blocked', 'maneuvering')),
  updated_by uuid references public.profiles(id),
  updated_at timestamptz not null default now()
);

create table if not exists public.train_events (
  id uuid primary key default gen_random_uuid(),
  event_type text not null check (
    event_type in (
      'train_entering',
      'train_leaving',
      'maneuver_started',
      'maneuver_finished',
      'crossing_passing',
      'crossing_blocked',
      'crossing_maneuvering',
      'crossing_free'
    )
  ),
  crossing_id uuid references public.crossings(id),
  direction text check (direction in ('entering', 'leaving')),
  reference_point text check (reference_point in ('la_cantera', 'pantanal')),
  note text,
  target_role text not null default 'user' check (target_role in ('user', 'notifier', 'admin')),
  created_by uuid not null references public.profiles(id),
  created_at timestamptz not null default now()
);

create table if not exists public.device_tokens (
  id uuid primary key default gen_random_uuid(),
  user_id uuid not null references public.profiles(id) on delete cascade,
  role text not null check (role in ('user', 'notifier', 'admin')),
  fcm_token text not null,
  platform text not null default 'android',
  created_at timestamptz not null default now(),
  last_seen_at timestamptz not null default now(),
  unique(user_id, fcm_token)
);

create table if not exists public.alternate_routes (
  id uuid primary key default gen_random_uuid(),
  crossing_id uuid not null references public.crossings(id) on delete cascade,
  title text not null,
  description text not null,
  priority int not null default 1,
  is_active boolean not null default true,
  created_at timestamptz not null default now()
);
