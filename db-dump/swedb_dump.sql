--
-- PostgreSQL database dump
--

-- Dumped from database version 16.6 (Debian 16.6-1.pgdg120+1)
-- Dumped by pg_dump version 17.2 (Debian 17.2-1.pgdg120+1)

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
-- Name: aircraft; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.aircraft (
    id integer NOT NULL,
    manufacturer text NOT NULL,
    model text NOT NULL,
    specification text NOT NULL,
    range integer NOT NULL,
    assistants_number integer NOT NULL,
    class text NOT NULL,
    seats integer NOT NULL
);


ALTER TABLE public.aircraft OWNER TO postgres;

--
-- Name: aircraft_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.aircraft_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.aircraft_id_seq OWNER TO postgres;

--
-- Name: aircraft_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.aircraft_id_seq OWNED BY public.aircraft.id;


--
-- Name: aircraft_instance; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.aircraft_instance (
    plate text NOT NULL,
    aircraft integer NOT NULL
);


ALTER TABLE public.aircraft_instance OWNER TO postgres;

--
-- Name: airport; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.airport (
    icao text NOT NULL,
    class text NOT NULL,
    name text NOT NULL,
    nation text NOT NULL,
    city text NOT NULL
);


ALTER TABLE public.airport OWNER TO postgres;

--
-- Name: commanders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.commanders (
    commander integer,
    flight integer
);


ALTER TABLE public.commanders OWNER TO postgres;

--
-- Name: credentials; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.credentials (
    username text NOT NULL,
    passwd text,
    employee_id integer
);


ALTER TABLE public.credentials OWNER TO postgres;

--
-- Name: first_officer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.first_officer (
    first_officer integer,
    flight integer
);


ALTER TABLE public.first_officer OWNER TO postgres;

--
-- Name: flight; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flight (
    id integer NOT NULL,
    departure_time text NOT NULL,
    passengers_number text NOT NULL,
    route integer NOT NULL,
    aircraft text
);


ALTER TABLE public.flight OWNER TO postgres;

--
-- Name: flight_assistants; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.flight_assistants (
    flight integer,
    assistant integer
);


ALTER TABLE public.flight_assistants OWNER TO postgres;

--
-- Name: flight_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.flight_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.flight_id_seq OWNER TO postgres;

--
-- Name: flight_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.flight_id_seq OWNED BY public.flight.id;


--
-- Name: parking; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.parking (
    aircraft text NOT NULL,
    airport text NOT NULL
);


ALTER TABLE public.parking OWNER TO postgres;

--
-- Name: personal; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.personal (
    id integer NOT NULL,
    name text NOT NULL,
    lastname text NOT NULL,
    role text NOT NULL,
    abilitation text
);


ALTER TABLE public.personal OWNER TO postgres;

--
-- Name: personal_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.personal_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.personal_id_seq OWNER TO postgres;

--
-- Name: personal_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.personal_id_seq OWNED BY public.personal.id;


--
-- Name: route; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.route (
    id integer NOT NULL,
    distance integer NOT NULL,
    duration integer NOT NULL,
    departure text NOT NULL,
    stepover text,
    arrival text NOT NULL
);


ALTER TABLE public.route OWNER TO postgres;

--
-- Name: route_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.route_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.route_id_seq OWNER TO postgres;

--
-- Name: route_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.route_id_seq OWNED BY public.route.id;


--
-- Name: aircraft id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft ALTER COLUMN id SET DEFAULT nextval('public.aircraft_id_seq'::regclass);


--
-- Name: flight id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight ALTER COLUMN id SET DEFAULT nextval('public.flight_id_seq'::regclass);


--
-- Name: personal id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal ALTER COLUMN id SET DEFAULT nextval('public.personal_id_seq'::regclass);


--
-- Name: route id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route ALTER COLUMN id SET DEFAULT nextval('public.route_id_seq'::regclass);


--
-- Data for Name: aircraft; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.aircraft (id, manufacturer, model, specification, range, assistants_number, class, seats) FROM stdin;
1	Airbus	A220	100	6204	3	3C	125
2	Airbus	A220	300	6204	3	3C	148
3	Airbus	A319	100	6950	3	3C	144
4	Airbus	A320	200	6200	4	3C	174
5	Airbus	A320	Neo	6300	4	3C	180
6	Airbus	A321	Neo	7400	4	4C	165
7	Airbus	A330	200	13334	7	4E	256
8	Airbus	A330	900	13334	8	4E	291
9	Airbus	A350	900	15000	8	4E	334
\.


--
-- Data for Name: aircraft_instance; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.aircraft_instance (plate, aircraft) FROM stdin;
EI-IMB	3
EI-IML	3
EI-IMV	3
EI-HLB	1
EI-HLA	1
EI-HHK	2
EI-HHI	2
EI-HXA	6
EI-HPA	8
EI-IFD	9
\.


