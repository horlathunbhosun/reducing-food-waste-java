## Project Topic : Reducing Food Waste


### Project Description
# Context

The startup's goal is to create a smartphone application that eliminates the issue of food waste by providing a platform for businesses (restaurants, cafés, and food trucks) to sell extra, but completely edible, food to environmentally conscientious individuals known as "waste warriors." The businesses, referred to as partners, will sell bags of leftover food, dubbed "magic bags," via the app. Waste warriors can purchase these magical bags at a bargain, but the contents are undisclosed until they choose the bag. Waste warriors can rate partners after each transaction to encourage participation and obtain feedback. Some limitations are in place, including restricting a waste warrior to one purchase per day with a specific partner and preventing the purchase of multiple bags from the same partner on the same day.

## Data Dictionary

For this system, six tables are defined:

### 1. `users`

This table holds information about both waste warriors and partners.

- `user_id`: Unique identifier for each user.
- `username`: User's chosen username.
- `user_type`: Indicates whether the user is a waste warrior or a partner.
- Additional user-related information.

**User Table**

| Attribute           | Information                                      | Type                  |
|---------------------|--------------------------------------------------|-----------------------|
| User Id             | Unique identifier for each user                 | Integer (Primary Key)|
| Name                | Name of the user                                  | Character (Max 30)   |
| Email               | Email of the user                                 | Character (Max 30)   |
| Phone Number        | Phone number of the user                          | Character (Max 12)   |
| User Type           | Identify user type (partner or waste_warrior)    | ENUM (Max 120)       |
| Date of Registration| Date the user registered                          | DATE                 |


### 2. `partners`

This table contains details about the partners responsible for selling magic bags.

- `partner_id`: Unique identifier for each partner.
- `user_id`: Foreign key referencing the `users` table.
- Additional partner-related information.

**Partners Table**

| Attribute                | Information                           | Type                   |
|--------------------------|---------------------------------------|------------------------|
| Partner Id               | Unique identifier for each partner    | Integer (Primary Key) |
| Business Registration No | Company registration number           | Character (Max 30)    |
| Partner Company Logo     | Partner company logo                  | Character (Max 30)    |
| Partner Address          | Address of the company                 | Character (Max 30)    |
| Date                     | Date partner was created               | DATE                  |


### 3. `magicbags`

This table stores information about the magic bags to be sold by partners.

- `magicbag_id`: Unique identifier for each magic bag.
- `partner_id`: Foreign key referencing the `partners` table.
- `price`: Cost of the magic bag.

**Magic Bags Table**

| Attribute         | Information                                   | Type                  |
|-------------------|-----------------------------------------------|-----------------------|
| Magic Bag Id      | Unique identifier for each magic bag           | Integer (Primary Key)|
| Partner Id        | Foreign key mapping to the Partners table      | Integer (Foreign Key)|
| Bag Price         | Cost of the bag                                | DECIMAL               |
| Date              | Date the magic bag was created                | DATE                  |


### 4. `transactions`

This table records the transactions made by waste warriors.

- `transaction_id`: Unique identifier for each transaction.
- `user_id`: Foreign key referencing the `users` table.
- `magicbag_id`: Foreign key referencing the `magicbags` table.
- `purchase_date`: Date and time of the purchase.
- Additional transaction-related information.

**Transactions Table**

| Attribute         | Information                                   | Type                  |
|-------------------|-----------------------------------------------|-----------------------|
| Transaction Id    | Unique identifier for each transaction        | Integer (Primary Key)|
| User Id           | Foreign key mapping to the Users table         | Integer (Foreign Key)|
| Magic Bag Id      | Foreign key mapping to the Magic Bags table    | Integer (Foreign Key)|
| Transaction Date  | Date the transaction was created               | DATE                  |
| Amount            | Price of the purchased bag                     | DECIMAL               |
| Pick Up Date      | Date the user picked up the magic bag          | DATE                  |


### 5. `feedback`

This table is used to evaluate the magic bags sold by partners.

- `feedback_id`: Unique identifier for each feedback entry.
- `partner_id`: Foreign key referencing the `partners` table.
- `user_id`: Foreign key referencing the `users` table.
- `rating`: Numerical rating given by the waste warrior.
- `comments`: Additional comments provided by the waste warrior.


**Feedback/Rating Table**

| Attribute         | Information                                   | Type                  |
|-------------------|-----------------------------------------------|-----------------------|
| Feedback Id       | Unique identifier for each feedback            | Integer (Primary Key)|
| User Id           | Foreign key mapping to the Users table         | Integer (Foreign Key)|
| Transaction Id    | Foreign key mapping to the Transactions table  | Integer (Foreign Key)|
| Rating            | Rating field                                  | INTEGER               |
| Comment           | Comments for rating                           | Character             |
| Date              | Date of the rating                             | DATE                  |


### 6. `products`

This table consists of the individual items contained in the magic bags.

- `product_id`: Unique identifier for each product.
- `magicbag_id`: Foreign key referencing the `magicbags` table.
- `product_name`: Name of the product.
- Additional product-related information.
  **Product Table**

| Attribute         | Information                                   | Type                  |
|-------------------|-----------------------------------------------|-----------------------|
| Product Id        | Unique identifier for each product             | Integer (Primary Key)|
| Product Name      | Name of the product in a magic bag             | Character             |
| Created At        | Date of the product                            | DATE                  |




**Relational Schema**

User(#user_id: integer, name: string, email: string, phone_number: varchar, user_type: enum, date_created: date)

Partners(#partner_id: integer, brn: integer, logo: blob, address: string, created_at: date, ptn_user → Users)

Product(#prd_id: integer, product_name: string, date_created: date)

MagicBag(#mgb_id: integer, bag_price: double, date_created: date, mag_bag_ptn → Partners)

has(#mgb_id → MagicBag, #prd_id → Product, quantity: integer)

Transaction(#trans_id: integer, amount: double, pick_up_date: date, trans_date: date, user_trn → User, mag_bag_trn → MagicBag)

Feedback(#fdb_id: integer, rating: integer, comment: string, date_added: date, trans_id → Transaction)
