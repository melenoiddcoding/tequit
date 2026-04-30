-- 003_rls_policies.sql

alter table public.profiles enable row level security;
alter table public.train_status enable row level security;
alter table public.crossings enable row level security;
alter table public.train_events enable row level security;
alter table public.device_tokens enable row level security;
alter table public.alternate_routes enable row level security;

-- Helpers

create or replace function public.current_user_role()
returns text
language sql
security definer
set search_path = public
as $$
  select role from public.profiles where id = auth.uid()
$$;

-- profiles

create policy "Users can read own profile"
on public.profiles
for select
to authenticated
using (id = auth.uid());

create policy "Admins can read all profiles"
on public.profiles
for select
to authenticated
using (public.current_user_role() = 'admin');

create policy "Admins can update profiles"
on public.profiles
for update
to authenticated
using (public.current_user_role() = 'admin')
with check (public.current_user_role() = 'admin');

-- train_status

create policy "Authenticated users can read train status"
on public.train_status
for select
to authenticated
using (true);

create policy "Notifiers and admins can update train status"
on public.train_status
for update
to authenticated
using (public.current_user_role() in ('notifier', 'admin'))
with check (public.current_user_role() in ('notifier', 'admin'));

-- crossings

create policy "Authenticated users can read crossings"
on public.crossings
for select
to authenticated
using (true);

create policy "Notifiers and admins can update crossings"
on public.crossings
for update
to authenticated
using (public.current_user_role() in ('notifier', 'admin'))
with check (public.current_user_role() in ('notifier', 'admin'));

-- train_events

create policy "Authenticated users can read train events"
on public.train_events
for select
to authenticated
using (true);

create policy "Notifiers and admins can create train events"
on public.train_events
for insert
to authenticated
with check (
  public.current_user_role() in ('notifier', 'admin')
  and created_by = auth.uid()
);

-- device_tokens

create policy "Users can manage own device tokens"
on public.device_tokens
for all
to authenticated
using (user_id = auth.uid())
with check (user_id = auth.uid());

-- alternate_routes

create policy "Authenticated users can read alternate routes"
on public.alternate_routes
for select
to authenticated
using (is_active = true);

create policy "Admins can manage alternate routes"
on public.alternate_routes
for all
to authenticated
using (public.current_user_role() = 'admin')
with check (public.current_user_role() = 'admin');
