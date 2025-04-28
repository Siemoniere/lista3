#!/bin/bash

plik="wyniki.csv"
echo "n;cmp;time" > $plik  # Nagłówek CSV

m=5  # liczba powtórzeń

for n in $(seq 1000 1000 100000); do
    # Wygeneruj dane dla danego n
    java DataGenerator r $n > input.txt

    # Sumujemy wyniki z m powtórzeń
    total_cmp=0
    total_time=0
    for i in $(seq 1 $m); do
        output=$(cat input.txt | java Main)

        cmp=$(echo "$output" | grep "Comparisons" | awk '{print $2}')
        time=$(echo "$output" | grep "Time" | awk '{print $2}')

        total_cmp=$((total_cmp + cmp))
        total_time=$((total_time + time))
    done

    avg_cmp=$((total_cmp / m))
    avg_time=$((total_time / m))

    echo "$n;$avg_cmp;$avg_time" >> $plik
    echo "Done: n=$n"
done
