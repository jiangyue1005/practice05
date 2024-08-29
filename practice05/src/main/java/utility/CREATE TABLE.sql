--運勢マスタ
CREATE TABLE IF NOT EXISTS unsei_mst (
  unsei_code character varying(2) not null
  , unsei_name character varying(8) not null
  , register_datetime timestamp(6) without time zone
  , register_user character varying(8)
  , update_datetime timestamp(6) without time zone
  , update_user character varying(8)
  , primary key (unsei_code)
);

comment on table unsei_mst is '運勢マスタ';
comment on column unsei_mst.unsei_code is '運勢コード';
comment on column unsei_mst.unsei_name is '運勢名';
comment on column unsei_mst.register_datetime is '登録日時';
comment on column unsei_mst.register_user is '登録ユーザ';
comment on column unsei_mst.update_datetime is '更新日時';
comment on column unsei_mst.update_user is '更新ユーザ';


--おみくじ
CREATE TABLE IF NOT EXISTS omikuji (
  omikuji_code character varying(10) not null
  , unsei_code character varying(10)
  , negaigoto character varying(100) not null
  , akinai character varying(100) not null
  , gakumon character varying(100) not null
  , register_datetime timestamp(6) without time zone
  , register_user character varying(8)
  , update_datetime timestamp(6) without time zone
  , update_user character varying(8)
  , primary key (omikuji_code)
);

comment on table omikuji is 'おみくじ';
comment on column omikuji.omikuji_code is 'おみくじコード';
comment on column omikuji.unsei_code is '運勢コード';
comment on column omikuji.negaigoto is '願い事';
comment on column omikuji.akinai is '商い';
comment on column omikuji.gakumon is '学問';
comment on column omikuji.register_datetime is '登録日時';
comment on column omikuji.register_user is '登録ユーザ';
comment on column omikuji.update_datetime is '更新日時';
comment on column omikuji.update_user is '更新ユーザ';


--占い結果
CREATE TABLE IF NOT EXISTS result (
  uranai_date date not null
  , birthday date not null
  , omikuji_code character varying(3) not null
  , register_datetime timestamp(6) without time zone
  , register_user character varying(8)
  , update_datetime timestamp(6) without time zone
  , update_user character varying(8)
);
comment on table result is '占い結果';
comment on column result.uranai_date is '占い日付';
comment on column result.birthday is '誕生日';
comment on column result.omikuji_code is 'おみくじコード';
comment on column result.register_datetime is '登録日時';
comment on column result.register_user is '登録ユーザ';
comment on column result.update_datetime is '更新日時';
comment on column result.update_user is '更新ユーザ';
