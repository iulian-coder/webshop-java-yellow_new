DROP TABLE IF EXISTS public.users;
CREATE TABLE users
(
    id               serial primary key NOT NULL,
    username         varchar(20)        NOT NULL,
    password         varchar(20)        NOT NULL,
    first_name       VARCHAR(20)        NOT NULL,
    last_name        VARCHAR(20)        NOT NULL,
    phone_number     VARCHAR(14)        NOT NULL,
    email            VARCHAR(20) UNIQUE NOT NULL,
    billing_address  VARCHAR(255),
    shipping_address VARCHAR(255)
);

DROP TABLE IF EXISTS public.category;
CREATE TABLE category
(
    id          serial primary key NOT NULL,
    name        varchar(40)        NOT NULL,
    department  VARCHAR(20)        NOT NULL,
    description text               NOT NULL
);

DROP TABLE IF EXISTS public.supplier;
CREATE TABLE supplier
(
    id          serial primary key NOT NULL,
    name        varchar(40)        NOT NULL,
    description text               NOT NULL
);

DROP TABLE IF EXISTS public.product;
CREATE TABLE product
(
    id          serial primary key NOT NULL,
    name        varchar(40)        NOT NULL,
    price       FLOAT(5)           NOT NULL,
    currency    varchar(5)         not NULL,
    image       VARCHAR(100)       NOT NULL,
    description text               NOT NULL,
    supplier_id INTEGER REFERENCES supplier (id) ON DELETE CASCADE,
    category_id INTEGER REFERENCES category (id) ON DELETE CASCADE
);


DROP TABLE IF EXISTS public.cart;
CREATE TABLE cart
(
    id      serial primary key NOT NULL,
    user_id INTEGER REFERENCES users (id) ON DELETE CASCADE,
    date    timestamp          NOT NULL DEFAULT now()
);

DROP TABLE IF exists public.product_cart;
CREATE TABLE product_cart
(
    cart_id    INTEGER REFERENCES cart (id) ON DELETE CASCADE,
    product_id INTEGER REFERENCES product (id) ON DELETE CASCADE
);

DROP TABLE IF exists public.orders;
CREATE TABLE orders
(
    id          serial primary key NOT NULL,
    date        timestamp          NOT NULL DEFAULT now(),
    cart_id     INTEGER REFERENCES cart (id) ON DELETE CASCADE,
    user_id     INTEGER REFERENCES users (id) ON DELETE CASCADE,
    status      varchar(20)                 default null,
    total_price int                not NULL
);






