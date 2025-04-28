#!/bin/bash

plik="wyniki2.csv"
echo "k;n;cmp;swap" > $plik

m=50 # liczba powtórzeń

for n in $(seq 1000 1000 50000); do
    # Wygeneruj dane RAZ dla danego n
    java DataGenerator l $n > input.txt

    for ktype in first middle last; do
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
        for i in $(seq 1 $m); do
            output=$(cat input.txt | java Main $effective_k)
            cmp=$(echo "$output" | grep "Comparisons" | awk '{print $2}')
            swap=$(echo "$output" | grep "Swaps" | awk '{print $2}')
            total_cmp=$((total_cmp + cmp))
            total_swap=$((total_swap + swap))
        done
        avg_cmp=$((total_cmp / m))
        avg_swap=$((total_swap / m))
        echo "$ktype;$n;$avg_cmp;$avg_swap" >> $plik
        echo "Done: ktype=$ktype (k=$effective_k) n=$n"
    done
done
