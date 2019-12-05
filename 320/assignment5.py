#   ASSIGNMENT DESCRIPTION

#   Problem: Necklace Jewel Distribution
#   We have a King with several Necklaces that he wants to split-up and divide the jewel stones to his two daughters as 
#   fairly as possible. Each jewel on an necklace has a fixed value and he has decided to let one daughter pick a subset 
#   of the jewels then the other daughter take the remaining. The “fairness idea’ that the King decides to use is to 
#   restrict the first daughter to pick only a subset of jewels that are not adjacent to any others in that set. The jewels
#   are in a circular pattern in index positions 0, 1, 2, . . . , n − 1. Where if the first daughter selects jewel at 
#   position i then she can not take jewel at position i − 1 or i + 1 (modulo n). The maximum number of jewels per necklace 
#   is one million.

#   An input (taken from keyboard) for you to process is a sequence of necklaces, one per line. Each necklaces is a sequence
#   of positive integers separated by white spaces denoting the values of each jewel.
#   The printed output of your program, one line per each scenario, is the maximum value daughter one can obtain and the 
#   remaining value the other daughter receives for each necklace.

#   Sample Input:
#   4 10 8
#   10 4 8 5
#   3 2 10 2 3 7 
#   1 1 1 1 1

#   Sample Output
#   10 12 
#   18 9 
#   17 10 
#   2 3

from sys import stdin

for line in stdin:
    line = line [:-1]
    jewels = lst_int = [int(x) for x in line.split()]
    if jewels[-1] == '':
        jewels = jewels[:-1]
    tot_val = sum(jewels)

    length = len(jewels)
    if length is 0:
        first_s =  0
    elif length is 1:
        first_s = jewels[0]
    elif length is 2:
        first_s = max(jewels[1], jewels[0])
    else:
        to_try = [jewels[0: length - 1], jewels[1: length]]
        maxes = []
        for jewel_list in to_try:
            length = len(jewel_list)
            tbl = [0 for x in range(length)]
            tbl[0] = jewel_list[0]
            tbl[1] = max(jewel_list[1], tbl[0])
            if not jewel_list:
                to_add = 0
            elif length is 1:
                to_add = jewel_list[0]
            elif length is 2:
                to_add = max(jewel_list[0], jewel_list[1])
            else:
                for i in range(2, length):
                    tbl[i] = max(tbl[i - 2] + jewel_list[i], tbl[i - 1])
                to_add = tbl[-1]
            maxes.append(to_add)
        first_s = max(maxes)
    print(first_s, tot_val - first_s)