--
-- Data for Name: airport; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.airport (icao, class, name, nation, city) FROM stdin;
LIBR	4D	Aeroporto di Brindisi-Casale	Italia	Brindisi
LIPE	4D	Aeroporto Guglielmo Marconi	Italia	Bologna
LIBD	4E	Aeroporto di Bari-Palese	Italia	Bari
LIEE	4D	Aeroporto di Cagliari-Elmas	Italia	Cagliari
LICC	4D	Aeroporto di Catania-Fontanarossa	Italia	Catania
LIRF	4F	Aeroporto di Roma-Fiumicino	Italia	Roma
LIRQ	3C	Aeroporto di Firenze-Peretola	Italia	Firenze
LIMJ	4E	Aeroporto di Genova Cristoforo Colombo	Italia	Genova
LIML	4D	Aeroporto Enrico Forlanini	Italia	Milano Linate
LIRN	4D	Aeroporto di Napoli-Capodichino Ugo Niutta	Italia	Napoli
KBOS	4E	Boston Logan Interntional Airport	United States of America	Boston
KJFK	4F	John F. Kennedy International Airport	United States of America	New York
DAAG	4F	Aeroporto Internazionale Houari Houari Boumédiène	Algeria	Algeri
EDDH	4E	Aeroporto Amburgo-Fuhlsbuttel	Germania	Amburgo
EHAM	4F	Aeroporto di Amsterdam-Schiphol	Paesi Bassi	Amsterdam
LGAV	4E	Aeroporto di Atene-Eleftherios Venizelos	Grecia	Atene
LEBL	4F	Aeroporto di Barcellona-El Prat	Spagna	Barcellona
EBBR	4E	Aeroporto di Bruxelles-National	Belgio	Bruxelles
EDDL	4E	Aeroporto Internazionale Dusseldorf	Germania	Dusseldorf
EDDF	4F	Aeroporto Francoforte sul Meno	Germania	Francoforte
OEJN	4F	Aeroporto Internazionale di Gedda-Re Abd al-Aziz	Arabia Saudita	Gedda
LSGG	4E	Aeroporto di Ginevra-Cointrin	Svizzera	Ginevra
\.


--
-- Data for Name: commanders; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.commanders (commander, flight) FROM stdin;
\.


