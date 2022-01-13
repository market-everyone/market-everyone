
drop table if exists REGISTER;
drop table if exists REVIEW;
drop table if exists ORDER_ITEM;
drop table if exists ORDERS;
drop table if exists ITEM_OPTION;
drop table if exists INQUIRY;
drop table if exists DELIVERY;
drop table if exists CART_ITEM;
drop table if exists CART;
drop table if exists BOARD;
drop table if exists ITEM;
drop table if exists CATEGORY;
drop table if exists SELLER;
drop table if exists USER;
drop table if exists ANSWER;


create table ANSWER (
                ANSWER_NO bigint not null auto_increment,
                        CREATE_DATE datetime(6),
                        MODIFIED_DATE datetime(6),
                        CONTENT varchar(255),
                        USERNAME varchar(255),
                        primary key (ANSWER_NO)
);

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
);


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
);

create table CATEGORY (
                          CATEGORY_NO bigint not null auto_increment,
                          IMAGE_PATH varchar(255),
                          NAME varchar(255),
                          primary key (CATEGORY_NO)
);

create table ITEM (
                      ITEM_NO bigint not null auto_increment,
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
                      SELLER_NO bigint,
                      primary key (ITEM_NO),
                      FOREIGN KEY (CATEGORY_NO) REFERENCES CATEGORY(CATEGORY_NO),
                      FOREIGN KEY (SELLER_NO) REFERENCES SELLER(SELLER_NO)
);

create table BOARD (
                       BOARD_NO bigint not null auto_increment,
                       CREATE_DATE datetime(6),
                       MODIFIED_DATE datetime(6),
                       CONTENT varchar(1000),
                       TITLE varchar(255),
                       TYPE varchar(255),
                       USER_NO bigint,
                       primary key (BOARD_NO),
                       FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)
);

create table CART (
                      CART_NO bigint not null auto_increment,
                      USER_NO bigint,
                      primary key (CART_NO),
                      FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)
);

create table CART_ITEM
(
    CART_ITEM_NO            bigint  not null,
    ITEM_COUNT    integer not null,
    ITEM_NO       bigint,
    CART_ITEM_KEY bigint  not null,
    primary key (CART_ITEM_NO, CART_ITEM_KEY),
    FOREIGN KEY (ITEM_NO) REFERENCES ITEM (ITEM_NO)
);



create table DELIVERY (
                          DELIVERY_NO bigint not null auto_increment,
                          primary key (DELIVERY_NO)
);

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
                         primary key (INQUIRY_NO),
                         FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO),
                         FOREIGN KEY (ANSWER_NO) REFERENCES ANSWER(ANSWER_NO)
);



create table ITEM_OPTION (
                             ITEM_OPTION_NO bigint not null auto_increment,
                             NAME varchar(255),
                             PRICE integer not null,
                             QUANTITY integer not null,
                             ITEM_NO bigint,
                             primary key (ITEM_OPTION_NO),
                             FOREIGN KEY (ITEM_NO) REFERENCES ITEM(ITEM_NO)
);
create table ORDERS (
                        ORDER_NO bigint not null auto_increment,
                        DATE datetime(6),
                        STATUS varchar(255),
                        USER_NO bigint,
                        primary key (ORDER_NO),
                        FOREIGN KEY (USER_NO) REFERENCES USER(USER_NO)
);

create table ORDER_ITEM (
                            ORDER_ITEM_NO bigint not null auto_increment,
                            COUNT integer not null,
                            PRICE integer not null,
                            ITEM_NO bigint,
                            ORDER_NO bigint,
                            primary key (ORDER_ITEM_NO),
                            FOREIGN KEY (ITEM_NO) REFERENCES ITEM(ITEM_NO),
                            FOREIGN KEY (ORDER_NO) REFERENCES ORDERS(ORDER_NO)
);


create table REGISTER
(
    REGISTER_NO bigint not null auto_increment,
    primary key (REGISTER_NO)
);

create table REVIEW (
                        REVIEW_NO bigint not null auto_increment,
                        CONTENT varchar(255),
                        STAR integer not null,
                        TITLE varchar(255),
                        ITEM_NO bigint,
                        primary key (REVIEW_NO),
                        FOREIGN KEY (ITEM_NO) REFERENCES ITEM(ITEM_NO)
);