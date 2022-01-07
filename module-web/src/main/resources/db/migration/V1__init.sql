drop table if exists ANSWER;
drop table if exists BOARD;
drop table if exists CART;
drop table if exists CART_ITEM;
drop table if exists CATEGORY;
drop table if exists DELIVERY;
drop table if exists INQUIRY;
drop table if exists ITEM;
drop table if exists ITEM_OPTION;
drop table if exists ORDER_ITEM;
drop table if exists ORDERS;
drop table if exists REGISTER;
drop table if exists REVIEW;
drop table if exists SELLER;
drop table if exists USER;

create table ANSWER (
    ANSWER_NO bigint not null auto_increment,
    CREATE_DATE datetime(6),
    MODIFIED_DATE datetime(6),
    CONTENT varchar(255),
    USERNAME varchar(255),
    primary key (ANSWER_NO)
) engine=InnoDB;

create table BOARD (
    BOARD_NO bigint not null auto_increment,
    CREATE_DATE datetime(6),
    MODIFIED_DATE datetime(6),
    CONTENT varchar(1000),
    TITLE varchar(255),
    TYPE varchar(255),
    USER_NO bigint,
    primary key (BOARD_NO)
) engine=InnoDB;

create table CART (
    CART_NO bigint not null auto_increment,
    USER_ID bigint,
    primary key (CART_NO)
) engine=InnoDB;

create table CART_ITEM (
    CART_CART_NO bigint not null,
    ITEM_COUNT integer not null,
    ITEM_ID bigint,
    CART_ITEM_KEY bigint not null,
    primary key (CART_CART_NO, CART_ITEM_KEY)
) engine=InnoDB;

create table CATEGORY (
    ID bigint not null auto_increment,
    IMAGE_PATH varchar(255),
    NAME varchar(255),
    primary key (ID)
) engine=InnoDB;

create table DELIVERY (
    ID bigint not null auto_increment,
    primary key (ID)
) engine=InnoDB;

create table INQUIRY (
    INQUIRY_NO bigint not null auto_increment,
    CREATE_DATE datetime(6),
    MODIFIED_DATE datetime(6),
    CONTENT varchar(255),
    IMAGE_PATH varchar(255),
    TITLE varchar(255),
    TYPE varchar(255),
    ANSWER_NO bigint,
    USER_NO bigint,
    primary key (INQUIRY_NO)
) engine=InnoDB;

create table ITEM (
    ID bigint not null auto_increment,
    CONTENT varchar(255),
    DELIVERY varchar(255),
    EXCHANGE varchar(255),
    IMAGE_NAME varchar(255),
    IMAGE_PATH varchar(255),
    NAME varchar(255),
    PRICE integer not null,
    QUANTITY integer not null,
    REFUND varchar(255),
    CATEGORY_NO bigint,
    primary key (ID)
) engine=InnoDB;

create table ITEM_OPTION (
     ID bigint not null auto_increment,
     NAME varchar(255),
     PRICE integer not null,
     QUANTITY integer not null,
     ITEM_NO bigint,
     primary key (ID)
) engine=InnoDB;

create table ORDER_ITEM (
    ORDER_ITEM_NO bigint not null auto_increment,
    COUNT integer not null,
    PRICE integer not null,
    ITEM_NO bigint,
    ORDER_NO bigint,
    primary key (ORDER_ITEM_NO)
) engine=InnoDB;

create table ORDERS (
    ORDER_NO bigint not null auto_increment,
    DATE datetime(6),
    STATUS varchar(255),
    USER_NO bigint,
    primary key (ORDER_NO)
) engine=InnoDB;

create table REGISTER (
    ID bigint not null auto_increment,
    primary key (ID)
) engine=InnoDB;

create table REVIEW (
    REVIEW_NO bigint not null auto_increment,
    CONTENT varchar(255),
    STAR integer not null,
    TITLE varchar(255),
    ITEM_NO bigint,
    primary key (REVIEW_NO)
) engine=InnoDB;

create table SELLER (
    SELLER_NO bigint not null auto_increment,
    CREATE_DATE datetime(6),
    MODIFIED_DATE datetime(6),
    BRAND_CONTENT varchar(1000),
    BRAND_NAME varchar(255),
    EMAIL varchar(255),
    IMAGE_PATH varchar(255),
    ITEM_CONTENT varchar(1000),
    PASSWORD varchar(255),
    ROLE varchar(255),
    SELLER_STATUS varchar(255),
    primary key (SELLER_NO)
) engine=InnoDB;

create table USER (
    USER_NO bigint not null auto_increment,
    CREATE_DATE datetime(6),
    MODIFIED_DATE datetime(6),
    ADDRESS varchar(255),
    DETAIL_ADDRESS varchar(255),
    POSTCODE varchar(255),
    EMAIL varchar(255),
    MEMO varchar(255),
    NAME varchar(255),
    NICKNAME varchar(255),
    PASSWORD varchar(255),
    PHONE varchar(255),
    POINT integer not null,
    PROVIDER varchar(255),
    PROVIDER_ID varchar(255),
    ROLE varchar(255),
    primary key (USER_NO)
) engine=InnoDB;