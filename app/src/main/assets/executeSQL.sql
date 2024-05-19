INSERT INTO service (name, price) VALUES ('Popcorn', 50000);
INSERT INTO service (name, price) VALUES ('Soda', 35000);
INSERT INTO service (name, price) VALUES ('Candy', 20000);

INSERT INTO payment (name) VALUES ('VNPAY');
INSERT INTO payment (name) VALUES ('MOMO');

-- Thêm dữ liệu vào bảng bill
INSERT INTO bill (user_id, create_date, price, state, payment, service_id) 
VALUES 
(1, '16/04/2024', 130000, 1, 1, 1),
(1, '16/04/2024', 115000, 1, 2, 2),
(2, '16/04/2024', 100000, 1, 1, 3);

INSERT INTO cinema (name, address) 
VALUES ('Quang Trung galaxy', 'Địa chỉ của Quang Trung galaxy');

INSERT INTO threater (name, cinema_id) 
VALUES ('Rạp 1', 1);

INSERT INTO "show" (movie_id, threater_id, datetime) 
VALUES (1, 1, '25/04/2024 15:30:00');

INSERT INTO ticket (show_id, seatName, price, state) 
VALUES 
    (1, 'A1', 80000, 1),
    (1, 'A2', 80000, 1),
    (1, 'A3', 80000, 1);
	
INSERT INTO trans (bill_id, ticket_id)
VALUES
(1, 1),
(1, 2),
(2, 3);

INSERT INTO notify (title, content, user_id)
VALUES 
('Thông báo 1', 'Nội dung thông báo 1', 1),
('Thông báo 2', 'Nội dung thông báo 2', 1),
('Thông báo 3', 'Nội dung thông báo 3', 2);



CREATE VIEW viewTicketAndBill_id
AS
	SELECT ticket.id, show_id, seatName, price, state, bill_id
	FROM trans INNER JOIN ticket
	ON trans.ticket_id = ticket.id;
	
	
CREATE VIEW viewHistories
AS
	SELECT DISTINCT show.bill_id, movie.name, movie_type.name as type, movie.requiredAge as age, cinema.name as cinema, threater.name as threater, show.datetime, movie.image, price as bill_price
	FROM 
		movie 
		INNER JOIN
			(SELECT show.id, show.movie_id, show.threater_id, show.datetime, bill_id, price
			FROM viewTicketAndBill_id INNER JOIN show
			ON viewTicketAndBill_id.show_id = show.id) as show 
		ON show.movie_id = movie.id
		INNER JOIN
			movie_type
		ON movie.type_id = movie_type.id
		INNER JOIN
			threater
		ON show.threater_id = threater.id
		INNER JOIN
			cinema
		ON threater.cinema_id = cinema.id;
		
INSERT INTO seat (threater_id, name)
SELECT threater_id, seat_name
FROM (
    SELECT 2 AS threater_id, 'A1' AS seat_name UNION ALL
    SELECT 2, 'A2' UNION ALL
    SELECT 2, 'A3' UNION ALL
    SELECT 2, 'A4' UNION ALL
    SELECT 2, 'A5' UNION ALL
    SELECT 2, 'A6' UNION ALL
    SELECT 2, 'A7' UNION ALL
    SELECT 2, 'A8' UNION ALL
    SELECT 2, 'B1' UNION ALL
    SELECT 2, 'B2' UNION ALL
    SELECT 2, 'B3' UNION ALL
    SELECT 2, 'B4' UNION ALL
    SELECT 2, 'B5' UNION ALL
    SELECT 2, 'B6' UNION ALL
    SELECT 2, 'B7' UNION ALL
    SELECT 2, 'B8' UNION ALL
    SELECT 2, 'C1' UNION ALL
    SELECT 2, 'C2' UNION ALL
    SELECT 2, 'C3' UNION ALL
    SELECT 2, 'C4' UNION ALL
    SELECT 2, 'C5' UNION ALL
    SELECT 2, 'C6' UNION ALL
    SELECT 2, 'C7' UNION ALL
    SELECT 2, 'C8' UNION ALL
    SELECT 2, 'D1' UNION ALL
    SELECT 2, 'D2' UNION ALL
    SELECT 2, 'D3' UNION ALL
    SELECT 2, 'D4' UNION ALL
    SELECT 2, 'D5' UNION ALL
    SELECT 2, 'D6' UNION ALL
    SELECT 2, 'D7' UNION ALL
    SELECT 2, 'D8' UNION ALL
    SELECT 2, 'E1' UNION ALL
    SELECT 2, 'E2' UNION ALL
    SELECT 2, 'E3' UNION ALL
    SELECT 2, 'E4' UNION ALL
    SELECT 2, 'E5' UNION ALL
    SELECT 2, 'E6' UNION ALL
    SELECT 2, 'E7' UNION ALL
    SELECT 2, 'E8'
) AS seats;