--
-- Data for Name: credentials; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.credentials (username, passwd, employee_id) FROM stdin;
lomgra	2bb0f4369cee38f0511199dffec5e4a7e3feccf5d34c55436f21e7ea5703c05f2e3bd72914f7b7792fee19cead6b40c431da2eaa3834bf96966b94c632cbd27a	1
genern	07f9de7f9f6d3397e2ff16befe29215a5919429484b28d45e76a8bb41c3c00809e562bcce56ee2375dc7f5285f5051de07175dc5d0eaed8b3321c8db580e8ea9	2
catalf	710de309a7f8fd630da25720629ff9127e3453f598627499b1863636c5004e5ef92deb754140a90c02596c1dc6efca328c4960728c6e76aa2db36ab1e8d15bb9	3
manast	c41d5bd2f4d409dbf501e5e7181d8e5bad7a0d5047dcf4abbc17471c46680298f328f646dcba8be782de3e2958461bb65776a2c7b0a6ba3ee6a13a7e6b31d35a	4
caldon	be9161ab61f211230228f687493c4d3a29aaa2ee7580ecc6bc74f0566a2a292c6ca70da0d8592058949084897a9bbe43de11607f0e198978c46efda91f3b46f5	5
napghe	4b887a89564e959a22ff306ccbba40ca3db4940554c0e13dc0e14787ba904812ed4f9f0e9a9031d44dfdf023dfadd0465983e8f80bb12593bf6cfe829f33de91	6
milvin	4ce484c332c2af6cf13e6fd221d3403097da7ec9e45fd06425b62cb00e1ee21f2a8d95247ddf0f9f0d199fdbe7bf0d3c4c189099d8f8f89fe036632d77fe997f	7
ferfed	7c78a6db14cd5752fafb55aa448309fd1b4e7cb280a9a15d9cae14c332af315f1c124511af74e28abc91568435a18c8b7cf3195c51a8d07240980870280476a6	8
catvio	699a095a4bfa2626b4840bc196f089d1da9cc653d07fdd18e4a9df4fc32e309aa181310c9a9ba09f06f4554653f486cc65746472173937acc0f06fc1244a4eeb	9
pisire	feea8a77c5b39d328f4e613f9ddee38df1fa6d60ef06a5765bf8de4f382ff301ea641aedf0e50051f918635c5b83f75652123b69a9e238be466d0fb9167e0902	10
schvit	45463460fd282f017c61b768b58fa0ddccfff0fc12a3b1b00397a3ae3129ed9cb431715008c2679d650fd3034cd82a98260a7de6662cb03d1904f6ebab849a7a	11
lorsan	fc27a760fdbd4d221e3d8cef8fcf2464d2a77c537bafefb40a4ae7eebdc1431c4368ca9ea27195c01038a053d630853e9121d8a20a1644da051beeb16213d5c1	12
bruiva	55c7ab6fa5dabd50e8e662d81fd3cfd5436fa62b6524134f62a2dd5d2e1cb14812f1111114c49327d24e74844fcb549b9b121d0149dffb7a6b2ce4d62481464d	13
tosvio	57d50f0c51cdea9fcb6ddbd9eb4b99ddbef1bbdcf23ebb7b65146364d9c4152769bb87ac0a769d196ae19cfc07960b48755426a21586dba186e215097fbc6aca	14
mazmar	a0b9c8d289f31e777389f6dbbfba68a950bb5529149550b4e3781998ca949fdd64c3bba89b824738cb5dcb7b217e6e0c07397da294483fbe51a8d85a91f48b85	15
giofla	0a14907229938da318d6d137cedb570872c2c9d84187f5a48e77d734c8ee9f91d7e9d90763586208ba0d6ec2976a15dbfb5919dcb3123767ff3e3f13e7d949fe	16
barses	894ac351ff069b7b48f1adde930f9295c28ef0ac93a4c41c8c9eab466bde4bf14b6c1bf13faa8b818d8fddf0880665f1c43f7bf7587e9ab41fe4579f0d77cdb0	17
rusiva	2411d7f174723217dac8280f819de7e44d8290b0c69e3116d24ec96ec110cec7042f2c3ff52a8fa1c6ba967048d09b34ad802e39e35ac9753d4f83b15af082a0	18
lomcas	739ac9eee2360d1cb0cae460eaa8b2451ca16bc5c08fb5c9ef707c93b2452f48d75087126e30649840df5409ea5e45f2140cc0a566a1bce65fa37149b3d301e1	19
tosame	9bfa8be67e3783c84caacfef1876a0b59b269dc608c1722e6795131ac114de0b7f635c4bc3359e1064e25152f73c9fd970cb81388082c60d04db4de26da1981b	20
siccam	e228272b5c83debb6f1f8112ae0b11a415e905c6e57713e1d81fd8d603dec69d444654e09f41e69b7f4b9a2a79494d9bac03123816229a6c37203c6a4fa5ad61	21
folnat	8d26f522b707f902bf6b0b150e2626eab0aa52b82342a5fee2e24c863462c4234af21d76985a42903af789ee0a03f0fd33c7f5ebb253726e0d1d9479aaf87095	22
lonces	b9cd80fc1bf6a6df38f4fdbf95b74c4d0a2a49ad277e0c7558f13664bc6744a7e7da67fdfcbd016df2550bb0761481779ad11e63ff19677c1607d4cfc7442e1d	23
zetchi	9d772789edad0beb2a268e760bb6d0a3350e04af511c1f13c37a37185aae2bf68afe71ecd2fe733532ca5004a369deb4afb7bbced8dee3fcf51df4824aceb82e	24
mangem	dfe15ca118afa69ca70cad76fad455d2b4b985622d175f1aecc1ed4fa762fe1cb9446e23a3f93ac11de2ab8004a07578670b61ff54efe53d3427d06fc720fb3f	25
treita	d32313358027dfff2f225729af78aefad21c5fce2e0911aa3aab78535ea0c65e16429ca2c6396c86bec698913ee9e2fbd54e42e20261398002a958d528190be7	26
verald	89492e8d1a1f8aee1755704a78d78b571c21231c298269ecd1ab1d548ae236f606d8aefa10ae83ce1e33690068fa8007b0641a3bef4133142cb665cd07f456c8	27
vangia	c2befc412095e2824337fe91645b8fbd0c008e4e5097114e2df2916bb49fc6ea1980f0280c33e3c177ed83571db70f4ba91e62867d9a6249ec856d64eadd0910	28
antann	2505b638a0c2644b52280447473461b51b3b3c649d7dd6840906563033d3b9415ee1c8db86cb633061c7b80cef2f83b295a6ae87dfff2ab0a0afc1a346b8ea54	29
nuznic	bbefa3125522e8cabd5877a3d292cbc49919d58f041bd7a603db62206543e79327f22a4180dfb6694da31629f7f076114aec070c32b059bd8745725f006b3372	30
rivmia	cfddf6cf781a41e330f0ca7fcea20ae6de2a97a81ce2a214a349d0dca473e1356e294e02519918513ff84045689321a56ea434a6fdecb4cbe0a1819a53296987	31
solani	6da8cbefa013a0a53e9b00a3e780682fe5877fb35c56c0c21fa711ecfe165905e9d3c803ec73f458969967c84f7c9ca6e02f7cfb4e9e93d2d038110a824c8848	32
barvol	adc83265c2890048191d4735b81887d50f223f436eae54d47a7ac8911bc9112654e4199a33710b3f0c9b9a7eb71622b53fdd9222e2c58d061b7973c5558031f7	33
buofio	69d8ec10b3110bca220ab4ff3d90830fa0f4def6eb1eda0be29f15b1df2b1d220baa4fcde49d5ab93253f89718728f991fba7bef1ca9d17ccfff436465cc5dd4	34
donfla	edad570a540aa029ff7d785d3ca0d1e71c1bbb2c64ff97bd7cf371d4fcbb74988e4c5de4685d123c4974fe1f9a16f955552af7d0a9273e6cc9b400e571a18067	35
greant	ba4547ac54f455c130ed652450b029ba6f3bbaf7cfa006c50510eb4df06d0ab38b9ff1a7acc9e91e1e2b1fcef7879ce49cc0dc33a511c0d953e9d3036773d063	36
ercpao	ccac092f285a559dd0d46a144370dbada794d3a54d5f504dc2efbb75c682f5bfa272e89d0014423496db57e0884bc073f7b073974710a437c612ad2d3514567b	37
fioalb	9205f903423ed9d627515a27b00493734b9931b980233cc8fb6fcd765e1d6ee49f3d3a65aa09e640db3a5b482f5a27c9a660fd735d7e0988f27d1ba62be1e31c	38
sormat	c7d3d03ff672c47d0dc3786e148ba69a2cca365d35791b22a3065c6cd5bcf58f4016754481f99f8efff6181e8fec11604551c3d416fc74bec04a6430316c4e0d	42
panumb	c292e47b3989361872b8f834611339b2d80df9a780e23fc4d37153c2e1c481ac1f37739d1eab88ae2df82def9f6c1656ddbf75fe7ca7beda7247f1a92c8b4919	45
vermar	cbda3c73ac2e1962fbecdebbff7aa4f743c8caf3de13ac0374cc8f89c2bccc6b8805cd18e1e77feb13cbde6006e0ec21debaf5b53bfe26a6b9a1724814b9d917	49
porgen	cd639d8a15f7ea00eef0b83c5d439db0ec5c1a921cff873a7862a0a232b855fe84e31ab0afc39bfba9b443d6db0c0999f9e675c47f4dbcc2f9fb7ac455e912f7	52
peledo	47eb4c979fbf22aa53d8790fc81a316904bdd30a5713971484ef5366f86b7ff093422e7476f6b7bec886827f3b1b536e11b93bb2e5dc9463519df8f5fdf3ba67	56
nuclud	e8694609717dd2981eb23626045978ae5e0071ed039de23ae3e52d44c0ac93d41c04ae31fd8ce2784bd078701dbec827167f8b8136ef334e22b4be22229632e9	60
troant	491284c0f26a2588bb1b1dc6a5f4f56e8efb23d5644e0ab6730eec7a00c482f86d30e17914f9c5a9d4cbc07b4459b3fcd9d1737da0e9d80828863e058037336a	63
gricar	3a1a0d1e1a9427111af0f4b14c50f740858e329851ef69bcd150453b5304fdef45923a1070e2e35992a21744795c29823f6bba7e3e8a9a4756d5a32d4ce5ec49	39
tanmat	4820fb4a99b2c97ed790272c2e51d2bd8e407974c613019456674a36fa69f0797dddc47aa75cafea82d58f445046894b164ff3dd892d4389c25193d8834ac4ee	40
liofab	9d9487fccfe49b74cd35a635d946948280532b7615e2f8902524cfa9336fe1dbf20364e263aa0c337399e0e17e8b072854616691c27b59b329c04de4840cb9be	41
palelm	9b7c6943736a3480b86f686caca79df33c7b8ce226b0bd3f070326ff5dde99c70902e5f3da243d50a0888472e3cf087f156de9b2066dfe9f0656e2c67208eae9	43
giamar	a0c450a37c537ea0ff605e5406d7abd2af6313adee5ac3227a498cbb9eb6256aa010b16bf2dd16005c1305bfdc37d82a108f038ccdab84c70769e7b8611b9ea3	44
pazang	10bb7b7673509b983660dd7da2af39521562e8521b06c230cd5945856dd81f39d54fa45b1bc461017cf4e3457eb13e8b856ac4089095074bcb56bbbc0841d908	46
verraf	c4470793c22a56ac378f200b81e837cd57927e895977c6db1ba8aefb842cdc0a526dde1f803b212a140c54412444a4abdb01d67bb7ed44d8bac8bfed30f9a142	47
riccos	413bb97138f2e59ca09c1507d9b373adfa0c8912c8d98743485387bee8566abb503a497792036369b4173b952f9c9fd876558ec919e6ef089d1c6ddf2556f959	48
sanale	775c495aef0fb768ae6b8851919b7d35826d0830b5b956ebcfd4dfe7f9a5b236b09928e20fc2276116232fbaa76ed8dcf819077effa8f9bad866c53aad031fec	50
buocat	6afbb7935828e989cee93bd53282cd53391bb29b95292241f19baa6820ef334e42049306af261203ff79b0d076fe2250f813bb5e589418d2a33a0cd4709675d6	51
izznic	0035ad7868e0cf65050d729026c213ba875f2399aeeabafb1e1f591e5e01d3d72c1aafe3ea0fa374677c602693b95e0fd86380fd5cc1c6250e6c356b8fbfb6bf	53
gatreb	263b10c7d7ab959cdcbcf86cc37361752243a49bc31816514d983c26f55fdae28fd74e59680402af3a87e8d31e17e8f7b6e6d4ff0f101c755e20674253125a43	54
marmar	5790e8483e0f5b9b01152d64bd847926efe98a7cbf1d87df03c65fa48841987d3045093df3367db7c0384b976b1c8f018b2982f1ceb8d3c32e271b601085b7cf	55
bendar	8bd1f625bc7fbdbbdda65080db940b0986ff69a2a13b84423774eeaadc85f76d81eb2438f506f3b646e000d17e6e2a1827764b05f032962827debbb5c7897cf5	57
andsof	33f799ddeaab4b2ac98469a7365d21d5ad703f142374b32e658973afb622a548f46a261e7a0bba5211e620eea96b4806f7c56cfe1b1c7b55dd03d784ec238d1e	58
banlui	74d9e6d6cfd2dadbffbfa172938f01f9a21b6de50f035049141a3065a3c8a0a5eed28a96af3fbff54fec958676f3f274abda505733e54e4358f726d5e3c75512	59
sagisa	4bfa6efaa834dcaaee6fb02d5f5e4f60d45c70112aca19cfebcd7819f0eaffc46ad6cc8747de00f245817a5ff851da43150816262d74e2bf4a518c259c52dd25	61
carani	49f8e831a658b2fb1b96b64a46afecf8a1f27755d6939ee676a521d658030248146ec30a6a842ad44b6f6e55cd40a0effc90f71f4b6bb65f3ffd0d411582d186	62
romgre	af59ee53a2bc6bfd932300057360b2d06e700326a82423a127f34882e33ad9468d60a33825af7fd5416296b51de399d69c8447b9bbacfefef0fdb86e1e1a9581	64
admin	14315332725ba7c4e401ed783f168eb9acaea1d3b35ac35dd6efff80e59c0fd7175702f3b86bd77a6bfa1e73e15c0e511ce444db54d423d15ffaff8d0ec96817	0
\.


