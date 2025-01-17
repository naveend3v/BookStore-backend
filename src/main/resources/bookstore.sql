-- create a user
CREATE USER 'book-user'@'%' IDENTIFIED BY 'bookstore';

-- create a database
CREATE DATABASE  IF NOT EXISTS `bookstore`;

-- Grant privilege to user
GRANT ALL PRIVILEGES ON bookstore.* TO 'book-user'@'%';
FLUSH PRIVILEGES;

-- create a table
CREATE TABLE `bookslist` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `book_name` VARCHAR(255) DEFAULT NULL, 
  `author` VARCHAR(100) DEFAULT NULL,     
  `price` DECIMAL(10,2) DEFAULT NULL,    
  `published_date` DATE DEFAULT NULL,
  `category` VARCHAR(100) DEFAULT NULL,  
  `image_path` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

-- updating tables with values

INSERT INTO bookslist (book_name, author, price, published_date, category, image_path) VALUES
('The Happiness Trap', 'Russ Harris', 8.34, '2022-07-05', 'Medical', 'dataset/Medical/0000001.jpg'),
('The Man Who Mistook His Wife for a Hat', 'Oliver Sacks', 5.92, '1985-10-02', 'Medical', 'dataset/Medical/0000002.jpg'),
('The 8-week Blood Sugar Diet', 'Michael Mosley', 8.85, '2015-12-28', 'Medical', 'dataset/Medical/0000003.jpg'),
('The Clever Guts Diet', 'Michael Mosley', 8.92, '2017-05-18', 'Medical', 'dataset/Medical/0000004.jpg'),
('12 Rules for Life', 'Jordan B. Peterson', 18.31, '2018-01-23', 'Medical', 'dataset/Medical/0000005.jpg'),
('The Brain That Changes Itself', 'Norman Doidge', 11.03, '2007-05-15', 'Medical', 'dataset/Medical/0000006.jpg'),
('The Psychopath Test', 'Jon Ronson', 8.23, '2011-05-12', 'Science-Geography', 'dataset/Science-Geography/0000001.jpg'),
('This is Going to Hurt', 'Adam Kay', 7.6, '2017-09-07', 'Biography', 'dataset/Biography/0000001.jpg'),
('When Breath Becomes Air', 'Paul Kalanithi', 9.05, '2016-01-12', 'Biography', 'dataset/Biography/0000002.jpg'),
('Man\'s Search For Meaning', 'Viktor E. Frankl', 9.66, '1946-01-01', 'Biography', 'dataset/Biography/0000003.jpg'),
('Can\'t Hurt Me', 'David Goggins', 19.37, '2018-11-13', 'Biography', 'dataset/Biography/0000004.jpg'),
('One Up On Wall Street', 'Peter Lynch', 10.95, '1989-03-13', 'Biography', 'dataset/Biography/0000005.jpg'),
('Fantastic Beasts and Where to Find Them', 'J. K. Rowling', 11.05, '2001-03-01', 'Entertainment', 'dataset/Entertainment/0000001.jpg'),
('Born to Run', 'Bruce Springsteen', 27.12, '2009-08-29', 'Entertainment', 'dataset/Entertainment/0000002.jpg'),
('Darth Vader and Son', 'Jeffrey Brown', 9.61, '2012-04-18', 'Entertainment', 'dataset/Entertainment/0000003.jpg'),
('The Happiest Refugee', 'Anh Do', 20.87, '2010-09-01', 'Entertainment', 'dataset/Entertainment/0000004.jpg'),
('Useless Magic', 'Florence Welch', 21.07, '2018-07-05', 'Entertainment', 'dataset/Entertainment/0000005.jpg'),
('The AB Guide to Music Theory, Part I', 'Eric Taylor', 7.24, '1989-01-01', 'Entertainment', 'dataset/Entertainment/0000006.jpg'),
('Bastien Piano Basics: Piano Level 1', 'James Bastien', 6.5, '1985-01-01', 'Entertainment', 'dataset/Entertainment/0000007.jpg'),
('Total Recall', 'Arnold Schwarzenegger', 13.91, '2012-10-01', 'Entertainment', 'dataset/Entertainment/0000008.jpg'),
('The Subtle Art of Not Giving a F*ck', 'Mark Manson', 15.8, '2016-09-13', 'Personal-Development', 'dataset/Personal-Development/0000001.jpg'),
('How to Win Friends and Influence People', 'Dale Carnegie', 8.3, '1936-10-01', 'Personal-Development', 'dataset/Personal-Development/0000002.jpg'),
('Thinking, Fast and Slow', 'Daniel Kahneman', 11.5, '2011-10-25', 'Personal-Development', 'dataset/Personal-Development/0000003.jpg'),
('The 4-Hour Work Week', 'Timothy Ferriss', 14.32, '2007-04-24', 'Personal-Development', 'dataset/Personal-Development/0000004.jpg'),
('Rich Dad Poor Dad', 'Robert T. Kiyosaki', 7.34, '1997-04-01', 'Personal-Development', 'dataset/Personal-Development/0000005.jpg'),
('Atomic Habits', 'James Clear', 15.49, '2018-10-16', 'Personal-Development', 'dataset/Personal-Development/0000006.jpg'),
('Deep Work', 'Cal Newport', 11.54, '2016-01-05', 'Personal-Development', 'dataset/Personal-Development/0000007.jpg'),
('The Richest Man In Babylon', 'George S. Clason', 8.07, '1926-01-01', 'Personal-Development', 'dataset/Personal-Development/0000008.jpg'),
('Tools of Titans', 'Timothy Ferriss', 18.23, '2016-12-06', 'Personal-Development', 'dataset/Personal-Development/0000009.jpg'),
('Think and Grow Rich', 'Napoleon Hill', 9.06, '1937-01-01', 'Personal-Development', 'dataset/Personal-Development/0000010.jpg'),
('Meditations', 'Marcus Aurelius', 8.13, '0180-01-01', 'Personal-Development', 'dataset/Personal-Development/0000011.jpg'),
('The 5 AM Club', 'Robin Sharma', 13.43, '2018-11-16', 'Personal-Development', 'dataset/Personal-Development/0000012.jpg'),
('The Monk Who Sold his Ferrari', 'Robin Sharma', 7.25, '1997-04-21', 'Personal-Development', 'dataset/Personal-Development/0000013.jpg'),
('Arnold: the Education of a Bodybuilder', 'Arnold Schwarzenegger', 13.81, '1977-10-01', 'Sport', 'dataset/Sport/0000001.jpg');
