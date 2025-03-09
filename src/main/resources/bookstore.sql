-- create a user
CREATE USER IF NOT EXISTS 'bookuser'@'%' IDENTIFIED BY 'bookstore';

-- create a database
CREATE DATABASE IF NOT EXISTS `bookstore`;

-- Grant privilege to user
GRANT ALL PRIVILEGES ON bookstore.* TO 'bookuser'@'%';
FLUSH PRIVILEGES;

-- create a table
CREATE TABLE IF NOT EXISTS `bookslist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_name` VARCHAR(255) DEFAULT NULL,
  `author` VARCHAR(100) DEFAULT NULL,
  `description` TEXT DEFAULT NULL,
  `price` DECIMAL(10,2) DEFAULT NULL,
  `published_date` DATE DEFAULT NULL,
  `category` VARCHAR(100) DEFAULT NULL,
  `image_path` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- updating tables with values

INSERT INTO bookslist (book_name, author, description, price, published_date, category, image_path) VALUES
('The Happiness Trap', 'Russ Harris',
'Are you, like millions of Americans, caught in the happiness trap?\n
Russ Harris explains that the way most of us go about trying to find happiness ends up making us miserable, driving the epidemics of stress, anxiety, and depression.\n\n
This empowering book presents the insights and techniques of ACT (Acceptance and Commitment Therapy), a revolutionary new psychotherapy based on cutting-edge research in behavioral psychology.\n\n
By clarifying your values and developing mindfulness (a technique for living fully in the present moment), ACT helps you escape the happiness trap and find true satisfaction in life.',
8.34, '2022-07-05', 'Medical', 'https://myspringbootapp25.s3.us-east-1.amazonaws.com/1738851449912_book-1.jpg'),
('The Psychology of Money', 'Morgan Housel',
'Doing well with money isn''t necessarily about what you know. It''s about how you behave. And behavior is hard to teach, even to really smart people.\n\n
Money—investing, personal finance, and business decisions—is typically taught as a math-based field, where data and formulas tell us exactly what to do.\n\n
But in the real world, people don''t make financial decisions on a spreadsheet. They make them at the dinner table, or in a meeting room, where personal history, your own unique view of the world, ego, pride, marketing, and odd incentives are scrambled together.\n\n
In *The Psychology of Money*, award-winning author Morgan Housel shares **19 short stories** exploring the strange ways people think about money and teaches you how to make better sense of one of life''s most important topics.',
14.5, '2025-01-01', 'Self-Help', 'https://myspringbootapp25.s3.us-east-1.amazonaws.com/1738851466051_book-2.jpg'),
('12 Rules for Life', 'Jordan B. Peterson',
'What does everyone in the modern world need to know?\n
Renowned psychologist Jordan B. Peterson''s answer to this most difficult of questions uniquely combines the hard-won truths of ancient tradition with the stunning revelations of cutting-edge scientific research.\n\n
**Key Insights:**\n
- Why skateboarding boys and girls must be left alone.\n
- What terrible fate awaits those who criticize too easily.\n
- Why you should always pet a cat when you meet one on the street.\n\n
Dr. Peterson journeys broadly, discussing discipline, freedom, adventure, and responsibility, distilling the world''s wisdom into **12 practical and profound rules for life**.',
18.31, '2018-01-23', 'Medical', 'https://myspringbootapp25.s3.us-east-1.amazonaws.com/1738851478411_book-3.jpg'),
('The 4-Hour Work Week', 'Timothy Ferriss',
'Forget the old concept of retirement and the rest of the deferred-life plan—there is no need to wait and every reason not to, especially in unpredictable economic times.\n\n
This step-by-step guide to luxury lifestyle design teaches:\n
* How Tim went from **$40,000 per year and 80 hours per week** to **$40,000 per MONTH and 4 hours per week**.\n
* How to **outsource your life** to overseas virtual assistants for $5 per hour and do whatever you want.\n
* How blue-chip escape artists travel the world without quitting their jobs.\n
* How to **eliminate 50% of your work** in 48 hours using the principles of a forgotten Italian economist.\n
* How to trade a long-haul career for short work bursts and frequent "mini-retirements".\n\n
**This new updated and expanded edition includes:**\n
* More than **50 practical tips** and case studies from readers who have doubled their income.\n
* Real-world templates for **eliminating email, negotiating with bosses and clients**.\n
* The latest tools and tricks for living like a diplomat or millionaire without being either.',
14.32, '2007-04-24', 'Personal-Development', 'https://myspringbootapp25.s3.us-east-1.amazonaws.com/1738851490215_book-4.jpg'),
('Rich Dad Poor Dad', 'Robert T. Kiyosaki',
'A mini abridgment of the **#1 Personal Finance book of all time**.\n\n
Wisdom from *Rich Dad Poor Dad* tells the story of Robert Kiyosaki and his two dads—his real father and the father of his best friend, his rich dad—and the ways in which both men shaped his thoughts about investing.\n\n
**Key Takeaways:**\n
- You don''t need to earn a high income to be rich.\n
- Understand the difference between **working for money and having your money work for you**.',
7.34, '1997-04-01', 'Personal-Development', 'https://myspringbootapp25.s3.us-east-1.amazonaws.com/1738851504021_book-5.jpg');