--
-- Data for Name: first_officer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.first_officer (first_officer, flight) FROM stdin;
\.


--
-- Data for Name: flight; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flight (id, departure_time, passengers_number, route, aircraft) FROM stdin;
1	10-08-2024 17:30:00	0	29	EI-IFD
2	10-08-2024 17:30:00	0	29	EI-IFD
3	10-08-2024 17:30:00	0	29	EI-IFD
4	10-08-2024 17:30:00	0	53	EI-IMV
5	10-08-2024 17:30:00	0	35	EI-IMB
6	10-08-2024 22:30:00	0	54	EI-IMV
7	10-08-2024 17:30:00	0	23	EI-HPA
8	11-08-2024 02:20:00	0	30	EI-IFD
9	11-08-2024 03:30:00	0	53	EI-IMV
10	10-08-2024 20:15:00	0	36	EI-IMB
11	11-08-2024 11:10:00	0	29	EI-IFD
12	10-08-2024 17:30:00	0	53	EI-IML
13	10-08-2024 19:45:00	0	24	EI-HPA
14	11-08-2024 08:30:00	0	54	EI-IMV
15	10-08-2024 22:30:00	0	54	EI-IML
16	10-08-2024 22:00:00	0	23	EI-HPA
17	10-08-2024 17:30:00	0	29	EI-IFD
18	10-08-2024 17:30:00	0	53	EI-IMV
19	10-08-2024 17:30:00	0	35	EI-IMB
20	10-08-2024 22:30:00	0	54	EI-IMV
21	10-08-2024 17:30:00	0	23	EI-HPA
22	11-08-2024 02:20:00	0	30	EI-IFD
23	11-08-2024 03:30:00	0	53	EI-IMV
24	10-08-2024 20:15:00	0	36	EI-IMB
25	11-08-2024 11:10:00	0	29	EI-IFD
26	10-08-2024 17:30:00	0	53	EI-IML
27	10-08-2024 19:45:00	0	24	EI-HPA
28	11-08-2024 08:30:00	0	54	EI-IMV
29	10-08-2024 22:30:00	0	54	EI-IML
30	10-08-2024 22:00:00	0	23	EI-HPA
31	10-08-2024 17:30:00	0	29	EI-IFD
32	10-08-2024 17:30:00	0	53	EI-IMV
33	10-08-2024 17:30:00	0	35	EI-IMB
34	10-08-2024 22:30:00	0	54	EI-IMV
35	10-08-2024 17:30:00	0	23	EI-HPA
36	11-08-2024 02:20:00	0	30	EI-IFD
37	11-08-2024 03:30:00	0	53	EI-IMV
38	10-08-2024 20:15:00	0	36	EI-IMB
39	11-08-2024 11:10:00	0	29	EI-IFD
40	10-08-2024 17:30:00	0	53	EI-IML
41	10-08-2024 19:45:00	0	24	EI-HPA
42	11-08-2024 08:30:00	0	54	EI-IMV
43	10-08-2024 22:30:00	0	54	EI-IML
44	10-08-2024 22:00:00	0	23	EI-HPA
45	10-08-2024 17:30:00	0	29	EI-IFD
46	10-08-2024 17:30:00	0	53	EI-IMV
47	10-08-2024 17:30:00	0	35	EI-IMB
48	10-08-2024 22:30:00	0	54	EI-IMV
49	10-08-2024 17:30:00	0	23	EI-HPA
50	11-08-2024 02:20:00	0	30	EI-IFD
51	11-08-2024 03:30:00	0	53	EI-IMV
52	10-08-2024 20:15:00	0	36	EI-IMB
53	11-08-2024 11:10:00	0	29	EI-IFD
54	10-08-2024 17:30:00	0	53	EI-IML
55	10-08-2024 19:45:00	0	24	EI-HPA
56	11-08-2024 08:30:00	0	54	EI-IMV
57	10-08-2024 22:30:00	0	54	EI-IML
58	10-08-2024 22:00:00	0	23	EI-HPA
59	10-08-2024 17:30:00	0	29	EI-IFD
60	10-08-2024 17:30:00	0	53	EI-IMV
61	10-08-2024 17:30:00	0	35	EI-IMB
62	10-08-2024 22:30:00	0	54	EI-IMV
63	10-08-2024 17:30:00	0	23	EI-HPA
64	11-08-2024 02:20:00	0	30	EI-IFD
65	11-08-2024 03:30:00	0	53	EI-IMV
66	10-08-2024 20:15:00	0	36	EI-IMB
67	11-08-2024 11:10:00	0	29	EI-IFD
68	10-08-2024 17:30:00	0	53	EI-IML
69	10-08-2024 19:45:00	0	24	EI-HPA
70	11-08-2024 08:30:00	0	54	EI-IMV
71	10-08-2024 22:30:00	0	54	EI-IML
72	10-08-2024 22:00:00	0	23	EI-HPA
73	10-08-2024 17:30:00	0	29	EI-IFD
74	10-08-2024 17:30:00	0	53	EI-IMV
75	10-08-2024 17:30:00	0	35	EI-IMB
76	10-08-2024 22:30:00	0	54	EI-IMV
77	10-08-2024 17:30:00	0	23	EI-HPA
78	11-08-2024 02:20:00	0	30	EI-IFD
79	11-08-2024 03:30:00	0	53	EI-IMV
80	10-08-2024 20:15:00	0	36	EI-IMB
81	11-08-2024 11:10:00	0	29	EI-IFD
82	10-08-2024 17:30:00	0	53	EI-IML
83	10-08-2024 19:45:00	0	24	EI-HPA
84	11-08-2024 08:30:00	0	54	EI-IMV
85	10-08-2024 22:30:00	0	54	EI-IML
86	10-08-2024 22:00:00	0	23	EI-HPA
87	10-08-2024 17:30:00	0	29	EI-IFD
88	10-08-2024 17:30:00	0	53	EI-IMV
89	10-08-2024 17:30:00	0	35	EI-IMB
90	10-08-2024 22:30:00	0	54	EI-IMV
91	10-08-2024 17:30:00	0	23	EI-HPA
92	11-08-2024 02:20:00	0	30	EI-IFD
93	11-08-2024 03:30:00	0	53	EI-IMV
94	10-08-2024 20:15:00	0	36	EI-IMB
95	11-08-2024 11:10:00	0	29	EI-IFD
96	10-08-2024 17:30:00	0	53	EI-IML
97	10-08-2024 19:45:00	0	24	EI-HPA
98	11-08-2024 08:30:00	0	54	EI-IMV
99	10-08-2024 22:30:00	0	54	EI-IML
100	10-08-2024 22:00:00	0	23	EI-HPA
101	19-10-2024 18:30:00	0	29	EI-IFD
102	19-10-2024 18:30:00	0	53	EI-IMV
103	19-10-2024 18:30:00	0	35	EI-IMB
104	19-10-2024 23:30:00	0	54	EI-IMV
105	19-10-2024 18:30:00	0	23	EI-HPA
106	20-10-2024 03:20:00	0	30	EI-IFD
107	20-10-2024 04:30:00	0	53	EI-IMV
108	19-10-2024 21:15:00	0	36	EI-IMB
109	20-10-2024 12:10:00	0	29	EI-IFD
110	19-10-2024 18:30:00	0	53	EI-IML
111	19-10-2024 20:45:00	0	24	EI-HPA
112	20-10-2024 09:30:00	0	54	EI-IMV
113	19-10-2024 23:30:00	0	54	EI-IML
114	19-10-2024 23:00:00	0	23	EI-HPA
115	20-10-2024 11:30:00	0	29	EI-IFD
116	20-10-2024 11:30:00	0	53	EI-IMV
117	20-10-2024 11:30:00	0	35	EI-IMB
118	20-10-2024 16:30:00	0	54	EI-IMV
119	20-10-2024 11:30:00	0	23	EI-HPA
120	20-10-2024 20:20:00	0	30	EI-IFD
121	20-10-2024 21:30:00	0	53	EI-IMV
122	20-10-2024 14:15:00	0	36	EI-IMB
123	21-10-2024 05:10:00	0	29	EI-IFD
124	20-10-2024 11:30:00	0	53	EI-IML
125	20-10-2024 13:45:00	0	24	EI-HPA
126	21-10-2024 02:30:00	0	54	EI-IMV
127	20-10-2024 16:30:00	0	54	EI-IML
128	20-10-2024 16:00:00	0	23	EI-HPA
129	20-10-2024 11:30:00	0	29	EI-IFD
130	20-10-2024 11:30:00	0	53	EI-IMV
131	20-10-2024 11:30:00	0	35	EI-IMB
132	20-10-2024 16:30:00	0	54	EI-IMV
133	20-10-2024 11:30:00	0	23	EI-HPA
134	20-10-2024 20:20:00	0	30	EI-IFD
135	20-10-2024 21:30:00	0	53	EI-IMV
136	20-10-2024 14:15:00	0	36	EI-IMB
137	21-10-2024 05:10:00	0	29	EI-IFD
138	20-10-2024 11:30:00	0	53	EI-IML
139	20-10-2024 13:45:00	0	24	EI-HPA
140	21-10-2024 02:30:00	0	54	EI-IMV
141	20-10-2024 16:30:00	0	54	EI-IML
142	20-10-2024 16:00:00	0	23	EI-HPA
143	07-12-2024 20:30:00	0	29	EI-IFD
144	07-12-2024 20:30:00	0	29	EI-IFD
145	07-12-2024 20:30:00	0	29	EI-IFD
146	07-12-2024 20:30:00	0	53	EI-IMV
147	07-12-2024 20:30:00	0	35	EI-IMB
148	08-12-2024 01:30:00	0	54	EI-IMV
149	07-12-2024 20:30:00	0	23	EI-HPA
150	08-12-2024 05:20:00	0	30	EI-IFD
151	08-12-2024 06:30:00	0	53	EI-IMV
152	07-12-2024 23:15:00	0	36	EI-IMB
153	08-12-2024 14:10:00	0	29	EI-IFD
154	07-12-2024 20:30:00	0	53	EI-IML
155	07-12-2024 22:45:00	0	24	EI-HPA
156	08-12-2024 11:30:00	0	54	EI-IMV
157	08-12-2024 01:30:00	0	54	EI-IML
158	08-12-2024 01:00:00	0	23	EI-HPA
\.


