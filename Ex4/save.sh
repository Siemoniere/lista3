#!/bin/bash

plik="wyniki.csv"
echo "n;x;cmp;time" > $plik  # Nagłówki w pliku CSV

m=5  # liczba powtórzeń

# Najpierw wszystkie n dla x = 1
for x_type in 1 n '2n-1' '5n'; do
    for n in $(seq 1000 1000 100000); do
        # Wygeneruj dane dla danego n
        java DataGenerator r $n > input.txt

        # Oblicz odpowiednie x w zależności od typu
        case "$x_type" in
            1)
                x=1
                ;;
            n)
                x=$n
                ;;
            '2n-1')
                x=$((2 * n - 1))
                ;;
            '5n')
                x=$((5 * n))
                ;;
        esac

        # Sumujemy wyniki z m powtórzeń
        total_cmp=0
        total_time=0
        for i in $(seq 1 $m); do
            output=$(cat input.txt | java Main $x)

            cmp=$(echo "$output" | grep "Comparisons" | awk '{print $2}')
            time=$(echo "$output" | grep "Time" | awk '{print $2}')

            total_cmp=$((total_cmp + cmp))
            total_time=$((total_time + time))
        done

        avg_cmp=$((total_cmp / m))
        avg_time=$((total_time / m))

        echo "$n;$x;$avg_cmp;$avg_time" >> $plik
        echo "Done: n=$n x=$x"
    done
done
