#   PROBLEM DESCRIPTION

#   Find the maximum number of intervals that overlap at a single point (on x-axis).

#   Sample Input:
#   4
#   1 3 0 2 3 4 
#   0 3 1 2 1 3 4 4 
#   0 2 3 4 5 6 3 6 2 4 
#   1 1 1 2 1 3 1 4 1 5

#   Sample output 

#   2
#   3
#   3
#   5

inp = str(input())   # graph size

results = []

for t in range(int(inp)):
    inpp = str(input())   # vertex info

    split_inp = [int(x) for x in inpp.split()]

    t_split_inp = []
    for i in range(0,len(split_inp), 2):

        t_split_inp.append((tuple([split_inp[i], split_inp[i+1]])))
    sorted_inp = sorted(t_split_inp, key=lambda y: y[0])
    k = []
    for tup in sorted_inp:
        if k == []:
            k.append([tup])
        else:
            for room in range(len(k)):
                room_added = False
                if k[room][-1][1] < tup[0]:
                    k[room].append(tup)
                    room_added = True
                    break
            if room_added == False:
                k.append([tup])

    results.append(len(k))

for i in results:
    print(i)









