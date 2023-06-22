UPDATE book SET rating = calculate_book_rating_function(id), popularity = calculate_book_popularity_function(id) WHERE id > 0;
