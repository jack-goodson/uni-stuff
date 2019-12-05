# PROBLEM DESCRIPTION

#


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
