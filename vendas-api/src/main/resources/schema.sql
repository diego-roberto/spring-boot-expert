create schema `vendas_db`;

use `vendas_db`;

create table `clients` (
    id   bigint auto_increment not null,
    name varchar(100)          null,
    cpf  varchar(11)           null,
    constraint pk_clients primary key (id)
);

create table `products` (
    id bigint auto_increment    not null,
    description varchar(255)    null,
    price_unit    decimal       null,
    constraint pk_products primary key (id)
);

create table `orders` (
    id         bigint auto_increment not null,
    client_id  bigint                null,
    order_date date                  null,
    total      decimal(20, 2)        null,
    status     varchar(255)          null,
    constraint pk_orders primary key (id)
);

alter table `orders`
    add constraint fk_orders_on_clients foreign key (client_id) references `clients` (id);

create table `order_items` (
    id         bigint auto_increment not null,
    order_id   bigint                null,
    product_id bigint                null,
    quantity   int                   null,
    constraint pk_order_items primary key (id)
);

alter table `order_items`
    add constraint fk_order_item_on_orders foreign key (order_id) references `orders` (id);

alter table `order_items`
    add constraint fk_order_items_on_products foreign key (product_id) references `products` (id);

create table `users` (
    id       int auto_increment not null,
    login    varchar(255)       null,
    password varchar(255)       null,
    admin    bit(1)             null,
    constraint pk_users primary key (id)
);