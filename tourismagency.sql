PGDMP         .                |            trouismagency    13.13    13.13 "    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16964    trouismagency    DATABASE     k   CREATE DATABASE trouismagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Turkish_T�rkiye.1254';
    DROP DATABASE trouismagency;
                postgres    false            �            1259    16977    hotel    TABLE     �  CREATE TABLE public.hotel (
    id integer NOT NULL,
    name text NOT NULL,
    address text NOT NULL,
    mail text NOT NULL,
    phone text NOT NULL,
    star text NOT NULL,
    car_park boolean NOT NULL,
    wifi boolean NOT NULL,
    pool boolean NOT NULL,
    fitness boolean NOT NULL,
    concierge boolean NOT NULL,
    spa boolean NOT NULL,
    room_service boolean NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    16975    hotel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    203            �            1259    16987    hotel_pension    TABLE     ~   CREATE TABLE public.hotel_pension (
    id integer NOT NULL,
    hotel_id integer NOT NULL,
    pension_type text NOT NULL
);
 !   DROP TABLE public.hotel_pension;
       public         heap    postgres    false            �            1259    16985    hotel_pension_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_pension ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    205            �            1259    16997    hotel_season    TABLE     �   CREATE TABLE public.hotel_season (
    id integer NOT NULL,
    hotel_id integer NOT NULL,
    start_date date NOT NULL,
    finish_date date NOT NULL
);
     DROP TABLE public.hotel_season;
       public         heap    postgres    false            �            1259    16995    hotel_season_id_seq    SEQUENCE     �   ALTER TABLE public.hotel_season ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.hotel_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    207            �            1259    17014    reservation    TABLE     j  CREATE TABLE public.reservation (
    id integer NOT NULL,
    room_id integer NOT NULL,
    check_in_date date NOT NULL,
    check_out_date date NOT NULL,
    total_price double precision NOT NULL,
    guest_count integer NOT NULL,
    guest_name text NOT NULL,
    guest_citizen_id text NOT NULL,
    guest_mail text NOT NULL,
    guest_phone text NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    17012    reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    211            �            1259    17004    room    TABLE       CREATE TABLE public.room (
    id integer NOT NULL,
    hotel_id integer NOT NULL,
    pension_id integer NOT NULL,
    season_id integer NOT NULL,
    type text NOT NULL,
    stock integer NOT NULL,
    adult_price double precision NOT NULL,
    child_price double precision NOT NULL,
    bed_capacity integer NOT NULL,
    square_meter integer NOT NULL,
    television boolean NOT NULL,
    minibar boolean NOT NULL,
    game_console boolean NOT NULL,
    cash_box boolean NOT NULL,
    projection boolean NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    17002    room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    209            �            1259    16967    user    TABLE     �   CREATE TABLE public."user" (
    user_id bigint NOT NULL,
    user_name text NOT NULL,
    user_pass text NOT NULL,
    user_role text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16965    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    201            �          0    16977    hotel 
   TABLE DATA           �   COPY public.hotel (id, name, address, mail, phone, star, car_park, wifi, pool, fitness, concierge, spa, room_service) FROM stdin;
    public          postgres    false    203   (       �          0    16987    hotel_pension 
   TABLE DATA           C   COPY public.hotel_pension (id, hotel_id, pension_type) FROM stdin;
    public          postgres    false    205   {(       �          0    16997    hotel_season 
   TABLE DATA           M   COPY public.hotel_season (id, hotel_id, start_date, finish_date) FROM stdin;
    public          postgres    false    207   �(       �          0    17014    reservation 
   TABLE DATA           �   COPY public.reservation (id, room_id, check_in_date, check_out_date, total_price, guest_count, guest_name, guest_citizen_id, guest_mail, guest_phone) FROM stdin;
    public          postgres    false    211   )       �          0    17004    room 
   TABLE DATA           �   COPY public.room (id, hotel_id, pension_id, season_id, type, stock, adult_price, child_price, bed_capacity, square_meter, television, minibar, game_console, cash_box, projection) FROM stdin;
    public          postgres    false    209   )       �          0    16967    user 
   TABLE DATA           J   COPY public."user" (user_id, user_name, user_pass, user_role) FROM stdin;
    public          postgres    false    201   �)       �           0    0    hotel_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.hotel_id_seq', 2, true);
          public          postgres    false    202            �           0    0    hotel_pension_id_seq    SEQUENCE SET     B   SELECT pg_catalog.setval('public.hotel_pension_id_seq', 7, true);
          public          postgres    false    204            �           0    0    hotel_season_id_seq    SEQUENCE SET     A   SELECT pg_catalog.setval('public.hotel_season_id_seq', 3, true);
          public          postgres    false    206            �           0    0    reservation_id_seq    SEQUENCE SET     @   SELECT pg_catalog.setval('public.reservation_id_seq', 4, true);
          public          postgres    false    210            �           0    0    room_id_seq    SEQUENCE SET     9   SELECT pg_catalog.setval('public.room_id_seq', 3, true);
          public          postgres    false    208            �           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 2, true);
          public          postgres    false    200            J           2606    16994     hotel_pension hotel_pension_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.hotel_pension
    ADD CONSTRAINT hotel_pension_pkey PRIMARY KEY (id);
 J   ALTER TABLE ONLY public.hotel_pension DROP CONSTRAINT hotel_pension_pkey;
       public            postgres    false    205            H           2606    16984    hotel hotel_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT hotel_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.hotel DROP CONSTRAINT hotel_pkey;
       public            postgres    false    203            L           2606    17001    hotel_season hotel_season_pkey 
   CONSTRAINT     \   ALTER TABLE ONLY public.hotel_season
    ADD CONSTRAINT hotel_season_pkey PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.hotel_season DROP CONSTRAINT hotel_season_pkey;
       public            postgres    false    207            P           2606    17021    reservation reservation_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    211            N           2606    17011    room room_pkey 
   CONSTRAINT     L   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    209            F           2606    16974    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    201            �   R   x�3�,H,��N��,.I�K*́��s3s���s99M9K�0Drq���p:�e'%��H���8M��� ڸb���� H-J      �   O   x�3�4�NLIMN�L,I��2�Cs�3+���8�8�S�3�sJ2�́�9%E��Eũ�.��9\1z\\\ 8�      �   +   x�3�4�4202�50�5@bqsA���@eW� �	S      �   Z   x�M�;
�0D�ٻ$�'k�!,mDD���?�!ݛ�ʚK`�(wf(���H����u���E��]~&�:ðT�j�fۨ�����C�      �   f   x�5��@@D�ӯ��.�G88p���fݬ�7,�L�7��P����p�S�g��S$j$���!���ܯKn���?M�b����x})%��}%S)"7��      �   %   x�3�LL����<.#��T0�-�ɯLM����� ��	�     