--
-- Data for Name: flight_assistants; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.flight_assistants (flight, assistant) FROM stdin;
\.


--
-- Data for Name: parking; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.parking (aircraft, airport) FROM stdin;
EI-IMB	LIRF
EI-IML	LIRF
EI-IMV	LIRF
EI-IFD	LIRF
EI-HPA	LIML
\.


--
-- Data for Name: personal; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.personal (id, name, lastname, role, abilitation) FROM stdin;
1	Grazia	Lombardi	commander	A319
2	Ernesto	Genovesi	commander	A319
3	Alfonso	Cattaneo	commander	A220
4	Astolfo	Mancini	firstofficer	A319
5	Donato	Calabresi	firstofficer	A220
6	Gherardo	Napolitano	firstofficer	A319
7	Vincenzo	Milanesi	hostess/steward	\N
8	Federico	Ferri	hostess/steward	\N
9	Violetta	Cattaneo	hostess/steward	\N
10	Irene	Pisano	hostess/steward	\N
11	Vito	Schiavone	hostess/steward	\N
12	Sandra	Lori	hostess/steward	\N
13	Ivana	Bruno	hostess/steward	\N
14	Viola	Toscano	hostess/steward	\N
15	Marzia	Mazzi	hostess/steward	\N
16	Flavio	Giordano	hostess/steward	\N
17	Sesto	Barese	hostess/steward	\N
18	Ivana	Russo	hostess/steward	\N
19	Cassandra	Lombardo	hostess/steward	\N
20	Amelia	Toscani	hostess/steward	\N
21	Camelia	Siciliani	hostess/steward	\N
22	Natalina	Folliero	hostess/steward	\N
23	Cesare	Longo	hostess/steward	\N
24	Chiara	Zetticci	hostess/steward	\N
25	Gemma	Manna	hostess/steward	\N
26	Italo	Trevisano	hostess/steward	\N
27	Aldo	Verrilli	hostess/steward	\N
28	Gianna	Vanetti	hostess/steward	\N
29	Anna	Antonacci	hostess/steward	\N
30	Nicole	Nuzzo	hostess/steward	\N
31	Mia	Rivera	hostess/steward	\N
32	Anita	Solinas	hostess/steward	\N
33	Volta	Barzini	hostess/steward	\N
34	Fiore	Buonocore	hostess/steward	\N
35	Flavia	Donini	commander	A319
36	Antonello	Greco	commander	A319
37	Paolo	Ercolano	commander	A319
38	Alberto	Fiore	commander	A319
39	Carmine	Grieco	commander	A330
40	Matilde	Tanoli	firstofficer	A319
41	Fabio	Lionetti	firstofficer	A319
42	Mattia	Sorisi	firstofficer	A319
43	Elmo	Palmieri	firstofficer	A319
44	Martina	Giambalvo	firstofficer	A330
45	Umberto	Panicucci	hostess/steward	\N
46	Angela	Pazzi	hostess/steward	\N
47	Raffaella	Verrilli	hostess/steward	\N
48	Costantino	Ricci	hostess/steward	\N
49	Martina	Vercellino	hostess/steward	\N
50	Alessandra	Santorelli	hostess/steward	\N
51	Caterina	Buono	hostess/steward	\N
52	Gennaro	Porcelli	hostess/steward	\N
53	Nicole	Izzo	hostess/steward	\N
54	Rebecca	Gatti	hostess/steward	\N
55	Marta	Martini	hostess/steward	\N
56	Edoardo	Pellegrini	hostess/steward	\N
57	Dario	Benedetti	hostess/steward	\N
58	Sofia	Andolini	hostess/steward	\N
59	Luisa	Bandini	hostess/steward	\N
60	Ludovico	Nucci	hostess/steward	\N
61	Isabella	Sagese	hostess/steward	\N
62	Anita	Caruso	hostess/steward	\N
63	Antonello	Tropea	hostess/steward	\N
64	Greta	Romano	hostess/steward	\N
65	Davide	Bianchi	commander	A350
66	Clemente	Mazzanti	commander	A350
67	Renato	Palerma	firstofficer	A350
68	Martina	Baresi	firstofficer	A350
69	Albino	Calabresi	commander	A330
70	Jacopo	Ricci	firstofficer	A330
\.


