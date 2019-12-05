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

