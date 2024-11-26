cmake_minimum_required(VERSION 3.22.1)

add_library( # Имя библиотеки
        native_lib
        # Тип библиотеки
        SHARED
        # Укажите путь к вашему файлу .c
        native_lib.c
        validate_cat.c
        sort.c)

# Линкуем библиотеку логирования
find_library( log-lib log )
target_link_libraries( native_lib ${log-lib} )