--
-- Data for Name: route; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.route (id, distance, duration, departure, stepover, arrival) FROM stdin;
1	232	55	LIRF	\N	LIRQ
2	232	55	LIRQ	\N	LIRF
3	188	55	LIRN	\N	LIRF
4	188	55	LIRF	\N	LIRN
5	402	65	LIRF	\N	LIMJ
6	402	65	LIMJ	\N	LIRF
7	374	65	LIRF	\N	LIBD
8	374	65	LIBD	\N	LIRF
9	536	85	LIRF	\N	LICC
10	536	85	LICC	\N	LIRF
11	476	70	LIRF	\N	LIBR
12	476	70	LIBR	\N	LIRF
13	884	95	LIML	\N	LIBR
14	884	95	LIBR	\N	LIML
15	304	55	LIPE	\N	LIRF
16	304	55	LIRF	\N	LIPE
17	780	85	LIML	\N	LIBD
18	780	85	LIBD	\N	LIML
19	413	65	LIRF	\N	LIEE
20	413	65	LIEE	\N	LIRF
21	693	85	LIML	\N	LIEE
22	693	85	LIEE	\N	LIML
23	1007	105	LIML	\N	LICC
24	1007	105	LICC	\N	LIML
25	648	75	LIML	\N	LIRN
26	648	75	LIRN	\N	LIML
27	6564	470	LIRF	\N	KBOS
28	6564	470	KBOS	\N	LIRF
29	6862	500	LIRF	\N	KJFK
30	6862	500	KJFK	\N	LIRF
31	960	115	LIRF	\N	DAAG
32	960	115	DAAG	\N	LIRF
33	909	110	LIML	\N	EDDH
34	909	110	EDDH	\N	LIML
35	1295	135	LIRF	\N	EHAM
36	1295	135	EHAM	\N	LIRF
37	828	100	LIML	\N	EHAM
38	828	100	EHAM	\N	LIML
39	1084	125	LIRF	\N	LGAV
40	1084	125	LGAV	\N	LIRF
41	844	105	LIRF	\N	LEBL
42	844	105	LEBL	\N	LIRF
43	1171	125	LIRF	\N	EBBR
44	1171	125	EBBR	\N	LIRF
45	701	85	LIML	\N	EBBR
46	701	85	EBBR	\N	LIML
47	674	85	LIML	\N	EDDL
48	674	85	EDDL	\N	LIML
49	511	75	LIML	\N	EDDF
50	511	75	EDDF	\N	LIML
51	957	125	LIRF	\N	EDDF
52	957	125	EDDF	\N	LIRF
53	3360	270	LIRF	\N	OEJN
54	3360	270	OEJN	\N	LIRF
55	693	90	LIRF	\N	LSGG
56	693	90	LSGG	\N	LIRF
\.


