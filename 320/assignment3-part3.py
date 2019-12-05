#   PROBLEM DESCRIPTION

#   Compute the largest contiguous interval obtained by taking a union of some of the input intervals.

#   Sample Input:
#   4
#   1 3 0 2 3 4 
#   0 3 1 2 1 3 4 4 
#   0 2 3 4 5 6 3 6 2 4 
#   1 1 1 2 1 3 1 4 1 5

#   Sample output 

#   4
#   3
#   6
#   4

inp = str(input())   # graph size
real_results = []


for t in range(int(inp)):
    results = []
    inpp = str(input())   # vertex info
    split_inp = [int(x) for x in inpp.split()]

    t_split_inp = []
    for i in range(0,len(split_inp), 2):
        t_split_inp.append((tuple([split_inp[i], split_inp[i+1]])))
    sorted_inp = sorted(t_split_inp, key=lambda y: y[0])


    l = sorted_inp[0][0]
    h = sorted_inp[0][1]
    inp_alt = sorted_inp[1:]
    for tup in inp_alt:
        if tup[0] <= h:
            h = max(tup[1], h)
        else:
            results.append(h-l)
            l = tup[0]
            h = tup[1]

    results.append(h-l)

    real_results.append(max(results))

for i in real_results:
    print(i)
