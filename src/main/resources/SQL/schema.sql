--
-- PostgreSQL database dump
--

-- Dumped from database version 17.2
-- Dumped by pg_dump version 17.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: board; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.board (
    id integer NOT NULL,
    name character varying(50) NOT NULL,
    code_name character varying(5) NOT NULL
);


ALTER TABLE public.board OWNER TO postgres;

--
-- Name: board_id_counter; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.board_id_counter (
    id integer NOT NULL,
    board_id integer NOT NULL,
    next_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.board_id_counter OWNER TO postgres;

--
-- Name: board_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.board_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.board_id_seq OWNER TO postgres;

--
-- Name: board_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.board_id_seq OWNED BY public.board.id;


--
-- Name: media; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.media (
    id integer NOT NULL,
    filename character varying(255) NOT NULL,
    post_id integer NOT NULL
);


ALTER TABLE public.media OWNER TO postgres;

--
-- Name: next_post_id_by_board_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.next_post_id_by_board_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.next_post_id_by_board_id_seq OWNER TO postgres;

--
-- Name: next_post_id_by_board_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.next_post_id_by_board_id_seq OWNED BY public.board_id_counter.id;


--
-- Name: post; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.post (
    id integer NOT NULL,
    author_id character varying(36) NOT NULL,
    content character varying(8000) NOT NULL,
    nickname character varying(30) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    post_number integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.post OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.post_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_id_seq OWNER TO postgres;

--
-- Name: post_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.post_id_seq OWNED BY public.post.id;


--
-- Name: post_media_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.post_media_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.post_media_id_seq OWNER TO postgres;

--
-- Name: post_media_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.post_media_id_seq OWNED BY public.media.id;


--
-- Name: reply; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.reply (
    id integer NOT NULL,
    thread_id integer NOT NULL
);


ALTER TABLE public.reply OWNER TO postgres;

--
-- Name: reply_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.reply_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.reply_id_seq OWNER TO postgres;

--
-- Name: reply_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.reply_id_seq OWNED BY public.reply.id;


--
-- Name: thread; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.thread (
    id integer DEFAULT nextval('public.post_id_seq'::regclass) NOT NULL,
    title character varying(30) NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    last_bump_at timestamp without time zone NOT NULL,
    is_pinned boolean NOT NULL,
    board_id integer DEFAULT 1 NOT NULL
);


ALTER TABLE public.thread OWNER TO postgres;

--
-- Name: board id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board ALTER COLUMN id SET DEFAULT nextval('public.board_id_seq'::regclass);


--
-- Name: board_id_counter id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board_id_counter ALTER COLUMN id SET DEFAULT nextval('public.next_post_id_by_board_id_seq'::regclass);


--
-- Name: media id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.media ALTER COLUMN id SET DEFAULT nextval('public.post_media_id_seq'::regclass);


--
-- Name: post id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post ALTER COLUMN id SET DEFAULT nextval('public.post_id_seq'::regclass);


--
-- Name: reply id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply ALTER COLUMN id SET DEFAULT nextval('public.reply_id_seq'::regclass);


--
-- Name: board board_code_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board
    ADD CONSTRAINT board_code_name_key UNIQUE (code_name);


--
-- Name: board board_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board
    ADD CONSTRAINT board_pkey PRIMARY KEY (id);


--
-- Name: media media_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.media
    ADD CONSTRAINT media_pkey PRIMARY KEY (id);


--
-- Name: board_id_counter next_post_id_by_board_board_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board_id_counter
    ADD CONSTRAINT next_post_id_by_board_board_id_key UNIQUE (board_id);


--
-- Name: board_id_counter next_post_id_by_board_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board_id_counter
    ADD CONSTRAINT next_post_id_by_board_pkey PRIMARY KEY (id);


--
-- Name: post post_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.post
    ADD CONSTRAINT post_pkey PRIMARY KEY (id);


--
-- Name: reply reply_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply
    ADD CONSTRAINT reply_pkey PRIMARY KEY (id);


--
-- Name: thread thread_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.thread
    ADD CONSTRAINT thread_pkey PRIMARY KEY (id);


--
-- Name: media media_post_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.media
    ADD CONSTRAINT media_post_id_fkey FOREIGN KEY (post_id) REFERENCES public.post(id) NOT VALID;


--
-- Name: board_id_counter next_post_id_by_board_board_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.board_id_counter
    ADD CONSTRAINT next_post_id_by_board_board_id_fkey FOREIGN KEY (board_id) REFERENCES public.board(id);


--
-- Name: reply reply_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply
    ADD CONSTRAINT reply_id_fkey FOREIGN KEY (id) REFERENCES public.post(id) NOT VALID;


--
-- Name: reply reply_thread_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.reply
    ADD CONSTRAINT reply_thread_id_fkey FOREIGN KEY (thread_id) REFERENCES public.thread(id) NOT VALID;


--
-- Name: thread thread_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.thread
    ADD CONSTRAINT thread_id_fkey FOREIGN KEY (id) REFERENCES public.post(id) NOT VALID;


--
-- PostgreSQL database dump complete
--

