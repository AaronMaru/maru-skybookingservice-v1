    SELECT hpc.destination_code as destinationCode
    FROM hotel_popular_city hpc
    WHERE hpc.type  = :type ;