INSERT INTO seat (threater_id, name)
SELECT threater_id, seat_name
FROM (
    SELECT 1 AS threater_id, 'A1' AS seat_name UNION ALL
    SELECT 1, 'A2' UNION ALL
    SELECT 1, 'A3' UNION ALL
    SELECT 1, 'A4' UNION ALL
    SELECT 1, 'A5' UNION ALL
    SELECT 1, 'A6' UNION ALL
    SELECT 1, 'A7' UNION ALL
    SELECT 1, 'A8' UNION ALL
    SELECT 1, 'B1' UNION ALL
    SELECT 1, 'B2' UNION ALL
    SELECT 1, 'B3' UNION ALL
    SELECT 1, 'B4' UNION ALL
    SELECT 1, 'B5' UNION ALL
    SELECT 1, 'B6' UNION ALL
    SELECT 1, 'B7' UNION ALL
    SELECT 1, 'B8' UNION ALL
    SELECT 1, 'C1' UNION ALL
    SELECT 1, 'C2' UNION ALL
    SELECT 1, 'C3' UNION ALL
    SELECT 1, 'C4' UNION ALL
    SELECT 1, 'C5' UNION ALL
    SELECT 1, 'C6' UNION ALL
    SELECT 1, 'C7' UNION ALL
    SELECT 1, 'C8'
) AS seats;

INSERT INTO ticket (show_id, seatName, price, state) VALUES
(14, 'A1', 10000.0, 0),
(14, 'A2', 10000.0, 1),
(14, 'A3', 10000.0, 0),
(14, 'A4', 10000.0, 3),
(14, 'A5', 10000.0, 1),
(14, 'A6', 10000.0, 3),
(14, 'A7', 10000.0, 0),
(14, 'A8', 10000.0, 2),
(14, 'B1', 10000.0, 0),
(14, 'B2', 10000.0, 0),
(14, 'B3', 10000.0, 3),
(14, 'B4', 10000.0, 2),
(14, 'B5', 10000.0, 0),
(14, 'B6', 10000.0, 1),
(14, 'B7', 10000.0, 0),
(14, 'B8', 10000.0, 2),
(14, 'C1', 10000.0, 0),
(14, 'C2', 10000.0, 3),
(14, 'C3', 10000.0, 0),
(14, 'C4', 10000.0, 1),
(14, 'C5', 10000.0, 0),
(14, 'C6', 10000.0, 1),
(14, 'C7', 10000.0, 0),
(14, 'C8', 10000.0, 2),
(14, 'D1', 10000.0, 0),
(14, 'D2', 10000.0, 3),
(14, 'D3', 10000.0, 0),
(14, 'D4', 10000.0, 2),
(14, 'D5', 10000.0, 0),
(14, 'D6', 10000.0, 0),
(14, 'D7', 10000.0, 0),
(14, 'D8', 10000.0, 0),
(14, 'E1', 10000.0, 0),
(14, 'E2', 10000.0, 0),
(14, 'E3', 10000.0, 0),
(14, 'E4', 10000.0, 0),
(14, 'E5', 10000.0, 0),
(14, 'E6', 10000.0, 0),
(14, 'E7', 10000.0, 0),
(14, 'E8', 10000.0, 0);
INSERT INTO ticket (show_id, seatName, price, state) VALUES
(5, 'A1', 10000.0, 0),
(5, 'A2', 10000.0, 0),
(5, 'A3', 10000.0, 0),
(5, 'A4', 10000.0, 0),
(5, 'A5', 10000.0, 0),
(5, 'A6', 10000.0, 0),
(5, 'A7', 10000.0, 0),
(5, 'A8', 10000.0, 0),
(5, 'B1', 10000.0, 0),
(5, 'B2', 10000.0, 0),
(5, 'B3', 10000.0, 0),
(5, 'B4', 10000.0, 0),
(5, 'B5', 10000.0, 0),
(5, 'B6', 10000.0, 0),
(5, 'B7', 10000.0, 0),
(5, 'B8', 10000.0, 0),
(5, 'C1', 10000.0, 0),
(5, 'C2', 10000.0, 0),
(5, 'C3', 10000.0, 0),
(5, 'C4', 10000.0, 0),
(5, 'C5', 10000.0, 0),
(5, 'C6', 10000.0, 0),
(5, 'C7', 10000.0, 0),
(5, 'C8', 10000.0, 0),
(5, 'D1', 10000.0, 0),
(5, 'D2', 10000.0, 0),
(5, 'D3', 10000.0, 0),
(5, 'D4', 10000.0, 0),
(5, 'D5', 10000.0, 0),
(5, 'D6', 10000.0, 0),
(5, 'D7', 10000.0, 0),
(5, 'D8', 10000.0, 0),
(5, 'E1', 10000.0, 0),
(5, 'E2', 10000.0, 0),
(5, 'E3', 10000.0, 0),
(5, 'E4', 10000.0, 0),
(5, 'E5', 10000.0, 0),
(5, 'E6', 10000.0, 0),
(5, 'E7', 10000.0, 0),
(5, 'E8', 10000.0, 0);
