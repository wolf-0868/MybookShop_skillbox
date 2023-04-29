CREATE OR REPLACE PROCEDURE CALCULATE_BOOK_RATING_PROCEDURE(IN book_id_in BIGINT, OUT rating_out DOUBLE PRECISION)
    LANGUAGE plpgsql AS
$$
DECLARE
    count_all  int;
    count_cart int;
    count_kept int;
BEGIN
    count_all := count(*) FROM book2user WHERE book_id = book_id_in;
    IF count_all = 0 THEN
        rating_out := 0;
    ELSE
        count_cart := count(*) FROM book2user WHERE book_id = book_id_in and type = 'CART';
        count_kept := count(*) FROM book2user WHERE book_id = book_id_in and type = 'KEPT';
        rating_out := count_all + (0.7 * count_cart) + (0.4 * count_kept);
    END IF;
END
$$;