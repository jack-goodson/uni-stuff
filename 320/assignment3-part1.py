# PROBLEM DESCRIPTION

# Determine the maximum number of non-overlapping intervals.
#   Sample Input:
#   4
#   1 3 0 2 3 4 
#   0 3 1 2 1 3 4 4 
#   0 2 3 4 5 6 3 6 2 4 
#   1 1 1 2 1 3 1 4 1 5

#   Sample output 

#   2
#   2
#   3
#   1


inp = str(input())   # graph size
results = []
for t in range(int(inp)):
    inpp = str(input())   # vertex info
    split_inp = [int(x) for x in inpp.split()]
    t_split_inp = []
    for i in range(0,len(split_inp), 2):
        t_split_inp.append((tuple([split_inp[i], split_inp[i+1]])))
    sorted_inp = sorted(t_split_inp, key=lambda y: y[1])
    n_overlap = []
    for tup in sorted_inp:
        if len(n_overlap) == 0:
            n_overlap.append(tup)
        else:
            if n_overlap[-1][1] < tup[0]:
                n_overlap.append(tup)
    results.append(len((n_overlap)))
for i in results:
    print(i)
