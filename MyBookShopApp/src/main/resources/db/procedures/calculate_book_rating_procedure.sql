CREATE OR REPLACE FUNCTION CALCULATE_BOOK_RATING_FUNCTION(a_book_id bigint) RETURNS double precision
    IMMUTABLE
    LANGUAGE plpgsql AS
$$
DECLARE
    count  int;
    sum_value  int;
BEGIN
    count := count(*) FROM rating_book WHERE book_id = a_book_id;
    if count = 0 then
        RETURN 0;
    end if;
    sum_value := sum(value) FROM rating_book WHERE book_id = a_book_id;
    if sum_value = 0 then
        RETURN 0;
    end if;
    RETURN sum_value / count;
END
$$;