--
-- Name: aircraft_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.aircraft_id_seq', 9, true);


--
-- Name: flight_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.flight_id_seq', 158, true);


--
-- Name: personal_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.personal_id_seq', 70, true);


--
-- Name: route_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.route_id_seq', 56, true);


--
-- Name: aircraft_instance aircraft_instance_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft_instance
    ADD CONSTRAINT aircraft_instance_pkey PRIMARY KEY (plate);


--
-- Name: aircraft aircraft_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft
    ADD CONSTRAINT aircraft_pkey PRIMARY KEY (id);


--
-- Name: airport airport_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.airport
    ADD CONSTRAINT airport_pkey PRIMARY KEY (icao);


--
-- Name: credentials credentials_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.credentials
    ADD CONSTRAINT credentials_pkey PRIMARY KEY (username);


--
-- Name: flight flight_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_pkey PRIMARY KEY (id);


--
-- Name: parking parking_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT parking_pkey PRIMARY KEY (aircraft, airport);


--
-- Name: personal personal_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.personal
    ADD CONSTRAINT personal_pkey PRIMARY KEY (id);


--
-- Name: route route_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_pkey PRIMARY KEY (id);


--
-- Name: aircraft_instance aircraft_instance_aircraft_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.aircraft_instance
    ADD CONSTRAINT aircraft_instance_aircraft_fkey FOREIGN KEY (aircraft) REFERENCES public.aircraft(id);


