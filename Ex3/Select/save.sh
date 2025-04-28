#!/bin/bash

plik="wyniki3.csv"
echo "k;ktype;n;cmp;swap;time" > $plik

m=5 # liczba powtórzeń

for k in 3 5 7 9; do
    for ktype in first middle last; do
        for n in $(seq 1000 1000 50000); do
            # Wygeneruj dane RAZ dla danego n
            java DataGenerator l $n > input.txt

            case $ktype in
                first)
                    effective_k=1
                    ;;
                middle)
                    effective_k=$((n / 2))
                    ;;
                last)
                    effective_k=$n
                    ;;
            esac

            total_cmp=0
            total_swap=0
            total_time=0
            for i in $(seq 1 $m); do
                output=$(cat input.txt | java Main $effective_k $k)
                cmp=$(echo "$output" | grep "Comparisons" | awk '{print $2}')
                swap=$(echo "$output" | grep "Swaps" | awk '{print $2}')
                time=$(echo "$output" | grep "Time" | awk '{print $2}' | sed 's/ms//')
                total_cmp=$((total_cmp + cmp))
                total_swap=$((total_swap + swap))
                total_time=$((total_time + time))
            done

            avg_cmp=$((total_cmp / m))
            avg_swap=$((total_swap / m))
            avg_time=$((total_time / m))
            echo "$k;$ktype;$n;$avg_cmp;$avg_swap;$avg_time" >> $plik
            echo "Done: k=$k ktype=$ktype (k=$effective_k) n=$n"
        done
    done
done
