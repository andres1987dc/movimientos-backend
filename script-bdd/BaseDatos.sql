--
-- PostgreSQL database dump
--

-- Dumped from database version 14.5
-- Dumped by pg_dump version 14.5

-- Started on 2022-10-10 07:01:36

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3343 (class 1262 OID 16394)
-- Name: movimientosbdd; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE movimientosbdd WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'Spanish_Ecuador.1252';


ALTER DATABASE movimientosbdd OWNER TO postgres;

\connect movimientosbdd

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3344 (class 0 OID 0)
-- Dependencies: 3343
-- Name: DATABASE movimientosbdd; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON DATABASE movimientosbdd IS 'base de datos de movimientos';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 211 (class 1259 OID 24743)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    identificacion character varying(13) NOT NULL,
    direccion character varying(100) NOT NULL,
    edad smallint NOT NULL,
    genero character varying(20) NOT NULL,
    nombre character varying(50) NOT NULL,
    telefono character varying(15) NOT NULL,
    contrasena character varying(20) NOT NULL,
    estado boolean NOT NULL
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 24748)
-- Name: cuenta; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuenta (
    numero character varying(10) NOT NULL,
    estado boolean NOT NULL,
    saldo_inicial numeric(19,2) NOT NULL,
    tipo character varying(10) NOT NULL,
    id_cliente character varying(13) NOT NULL
);


ALTER TABLE public.cuenta OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 24754)
-- Name: movimiento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.movimiento (
    id bigint NOT NULL,
    fecha date NOT NULL,
    saldo numeric(19,2) NOT NULL,
    tipo character varying(30) NOT NULL,
    valor numeric(19,2) NOT NULL,
    id_cuenta character varying(10) NOT NULL
);


ALTER TABLE public.movimiento OWNER TO postgres;

--
-- TOC entry 213 (class 1259 OID 24753)
-- Name: movimiento_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.movimiento_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.movimiento_id_seq OWNER TO postgres;

--
-- TOC entry 3345 (class 0 OID 0)
-- Dependencies: 213
-- Name: movimiento_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.movimiento_id_seq OWNED BY public.movimiento.id;


--
-- TOC entry 210 (class 1259 OID 24695)
-- Name: parametro; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parametro (
    id integer NOT NULL,
    clave character varying(30) NOT NULL,
    valor character varying(200) NOT NULL
);


ALTER TABLE public.parametro OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 24694)
-- Name: parametro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.parametro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.parametro_id_seq OWNER TO postgres;

--
-- TOC entry 3346 (class 0 OID 0)
-- Dependencies: 209
-- Name: parametro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.parametro_id_seq OWNED BY public.parametro.id;


--
-- TOC entry 3178 (class 2604 OID 24757)
-- Name: movimiento id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento ALTER COLUMN id SET DEFAULT nextval('public.movimiento_id_seq'::regclass);


--
-- TOC entry 3177 (class 2604 OID 24698)
-- Name: parametro id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametro ALTER COLUMN id SET DEFAULT nextval('public.parametro_id_seq'::regclass);


--
-- TOC entry 3334 (class 0 OID 24743)
-- Dependencies: 211
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cliente (identificacion, direccion, edad, genero, nombre, telefono, contrasena, estado) VALUES ('00001', 'Otavalo sn y principal', 20, 'Masculino', 'Jose Lema', '098254785', '1234', true);
INSERT INTO public.cliente (identificacion, direccion, edad, genero, nombre, telefono, contrasena, estado) VALUES ('00002', 'Amazonas y NNUU', 21, 'Femenino', 'Marianela Montalvo', '097548965', '5678', true);
INSERT INTO public.cliente (identificacion, direccion, edad, genero, nombre, telefono, contrasena, estado) VALUES ('00003', '13 junio y Equinoccial', 23, 'Masculino', 'Juan Osorio', '098874587', '1245', true);