--
-- Name: commanders commander_reference; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commanders
    ADD CONSTRAINT commander_reference FOREIGN KEY (commander) REFERENCES public.personal(id);


--
-- Name: first_officer commander_reference; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.first_officer
    ADD CONSTRAINT commander_reference FOREIGN KEY (first_officer) REFERENCES public.personal(id);


--
-- Name: flight flight_aircraft_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_aircraft_fkey FOREIGN KEY (aircraft) REFERENCES public.aircraft_instance(plate);


--
-- Name: commanders flight_reference; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.commanders
    ADD CONSTRAINT flight_reference FOREIGN KEY (flight) REFERENCES public.flight(id);


--
-- Name: first_officer flight_reference; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.first_officer
    ADD CONSTRAINT flight_reference FOREIGN KEY (flight) REFERENCES public.flight(id);


--
-- Name: flight flight_route_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight
    ADD CONSTRAINT flight_route_fkey FOREIGN KEY (route) REFERENCES public.route(id);


--
-- Name: flight_assistants flightassistants_assistant_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight_assistants
    ADD CONSTRAINT flightassistants_assistant_fkey FOREIGN KEY (assistant) REFERENCES public.personal(id);


--
-- Name: flight_assistants flightassistants_flight_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.flight_assistants
    ADD CONSTRAINT flightassistants_flight_fkey FOREIGN KEY (flight) REFERENCES public.flight(id);


--
-- Name: parking parking_aircraft_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT parking_aircraft_fkey FOREIGN KEY (aircraft) REFERENCES public.aircraft_instance(plate);


--
-- Name: parking parking_airport_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.parking
    ADD CONSTRAINT parking_airport_fkey FOREIGN KEY (airport) REFERENCES public.airport(icao);


--
-- Name: route route_arrival_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_arrival_fkey FOREIGN KEY (arrival) REFERENCES public.airport(icao);


--
-- Name: route route_departure_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_departure_fkey FOREIGN KEY (departure) REFERENCES public.airport(icao);


--
-- Name: route route_stepover_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.route
    ADD CONSTRAINT route_stepover_fkey FOREIGN KEY (stepover) REFERENCES public.airport(icao);


--
-- PostgreSQL database dump complete
--

