CREATE OR REPLACE FUNCTION CALCULATE_BOOK_RATING_FUNCTION(a_book_id bigint) RETURNS double precision
    LANGUAGE plpgsql AS
$$
DECLARE
    count_all  int;
    count_cart int;
    count_kept int;
BEGIN
    count_all := count(*) FROM book2user WHERE book_id = a_book_id;
    if count_all = 0 then
        RETURN 0;
    end if;
    count_cart := count(*) FROM book2user WHERE book_id = a_book_id and type = 'CART';
    count_kept := count(*) FROM book2user WHERE book_id = a_book_id and type = 'KEPT';
    RETURN count_all + (0.7 * count_cart) + (0.4 * count_kept);
END
$$;