--
-- TOC entry 3335 (class 0 OID 24748)
-- Dependencies: 212
-- Data for Name: cuenta; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cuenta (numero, estado, saldo_inicial, tipo, id_cliente) VALUES ('585545', true, 1000.00, 'Corriente', '00001');
INSERT INTO public.cuenta (numero, estado, saldo_inicial, tipo, id_cliente) VALUES ('478758', true, 1425.00, 'Ahorro', '00001');
INSERT INTO public.cuenta (numero, estado, saldo_inicial, tipo, id_cliente) VALUES ('225487', true, 700.00, 'Corriente', '00002');
INSERT INTO public.cuenta (numero, estado, saldo_inicial, tipo, id_cliente) VALUES ('496825', true, 0.00, 'Ahorros', '00002');
INSERT INTO public.cuenta (numero, estado, saldo_inicial, tipo, id_cliente) VALUES ('495878', true, 300.00, 'Ahorros', '00003');


--
-- TOC entry 3337 (class 0 OID 24754)
-- Dependencies: 214
-- Data for Name: movimiento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.movimiento (id, fecha, saldo, tipo, valor, id_cuenta) VALUES (1, '2022-10-08', 1425.00, 'DEBITO', -575.00, '478758');
INSERT INTO public.movimiento (id, fecha, saldo, tipo, valor, id_cuenta) VALUES (2, '2022-10-08', 700.00, 'CREDITO', 600.00, '225487');
INSERT INTO public.movimiento (id, fecha, saldo, tipo, valor, id_cuenta) VALUES (3, '2022-10-08', 150.00, 'CREDITO', 150.00, '495878');
INSERT INTO public.movimiento (id, fecha, saldo, tipo, valor, id_cuenta) VALUES (4, '2022-10-08', 0.00, 'DEBITO', -540.00, '496825');
INSERT INTO public.movimiento (id, fecha, saldo, tipo, valor, id_cuenta) VALUES (5, '2022-10-08', 300.00, 'CREDITO', 150.00, '495878');


--
-- TOC entry 3333 (class 0 OID 24695)
-- Dependencies: 210
-- Data for Name: parametro; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.parametro (id, clave, valor) VALUES (1, 'MAX_CUPO_DIARIO', '1000');


--
-- TOC entry 3347 (class 0 OID 0)
-- Dependencies: 213
-- Name: movimiento_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.movimiento_id_seq', 11, true);


--
-- TOC entry 3348 (class 0 OID 0)
-- Dependencies: 209
-- Name: parametro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.parametro_id_seq', 1, true);


--
-- TOC entry 3184 (class 2606 OID 24747)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (identificacion);


--
-- TOC entry 3188 (class 2606 OID 24752)
-- Name: cuenta cuenta_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT cuenta_pkey PRIMARY KEY (numero);


--
-- TOC entry 3190 (class 2606 OID 24759)
-- Name: movimiento movimiento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT movimiento_pkey PRIMARY KEY (id);


--
-- TOC entry 3180 (class 2606 OID 24700)
-- Name: parametro parametro_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametro
    ADD CONSTRAINT parametro_pkey PRIMARY KEY (id);


--
-- TOC entry 3186 (class 2606 OID 24761)
-- Name: cliente uk_147rikqkb68rxqijmxxgpjwot; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT uk_147rikqkb68rxqijmxxgpjwot UNIQUE (identificacion);


--
-- TOC entry 3182 (class 2606 OID 24704)
-- Name: parametro uk_ilbxn7qfajl4myendxm6cj6do; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parametro
    ADD CONSTRAINT uk_ilbxn7qfajl4myendxm6cj6do UNIQUE (clave);


--
-- TOC entry 3191 (class 2606 OID 24762)
-- Name: cuenta fk_cliente_cuenta; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuenta
    ADD CONSTRAINT fk_cliente_cuenta FOREIGN KEY (id_cliente) REFERENCES public.cliente(identificacion);


--
-- TOC entry 3192 (class 2606 OID 24767)
-- Name: movimiento fk_cuenta_movimiento; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.movimiento
    ADD CONSTRAINT fk_cuenta_movimiento FOREIGN KEY (id_cuenta) REFERENCES public.cuenta(numero);


-- Completed on 2022-10-10 07:01:42

--
-- PostgreSQL database dump complete
--

